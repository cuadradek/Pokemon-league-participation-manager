package cz.muni.fi.pa165.plpm.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * Exception for different cases in service layer,
 * e.g. nickname of new trainer already exists.
 */
public class PlpmServiceException extends DataAccessException {

    public PlpmServiceException(String message) {
        super(message);
    }

    public PlpmServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
