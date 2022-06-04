package dev.josiah.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class WebPage {
    private String location = "";
    private String source = "";

    @Override
    public String toString() {
        String Return = "";
        Return += "************ " + location + " ************";
        Return += "\n" + source + "\n";
        Return += "************************************";
        return Return;
    }

    public WebPage() {}

    public WebPage(String location, String source) {
        this.location = location;
        this.source = source;
    }

    public WebPage(String location) {
        this.location = location;
        load();
    }

    public void load() {
        try {
            //System.out.println("Working Directory = " + System.getProperty("user.dir"));
            File myObj = new File(location);
            Scanner myReader = new Scanner(myObj);
            String fileContents = "";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileContents += data + "\n";
            }
            myReader.close();
            //System.out.println("Loaded webpage into source:" + fileContents);
            source = fileContents;
        } catch (FileNotFoundException e) {
            String complaint = "File Not Found.";
            System.out.println(complaint);
            Complain(complaint);
            Complain(e);
            e.printStackTrace();
        } catch (Throwable t) {
            String complaint = "An error occurred.";
            System.out.println(complaint);
            Complain(complaint);
            Complain(t);
            t.printStackTrace();
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
