package org.rottweiler488.min.model;

import org.rottweiler488.min.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class ChatData {
    private List<UserData> users = new ArrayList<>();

    private String name = "";
    private String password = "";

    public ChatData() {}

    public ChatData(List<org.rottweiler488.min.model.UserData> users, String name, String password) {
        this.users = users;
        this.name = name;
        this.password = password;
    }

    public List<org.rottweiler488.min.model.UserData> getUsers() { return users; }

    public void setUsers(List<org.rottweiler488.min.model.UserData> users) { this.users = users; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
