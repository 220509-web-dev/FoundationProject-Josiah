package dev.josiah.daos;

import dev.josiah.entities.UserPriv;
import dev.josiah.utils.ConnectionFactory;

import javax.sql.rowset.RowSetWarning;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

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
    public void createUserInfo(UserPriv userp) throws SQLException {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionFactory.getInstance().getConnection();
            String sql = "insert into "+st+" values (";
            for (int i = 0; i<c.length-1;i++) { sql += "?,"; }
            sql += "?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userp.getUser_id());
            ps.setString(2, userp.getSocial_sn());
            ps.setString(3, userp.getPassword());
            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public UserPriv getUserInfoById(int id) throws SQLException {
//        try(Connection conn = ConnectionUtil.getConnection()) {
          try {Connection conn = ConnectionFactory.getInstance().getConnection();
            String sql = "select * from "+st+" where "+c[0]+" = ? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                UserPriv userp = new UserPriv();
                userp.setUser_id(   rs.getInt(c[0]));
                userp.setSocial_sn( rs.getString(c[1]));
                userp.setPassword(   rs.getString(c[2]));
                return userp;
            }
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public List<UserPriv> getAllUserInfo() throws SQLException {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionFactory.getInstance().getConnection();
            String sql = "select * from "+st+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<UserPriv> userps = new ArrayList<UserPriv>();
            boolean results = false;
            while (rs.next()) {
                results = true;
                UserPriv userp = new UserPriv();
                userp.setUser_id(   rs.getInt(   c[0]));
                userp.setSocial_sn( rs.getString(c[1]));
                userp.setPassword(  rs.getString(c[2]));
                userps.add(userp);
            }
            if (results) return userps;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public UserPriv updateUserInfo(UserPriv userp) throws SQLException {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionFactory.getInstance().getConnection();
            String sql = "update "+st+" set ";
            for (int i=1; i<c.length-1;i++) {sql += c[i]+" = ?,";}
            sql += c[c.length-1]+" = ? where "+c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString( 1, userp.getSocial_sn());
            ps.setString( 2, userp.getPassword());
            ps.setInt(    3, userp.getUser_id());
            ps.execute();

            return userp;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        //return null;
    }

    @Override
    public void deleteUserInfoById(int id) throws SQLException {
//      try(Connection conn = ConnectionUtil.getConnection()) {
        try {Connection conn = ConnectionFactory.getInstance().getConnection();
            String sql = "delete from "+st+" where "+c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }
}
