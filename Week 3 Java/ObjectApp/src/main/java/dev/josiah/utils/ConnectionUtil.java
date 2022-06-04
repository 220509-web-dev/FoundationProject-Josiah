package dev.josiah.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class ConnectionUtil {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {connect();}
        return connection;
    }
    private static void connect() {
        System.out.println("Making a new connection!");
        try {
            String dbInfo = "jdbc:postgresql://localhost/postgres?user=postgres&password=revature";
            //String dbinfo = System.getenv("DB_CONNECTION");
            connection = DriverManager.getConnection(dbInfo);
        }  catch (SQLException e) {
            Complain(e);
            e.printStackTrace();
        }  catch (Throwable t) {
            Complain(t);
        }
    }
}
