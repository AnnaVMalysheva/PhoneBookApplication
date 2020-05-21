package com.example.phonebookapplication;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//SingleTon
public class DataRepository {

    private MutableLiveData<List<Contact>> mContactList;
    private Application application;

    private static DataRepository instance;

    private DataRepository(Application application) {
        this.application = application;
    }

    public static synchronized DataRepository getInstance(Application application) {
        if (instance == null) {
            instance = new DataRepository(application);

        }

        return instance;
    }

    public MutableLiveData<List<Contact>> getContactList() {
        if (mContactList == null) {
            mContactList = new MutableLiveData<>();
            loadContacts(application);
        }
        return mContactList;
    }

    public MutableLiveData<List<Contact>> addContact(Contact contact) {
        if (mContactList == null) {
            mContactList = new MutableLiveData<>();
            List<Contact> contacts = new ArrayList<>();
            contacts.add(contact);
            mContactList.postValue(contacts);
        } else {
            List contacts = mContactList.getValue();
            contacts.add(contact);
            mContactList.postValue(contacts);
        }
        return mContactList;
    }

    private void loadContacts(Application application) {

        HandlerThread handlerThread = new HandlerThread("HandlerThreadName");

        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());

        handler.post(() -> {
            String jsonContacts = readJson(application.getAssets());
            Gson gson = new Gson();
            Type contactListType = new TypeToken<List<Contact>>() {}.getType();

            List<Contact> contacts = gson.fromJson(jsonContacts, contactListType);
            mContactList.postValue(contacts);
        });
    }

    private String readJson(AssetManager assetManager) {
        String json;
        try (InputStream inputStream = assetManager.open("contacts_data_cut.json");) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
