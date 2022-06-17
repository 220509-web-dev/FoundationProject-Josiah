package dev.josiah.entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
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

    public User() { super(); }
}
