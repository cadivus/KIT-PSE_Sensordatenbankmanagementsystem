package edu.teco.sensordatenbankmanagementsystem.exceptions;

public class CantInterpolateWithNoSamplesException extends IllegalInterpolationParameterException {

    public CantInterpolateWithNoSamplesException() {
        super("can't interpolate with 0 samples");
    }
}
