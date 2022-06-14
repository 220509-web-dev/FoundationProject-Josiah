package dev.josiah.entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private int id;
    private String username;
    private String fname;
    private String lname;
    private String creationdate;
    private String creationtime;

    public User() { super(); }
}
