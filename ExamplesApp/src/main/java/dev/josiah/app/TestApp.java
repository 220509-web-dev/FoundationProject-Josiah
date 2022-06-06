package dev.josiah.app;

import dev.josiah.entities.WebPage;
import dev.josiah.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TestApp {
    public static void main(String[] args) throws SQLException {
        //Connection conn = ConnectionUtil.getConnection();
        WebPage page = new WebPage("webpages/java.html");
        System.out.println(page.getSource());


    }
}
