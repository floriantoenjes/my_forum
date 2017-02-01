package com.floriantoenjes.forum.web;

public class FlashMessage {
    String message;
    Status status;

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public enum Status {
        SUCCESS,
        FAILED
    }
}
