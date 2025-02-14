package mobileapp.ctemplar.com.ctemplarapp.repository;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mobileapp.ctemplar.com.ctemplarapp.CTemplarApp;
import mobileapp.ctemplar.com.ctemplarapp.net.RestService;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AddFirebaseTokenRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AntiPhishingPhraseRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AutoReadEmailRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.AutoSaveContactEnabledRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.CaptchaVerifyRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.ChangePasswordRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.CheckUsernameRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.DarkModeRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.DisableLoadingImagesRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.IncludeOriginalMessageRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.NotificationEmailRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.PublicKeysRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.RecoverPasswordRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.RecoveryEmailRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SignInRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SignUpRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SignatureRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SubjectEncryptedRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.SubscriptionMobileUpgradeRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.UpdateReportBugsRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.WarnExternalLinkRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.contacts.ContactsEncryptionRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.emails.UnsubscribeMailingRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.filters.EmailFilterRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.folders.EmptyFolderRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.folders.MoveToFolderRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes.CreateMailboxKeyRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes.CreateMailboxRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes.DefaultMailboxRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes.DeleteMailboxKeyRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes.EnabledMailboxRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.mailboxes.UpdateMailboxPrimaryKeyRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.messages.MarkMessageAsReadRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.messages.MarkMessageIsStarredRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.messages.SendMessageRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.response.AddFirebaseTokenResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.CaptchaResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.CaptchaVerifyResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.CheckUsernameResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.PagableResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.RecoverPasswordResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.SignInResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.SignUpResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.SubscriptionMobileUpgradeResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.domains.DomainsResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.filters.EmailFilterResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.filters.EmailFilterResult;
import mobileapp.ctemplar.com.ctemplarapp.net.response.invites.InviteCodeResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.keys.KeysResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.mailboxes.MailboxKeyResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.mailboxes.MailboxKeysResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.mailboxes.MailboxResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.mailboxes.MailboxesResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.messages.EmptyFolderResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.messages.MessageAttachment;
import mobileapp.ctemplar.com.ctemplarapp.net.response.messages.MessagesResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.messages.MessagesResult;
import mobileapp.ctemplar.com.ctemplarapp.net.response.myself.BlackListContact;
import mobileapp.ctemplar.com.ctemplarapp.net.response.myself.MyselfResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.myself.SettingsResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.myself.WhiteListContact;
import mobileapp.ctemplar.com.ctemplarapp.net.response.whiteBlackList.BlackListResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.whiteBlackList.WhiteListResponse;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.DTOResource;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.PageableDTO;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.SearchMessagesDTO;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.invites.InviteCodeDTO;
import mobileapp.ctemplar.com.ctemplarapp.repository.entity.MailboxEntity;
import mobileapp.ctemplar.com.ctemplarapp.repository.entity.MailboxKeyEntity;
import mobileapp.ctemplar.com.ctemplarapp.repository.mapper.InviteCodeMapper;
import mobileapp.ctemplar.com.ctemplarapp.repository.mapper.PageableMapper;
import mobileapp.ctemplar.com.ctemplarapp.utils.EditTextUtils;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class UserRepository {
    private static UserRepository instance = new UserRepository();
    private final UserStore userStore;
    private RestService service;

    private final MutableLiveData<DTOResource<PageableDTO<InviteCodeDTO>>> inviteCodesLiveData = new MutableLiveData<>();

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public UserRepository() {
        CTemplarApp.getRestClientLiveData().observeForever(instance
                -> service = instance.getRestService());
        userStore = CTemplarApp.getUserStore();
    }

    public RestService getRestService() {
        return service;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public void clearToken() {
        userStore.clearToken();
    }

    public void saveUserToken(String token) {
        userStore.saveUserToken(token);
    }

    public String getUserToken() {
        return userStore.getUserToken();
    }

    public boolean isAuthorized() {
        return EditTextUtils.isNotEmpty(userStore.getUserToken());
    }

    public void saveFirebaseToken(String token) {
        userStore.saveFirebaseToken(token);
    }

    public String getFirebaseToken() {
        return userStore.getFirebaseToken();
    }

    public void saveUserPassword(String password) {
        userStore.savePassword(password);
    }

    public String getUserPassword() {
        return userStore.getUserPassword();
    }

    public void saveKeepMeLoggedIn(boolean keepMeLoggedIn) {
        userStore.saveKeepMeLoggedIn(keepMeLoggedIn);
    }

    public boolean getKeepMeLoggedIn() {
        return userStore.getKeepMeLoggedIn();
    }

    public void saveUsername(String username) {
        userStore.saveUsername(username);
    }

    public String getUsername() {
        return userStore.getUsername();
    }

    public void setStoreSettings(SettingsResponse settingsResponse) {
        userStore.saveTimeZone(settingsResponse.getTimezone());
        userStore.setContactsEncryptionEnabled(settingsResponse.isContactsEncrypted());
        userStore.setAutoReadEmailEnabled(settingsResponse.isAutoRead());
        userStore.setIncludeOriginalMessage(settingsResponse.isIncludeOriginalMessage());
        userStore.setBlockExternalImagesEnabled(settingsResponse.isDisableLoadingImages());
        userStore.setWarnExternalLinkEnabled(settingsResponse.isWarnExternalLink());
        userStore.setReportBugsEnabled(settingsResponse.isEnableReportBugs());
    }

    public void setIncludeOriginalMessage(boolean value) {
        userStore.setIncludeOriginalMessage(value);
    }

    public void setPrimeDialogShown(boolean value) {
        userStore.setPrimeDialogShown(value);
    }

    public void setSignatureEnabled(boolean value) {
        userStore.setSignatureEnabled(value);
    }

    public void setAutoReadEmailEnabled(boolean value) {
        userStore.setAutoReadEmailEnabled(value);
    }

    public boolean isSignatureEnabled() {
        return userStore.isSignatureEnabled();
    }

    public boolean isDraftsAutoSaveEnabled() {
        return userStore.isDraftsAutoSaveEnabled();
    }

    public boolean isAutoReadEmailEnabled() {
        return userStore.isAutoReadEmailEnabled();
    }

    public boolean isKeepDecryptedSubjectsEnabled() {
        return userStore.isKeepDecryptedSubjectsEnabled();
    }

    public boolean isIncludeOriginalMessage() {
        return userStore.isIncludeOriginalMessage();
    }

    public boolean isPrimeDialogShown() {
        return userStore.isPrimeDialogShown();
    }

    public void clearData() {
        userStore.logout();
        CTemplarApp.getAppDatabase().clearAllTables();
        FirebaseMessaging.getInstance().deleteToken().addOnFailureListener(Timber::e);
    }

    public void saveMailbox(MailboxEntity mailbox) {
        if (mailbox == null) {
            Timber.e("Mailbox is null");
            return;
        }
        CTemplarApp.getAppDatabase().mailboxDao().save(mailbox);
    }

    public void saveMailboxes(List<MailboxEntity> mailboxes) {
        if (mailboxes == null) {
            Timber.e("Mailboxes is null");
            return;
        }
        MailboxDao mailboxDao = CTemplarApp.getAppDatabase().mailboxDao();
        mailboxDao.deleteAll();
        mailboxDao.saveAll(mailboxes);
    }

    public void saveMailboxKey(MailboxKeyEntity mailboxKey) {
        if (mailboxKey == null) {
            Timber.e("MailboxKey is null");
            return;
        }
        CTemplarApp.getAppDatabase().mailboxKeyDao().save(mailboxKey);
    }

    public void saveMailboxKeys(List<MailboxKeyEntity> mailboxKeys) {
        if (mailboxKeys == null) {
            Timber.e("Mailbox keys is null");
            return;
        }
        MailboxKeyDao mailboxKeyDao = CTemplarApp.getAppDatabase().mailboxKeyDao();
        mailboxKeyDao.deleteAll();
        mailboxKeyDao.saveAll(mailboxKeys);
    }

    // Requests
    public Observable<SignInResponse> signIn(SignInRequest request) {
        return service.signIn(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<SignUpResponse> signUp(SignUpRequest request) {
        return service.signUp(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Void>> signOut(String platform, String deviceToken) {
        return service.signOut(platform, deviceToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CheckUsernameResponse> checkUsername(CheckUsernameRequest request) {
        return service.checkUsername(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<RecoverPasswordResponse> recoverPassword(RecoverPasswordRequest request) {
        return service.recoverPassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBody> changePassword(ChangePasswordRequest request) {
        return service.changePassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<RecoverPasswordResponse> resetPassword(RecoverPasswordRequest request) {
        return service.resetPassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MessagesResponse> getMessagesList(int limit, int offset, String folder) {
        return service.getMessages(limit, offset, folder)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    public Observable<MessagesResponse> getStarredMessagesList(int limit, int offset) {
        return service.getStarredMessages(limit, offset, true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    public Observable<MessagesResponse> searchMessages(
            SearchMessagesDTO dto, int limit, int offset
    ) {
        return service.searchMessages(
                dto.getQuery(),
                dto.isExact(),
                dto.getFolder(),
                dto.getSender(),
                dto.getReceiver(),
                dto.isHaveAttachment(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getSize(),
                dto.getSizeOperator(),
                limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    public Observable<MessagesResponse> getMessage(long id) {
        return service.getMessage(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public MutableLiveData<DTOResource<Response<Void>>> deleteMessages(Long[] messageIds) {
        MutableLiveData<DTOResource<Response<Void>>> liveData = new MutableLiveData<>();
        service.deleteMessages(TextUtils.join(",", messageIds))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Void> response) {
                        liveData.postValue(DTOResource.success(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(DTOResource.error(e));
                    }
                });
        return liveData;
    }

    public Observable<EmptyFolderResponse> emptyFolder(EmptyFolderRequest request) {
        return service.emptyFolder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Void>> toFolder(Long[] messageIds, String folder) {
        return service.toFolder(TextUtils.join(",", messageIds),
                new MoveToFolderRequest(folder))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MessagesResponse> getChainMessages(long id) {
        return service.getChainMessages(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    public Observable<Response<Void>> markMessageAsRead(Long[] messageIds, boolean isRead) {
        return service.markMessageAsRead(TextUtils.join(",", messageIds),
                new MarkMessageAsReadRequest(isRead))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Void>> markMessageIsStarred(long id, boolean starred) {
        return service.markMessageIsStarred(id, new MarkMessageIsStarredRequest(starred))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MyselfResponse> getMyselfInfo() {
        return service.getMyself()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MailboxesResponse> getMailboxes(int limit, int offset) {
        return service.getMailboxes(limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MailboxKeysResponse> getMailboxKeys(int limit, int offset) {
        return service.getMailboxKeys(limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<MailboxKeyResponse>> createMailboxKey(CreateMailboxKeyRequest request) {
        return service.createMailboxKey(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<Void>> deleteMailboxKey(long id, DeleteMailboxKeyRequest request) {
        return service.deleteMailboxKey(id, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MessagesResult> updateMessage(long id, SendMessageRequest request) {
        return service.updateMessage(id, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MessagesResult> sendMessage(SendMessageRequest request) {
        return service.sendMessage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DTOResource<MessageAttachment> uploadAttachmentSync(
            MultipartBody.Part document,
            long message,
            boolean isInline,
            boolean isEncrypted,
            String fileType,
            String name,
            long actualSize
    ) {
        try {
            return DTOResource.success(service.uploadAttachment(document, message, isInline,
                    isEncrypted, fileType, name, actualSize).blockingGet());
        } catch (Throwable e) {
            return DTOResource.error(e);
        }
    }

    public MutableLiveData<DTOResource<MessageAttachment>> uploadAttachment(
            MultipartBody.Part document,
            long message,
            boolean isInline,
            boolean isEncrypted,
            String fileType,
            String name,
            long actualSize
    ) {
        MutableLiveData<DTOResource<MessageAttachment>> liveData = new MutableLiveData<>();
        service.uploadAttachment(document, message, isInline, isEncrypted, fileType, name, actualSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MessageAttachment>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MessageAttachment response) {
                        liveData.postValue(DTOResource.success(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(DTOResource.error(e));
                    }
                });
        return liveData;
    }

    public DTOResource<KeysResponse> getEmailPublicKeysSync(Set<String> addresses) {
        try {
            return DTOResource.success(service.getKeys(new PublicKeysRequest(addresses))
                    .blockingGet());
        } catch (Throwable e) {
            return DTOResource.error(e);
        }
    }

    public DTOResource<MessagesResult> updateMessageSync(long id, SendMessageRequest request) {
        try {
            return DTOResource.success(service.updateMessage(id, request).blockingGet());
        } catch (Throwable e) {
            return DTOResource.error(e);
        }
    }

    public DTOResource<MessageAttachment> updateAttachmentSync(
            long id,
            MultipartBody.Part document,
            long messageId,
            boolean isInline,
            boolean isEncrypted,
            String fileType,
            String name,
            long actualSize
    ) {
        try {
            return DTOResource.success(service.updateAttachment(id, document, messageId, isInline,
                    isEncrypted, fileType, name, actualSize).blockingGet());
        } catch (Throwable e) {
            return DTOResource.error(e);
        }
    }

    public MutableLiveData<DTOResource<Response<Void>>> deleteAttachment(long id) {
        MutableLiveData<DTOResource<Response<Void>>> liveData = new MutableLiveData<>();
        service.deleteAttachment(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Void> response) {
                        liveData.postValue(DTOResource.success(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(DTOResource.error(e));
                    }
                });
        return liveData;
    }

    public Observable<EmailFilterResponse> getFilterList() {
        return service.getFilterList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<EmailFilterResult> createFilter(EmailFilterRequest emailFilterRequest) {
        return service.createFilter(emailFilterRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<EmailFilterResult> updateFilter(long id, EmailFilterRequest emailFilterRequest) {
        return service.updateFilter(id, emailFilterRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Void>> deleteFilter(long id) {
        return service.deleteFilter(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResponseBody> deleteBlacklistContact(BlackListContact contact) {
        return service.deleteBlacklistContact(contact.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResponseBody> deleteWhitelistContact(WhiteListContact contact) {
        return service.deleteWhitelistContact(contact.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BlackListResponse> getBlackListContacts() {
        return service.getBlackListContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BlackListContact> addBlacklistContact(BlackListContact contact) {
        return service.addBlacklistContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<WhiteListResponse> getWhiteListContacts() {
        return service.getWhiteListContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<WhiteListContact> addWhitelistContact(WhiteListContact contact) {
        return service.addWhitelistContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MailboxResponse> updateDefaultMailbox(
            long mailboxId,
            DefaultMailboxRequest request
    ) {
        return service.updateDefaultMailbox(mailboxId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MailboxResponse> updateEnabledMailbox(
            long mailboxId,
            EnabledMailboxRequest request
    ) {
        return service.updateEnabledMailbox(mailboxId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<MailboxResponse>> createMailbox(CreateMailboxRequest request) {
        return service.createMailbox(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<Void>> updateMailboxPrimaryKey(UpdateMailboxPrimaryKeyRequest request) {
        return service.updateMailboxPrimaryKey(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DomainsResponse> getDomains() {
        return service.getDomains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateRecoveryEmail(
            long settingId,
            RecoveryEmailRequest request
    ) {
        return service.updateRecoveryEmail(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateNotificationEmail(
            long settingId,
            NotificationEmailRequest request
    ) {
        return service.updateNotificationEmail(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateSubjectEncrypted(
            long settingId,
            SubjectEncryptedRequest request
    ) {
        return service.updateSubjectEncrypted(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateContactsEncryption(
            long settingId,
            ContactsEncryptionRequest request
    ) {
        return service.updateContactsEncryption(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateAutoSaveEnabled(
            long settingId,
            AutoSaveContactEnabledRequest request
    ) {
        return service.updateAutoSaveEnabled(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateAutoReadEmail(
            long settingId,
            AutoReadEmailRequest request
    ) {
        return service.updateAutoReadEmail(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<SettingsResponse> updateIncludeOriginalMessageText(
            long settingId,
            IncludeOriginalMessageRequest request
    ) {
        return service.updateIncludeOriginalMessage(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateAntiPhishingPhrase(
            long settingId,
            AntiPhishingPhraseRequest request
    ) {
        return service.updateAntiPhishingPhrase(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateDarkMode(
            long settingId,
            DarkModeRequest request
    ) {
        return service.updateDarkMode(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateDisableLoadingImages(
            long settingId,
            DisableLoadingImagesRequest request
    ) {
        return service.updateDisableLoadingImages(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateWarnExternalLink(
            long settingId,
            WarnExternalLinkRequest request
    ) {
        return service.updateWarnExternalLink(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SettingsResponse> updateReportBugs(
            long settingId,
            UpdateReportBugsRequest request
    ) {
        return service.updateReportBugs(settingId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MailboxResponse> updateSignature(long mailboxId, SignatureRequest request) {
        return service.updateSignature(mailboxId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CaptchaResponse> getCaptcha() {
        return service.getCaptcha()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CaptchaVerifyResponse> captchaVerify(CaptchaVerifyRequest request) {
        return service.captchaVerify(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<SubscriptionMobileUpgradeResponse> subscriptionUpgrade(
            SubscriptionMobileUpgradeRequest request
    ) {
        return service.subscriptionUpgrade(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AddFirebaseTokenResponse> addFirebaseToken(AddFirebaseTokenRequest request) {
        return service.addFirebaseToken(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Void>> deleteFirebaseToken(String token) {
        return service.deleteFirebaseToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public MutableLiveData<DTOResource<InviteCodeDTO>> generateInviteCode() {
        MutableLiveData<DTOResource<InviteCodeDTO>> liveData = new MutableLiveData<>();
        service.generateInviteCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<InviteCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(InviteCodeResponse response) {
                        liveData.postValue(DTOResource.success(InviteCodeMapper.map(response)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(DTOResource.error(e));
                    }
                });
        return liveData;
    }

    public MutableLiveData<DTOResource<Response<Void>>> unsubscribeMailing(long mailboxId, String mailto) {
        MutableLiveData<DTOResource<Response<Void>>> liveData = new MutableLiveData<>();
        service.unsubscribeMailing(new UnsubscribeMailingRequest(mailboxId, mailto))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Void> response) {
                        liveData.postValue(DTOResource.success(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(DTOResource.error(e));
                    }
                });
        return liveData;
    }

    public MutableLiveData<DTOResource<PageableDTO<InviteCodeDTO>>> getInviteCodesLiveData() {
        return inviteCodesLiveData;
    }

    public void getInviteCodes(int limit, int offset) {
        service.getInviteCodes(limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PagableResponse<InviteCodeResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PagableResponse<InviteCodeResponse> response) {
                        inviteCodesLiveData.postValue(DTOResource.success(
                                PageableMapper.map(InviteCodeMapper.class, response)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        inviteCodesLiveData.postValue(DTOResource.error(e));
                    }
                });
    }
}
