package dev.josiah.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Greeting {
    private String username;
    private String fname;
    private String lname;
    private String city;
    private String state;
}
