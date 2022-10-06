package com.kataleko.katalekonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String nomeBD = "katanotes.db";
    String[] sql = {
            "CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, body TEXT);",
            // "INSERT INTO notes(title, body) Values('Acordar e estudar Java', 'Tenho que me dedicar nos estudos de Java para passar na entrevista')",
            // "INSERT INTO notes(title, body) Values('Ir ao banco', 'Levantar dinheiro...')"
    };

    public DBHelper(@Nullable Context context) {
        super(context, nomeBD, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (int i = 0; i < sql.length; i++) {
            sqLiteDatabase.execSQL(sql[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        versao++;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(sqLiteDatabase);
    }

    // ===========================INSERT============================
    public long insetNote(String title, String body) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("body", body);
        return database.insert("notes", null, contentValues);
    }

    // ===========================UPDATE============================
    public long updateNote(String title, String body, int id) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("body", body);
        return database.update("notes", contentValues, "id=?", new String[]{String.valueOf(id)});
    }

    // ===========================DELETE============================
    public long deleteNote(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("notes", "id=?", new String[]{String.valueOf(id)});
    }

    // ===========================SELECT============================
    public Cursor selectAllNotes() {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM notes", null);
    }

    public  Cursor findById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM notes WHERE id=?", new String[]{String.valueOf(id)});
    }

}
