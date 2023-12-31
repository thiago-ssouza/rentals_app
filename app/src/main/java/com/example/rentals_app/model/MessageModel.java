package com.example.rentals_app.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class MessageModel implements Serializable {
    private TenantModel tenant;
    private String message;
    private Date messageDate;

    public MessageModel() {
    }

    public MessageModel(TenantModel tenant, String message, Date messageDate) {
        this.tenant = tenant;
        this.message = message;
        this.messageDate = messageDate;
    }

    public TenantModel getTenant() {
        return tenant;
    }

    public void setTenant(TenantModel tenant) {
        this.tenant = tenant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "tenant=" + tenant +
                ", message='" + message + '\'' +
                ", messageDate=" + messageDate +
                '}';
    }
}