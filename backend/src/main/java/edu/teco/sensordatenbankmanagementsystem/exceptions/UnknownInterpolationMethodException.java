package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * {@link UnknownInterpolationMethodException} is thrown when an interpolation method was suggested that is not
 * recognized by the application
 */
public class UnknownInterpolationMethodException extends IllegalInterpolationParameterException {

    public UnknownInterpolationMethodException(String illegal) {
        super(String.format("the interpolation method %s is unknown", illegal));
    }
}
