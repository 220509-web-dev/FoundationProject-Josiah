package dev.josiah.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            //String dbInfo = "jdbc:postgresql://localhost/local-db?user=postgres&password=g5rEW^";
            //String dbinfo = System.getenv("DB_CONNECTION");
            Connection connection = DriverManager.getConnection(System.getenv("DB_CONNECTION"));
            return connection;
        } catch (SQLException e) {
            Complain(e);
            e.printStackTrace();
            return null;
        }

    }
}
