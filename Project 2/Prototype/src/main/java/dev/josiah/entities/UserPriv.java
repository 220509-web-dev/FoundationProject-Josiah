package dev.josiah.entities;

import com.google.common.hash.Hashing;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.Encrypt.encrypt;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserPriv {
    private int id;
    private String password;

    public UserPriv(int id) {
        this.id = id;
    }

    public void encryptAndSetPassword(String password) {
        this.password = encrypt(password);
    }
}