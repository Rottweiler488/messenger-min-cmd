package org.rottweiler488.model;

public class MessageData {
    private String username;
    private String time;
    private String text;

    public MessageData() {}

    public MessageData(String username, String time, String text) {
        this.username = username;
        this.time = time;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {this.time = time;}

    public String getText() {
        return text;
    }

    public void setText(String text) {this.text = text;}
}