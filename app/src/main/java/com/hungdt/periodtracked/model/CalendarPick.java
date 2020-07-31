package com.hungdt.periodtracked.model;

import java.util.Date;

public class CalendarPick {
    Date date;
    boolean isPicked = false;

    public CalendarPick(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }
}
