package com.example.chat.Model;

public class Chat {
    private String sender;
    private String reciever;
    private String message;
    private Boolean isseen;

    public Chat(String sender, String reciever, String message, Boolean isseen) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.isseen = isseen;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsseen() {
        return isseen;
    }

    public void setIsseen(Boolean isseen) {
        this.isseen = isseen;
    }
}
