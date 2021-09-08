package edu.teco.sensordatenbankmanagementsystem.exceptions;

public class FunctionQueriedOutsideOfInterpolationIntervalException extends RuntimeException {

    public FunctionQueriedOutsideOfInterpolationIntervalException(double x, double lbound, double rbound) {
        super(String.format("interpolation function not fit for extrapolation was queried outside of the interval " +
                "[%s,%s] at %s", lbound, rbound, x));
    }

}
