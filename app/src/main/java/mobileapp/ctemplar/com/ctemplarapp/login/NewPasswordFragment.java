package mobileapp.ctemplar.com.ctemplarapp.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import mobileapp.ctemplar.com.ctemplarapp.BaseFragment;
import mobileapp.ctemplar.com.ctemplarapp.LoginActivityActions;
import mobileapp.ctemplar.com.ctemplarapp.R;
import mobileapp.ctemplar.com.ctemplarapp.net.ResponseStatus;
import mobileapp.ctemplar.com.ctemplarapp.utils.EditTextUtils;
import timber.log.Timber;

public class NewPasswordFragment extends BaseFragment {
    @BindView(R.id.fragment_new_password_input)
    TextInputEditText editChoose;

    @BindView(R.id.fragment_new_password_confirm_input)
    TextInputEditText editConfirm;

    @BindView(R.id.fragment_new_password_confirm_input_layout)
    TextInputLayout editConfirmLayout;

    @BindInt(R.integer.restriction_password_min)
    int PASSWORD_MIN;

    @BindInt(R.integer.restriction_password_max)
    int PASSWORD_MAX;

    private LoginActivityViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_password;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            Timber.e("FragmentActivity is null");
            return;
        }

        viewModel = new ViewModelProvider(activity).get(LoginActivityViewModel.class);
        viewModel.getResponseStatus().observe(getViewLifecycleOwner(), this::handleStatus);
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick(R.id.fragment_new_password_back)
    public void onClickBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.fragment_new_password_next_btn)
    public void onNextClick() {
        if (editChoose.length() < PASSWORD_MIN) {
            editConfirmLayout.setError(getString(R.string.error_password_small));
            return;
        }
        if (editChoose.length() > PASSWORD_MAX) {
            editConfirmLayout.setError(getString(R.string.error_password_big));
            return;
        }
        if(!TextUtils.equals(EditTextUtils.getText(editConfirm), EditTextUtils.getText(editChoose))) {
            editConfirmLayout.setError(getString(R.string.error_password_not_match));
            return;
        }

        if(!TextUtils.isEmpty(EditTextUtils.getText(editChoose)) &&
                !TextUtils.isEmpty(EditTextUtils.getText(editConfirm)) &&
                TextUtils.equals(EditTextUtils.getText(editChoose), EditTextUtils.getText(editConfirm))) {

            viewModel.getRecoverPasswordRequest().setPassword(EditTextUtils.getText(editChoose));
            viewModel.showProgressDialog();
            viewModel.resetPassword();
        }
    }

    private void handleStatus(ResponseStatus status) {
        if(status != null) {
            viewModel.hideProgressDialog();

            switch (status) {
                case RESPONSE_ERROR:
                    Toast.makeText(getActivity(), getString(R.string.error_change_password), Toast.LENGTH_LONG).show();
                    break;
                case RESPONSE_ERROR_CODE_NOT_MATCH:
                    Toast.makeText(getActivity(), getString(R.string.error_authentication_failed), Toast.LENGTH_LONG).show();
                    break;
                case RESPONSE_NEXT_NEW_PASSWORD:
                    viewModel.changeAction(LoginActivityActions.CHANGE_ACTIVITY_MAIN);
                    break;
                case RESPONSE_NEXT:
                    break;
            }
        }
    }

    private void setListeners() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editConfirmLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editConfirm.addTextChangedListener(watcher);
        editChoose.addTextChangedListener(watcher);
    }
}
