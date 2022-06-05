package dev.josiah.entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int user_id;
    private String username;
    private String fname;
    private String lname;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalcode;

    // Usernames cannot equal by SQL constraints
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.getUser_id() && Objects.equals(username, user.getUsername());
    }

    @Override
    public int hashCode() {return Objects.hash(user_id, username);}

}
