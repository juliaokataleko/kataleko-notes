package com.kataleko.katalekonotes;

public class Note {
    private String title;
    private String body;
    private int id;

    public Note(String title, String body, int id) {
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Note(String title) {
        this.title = title;
    }

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }
}
