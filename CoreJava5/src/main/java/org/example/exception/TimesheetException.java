package org.example.exception;



public class TimesheetException extends Exception {

    public TimesheetException(String message) {
        super(message);
    }

    public TimesheetException(String message, Throwable cause) {
        super(message, cause);
    }
}

