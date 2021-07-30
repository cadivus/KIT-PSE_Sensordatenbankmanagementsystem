package edu.teco.sensordatenbankmanagementsystem.exceptions;

public class UnknownInterpolationMethodException extends IllegalInterpolationParameterException {

    public UnknownInterpolationMethodException(String illegal) {
        super(String.format("the interpolation method %s is unknown", illegal));
    }
}
