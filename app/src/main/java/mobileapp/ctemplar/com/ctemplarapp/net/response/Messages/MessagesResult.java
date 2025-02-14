package mobileapp.ctemplar.com.ctemplarapp.net.response.messages;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessagesResult {
    @SerializedName("id")
    private long id;

    @SerializedName("encryption")
    private EncryptionMessageResponse encryptionMessage;

    @SerializedName("sender")
    private String sender;

    @SerializedName("attachments")
    private List<MessageAttachment> attachments;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("sender_display")
    private UserDisplayResponse senderDisplay;

    @SerializedName("receiver_display")
    private List<UserDisplayResponse> receiverDisplay;

    @SerializedName("cc_display")
    private List<UserDisplayResponse> ccDisplay;

    @SerializedName("bcc_display")
    private List<UserDisplayResponse> bccDisplay;

    @SerializedName("reply_to_display")
    private List<UserDisplayResponse> replyToDisplay;

    @SerializedName("participants")
    private Map<String, String> participants;

    @SerializedName("has_children")
    private boolean hasChildren;

    @SerializedName("has_attachments")
    private boolean hasAttachments;

    @SerializedName("children_count")
    private int childrenCount;

    @SerializedName("subject")
    private String subject;

    @SerializedName("content")
    private String content;

    @SerializedName("receiver")
    private String[] receivers;

    @SerializedName("cc")
    private String[] cc;

    @SerializedName("bcc")
    private String[] bcc;

    @SerializedName("folder")
    private String folderName;

    @SerializedName("updated")
    private Date updatedAt;

    @SerializedName("destruct_date")
    private Date destructDate;

    @SerializedName("delayed_delivery")
    private Date delayedDelivery;

    @SerializedName("dead_man_duration")
    private Long deadManDuration;

    @SerializedName("read")
    private boolean isRead;

    @SerializedName("send")
    private boolean send;

    @SerializedName("starred")
    private boolean isStarred;

    @SerializedName("sent_at")
    private Date sentAt;

    @SerializedName("is_encrypted")
    private boolean isEncrypted;

    @SerializedName("is_subject_encrypted")
    private boolean isSubjectEncrypted;

    @SerializedName("is_protected")
    private boolean isProtected;

    @SerializedName("is_verified")
    private boolean isVerified;

    @SerializedName("is_html")
    private boolean isHtml;

    @SerializedName("is_autocrypt_encrypted")
    private boolean isAutocryptEncrypted;

    @SerializedName("isSignVerified")
    private boolean isSignVerified;

    @SerializedName("incoming_headers")
    private String incomingHeaders;

    @SerializedName("hash")
    private String hash;

    @SerializedName("spam_reason")
    private List<String> spamReason;

    @SerializedName("last_action")
    private String lastAction;

    @SerializedName("last_action_thread")
    private String lastActionThread;

    @SerializedName("last_action_parent_id")
    private long lastActionParentId;

    @SerializedName("children")
    private MessagesResult[] children;

    @SerializedName("mailbox")
    private long mailboxId;

    @SerializedName("parent")
    private String parent;


    public long getId() {
        return id;
    }

    public EncryptionMessageResponse getEncryptionMessage() {
        return encryptionMessage;
    }

    public String getSender() {
        return sender;
    }

    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserDisplayResponse getSenderDisplay() {
        return senderDisplay;
    }

    public List<UserDisplayResponse> getReceiverDisplay() {
        return receiverDisplay;
    }

    public List<UserDisplayResponse> getCcDisplay() {
        return ccDisplay;
    }

    public List<UserDisplayResponse> getBccDisplay() {
        return bccDisplay;
    }

    public List<UserDisplayResponse> getReplyToDisplay() {
        return replyToDisplay;
    }

    public Map<String, String> getParticipants() {
        return participants;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public String[] getCc() {
        return cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public String getFolderName() {
        return folderName;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDestructDate() {
        return destructDate;
    }

    public Date getDelayedDelivery() {
        return delayedDelivery;
    }

    public Long getDeadManDuration() {
        return deadManDuration;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isSend() {
        return send;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public boolean isSubjectEncrypted() {
        return isSubjectEncrypted;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public boolean isAutocryptEncrypted() {
        return isAutocryptEncrypted;
    }

    public boolean isSignVerified() {
        return isSignVerified;
    }

    public String getIncomingHeaders() {
        return incomingHeaders;
    }

    public String getHash() {
        return hash;
    }

    public List<String> getSpamReason() {
        return spamReason;
    }

    public String getLastAction() {
        return lastAction;
    }

    public String getLastActionThread() {
        return lastActionThread;
    }

    public long getLastActionParentId() {
        return lastActionParentId;
    }

    public MessagesResult[] getChildren() {
        return children;
    }

    @NonNull
    public List<MessagesResult> getChildrenAsList() {
        return children == null ? new ArrayList<>() : Arrays.asList(children);
    }

    public long getMailboxId() {
        return mailboxId;
    }

    public String getParent() {
        return parent;
    }
}
