package edu.teco.sensordatenbankmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ObservationController} is the entry point for exception handling.
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Exception handler for {@code SensorNotFoundException}
     *
     * @param exception {@code SensorNotFoundException} to handle
     * @return http response for this exception
     */
    @ExceptionHandler(value = SensorNotFoundException.class)
    public ResponseEntity<Object> handleSensorException(SensorNotFoundException exception) {
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for {@code MethodArgumentNotValidException}
     *
     * @param ex {@code MethodArgumentNotValidException}'s ex gf to handle
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
