package com.example.phonebookapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ContactActivity extends AppCompatActivity {

    private ContactViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(ContactViewModel.class);
    }


    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    public void save(View view) {
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText phone = findViewById(R.id.phone);
        EditText email = findViewById(R.id.email);
        model.addContact(new Contact(firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString(), email.getText().toString()));
        finish();
    }
}
