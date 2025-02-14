package mobileapp.ctemplar.com.ctemplarapp.settings.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mobileapp.ctemplar.com.ctemplarapp.repository.dto.DTOResource;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.PageableDTO;
import mobileapp.ctemplar.com.ctemplarapp.repository.dto.folders.CustomFolderDTO;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import mobileapp.ctemplar.com.ctemplarapp.CTemplarApp;
import mobileapp.ctemplar.com.ctemplarapp.net.ResponseStatus;
import mobileapp.ctemplar.com.ctemplarapp.net.request.filters.EmailFilterOrderListRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.request.filters.EmailFilterRequest;
import mobileapp.ctemplar.com.ctemplarapp.net.response.HttpErrorResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.filters.EmailFilterOrderListResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.filters.EmailFilterResult;
import mobileapp.ctemplar.com.ctemplarapp.net.response.filters.EmailFilterResponse;
import mobileapp.ctemplar.com.ctemplarapp.repository.ManageFoldersRepository;
import mobileapp.ctemplar.com.ctemplarapp.repository.UserRepository;
import retrofit2.HttpException;
import retrofit2.Response;
import timber.log.Timber;

import static mobileapp.ctemplar.com.ctemplarapp.utils.DateUtils.GENERAL_GSON;

public class FiltersViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final ManageFoldersRepository manageFoldersRepository;

    private final MutableLiveData<EmailFilterResponse> filtersResponse = new MutableLiveData<>();
    private final MutableLiveData<ResponseStatus> addFilterResponseStatus = new MutableLiveData<>();
    private final MutableLiveData<ResponseStatus> deleteFilterResponseStatus = new MutableLiveData<>();
    private final MutableLiveData<ResponseStatus> editFilterResponseStatus = new MutableLiveData<>();
    private final MutableLiveData<EmailFilterOrderListResponse> filterOrderListResponse = new MutableLiveData<>();
    private final MutableLiveData<String> filterOrderListErrorResponse = new MutableLiveData<>();

    MutableLiveData<EmailFilterResponse> getFiltersResponse() {
        return filtersResponse;
    }

    MutableLiveData<ResponseStatus> getAddFilterResponseStatus() {
        return addFilterResponseStatus;
    }

    MutableLiveData<ResponseStatus> getDeleteFilterResponseStatus() {
        return deleteFilterResponseStatus;
    }

    MutableLiveData<ResponseStatus> getEditFilterResponseStatus() {
        return editFilterResponseStatus;
    }

    MutableLiveData<EmailFilterOrderListResponse> getEmailFilterOrderListResponse() {
        return filterOrderListResponse;
    }

    MutableLiveData<String> getFilterOrderListErrorResponse() {
        return filterOrderListErrorResponse;
    }

    public FiltersViewModel() {
        userRepository = CTemplarApp.getUserRepository();
        manageFoldersRepository = CTemplarApp.getManageFoldersRepository();
    }

    public void getFilters() {
        userRepository.getFilterList()
                .subscribe(new Observer<EmailFilterResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull EmailFilterResponse response) {
                        filtersResponse.postValue(response);
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

    public void addFilter(EmailFilterRequest emailFilterRequest) {
        userRepository.createFilter(emailFilterRequest)
                .subscribe(new Observer<EmailFilterResult>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull EmailFilterResult response) {
                        addFilterResponseStatus.postValue(ResponseStatus.RESPONSE_COMPLETE);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        addFilterResponseStatus.postValue(ResponseStatus.RESPONSE_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void editFilter(long id, EmailFilterRequest emailFilterRequest) {
        userRepository.updateFilter(id, emailFilterRequest)
                .subscribe(new Observer<EmailFilterResult>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull EmailFilterResult response) {
                        editFilterResponseStatus.postValue(ResponseStatus.RESPONSE_COMPLETE);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        editFilterResponseStatus.postValue(ResponseStatus.RESPONSE_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteFilter(long id) {
        userRepository.deleteFilter(id)
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull Response<Void> response) {
                        deleteFilterResponseStatus.postValue(ResponseStatus.RESPONSE_COMPLETE);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        deleteFilterResponseStatus.postValue(ResponseStatus.RESPONSE_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateEmailFiltersOrder(EmailFilterOrderListRequest request) {
        manageFoldersRepository.updateEmailFiltersOrder(request)
                .subscribe(new Observer<EmailFilterOrderListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull EmailFilterOrderListResponse response) {
                        filterOrderListResponse.postValue(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                        if (e instanceof HttpException) {
                            Response<?> errorResponse = ((HttpException) e).response();
                            if (errorResponse != null && errorResponse.errorBody() != null) {
                                try {
                                    String errorBody = errorResponse.errorBody().string();
                                    HttpErrorResponse httpErrorResponse = GENERAL_GSON
                                            .fromJson(errorBody, HttpErrorResponse.class);
                                    filterOrderListErrorResponse.postValue(httpErrorResponse.getError().getError());
                                } catch (IOException | JsonSyntaxException ex) {
                                    Timber.e(ex, "Can't parse filters order error");
                                }
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<DTOResource<PageableDTO<CustomFolderDTO>>> getCustomFoldersLiveData() {
        return manageFoldersRepository.getCustomFoldersLiveData();
    }

    public void getCustomFolders(int limit, int offset) {
        manageFoldersRepository.getCustomFolders(limit, offset);
    }
}
