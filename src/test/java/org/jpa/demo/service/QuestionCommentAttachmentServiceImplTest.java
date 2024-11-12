package org.jpa.demo.service;

import org.jpa.demo.domain.AttachmentUploadResponse;
import org.jpa.demo.domain.QuestionCommentAttachment;
import org.jpa.demo.exception.HandledException;
import org.jpa.demo.repository.QuestionCommentAttachmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Optional;


import static java.awt.SystemColor.text;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuestionCommentAttachmentServiceImplTest {

    @InjectMocks
    private QuestionCommentAttachmentServiceImpl service;

    @Mock
    private QuestionCommentAttachmentRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadAttachment_Success() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file content".getBytes());
        when(file.getOriginalFilename()).thenReturn("file.pdf");
        when(file.getContentType()).thenReturn("application/pdf");

        AttachmentUploadResponse response = new AttachmentUploadResponse();

        when(repository.save(any(QuestionCommentAttachment.class))).thenReturn(new QuestionCommentAttachment());

        AttachmentUploadResponse result = service.uploadAttachment(file);

        assertNotNull(result);
        assertEquals(response.getAttachmentFileKey(), result.getAttachmentFileKey());
        verify(repository, times(1)).save(any(QuestionCommentAttachment.class));
    }
    @Test
    void uploadAttachment_FileValidationFailed() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file content".getBytes());
        when(file.getOriginalFilename()).thenReturn("file.pdf");
        when(file.getContentType()).thenReturn(null);

        assertThrows(HandledException.class, () -> {
            service.uploadAttachment(file);
        });
    }
    @Test
    void uploadAttachment_FileValidationFailed_FileContentType_Invalid() throws Exception {
        MultipartFile file = mock(MultipartFile.class);

        when(file.getBytes()).thenReturn("file content".getBytes());
        when(file.getOriginalFilename()).thenReturn("org-springframework.jar");
        when(file.getContentType()).thenReturn("application/java-archive");

        assertThrows(HandledException.class, () -> {
            service.uploadAttachment(file);
        });
    }
    @Test
    void uploadAttachment_FileValidationFailed_FileNull() throws Exception {
        MultipartFile file = null;
        assertThrows(HandledException.class, () -> {
            service.uploadAttachment(file);
        });
    }

    @Test
    void updateAttachment_upsertAttachment_exception() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file content".getBytes());
        when(file.getOriginalFilename()).thenReturn("file.docx");
        when(file.getContentType()).thenReturn("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        QuestionCommentAttachment attachment = new QuestionCommentAttachment();
        attachment.setAttachmentKey(1);
        when(repository.findById(1)).thenReturn(Optional.of(attachment));
        when(repository.save(any(QuestionCommentAttachment.class))).thenThrow(new RuntimeException());
        assertThrows(HandledException.class, () -> {
            service.updateAttachment(file, 1);
        });

    }
    @Test
    void updateAttachment_Success() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file content".getBytes());
        when(file.getOriginalFilename()).thenReturn("file.docx");
        when(file.getContentType()).thenReturn("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        QuestionCommentAttachment attachment = new QuestionCommentAttachment();
        attachment.setAttachmentKey(1);

        when(repository.findById(1)).thenReturn(Optional.of(attachment));
        when(repository.save(any(QuestionCommentAttachment.class))).thenReturn(attachment);

        AttachmentUploadResponse response = service.updateAttachment(file, 1);

        assertNotNull(response);
        assertEquals(1, response.getAttachmentFileKey());
        verify(repository, times(1)).save(any(QuestionCommentAttachment.class));
    }

    @Test
    void updateAttachment_InvalidAttachmentFileKey() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("file content".getBytes());
        when(file.getOriginalFilename()).thenReturn("file.ppt");
        when(file.getContentType()).thenReturn("application/vnd.ms-powerpoint");

        assertThrows(HandledException.class, () -> {
            service.updateAttachment(file, 0);
        });
    }

    @Test
    void getAttachment_Success() throws Exception {
        QuestionCommentAttachment attachment = new QuestionCommentAttachment();
        attachment.setAttachment("file content".getBytes());
        File tempFile = File.createTempFile("test", ".txt");
        attachment.setAttachmentFileName(tempFile.getName());
        when(repository.findById(1)).thenReturn(Optional.of(attachment));
        File file = service.getAttachment(1);
        assertTrue(file.exists());
        assertTrue(file.getName().contains(attachment.getAttachmentFileName().replace(".txt","")),
                "Actual Response contains the Expected response " +
                        "and cannot not be exactly matched due to Random nextLong");
    }
    @Test
    void getAttachment_FileCreationException() throws Exception {
        QuestionCommentAttachment attachment = new QuestionCommentAttachment();
        attachment.setAttachment("file content".getBytes());
        File tempFile = File.createTempFile("test", "txt");
        attachment.setAttachmentFileName(tempFile.getName());
        when(repository.findById(1)).thenReturn(Optional.of(attachment));
        assertNull(service.getAttachment(1), "Expected null due to exception handling in getAttachment");
    }

    @Test
    void getAttachment_FileNameException() throws Exception {
        when(repository.findById(1)).thenThrow(new RuntimeException("File creation exception"));
        assertNull(service.getAttachment(1), "Expected null due to exception handling in getAttachment");

    }


    @Test
    void deleteAttachment_Success() throws HandledException {
        QuestionCommentAttachment attachment = new QuestionCommentAttachment();
        when(repository.findById(1)).thenReturn(Optional.of(attachment));

        service.deleteAttachment(1);

        verify(repository, times(1)).delete(attachment);
    }


    @Test
    void deleteAttachment_NotFound() {
        when(repository.findById(1)).thenThrow(new RuntimeException("Attachment not found"));

        assertThrows(RuntimeException.class, () -> {
            service.deleteAttachment(1);
        });
    }
}
