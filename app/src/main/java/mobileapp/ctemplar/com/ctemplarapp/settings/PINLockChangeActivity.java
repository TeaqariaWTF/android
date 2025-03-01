package mobileapp.ctemplar.com.ctemplarapp.settings;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import mobileapp.ctemplar.com.ctemplarapp.BaseActivity;
import mobileapp.ctemplar.com.ctemplarapp.CTemplarApp;
import mobileapp.ctemplar.com.ctemplarapp.R;
import mobileapp.ctemplar.com.ctemplarapp.repository.UserStore;
import mobileapp.ctemplar.com.ctemplarapp.utils.AppUtils;
import mobileapp.ctemplar.com.ctemplarapp.view.pinlock.KeypadAdapter;
import mobileapp.ctemplar.com.ctemplarapp.view.pinlock.KeypadView;
import mobileapp.ctemplar.com.ctemplarapp.view.pinlock.PasscodeView;
import timber.log.Timber;

public class PINLockChangeActivity extends BaseActivity {
    @BindView(R.id.activity_pin_lock_pass_code_view)
    PasscodeView passcodeView;

    @BindView(R.id.activity_pin_lock_key_pad_view)
    KeypadView keypadView;

    @BindView(R.id.activity_pin_lock_hint_text_view)
    TextView hintTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pin_lock;
    }

    private UserStore userStore;
    private boolean confirmStage = false;
    private String pinCode;

    private final KeypadAdapter.KeypadListener mKeypadListener = new KeypadAdapter.KeypadListener() {
        @Override
        public void onComplete(String pin) {
            if (!confirmStage) {
                confirmStage = true;
                pinCode = pin;
                keypadView.resetKeypadView();
                hintTextView.setText(R.string.confirm_pin);
                return;
            }
            if (!pinCode.equals(pin)) {
                notifyPINsDontMatch();
                return;
            }
            userStore.setPINLock(pinCode);
            onPINChangedSuccess();
        }

        @Override
        public void onPINChanged(int pinLength, String pinCode) {
            if (!confirmStage && pinLength == 1) {
                hintTextView.setText(R.string.choose_pin);
                hintTextView.setTextColor(ContextCompat.getColor(PINLockChangeActivity.this,
                        R.color.secondaryTextColor));
            }
        }

        @Override
        public void onEmpty() {
            //
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userStore = CTemplarApp.getUserStore();
        keypadView.attachPasscodeView(passcodeView);
        keypadView.setKeypadListener(mKeypadListener);
        hintTextView.setText(R.string.choose_pin);
    }

    private void onPINChangedSuccess() {
        Timber.d("onPINChangedSuccess");
        setResult(RESULT_OK);
        finish();
    }

    private void notifyPINsDontMatch() {
        Timber.d("notifyPINsDontMatch");
        confirmStage = false;
        keypadView.resetKeypadView();
        hintTextView.setText(R.string.pins_dont_match);
        hintTextView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        AppUtils.vibrate(getBaseContext(), 500);
    }
}
