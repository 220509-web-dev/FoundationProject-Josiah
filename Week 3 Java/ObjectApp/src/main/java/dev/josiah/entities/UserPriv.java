package dev.josiah.entities;

import com.google.common.hash.Hashing;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserPriv {
    private int user_id;

    @Setter(AccessLevel.NONE)
    private String social_sn;

    private String password;

    public UserPriv(int user_id) {
        this.user_id = user_id;
    }

    public Boolean setSocial_sn(String social_sn) {
        if (social_sn == null || social_sn.length() != 8) return false;
        try {
            int i = Integer.parseInt(social_sn);
            this.social_sn = social_sn;
            return true;
        }
        catch (NumberFormatException nfe) {
            Complain(nfe);
            return false;
        }
    }

    public void encryptAndSetPassword(String password) {
        String sha256hex = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        this.password = sha256hex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPriv userp = (UserPriv) o;
        return user_id == userp.getUser_id() &&
                Objects.equals(social_sn, userp.getSocial_sn()) &&
                Objects.equals(password, userp.getPassword());
    }
}

/*
create table users_private (
  user_id int not null,
  social_sn varchar(9) check (length(social_sn) = 8),
  password varchar(255) check (length(password) >= 8),

  constraint user_private_pk
  primary key(user_id),

  constraint user_private_fk
  foreign key (user_id)
  references users(user_id)

);
 */