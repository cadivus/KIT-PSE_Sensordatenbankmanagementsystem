package edu.teco.sensordatenbankmanagementsystem.exceptions;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The Exceptioncontroller is the entry point for exception handling.
 */
@ControllerAdvice
@CommonsLog(topic = "test")
public class ExceptionController {

    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    /**
     * This is an exception handler for an Object not found exception. Might divide further into Observation
     * and Sensor
     * @param exception the thrown exception to handle
     * @param request is the webrequest that resulted in this exception
     * @return A responseentity for the User to see
     */
    @ExceptionHandler(SensorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(
            SensorNotFoundException exception,
            WebRequest request
    ){
        log.error("Failed to find the requested element", exception);
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Exception handler for {@link MethodArgumentNotValidException}
     *
     * @param ex {@code MethodArgumentNotValidException} to handle
     * @return mapping of invalid argument field names to error concerning that field
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details."
        );

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(),
                    fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    /**
     * This is an exception handler for an InternalServerError exception
     * @param exception the exception to be handled
     * @param request the webrequest that lead to the error
     * @return A response entity that can be shown to the user
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception exception,
            WebRequest request){
        log.error("Unknown error occurred", exception);
        return buildErrorResponse(
                exception,
                "Unknown error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    /**
     * This is a helper method to be able to build an errorresponse without specifying the error message
     * @param exception The exception that was thrown
     * @param httpStatus The httpstatus that is to be returned after the exception occured
     * @param request The webrequest that lead to the exception to be thrown
     * @return
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                httpStatus,
                request);
    }

    /**
     * This is a helper method to be able to build an errorresponse
     * @param exception The exception that was thrown
     * @param message A custom error message if needed can be added here
     * @param httpStatus The httpstatus that is to be returned after the exception occured
     * @param request The webrequest that lead to the exception to be thrown
     * @return
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                message
        );

        if(printStackTrace && isTraceOn(request)){
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * This checks if the traceOn Spring property is set
     * @param request
     * @return
     */
    private boolean isTraceOn(WebRequest request) {
        String [] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }


}
