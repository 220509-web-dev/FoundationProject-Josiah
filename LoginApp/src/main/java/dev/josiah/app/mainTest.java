package dev.josiah.app;

import dev.josiah.complaintDepartment.AuthExceptions;

import java.util.ArrayList;
import java.util.Arrays;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static java.lang.Math.toIntExact;

public class mainTest {
    public static void main(String[] args) {
        ArrayList<String> userCannotContain = new ArrayList<String>(Arrays.asList(" ", "\n", ""+'\u00A0', "!", "*"));
        String u_bad = "abcde\n";
        String u_good = "abc";
        Boolean tf = userCannotContain.stream().allMatch(t -> !u_bad.contains(t));
        System.out.println(tf);
        tf = userCannotContain.stream().allMatch(t -> !u_good.contains(t));
        System.out.println(tf);

    }
}
