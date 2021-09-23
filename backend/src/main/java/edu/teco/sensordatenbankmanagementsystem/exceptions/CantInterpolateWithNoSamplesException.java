package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * {@link CantInterpolateWithNoSamplesException} if an interpolation with an empty sample set is attempted
 */
public class CantInterpolateWithNoSamplesException extends IllegalInterpolationParameterException {

    public CantInterpolateWithNoSamplesException() {
        super("can't interpolate with 0 samples");
    }
}
