package org.jpa.demo.service;

import lombok.extern.slf4j.Slf4j;

import org.jpa.demo.domain.AttachmentUploadResponse;
import org.jpa.demo.domain.QuestionCommentAttachment;
import org.jpa.demo.exception.HandledException;
import org.jpa.demo.repository.QuestionCommentAttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class QuestionCommentAttachmentServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionCommentAttachmentServiceImpl.class);

    @Autowired
    private QuestionCommentAttachmentRepository questionCommentAttachmentRepository;


    public AttachmentUploadResponse uploadAttachment(MultipartFile file) throws HandledException {
        LOG.info("QuestionCommentAttachmentServiceImpl::uploadAttachment Started");
        validate(file);
        return upsertAttachment(file,null);
    }


    public AttachmentUploadResponse updateAttachment(MultipartFile file,Integer attachmentFileKey) throws HandledException {
        LOG.info("QuestionCommentAttachmentServiceImpl::updateAttachment Started");
        String errMsg="";
        validate(file);
        if(Objects.isNull(attachmentFileKey) || attachmentFileKey == 0)
        {
            errMsg = "AttachmentFileKey should be non null value and greater than zero";
            LOG.error("QuestionCommentAttachmentServiceImpl::updateAttachment Ended ERROR - " + errMsg);
            throw new HandledException(errMsg, HttpStatus.BAD_REQUEST);
        }
        return upsertAttachment(file,attachmentFileKey);
    }


    public File getAttachment(Integer attachmentFileKey)
            throws HandledException {
        LOG.info("QuestionCommentAttachmentServiceImpl::getAttachment Started");

        File tmpFile = null;
        try {
            QuestionCommentAttachment attachment = getAttachmentFromDb(attachmentFileKey);
            byte[] content = attachment.getAttachment();
            String[] fileNameSplit = attachment.getAttachmentFileName().split("\\.");
            if(fileNameSplit.length != 2)
            {
                throw new RuntimeException("FileName spilt length should be 2");
            }
            tmpFile = File.createTempFile(fileNameSplit[0],"."+fileNameSplit[1]);
            Files.write(tmpFile.toPath(),content);
        }
        catch (Exception e) {
           log.error("Exception creating new Question Comment Attachment File for attachmentFileKey - "
                    +  e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOG.info("QuestionCommentAttachmentServiceImpl::getAttachment Ended - OK");
        return tmpFile;
    }


    public void deleteAttachment(Integer attachmentFileKey)  throws HandledException
    {
        QuestionCommentAttachment questionCommentAttachment = getAttachmentFromDb(attachmentFileKey);
        LOG.info("QuestionCommentAttachmentServiceImpl::deleteAttachment Attachment found in the DB for attachmentFileKey : {}",attachmentFileKey);
        questionCommentAttachmentRepository.delete(questionCommentAttachment);
    }

    private AttachmentUploadResponse upsertAttachment(MultipartFile file,Integer attachmentFileKey) throws HandledException
    {
        try {
            QuestionCommentAttachment questionCommentAttachment = buildQuestionCommentAttachment(file,attachmentFileKey);
            questionCommentAttachment = questionCommentAttachmentRepository.save(questionCommentAttachment);
            AttachmentUploadResponse response = new AttachmentUploadResponse();
            response.setAttachmentFileKey(questionCommentAttachment.getAttachmentKey());
            return response;
        }
        catch (Exception e)
        {
            String errMsg = e.getMessage();
            LOG.error("QuestionCommentAttachmentServiceImpl::upsertAttachment Ended ERROR - " + errMsg);
            throw new HandledException(errMsg, HttpStatus.BAD_REQUEST);
        }
    }

    private QuestionCommentAttachment getAttachmentFromDb(Integer attachmentFileKey)
    {
        return questionCommentAttachmentRepository.findById(attachmentFileKey)
                .orElseThrow(()->new RuntimeException("Attachment not found for attachmentFileKey : "+
                        attachmentFileKey));
    }

    private QuestionCommentAttachment buildQuestionCommentAttachment(MultipartFile file,Integer attachmentFileKey) throws IOException
    {
        QuestionCommentAttachment questionCommentAttachment = null;
        if(Objects.nonNull(attachmentFileKey))
        {
            questionCommentAttachment = getAttachmentFromDb(attachmentFileKey);
        }
        else {
            questionCommentAttachment = new QuestionCommentAttachment();
        }

        questionCommentAttachment.setAttachment(file.getBytes());
        questionCommentAttachment.setAttachmentFileName(file.getOriginalFilename());
        questionCommentAttachment.setAttachmentFileType(file.getContentType());

        return questionCommentAttachment;
    }

    private void validate(MultipartFile file) throws HandledException
    {   String errMsg = "";
        if(Objects.isNull(file) || file.isEmpty())
        {
            errMsg = "File is null or empty";
            LOG.error("QuestionCommentAttachmentServiceImpl::validate Ended ERROR - " + errMsg);
            throw new HandledException(errMsg, HttpStatus.BAD_REQUEST);
        }

        if(Objects.isNull(file.getContentType()))
        {
            errMsg = "File ContentType is null";
            LOG.error("QuestionCommentAttachmentServiceImpl::validate Ended ERROR - " + errMsg);
            throw new HandledException(errMsg, HttpStatus.BAD_REQUEST);
        }

        String contentType =file.getContentType();
        boolean isValidContentType=checkValidContentType(contentType);

        if(!isValidContentType)
        {
            LOG.error(isValidContentType+ "Invalid Attachment File Content type "+ contentType);
            errMsg = "Invalid file type. Only .pdf/.docx/.ppt allowed";
            LOG.error("QuestionCommentAttachmentServiceImpl::validate Ended ERROR - " + errMsg);
            throw new HandledException(errMsg, HttpStatus.BAD_REQUEST);
        }
        else {
            LOG.info(isValidContentType+ "Valid Attachment File Content type "+ contentType);
        }
    }

    private boolean checkValidContentType(String contentType) {
        LOG.info("QuestionCommentAttachmentServiceImpl::isValidContentType contentType - " + contentType);
        List<String> validContentTypeList = Arrays.asList(
                MediaType.APPLICATION_PDF_VALUE,
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                MediaType.IMAGE_JPEG_VALUE,
                "image/jpg",
                MediaType.IMAGE_PNG_VALUE);

        return validContentTypeList
                .stream()
                .anyMatch(validContentType->
                        validContentType.equals(contentType));
    }
}
