package com.hungdt.periodtracked.model;

import java.io.Serializable;

public class Paper implements Serializable {
    String id;
    int title;
    int body;
    int idImage;

    public Paper(String id, int title, int  body, int idImage) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.idImage = idImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int image) {
        this.idImage = idImage;
    }
}
