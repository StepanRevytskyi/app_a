package com.example.appa.model;

import java.util.Date;

public class Link {
    private String mLink;
    private String mStatus;
    private Date mDate;

    public Link() {

    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
