package mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes;

import com.google.gson.annotations.SerializedName;

public class EnabledMailboxRequest {
    @SerializedName("is_enabled")
    private boolean isEnabled;

    public EnabledMailboxRequest(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
