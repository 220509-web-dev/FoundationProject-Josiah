package dev.josiah.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class ConnectionUtil {
    private static Connection connection = null;
    static {
        //System.out.println("Making a new connection!");
        try {
            //String dbInfo = "jdbc:postgresql://localhost/local-db?user=postgres&password=g5rEW^";
            //String dbinfo = System.getenv("DB_CONNECTION");
            connection = DriverManager.getConnection(System.getenv("DB_CONNECTION"));
        }  catch (SQLException e) {
            Complain(e);
            e.printStackTrace();
        }  catch (Throwable t) {
            Complain(t);
        }
    }
    public static Connection getConnection() {return connection;}
}
