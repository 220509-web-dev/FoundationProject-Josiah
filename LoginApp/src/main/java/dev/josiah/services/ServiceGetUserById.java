package dev.josiah.services;

import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static java.lang.Math.toIntExact;

public class ServiceGetUserById {
    final private static Long minId = 0L;             // Using wrapper class so that it can be set to null if need be
    final private static Long maxId = 2147483647L;    // null values tell code to not enforce the min or max restraint

    public static User ServiceIdRequest(String id_feed, UserDAO userDAO) {
        long id = validateId(id_feed);
        int id_int = toIntExact(id); // can be removed later to accommodate >2.1bil DB records
        User user = new User();
        user = userDAO.getUserById(id_int);
        if (user == null) {
            throw new AuthExceptions.UserNotFound("User with ID "+id_int+" not found!");
        }
        return user;
    }
    private static long validateId(String id_feed) {
        if (id_feed == null) {
            Complain("ID was null");
            throw new AuthExceptions.InputNotAnInteger("ID was null");
        }
        long id;
        try {
            id = Long.parseLong(id_feed);
        } catch (NumberFormatException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }

        if ((minId != null && id < minId) || (maxId != null && maxId < id)) {
            throw new AuthExceptions.IdOutOfRange("ID "+id+" was not in the service-enforced range constraint");
        }

        return id;
    }

}
