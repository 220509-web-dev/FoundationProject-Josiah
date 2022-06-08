package dev.josiah.dtos;

import lombok.*;

/*
-- pSQL code for table column names
user_id int generated always as identity,
  username varchar(255) not null check(length(username) >=2),
  fname varchar(255) not null,
  lname varchar(255) not null,
  address1 varchar(255),
  address2 varchar(255),
  city varchar(255),
  state varchar(100),
  postalcode varchar(20),
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfo {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalcode;
}
