package dev.josiah.dtos;

import lombok.Getter;

import static dev.josiah.services.Encrypt.encrypt;

@Getter
public class UserPass {
    private String user;
    private String pass;
    private int passLen;

    public UserPass(String user, String pass) {
        passLen = (pass == null) ? 0 : pass.length();
        this.user = user;
        this.pass = encrypt(pass);
    }

}
