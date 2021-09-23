package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * {@link FunctionQueriedOutsideOfInterpolationIntervalException} is thrown when an interpolation function is queried
 * outside of its interpolation interval and the interpolation function is not fit for extrapolation
 */
public class FunctionQueriedOutsideOfInterpolationIntervalException extends RuntimeException {

    public FunctionQueriedOutsideOfInterpolationIntervalException(double x, double lbound, double rbound) {
        super(String.format("interpolation function not fit for extrapolation was queried outside of the interval " +
                "[%s,%s] at %s", lbound, rbound, x));
    }

}
