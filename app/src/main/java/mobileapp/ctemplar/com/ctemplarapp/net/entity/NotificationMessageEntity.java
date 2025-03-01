package mobileapp.ctemplar.com.ctemplarapp.net.entity;

import java.util.Map;

import mobileapp.ctemplar.com.ctemplarapp.utils.EditTextUtils;
import timber.log.Timber;

public class NotificationMessageEntity {
    private static final String DATA_ACTION = "action";
    private static final String DATA_SUBJECT = "subject";
    private static final String DATA_FOLDER = "folder";
    private static final String DATA_SENDER = "sender";
    private static final String DATA_MESSAGE_ID = "message_id";
    private static final String DATA_PARENT_ID = "parent_id";
    private static final String DATA_IS_SUBJECT_ENCRYPTED = "is_subject_encrypted";

    private final String action;
    private final String subject;
    private final String folder;
    private final String sender;
    private final long messageID;
    private final long parentID;
    private final boolean isSubjectEncrypted;

    public NotificationMessageEntity(String action, String subject, String folder, String sender, long messageID, long parentID, boolean isSubjectEncrypted) {
        this.action = action;
        this.subject = subject;
        this.folder = folder;
        this.sender = sender;
        this.messageID = messageID;
        this.parentID = parentID;
        this.isSubjectEncrypted = isSubjectEncrypted;
    }

    public String getAction() {
        return action;
    }

    public String getSubject() {
        return subject;
    }

    public String getFolder() {
        return folder;
    }

    public String getSender() {
        return sender;
    }

    public long getMessageID() {
        return messageID;
    }

    public long getParentID() {
        return parentID;
    }

    public boolean isSubjectEncrypted() {
        return isSubjectEncrypted;
    }

    public static NotificationMessageEntity getFromMap(Map<String, String> remoteMessageData) {
        String action = remoteMessageData.get(DATA_ACTION);
        String subject = remoteMessageData.get(DATA_SUBJECT);
        String folder = remoteMessageData.get(DATA_FOLDER);
        String sender = remoteMessageData.get(DATA_SENDER);
        String messageIDString = remoteMessageData.get(DATA_MESSAGE_ID);
        String parentIDString = remoteMessageData.get(DATA_PARENT_ID);
        boolean isSubjectEncrypted = Boolean.parseBoolean(
                remoteMessageData.get(DATA_IS_SUBJECT_ENCRYPTED));

        long messageID = -1;
        if (EditTextUtils.isNotEmpty(messageIDString)) {
            try {
                messageID = Long.parseLong(messageIDString);
            } catch (NumberFormatException e) {
                Timber.w(e);
            }
        }
        long parentID = -1;
        if (EditTextUtils.isNotEmpty(parentIDString)) {
            try {
                parentID = Long.parseLong(parentIDString);
            } catch (NumberFormatException e) {
                Timber.w(e);
            }
        }

        return new NotificationMessageEntity(
                action == null ? "" : action,
                subject == null ? "" : subject,
                folder == null ? "" : folder,
                sender == null ? "" : sender,
                messageID,
                parentID,
                isSubjectEncrypted
        );
    }
}
