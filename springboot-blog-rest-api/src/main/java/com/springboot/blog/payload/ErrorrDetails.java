package com.springboot.blog.payload;

import java.util.Date;

public class ErrorrDetails {
    private Date timestap;
    private String message;
    private String Details;

    public ErrorrDetails(Date timestap, String message, String details) {
        this.timestap = timestap;
        this.message = message;
        Details = details;
    }

    public Date getTimestap() {
        return timestap;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return Details;
    }
}
