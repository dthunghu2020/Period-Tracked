package com.hungdt.periodtracked.model;

public class Log {
    private int idImage;
    private int title;
    private boolean isChecked;

    public Log(int idImage, int title,boolean isChecked) {
        this.idImage = idImage;
        this.title = title;
        this.isChecked = isChecked;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
