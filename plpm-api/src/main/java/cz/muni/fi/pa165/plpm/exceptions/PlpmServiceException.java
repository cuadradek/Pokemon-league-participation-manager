package cz.muni.fi.pa165.plpm.exceptions;

/**
 * Checked exception for different cases in service layer,
 * e.g. nickname of new trainer already exists.
 */
public class PlpmServiceException extends Exception {

    public PlpmServiceException(String message) {
        super(message);
    }

    public PlpmServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlpmServiceException(Throwable cause) {
        super(cause);
    }
}
