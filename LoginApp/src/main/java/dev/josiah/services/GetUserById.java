package dev.josiah.services;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class GetUserById {
    static void AuthGetUserById(String id_string) {
        try {
            int id = Integer.parseInt(id_string); // use auto-unboxing
            if (id < 0) {
                throw new RuntimeException();
            }
        } catch (NumberFormatException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (AssertionError e) {
            Complain(e);
            throw new RuntimeException(e);
        }catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }
}
