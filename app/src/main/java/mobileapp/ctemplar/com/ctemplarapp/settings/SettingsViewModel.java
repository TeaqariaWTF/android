package mobileapp.ctemplar.com.ctemplarapp.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import mobileapp.ctemplar.com.ctemplarapp.CTemplarApp;
import mobileapp.ctemplar.com.ctemplarapp.net.ResponseStatus;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AntiPhishingPhraseRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AutoReadEmailRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AutoSaveContactEnabledRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.IncludeOriginalMessageRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.WarnExternalLinkRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.contacts.ContactsEncryptionRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.DarkModeRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.DisableLoadingImagesRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.NotificationEmailRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.RecoveryEmailRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SignatureRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SubjectEncryptedRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.UpdateReportBugsRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.response.contacts.ContactData;
import mobileapp.ctemplar.com.ctemplarapp.net.response.contacts.ContactsResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.contacts.EncryptContact;
import mobileapp.ctemplar.com.ctemplarapp.net.response.mailboxes.MailboxResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.myself.MyselfResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.myself.SettingsResponse;
import mobileapp.ctemplar.com.ctemplarapp.repository.AppDatabase;
import mobileapp.ctemplar.com.ctemplarapp.repository.ContactsRepository;
import mobileapp.ctemplar.com.ctemplarapp.repository.MessagesRepository;
import mobileapp.ctemplar.com.ctemplarapp.repository.UserRepository;
import mobileapp.ctemplar.com.ctemplarapp.repository.entity.Contact;
import mobileapp.ctemplar.com.ctemplarapp.repository.entity.ContactEntity;
import mobileapp.ctemplar.com.ctemplarapp.repository.entity.MailboxEntity;
import mobileapp.ctemplar.com.ctemplarapp.utils.EncryptUtils;
import timber.log.Timber;

import static mobileapp.ctemplar.com.ctemplarapp.utils.DateUtils.GENERAL_GSON;

public class SettingsViewModel extends ViewModel {
    private final ContactsRepository contactsRepository;
    private final UserRepository userRepository;
    private final MessagesRepository messagesRepository;
    private final AppDatabase appDatabase;

    public SettingsViewModel() {
        contactsRepository = CTemplarApp.getContactsRepository();
        userRepository = CTemplarApp.getUserRepository();
        messagesRepository = CTemplarApp.getMessagesRepository();
        appDatabase = CTemplarApp.getAppDatabase();
    }

    private final MutableLiveData<ResponseStatus> decryptionStatus = new MutableLiveData<>();
    private final MutableLiveData<ResponseStatus> updateSignatureStatus = new MutableLiveData<>();
    private final MutableLiveData<MyselfResponse> myselfResponse = new MutableLiveData<>();

    MutableLiveData<ResponseStatus> getDecryptionStatus() {
        return decryptionStatus;
    }

    LiveData<ResponseStatus> getUpdateSignatureStatus() {
        return updateSignatureStatus;
    }

    LiveData<MyselfResponse> getMySelfResponse() {
        return myselfResponse;
    }

    public List<MailboxEntity> getAllMailboxes() {
        return appDatabase.mailboxDao().getAll();
    }

    public void setSignatureEnabled(boolean isEnabled) {
        userRepository.setSignatureEnabled(isEnabled);
    }

    public boolean isSignatureEnabled() {
        return userRepository.isSignatureEnabled();
    }

    public void clearAllDecryptedSubjects() {
        messagesRepository.clearAllDecryptedSubjects();
    }

