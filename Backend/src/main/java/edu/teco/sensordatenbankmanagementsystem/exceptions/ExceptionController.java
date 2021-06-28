package edu.teco.sensordatenbankmanagementsystem.exceptions;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The Exceptioncontroller is the entry point for exception handling.
 */
@ControllerAdvice
@CommonsLog(topic = "test")
public class ExceptionController {

    /**
     * Exception handler for {@link SensorNotFoundException}
     *
     * @param exception The SensorNotFoundException to handle
     * @return The ResponseEntity containing a correctly formatted Errormessage as well as the {@link HttpStatus.NOT_FOUND}
     */
    @ExceptionHandler(value = SensorNotFoundException.class)
    public ResponseEntity<Object> handleSensorException(SensorNotFoundException exception) {
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception) {
        log.info("test");
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for {@link MethodArgumentNotValidException}
     *
     * @param ex MethodArgumentNotValidExceptions ex gf to handle
     * @return mapping of invalid argument field names to error concerning that field
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
