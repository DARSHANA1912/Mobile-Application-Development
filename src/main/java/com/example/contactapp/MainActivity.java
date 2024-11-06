package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText nameInput, phoneInput, idInput;
    Button addButton, viewButton, updateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database helper
        myDB = new DatabaseHelper(this);

        // Initialize UI components
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        idInput = findViewById(R.id.idInput);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Add contact logic
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = myDB.addContact(name, phone);
                    if (isInserted) {
                        Toast.makeText(MainActivity.this, "Contact added", Toast.LENGTH_SHORT).show();
                        nameInput.setText("");
                        phoneInput.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Error adding contact", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Update contact logic
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString();
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();

                if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isUpdated = myDB.updateContact(id, name, phone);
                    if (isUpdated) {
                        Toast.makeText(MainActivity.this, "Contact updated", Toast.LENGTH_SHORT).show();
                        idInput.setText("");
                        nameInput.setText("");
                        phoneInput.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Error updating contact", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Delete contact logic
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString();

                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter the ID", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isDeleted = myDB.deleteContact(id);
                    if (isDeleted) {
                        Toast.makeText(MainActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                        idInput.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Error deleting contact", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // View contacts logic
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = myDB.getAllContacts();

                if (data.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No contacts found", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder contacts = new StringBuilder();
                while (data.moveToNext()) {
                    contacts.append("ID: ").append(data.getString(0)).append("\n");
                    contacts.append("Name: ").append(data.getString(1)).append("\n");
                    contacts.append("Phone: ").append(data.getString(2)).append("\n\n");
                }

                // Display contacts (for simplicity using Toast, but you can use a ListView or RecyclerView)
                Toast.makeText(MainActivity.this, contacts.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

