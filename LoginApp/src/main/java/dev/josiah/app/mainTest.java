package dev.josiah.app;

import dev.josiah.complaintDepartment.AuthExceptions;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static java.lang.Math.toIntExact;

public class mainTest {
    public static void main(String[] args) {
        throw new AuthExceptions.UserNotFound("User with ID ... not found!");
        //throw new RuntimeException("message111");
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
