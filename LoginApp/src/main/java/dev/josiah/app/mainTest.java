package dev.josiah.app;

import dev.josiah.complaintDepartment.AuthExceptions;

import java.util.ArrayList;
import java.util.Arrays;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.AuthUser.checkUsername;
import static java.lang.Math.toIntExact;

public class mainTest {
    public static void main(String[] args) {
        String username = "!@revature.net";
        checkUsername(username);

    }
}
