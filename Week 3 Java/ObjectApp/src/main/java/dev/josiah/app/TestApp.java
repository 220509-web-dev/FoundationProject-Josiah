package dev.josiah.app;

import dev.josiah.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TestApp {
    public static void main(String[] args) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();

    }
}
