package dev.josiah.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Example {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public Example() { super(); }

    @Override
    public String toString() {
        return "Example{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
