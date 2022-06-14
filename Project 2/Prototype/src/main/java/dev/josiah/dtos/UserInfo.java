package dev.josiah.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

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

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserInfo {
    @JsonProperty("u")
    private String username;

    @JsonProperty("p")
    private String password;

    @JsonProperty("f")
    private String fname;

    @JsonProperty("l")
    private String lname;

    public UserInfo() {
    super();}

}