    void updateAutoSaveContactsEnabled(long settingId, boolean isEnabled) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateAutoSaveEnabled(
                settingId,
                new AutoSaveContactEnabledRequest(isEnabled)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("AutoSave contacts updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateAutoReadEmail(long settingId, boolean isEnabled) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateAutoReadEmail(
                settingId,
                new AutoReadEmailRequest(isEnabled)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        userRepository.setAutoReadEmailEnabled(isEnabled);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateIncludeOriginalMessage(long settingId, boolean isEnabled) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateIncludeOriginalMessageText(
                settingId,
                new IncludeOriginalMessageRequest(isEnabled)
        )
                .subscribe(new SingleObserver<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@androidx.annotation.NonNull SettingsResponse settingsResponse) {
                        userRepository.setIncludeOriginalMessage(isEnabled);
                    }

                    @Override
                    public void onError(@androidx.annotation.NonNull Throwable e) {
                        Timber.e(e);
                    }
                });
    }

    public void updateDarkMode(long settingId, boolean isEnabled) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateDarkMode(
                settingId,
                new DarkModeRequest(isEnabled)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SettingsResponse settingsResponse) {
                        Timber.i("Dark mode state is changed");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateDisableLoadingImages(long settingId, boolean isEnabled) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateDisableLoadingImages(
                settingId,
                new DisableLoadingImagesRequest(isEnabled)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("Disable loading images updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateWarnExternalLink(long settingId, boolean state) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateWarnExternalLink(settingId, new WarnExternalLinkRequest(state))
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.d("Warn external link updated with %s",
                                settingsResponse.isWarnExternalLink());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateReportBugs(long settingId, boolean isEnabled) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateReportBugs(
                settingId,
                new UpdateReportBugsRequest(isEnabled)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("Report bugs setting updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateSignature(long mailboxId, String displayName, String signatureText) {
        if (mailboxId == -1) {
            return;
        }
        userRepository.updateSignature(
                mailboxId,
                new SignatureRequest(displayName, signatureText)
        )
                .subscribe(new Observer<MailboxResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull MailboxResponse mailboxResponse) {
                        appDatabase.mailboxDao().updateSignature(mailboxId, displayName, signatureText);
                        updateSignatureStatus.postValue(ResponseStatus.RESPONSE_COMPLETE);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        updateSignatureStatus.postValue(ResponseStatus.RESPONSE_ERROR);
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateRecoveryEmail(long settingId, String newRecoveryEmail) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateRecoveryEmail(
                settingId,
                new RecoveryEmailRequest(newRecoveryEmail)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("Recovery email updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateNotificationEmail(long settingId, String emailAddress) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateNotificationEmail(
                settingId,
                new NotificationEmailRequest(emailAddress)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SettingsResponse settingsResponse) {
                        Timber.i("Notification email updated");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateSubjectEncryption(long settingId, boolean isSubjectEncryption) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateSubjectEncrypted(
                settingId,
                new SubjectEncryptedRequest(isSubjectEncryption)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Timber.i("Updating subject encryption");
                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("Subject encryption updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateContactsEncryption(long settingId, boolean isContactsEncryption) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateContactsEncryption(
                settingId,
                new ContactsEncryptionRequest(isContactsEncryption)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("Contacts encryption updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void decryptContacts(int offset) {
        contactsRepository.getContactsList(20, offset)
                .subscribe(new Observer<ContactsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull ContactsResponse contactsResponse) {
                        ContactData[] contacts = contactsResponse.getResults();
                        for (ContactData contactData : contacts) {
                            decryptContact(contactData);
                        }
                        if (contacts.length == 0) {
                            decryptionStatus.postValue(ResponseStatus.RESPONSE_COMPLETE);
                        } else {
                            decryptionStatus.postValue(ResponseStatus.RESPONSE_NEXT);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updateAntiPhishingPhrase(long settingId, boolean antiPhishingEnabled, String antiPhishingPhrase) {
        if (settingId == -1) {
            return;
        }
        userRepository.updateAntiPhishingPhrase(
                settingId,
                new AntiPhishingPhraseRequest(antiPhishingEnabled, antiPhishingPhrase)
        )
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull SettingsResponse settingsResponse) {
                        Timber.i("AntiPhishing phrase updated");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.w(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMyselfInfo() {
        userRepository.getMyselfInfo()
                .subscribe(new Observer<MyselfResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull MyselfResponse response) {
                        myselfResponse.postValue(response);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void decryptContact(ContactData contactData) {
        if (!contactData.isEncrypted()) {
            return;
        }
        String encryptedData = contactData.getEncryptedData();
        String decryptedData = EncryptUtils.decryptData(encryptedData);
        EncryptContact decryptedContact = GENERAL_GSON.fromJson(decryptedData, EncryptContact.class);
        if (decryptedContact == null) {
            return;
        }
        contactData.setEmail(decryptedContact.getEmail());
        contactData.setName(decryptedContact.getName());
        contactData.setAddress(decryptedContact.getAddress());
        contactData.setNote(decryptedContact.getNote());
        contactData.setPhone(decryptedContact.getPhone());
        contactData.setPhone2(decryptedContact.getPhone2());
        contactData.setProvider(decryptedContact.getProvider());
        contactData.setEncrypted(false);
        contactData.setEncryptedData("");

        contactsRepository.updateContact(contactData)
                .subscribe(new Observer<ContactData>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull ContactData contactData) {
                        ContactEntity contactEntity = Contact.fromContactDataToEntity(contactData);
                        contactsRepository.saveContact(contactEntity);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        decryptionStatus.postValue(ResponseStatus.RESPONSE_ERROR);
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
