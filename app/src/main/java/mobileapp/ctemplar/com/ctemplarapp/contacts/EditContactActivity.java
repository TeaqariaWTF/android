package mobileapp.ctemplar.com.ctemplarapp.contacts;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import mobileapp.ctemplar.com.ctemplarapp.BaseActivity;
import mobileapp.ctemplar.com.ctemplarapp.R;
import mobileapp.ctemplar.com.ctemplarapp.net.ResponseStatus;
import mobileapp.ctemplar.com.ctemplarapp.net.response.contacts.ContactData;
import mobileapp.ctemplar.com.ctemplarapp.repository.entity.Contact;
import mobileapp.ctemplar.com.ctemplarapp.utils.EditTextUtils;
import timber.log.Timber;

public class EditContactActivity extends BaseActivity {
    public static final String ARG_ID = "id";

    @BindView(R.id.activity_edit_contact_name_input)
    EditText editTextContactName;

    @BindView(R.id.activity_edit_contact_email_input)
    EditText editTextContactEmail;

    @BindView(R.id.activity_edit_contact_phone_input)
    EditText editTextContactPhoneNumber;

    @BindView(R.id.activity_edit_contact_phone_input_second)
    EditText editTextContactPhoneNumberSecond;

    @BindView(R.id.activity_edit_contact_address_input)
    EditText editTextContactAddress;

    @BindView(R.id.activity_edit_contact_note_input)
    EditText editTextContactNote;

    @BindView(R.id.activity_edit_contact_progress_bar)
    View progressBar;

    @BindView(R.id.edit_contact_toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_contact;
    }

    private ContactsViewModel viewModel;
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        viewModel.getResponseStatus().observe(this, this::handleResponse);
        viewModel.getContactResponse().observe(this, contact -> {
            if (contact != null) {
                fillFields(contact);
                progressBar.setVisibility(View.GONE);
            }
        });

        long id = getIntent().getLongExtra(ARG_ID, -1);
        if (id == -1) {
            Timber.e("Contact id is not defined");
            onBackPressed();
            return;
        }

        viewModel.getContact(id);
    }

    private void fillFields(Contact contact) {
        contactId = contact.getId();
        editTextContactName.setText(contact.getName());
        editTextContactEmail.setText(contact.getEmail());
        editTextContactPhoneNumber.setText(contact.getPhone());
        editTextContactPhoneNumberSecond.setText(contact.getPhone2());
        editTextContactAddress.setText(contact.getAddress());
        editTextContactNote.setText(contact.getNote());
    }

    private void handleResponse(ResponseStatus responseStatus) {
        if (responseStatus == null || responseStatus == ResponseStatus.RESPONSE_ERROR) {
            onBackPressed();
            Toast.makeText(this, R.string.txt_contact_loading_error, Toast.LENGTH_SHORT).show();
        } else if (responseStatus == ResponseStatus.RESPONSE_COMPLETE) {
            onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_contact:
                updateContact();
                break;

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void updateContact() {
        String contactName = EditTextUtils.getText(editTextContactName);
        String contactEmail = EditTextUtils.getText(editTextContactEmail);
        String contactPhone = EditTextUtils.getText(editTextContactPhoneNumber);
        String contactPhoneSecond = EditTextUtils.getText(editTextContactPhoneNumberSecond);
        String contactAddress = EditTextUtils.getText(editTextContactAddress);
        String contactNote = EditTextUtils.getText(editTextContactNote);

        if (contactName.isEmpty()) {
            editTextContactName.setError(getString(R.string.txt_enter_name));
        } else {
            editTextContactName.setError(null);
        }
        if (Patterns.EMAIL_ADDRESS.matcher(contactEmail).matches()) {
            editTextContactEmail.setError(null);
        } else {
            editTextContactEmail.setError(getString(R.string.txt_enter_valid_email));
        }
        if (editTextContactName.getError() != null || editTextContactEmail.getError() != null) {
            return;
        }

        ContactData contactData = new ContactData();
        contactData.setId(contactId);
        contactData.setName(contactName);
        contactData.setEmail(contactEmail);
        contactData.setPhone(contactPhone);
        contactData.setPhone2(contactPhoneSecond);
        contactData.setAddress(contactAddress);
        contactData.setNote(contactNote);

        viewModel.updateContact(contactData);
    }
}
