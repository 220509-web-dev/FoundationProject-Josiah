package dev.josiah.services.validation;

import dev.josiah.complaintDepartment.AuthExceptions;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class ValidateID {
    final private static Long minId = 0L;             // Using wrapper class so that it can be set to null if need be
    final private static Long maxId = 2147483647L;    // null values tell code to not enforce the min or max restraint
    final private static int maxLen = 20;             // Larger than this, the input is larger than largest Long value

    public static long validateID(String id_feed) {
        if (id_feed == null) {
            Complain("ID was null");
            throw new AuthExceptions.InputWasNullException("ID was null");
        }
        long id;
        try {
            id = Long.parseLong(id_feed);
        } catch (Throwable t) {
            Complain(t);
            throw new AuthExceptions.InputNotAnIntegerException(id_feed + " could not be parsed as an integer.");
        }

        if (id_feed.length() > maxLen || (minId != null && id < minId) || (maxId != null && maxId < id)) {
            throw new AuthExceptions.ValueOutOfRangeException ("ID "+id+" was not in the service-enforced range constraint");
        }
        return id;
    }
}
