package com.kataleko.katalekonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteActivity extends AppCompatActivity {
    EditText etTitle, etBody;
    Button btnAddNote, btnCancelNote;
    Intent i;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        getSupportActionBar().hide();

        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnCancelNote = findViewById(R.id.btnCancelNote);
        i = getIntent();

        dbHelper = new DBHelper(this);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString();
                String body = etBody.getText().toString();

                if(title.trim().isEmpty()) {
                    Toast.makeText(NewNoteActivity.this, "Por favor coloque o título da nota.", Toast.LENGTH_SHORT).show();
                } else {
                    long res = dbHelper.insetNote(title, body);
                    if(res > 0) {
                        Toast.makeText(NewNoteActivity.this, "Nota salva", Toast.LENGTH_SHORT).show();
                        setResult(1, i);
                        finish();
                    } else {
                        Toast.makeText(NewNoteActivity.this, "Houve um erro. Tente de novo", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        btnCancelNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(0, i);
                finish();
            }
        });

    }

}