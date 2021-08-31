package edu.teco.sensordatenbankmanagementsystem.exceptions;

public class FunctionQueriedOutsideOfInterpolationIntervalException extends RuntimeException{

    public FunctionQueriedOutsideOfInterpolationIntervalException() {
        super("interpolation function not fit for extrapolation was queried outside of the interval it was specified " +
                "for");
    }

}
