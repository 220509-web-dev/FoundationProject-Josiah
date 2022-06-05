package dev.josiah.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class ConnectionUtil {
    final private static String dbname = "DB_CONNECTION";
    //final private static String dbpath = "jdbc:postgresql://localhost/postgres?user=postgres&password=revature";
    final private static String dbpath = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=g5rEW^";
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {connect();}
        return connection;
    }
    private static void connect() {
        System.out.println("Making a new connection!");  // This should occur only once
        try {
            String dbinfo = System.getenv(dbname);
            if (dbinfo != null) { // The environmental variable exists and is not null
                connection = DriverManager.getConnection(dbinfo);
                // Now, it's up to the database to confirm the credentials
            } else { // The environmental variable either couldn't be reached or it's null
                String complaint = "The environmental variable \""+dbname+"\" could not be found.";
                complaint += "\nTry setting the environmental variable and restarting.";
                complaint += "\nTrying to use provided DB path...";
                Complain(complaint); // log the environmental variable issue
                System.err.println(complaint);
                connection = DriverManager.getConnection(dbpath); // try connection with provided dbpath string
            }
        }  catch (SQLException e) {
            Complain(e);
            e.printStackTrace();
        }  catch (Throwable t) {
            Complain(t);
        }
    }
}
