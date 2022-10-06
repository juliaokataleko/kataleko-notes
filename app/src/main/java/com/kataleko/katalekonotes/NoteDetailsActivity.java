package com.kataleko.katalekonotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText etTitleEdit, etBodyEdit;
    Button btnUpdateNote, btnDeleteNote, btnBack;
    Intent intent;
    DBHelper dbHelper;
    int noteId = 0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        etTitleEdit = findViewById(R.id.etTitleEdit);
        etBodyEdit = findViewById(R.id.etBodyEdit);

        btnUpdateNote = findViewById(R.id.btnUpdateNote);
        btnDeleteNote = findViewById(R.id.btnDeleteNote);
        btnBack = findViewById(R.id.btnBack);

        builder = new AlertDialog.Builder(this);

        intent = getIntent();
        dbHelper = new DBHelper(this);

        // carregarDadosDaNota
        noteId = intent.getExtras().getInt("id");
        Log.e("ID", String.valueOf(noteId));
        loadNoteData(noteId);

        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitleEdit.getText().toString();
                String body = etBodyEdit.getText().toString();

                if(noteId > 0) {
                    if (title.trim().isEmpty()) {
                        Toast.makeText(NoteDetailsActivity.this, "Por favor coloque o título da nota.", Toast.LENGTH_SHORT).show();
                    } else {
                        long res = dbHelper.updateNote(title, body, noteId);
                        if (res > 0) {
                            Toast.makeText(NoteDetailsActivity.this, "Nota salva", Toast.LENGTH_SHORT).show();
                            setResult(1, intent);
                            finish();
                        } else {
                            Toast.makeText(NoteDetailsActivity.this, "Houve um erro. Tente de novo", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(NoteDetailsActivity.this, "Nota inválida", Toast.LENGTH_SHORT).show();
                }


                setResult(1, intent);
                finish();
            }
        });

        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Atencão")
                        .setMessage("Deseja realmente excluir esta nota?")
                        .setCancelable(true)
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(noteId > 0) {
                                    long res = dbHelper.deleteNote(noteId);
                                    if(res > 0) {
                                        Toast.makeText(NoteDetailsActivity.this, "Nota excluída", Toast.LENGTH_SHORT).show();
                                        setResult(2, intent);
                                        finish();
                                    } else {
                                        Toast.makeText(NoteDetailsActivity.this, "Ocorreu um erro.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        })
                        .show();



            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(0, intent);
                finish();
            }
        });
    }

    private void loadNoteData(int id) {
        Cursor c = dbHelper.findById(id);
        c.moveToFirst();
        if(c.getCount() == 1) {
            do {
                @SuppressLint("Range") String title = c.getString(c.getColumnIndex("title"));
                @SuppressLint("Range") String body = c.getString(c.getColumnIndex("body"));
                etTitleEdit.setText(title);
                etBodyEdit.setText(body);

            } while (c.moveToNext());
        } else if(c.getCount() == 0) {
            Toast.makeText(this, "Esta nota não existe", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Houve um erro ao carregar a nota.", Toast.LENGTH_SHORT).show();
        }
    }
}