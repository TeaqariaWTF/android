package mobileapp.ctemplar.com.ctemplarapp.repository.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mobileapp.ctemplar.com.ctemplarapp.net.response.contacts.ContactData;
import mobileapp.ctemplar.com.ctemplarapp.net.response.contacts.EncryptContact;
import mobileapp.ctemplar.com.ctemplarapp.utils.EncryptUtils;

import static mobileapp.ctemplar.com.ctemplarapp.utils.DateUtils.GENERAL_GSON;
import static mobileapp.ctemplar.com.ctemplarapp.utils.EditTextUtils.replaceNullString;

import timber.log.Timber;

public class Contact {
    private long id;
    private String email;
    private String name;
    private String address;
    private String note;
    private String phone;
    private String phone2;
    private String provider;
    private boolean isEncrypted;
    private String encryptedData;

    public Contact() {
    }

    public Contact(long id, String email, String name, String address, String note, String phone, String phone2, String provider, boolean isEncrypted, String encryptedData) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.note = note;
        this.phone = phone;
        this.phone2 = phone2;
        this.provider = provider;
        this.isEncrypted = isEncrypted;
        this.encryptedData = encryptedData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    @NonNull
    public static Contact fromContactData(@Nullable ContactData contactData) {
        if (contactData == null) {
            return new Contact();
        }
        Contact contact = new Contact();
        contact.setId(contactData.getId());
        contact.setEmail(contactData.getEmail());
        contact.setName(contactData.getName());
        contact.setAddress(contactData.getAddress());
        contact.setNote(contactData.getNote());
        contact.setPhone(contactData.getPhone());
        contact.setPhone2(contactData.getPhone2());
        contact.setProvider(contactData.getProvider());
        contact.setEncrypted(contactData.isEncrypted());
        contact.setEncryptedData(contactData.getEncryptedData());
        return contact;
    }

    @NonNull
    public static Contact fromEntity(ContactEntity entity) {
        if (entity == null) {
            return new Contact();
        }
        Contact contact = new Contact();
        contact.setId(entity.getId());
        contact.setEncryptedData(entity.getEncryptedData());
        if (entity.isEncrypted()) {
            String encryptedData = entity.getEncryptedData();
            String decryptedData = EncryptUtils.decryptData(encryptedData);
            EncryptContact decryptedContact;
            try {
                decryptedContact = GENERAL_GSON.fromJson(decryptedData, EncryptContact.class);
            } catch (Throwable e) {
                Timber.e(e, "Decryption failed");
                decryptedContact = null;
            }
            if (decryptedContact != null) {
                contact.setEmail(decryptedContact.getEmail());
                contact.setName(decryptedContact.getName());
                contact.setAddress(decryptedContact.getAddress());
                contact.setNote(decryptedContact.getNote());
                contact.setPhone(decryptedContact.getPhone());
                contact.setPhone2(decryptedContact.getPhone2());
                contact.setProvider(decryptedContact.getProvider());
                contact.setEncrypted(false);
                return contact;
            }
        }
        contact.setEmail(replaceNullString(entity.getEmail()));
        contact.setName(replaceNullString(entity.getName()));
        contact.setAddress((entity.getAddress()));
        contact.setNote((entity.getNote()));
        contact.setPhone((entity.getPhone()));
        contact.setPhone2((entity.getPhone2()));
        contact.setProvider((entity.getProvider()));
        contact.setEncrypted(false);
        return contact;
    }

    public static ContactEntity fromContactDataToEntity(@Nullable ContactData contactData) {
        if (contactData == null) {
            return null;
        }
        return new ContactEntity(
                contactData.getId(),
                contactData.getEmail(),
                contactData.getName(),
                contactData.getAddress(),
                contactData.getNote(),
                contactData.getPhone(),
                contactData.getPhone2(),
                contactData.getProvider(),
                contactData.isEncrypted(),
                contactData.getEncryptedData()
        );
    }

    @NonNull
    public static List<Contact> fromContactData(@Nullable ContactData[] contactData) {
        if (contactData == null || contactData.length == 0) {
            return new ArrayList<>();
        }
        List<Contact> contactDataList = new LinkedList<>();
        for (ContactData contactDatum : contactData) {
            contactDataList.add(fromContactData(contactDatum));
        }
        return contactDataList;
    }

    @NonNull
    public static List<Contact> fromEntities(@Nullable List<ContactEntity> contactEntityList) {
        if (contactEntityList == null || contactEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Contact> contactList = new LinkedList<>();
        for (ContactEntity contactEntity : contactEntityList) {
            contactList.add(fromEntity(contactEntity));
        }
        return contactList;
    }

    @NonNull
    public static ContactEntity[] fromContactDataToEntities(@Nullable ContactData[] contactData) {
        if (contactData == null || contactData.length == 0) {
            return new ContactEntity[0];
        }
        ContactEntity[] contactEntities = new ContactEntity[contactData.length];
        for (int i = 0; i < contactData.length; ++i) {
            contactEntities[i] = fromContactDataToEntity(contactData[i]);
        }
        return contactEntities;
    }
}
