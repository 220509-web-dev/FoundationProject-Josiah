package dev.josiah.daos;

import dev.josiah.entities.User;
import dev.josiah.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
public class UserPrivDaoPostgres implements UserPrivDAO{
    final private String sn = "user_data";
    final private String tn = "users_private";      // Table name
    final private String st = sn + "." + tn;
    final private String c0 = "user_id";            // columns
    final private String c1 = "social_sn";
    final private String c2 = "password";
    final private String[] c = {c0,c1,c2};

    @Override
    public User createUserInfo(User user) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "insert into "+st+" values (";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User getUserInfoById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> getAllUserInfo() {
        try(Connection conn = ConnectionUtil.getConnection()) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User updateUserInfo(User user) {
        try(Connection conn = ConnectionUtil.getConnection()) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteUserInfoById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
