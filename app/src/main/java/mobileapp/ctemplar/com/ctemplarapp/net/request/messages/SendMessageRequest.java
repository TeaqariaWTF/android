package mobileapp.ctemplar.com.ctemplarapp.net.request.messages;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobileapp.ctemplar.com.ctemplarapp.net.response.messages.MessageAttachment;

public class SendMessageRequest {
    @SerializedName("encryption")
    private EncryptionMessageRequest encryptionMessage;

    @SerializedName("sender")
    private String sender;

    @SerializedName("subject")
    private String subject;

    @SerializedName("content")
    private String content;

    @SerializedName("receiver")
    private List<String> receivers;

    @SerializedName("cc")
    private List<String> cc;

    @SerializedName("bcc")
    private List<String> bcc;

    @SerializedName("folder")
    private String folder;

    @SerializedName("destruct_date")
    private Date destructDate;

    @SerializedName("delayed_delivery")
    private Date delayedDelivery;

    @SerializedName("dead_man_duration")
    private Long deadManDuration;

    @SerializedName("send")
    private boolean send;

    @SerializedName("is_encrypted")
    private boolean isEncrypted;

    @SerializedName("is_html")
    private boolean isHtml;

    @SerializedName("is_subject_encrypted")
    private boolean isSubjectEncrypted;

    @SerializedName("mailbox")
    private long mailbox;

    @SerializedName("last_action")
    private String lastAction;

    @SerializedName("parent")
    private Long parent;

    @SerializedName("attachments")
    private List<MessageAttachment> attachments;

    @SerializedName("updated")
    private Date updatedAt;

    public SendMessageRequest() {

    }

    public SendMessageRequest(String sender,
                              ArrayList<String> receivers,
                              ArrayList<String> cc,
                              ArrayList<String> bcc,
                              String folder,
                              long mailbox) {
        this.sender = sender;
        this.receivers = receivers;
        this.cc = cc;
        this.bcc = bcc;
        this.folder = folder;
        this.mailbox = mailbox;
    }

    public EncryptionMessageRequest getEncryptionMessage() {
        return encryptionMessage;
    }

    public void setEncryptionMessage(EncryptionMessageRequest encryptionMessage) {
        this.encryptionMessage = encryptionMessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Date getDestructDate() {
        return destructDate;
    }

    public void setDestructDate(Date destructDate) {
        this.destructDate = destructDate;
    }

    public Date getDelayedDelivery() {
        return delayedDelivery;
    }

    public void setDelayedDelivery(Date delayedDelivery) {
        this.delayedDelivery = delayedDelivery;
    }

    public Long getDeadManDuration() {
        return deadManDuration;
    }

    public void setDeadManDuration(Long deadManDuration) {
        this.deadManDuration = deadManDuration;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public boolean isSubjectEncrypted() {
        return isSubjectEncrypted;
    }

    public void setSubjectEncrypted(boolean subjectEncrypted) {
        isSubjectEncrypted = subjectEncrypted;
    }

    public long getMailbox() {
        return mailbox;
    }

    public void setMailbox(long mailbox) {
        this.mailbox = mailbox;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MessageAttachment> attachments) {
        this.attachments = attachments;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
