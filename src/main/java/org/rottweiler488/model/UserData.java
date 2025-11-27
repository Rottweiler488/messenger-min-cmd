package org.rottweiler488.model;

public class UserData {
    private String username = "";
    private String password = "";
    private String dateOfRegistration = "";

    public UserData() {}

    public UserData(String username, String password, String dateOfRegistration) {
        this.username = username;
        this.password = password;
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getDateOfRegistration() { return dateOfRegistration; }

    public void setDateOfRegistration(String dateOfRegistration) { this.dateOfRegistration = dateOfRegistration; }
}
