package dev.josiah.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

public class UserPass {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public UserPass() {
    super();}

    public UserPass(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPass userPass = (UserPass) o;
        return Objects.equals(username, userPass.username) && Objects.equals(password, userPass.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "UserPass{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
