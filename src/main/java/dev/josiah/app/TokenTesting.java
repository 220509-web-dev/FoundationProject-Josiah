package dev.josiah.app;

import java.util.Date;

import static dev.josiah.services.Encrypt.encrypt;
public class TokenTesting {
    private static long creationtime = new Date().getTime();

    public static void main(String[] args) {
        for (Long i=0L; true; i++) {
            if (i > 400000000L) {
                System.out.println("got it");
                break;
            }
        }
        System.out.println(new Date().getTime() - creationtime);
    }
}
