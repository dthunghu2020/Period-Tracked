package com.hungdt.periodtracked.model;

public class Report {
    private int imageId;
    private String type;
    private int count;

    public Report(int imageId,String type, int count) {
        this.imageId = imageId;
        this.count = count;
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
