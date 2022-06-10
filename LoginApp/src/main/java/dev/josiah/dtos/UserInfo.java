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

public class UserInfo {
    @JsonProperty("u")
    private String username;

    @JsonProperty("p")
    private String password;

    @JsonProperty("f")
    private String fname;

    @JsonProperty("l")
    private String lname;

    @JsonProperty("a1")
    private String address1;

    @JsonProperty("a2")
    private String address2;

    @JsonProperty("c")
    private String city;

    @JsonProperty("s")
    private String state;

    @JsonProperty("z")
    private String postalcode;

    public UserInfo() {
    super();}

    public UserInfo(String username, String password, String fname, String lname, String address1, String address2, String city, String state, String postalcode) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalcode = postalcode;
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(username, userInfo.username) && Objects.equals(password, userInfo.password) && Objects.equals(fname, userInfo.fname) && Objects.equals(lname, userInfo.lname) && Objects.equals(address1, userInfo.address1) && Objects.equals(address2, userInfo.address2) && Objects.equals(city, userInfo.city) && Objects.equals(state, userInfo.state) && Objects.equals(postalcode, userInfo.postalcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, fname, lname, address1, address2, city, state, postalcode);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalcode='" + postalcode + '\'' +
                '}';
    }
}
