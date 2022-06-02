package dev.josiah.daos;

import dev.josiah.entities.User;
import dev.josiah.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

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
public class UserDaoPostgres implements UserDAO{
    final private String sn = "user_data";  // Schema name
    final private String tn = "users";      // Table name
    final private String st = sn+"."+tn;
    final private String c0 = "user_id";    // columns
    final private String c1 = "username";
    final private String c2 = "fname";
    final private String c3 = "lname";
    final private String c4 = "address1";
    final private String c5 = "address2";
    final private String c6 = "city";
    final private String c7 = "state";
    final private String c8 = "postalcode";
    final private String[] c = {c0,c1,c2,c3,c4,c5,c6,c7,c8};

    @Override
    public User createUser(User user) {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionUtil.getConnection();
            String sql = "insert into "+st+" values (default";
            for (int i = 1; i<c.length;i++) { sql += ",?"; }
            sql += ");";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.setString(4, user.getAddress1());
            ps.setString(5, user.getAddress2());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getPostalcode());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedID = rs.getInt(c[0]);

            user.setUser_id(generatedID);
            return user;
        } catch (SQLException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionUtil.getConnection();
            String sql = "select * from "+st+" where "+c[0]+" = ?";
            sql += ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                User user = new User();
                user.setUser_id(id);
                user.setUsername(   rs.getString(c[1]));
                user.setFname(      rs.getString(c[2]));
                user.setLname(      rs.getString(c[3]));
                user.setAddress1(   rs.getString(c[4]));
                user.setAddress2(   rs.getString(c[5]));
                user.setCity(       rs.getString(c[6]));
                user.setState(      rs.getString(c[7]));
                user.setPostalcode( rs.getString(c[8]));
                return user;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        // Return type is not an array.
        // This works because username in DB has unique constraint.
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionUtil.getConnection();
            String sql = "select * from "+st+" where "+c[1]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUser_id(    rs.getInt(   c[0]));
                user.setUsername(   rs.getString(c[1])); // username also works
                user.setFname(      rs.getString(c[2]));
                user.setLname(      rs.getString(c[3]));
                user.setAddress1(   rs.getString(c[4]));
                user.setAddress2(   rs.getString(c[5]));
                user.setCity(       rs.getString(c[6]));
                user.setState(      rs.getString(c[7]));
                user.setPostalcode( rs.getString(c[8]));
                return user;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionUtil.getConnection();
            String sql = "select * from "+st+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setUser_id(    rs.getInt(   c[0]));
                user.setUsername(   rs.getString(c[1]));
                user.setFname(      rs.getString(c[2]));
                user.setLname(      rs.getString(c[3]));
                user.setAddress1(   rs.getString(c[4]));
                user.setAddress2(   rs.getString(c[5]));
                user.setCity(       rs.getString(c[6]));
                user.setState(      rs.getString(c[7]));
                user.setPostalcode( rs.getString(c[8]));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionUtil.getConnection();
            String sql = "update "+st+" set ";
            for (int i=1; i<c.length-1;i++) {sql += c[i]+" = ?,";}
            sql += c[c.length-1]+" = ? where "+c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.setString(4, user.getAddress1());
            ps.setString(5, user.getAddress2());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getPostalcode());
            ps.setInt(   9, user.getUser_id());
            ps.execute();

            return user;

        } catch (SQLException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
        }
        return null;
    }

    @Override
    public void deleteUserById(int id) {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionUtil.getConnection();
            String sql = "delete from "+st+" where "+c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
        }
    }
}
