package com.example.phonebookapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contact>> contactList = new MutableLiveData<>();
    private Application application;
    private DataRepository dataRepository;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.dataRepository = DataRepository.getInstance(application);
    }

    LiveData<List<Contact>> getContactList() {
        contactList = DataRepository.getInstance(application).getContactList();
        return contactList;
    }

    public void addContact(Contact contact) {
        dataRepository.addContact(contact);
    }

}
