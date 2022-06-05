package dev.josiah.services;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Encrypt {
    public static String encrypt(String string) {
        return (string == null) ? null : Hashing.sha256().
                hashString(string, StandardCharsets.UTF_8).toString();
    }
}
