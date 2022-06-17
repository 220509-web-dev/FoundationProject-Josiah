package dev.josiah.dtos;

import dev.josiah.complaintDepartment.Exceptions.TokenTimeoutException;

import java.util.Date;

import static dev.josiah.services.Encrypt.encrypt;

public class Token {
    private final static long timeout = 1000L * 60L * 2L; // half hour timeout
    private final String username;
    private final String password;
    private final long creationtime;

    public Token(String username, String password) {
        this.username = username;
        this.password = encrypt(password);
        creationtime = new Date().getTime();
    }

    public void verifyToken() throws TokenTimeoutException {
        if (new Date().getTime() - creationtime > timeout)  throw new TokenTimeoutException();

//        if (!this.getUsername().equals(username)) throw new TokenUsernameMismatchException();
//        User user = userDAO.getUserByUsername(username);
//        if (user == null) throw new UserNotFoundException();

    }
}
