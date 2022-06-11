package dev.josiah.complaintDepartment;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProblemScribe {
    final private static String complaintsFile = "complaints.txt";

    public static void Complain(Throwable ex) {
        try (FileWriter writer = new FileWriter(complaintsFile, true)) {
            SimpleDateFormat formatter= new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            writer.write(formatter.format(date) + "\n");
            writer.close();

            File file = new File(complaintsFile);
            PrintStream ps = new PrintStream(new FileOutputStream(file, true));
            ex.printStackTrace(ps);
            ps.close();
            System.out.println("Logged an error to " + complaintsFile);
        } catch (Throwable t) {}
    }

    // Debug messages are handled here
    public static void Complain(String complaint) {
        try (FileWriter writer = new FileWriter(complaintsFile, true)) {
            SimpleDateFormat formatter= new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            writer.write(formatter.format(date) + "\n" + complaint + "\n");
            writer.close();
            System.out.println("Logged a debug message to " + complaintsFile);
        } catch (Throwable t) {}
    }
}