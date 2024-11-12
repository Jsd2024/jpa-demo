package org.jpa.demo.domain;


import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;



@Entity
@Table(name = "attachment",schema = "datcap")
public class QuestionCommentAttachment
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_key", nullable = false)
    private Integer attachmentKey;

    @Lob
    @NotNull
    @Column(name = "attachment", nullable = false)
    private byte[] attachment;

    @Column(name = "attachment_file_name", nullable = false)
    private String attachmentFileName;

    @Column(name = "attachment_file_type", nullable = false)
    private String attachmentFileType;


     public byte[] getAttachment()
     {
         return this.attachment;
     }

    public void setAttachment(byte[] attachment)
    {
         this.attachment = attachment;
    }

    public Integer getAttachmentKey() {
        return attachmentKey;
    }

    public void setAttachmentKey(Integer attachmentKey) {
        this.attachmentKey = attachmentKey;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getAttachmentFileType() {
        return attachmentFileType;
    }

    public void setAttachmentFileType(String attachmentFileType) {
        this.attachmentFileType = attachmentFileType;
    }

}
