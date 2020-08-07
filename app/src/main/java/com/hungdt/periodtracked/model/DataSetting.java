package com.hungdt.periodtracked.model;

import java.util.List;

public class DataSetting {
    List<Data> data ;
    String month;

    public DataSetting(List<Data> data, String month) {
        this.data = data;
        this.month = month;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
