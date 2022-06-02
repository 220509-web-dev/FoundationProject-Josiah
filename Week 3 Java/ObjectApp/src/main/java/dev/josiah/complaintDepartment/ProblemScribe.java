package dev.josiah.complaintDepartment;

import dev.josiah.entities.UserPriv;
import dev.josiah.utils.ConnectionUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProblemScribe {
    final private static String complaintsFile = "complaints.txt";

    public static void main(String[] args) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            int i = 1;
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void Complain(Throwable ex) {
        setup(); // create file if it doesn't exist
        try (FileWriter writer = new FileWriter(complaintsFile, true)) {
            SimpleDateFormat formatter= new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            writer.write(formatter.format(date) + "\n");
            //  + e.printStackTrace(); + "\n"
            writer.close();
            File file = new File(complaintsFile);
            PrintStream ps = new PrintStream(new FileOutputStream(file, true));
            ex.printStackTrace(ps);
            ps.close();
            System.out.println("Successfully complaints data to file!");
        } catch (IOException e) {
            System.err.println("Could not get access to file " + complaintsFile);
            System.out.println("No data persisted to file");
            //throw new ResourcePersistenceException();
        } catch (Throwable t) {
            System.err.println("Some unexpected exception occurred.");
        }
    }

    private static void setup() {
        // Append newline, date now(), problems text
        try {
            File myObj = new File(complaintsFile);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An exception occurred during ProblemScribe setup.");
            e.printStackTrace();
        }
    }
}