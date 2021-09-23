package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * {@link IllegalInterpolationParameterException} is thrown when an illegal interpolation parameter was provided to
 * an interpolation method
 */
public class IllegalInterpolationParameterException extends RuntimeException {

    public IllegalInterpolationParameterException(String message) {
        super(message);
    }

}
