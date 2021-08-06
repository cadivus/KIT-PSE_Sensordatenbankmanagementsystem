package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * thrown when an illegal parameter for interpolation was entered
 */
public class IllegalInterpolationParameterException extends RuntimeException {

    public IllegalInterpolationParameterException(String message) {
        super(message);
    }

}
