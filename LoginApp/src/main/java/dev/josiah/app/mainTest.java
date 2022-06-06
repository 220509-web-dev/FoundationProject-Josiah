package dev.josiah.app;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class mainTest {
    public static void main(String[] args) {

        throw new RuntimeException("message111");
//        try {
//            int i = 2;
//            int g = 2/0;
//        } catch (Throwable e) {
//            Complain(e);
//            throw new RuntimeException(e);
//        }
//        System.out.println("Will we get here?");
    }
}
