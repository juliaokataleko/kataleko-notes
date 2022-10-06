package com.kataleko.katalekonotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewNote;
    List<Note> notesList;
    DBHelper dbHelper;
    Button btnAnotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        notesList = new ArrayList<>();
        listViewNote = findViewById(R.id.lvNotes);
        btnAnotar = findViewById(R.id.btnAnotar);

        btnAnotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                intent.putExtra("title", notesList.get(i).getTitle());
                intent.putExtra("body", notesList.get(i).getBody());
                intent.putExtra("id", notesList.get(i).getId());
                startActivityForResult(intent, 2);
            }
        });

        listNotes();


    }

    private void listNotes() {
        Cursor c = dbHelper.selectAllNotes();
        notesList.clear();

        if(c.getCount() > 0) {
            c.moveToFirst();
            do {

                @SuppressLint("Range") String title = c.getString(c.getColumnIndex("title"));
                @SuppressLint("Range") String body = c.getString(c.getColumnIndex("body"));
                @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));

                Log.e("ID", String.valueOf(id));
                notesList.add(new Note(title, body, id));

            } while (c.moveToNext());
        }

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                notesList);
        listViewNote.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 1) {
            listNotes();
        } else if(requestCode == 2 && (resultCode == 1 || resultCode == 2)) {
            listNotes();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}