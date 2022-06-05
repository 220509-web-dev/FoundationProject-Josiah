package dev.josiah.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil_min {
    public static Connection getConnection() {
        return DriverManager.getConnection(dbpath);
    }
}
