package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * {@link BadSortingTypeStringException} is thrown when a sorting type string was provided that does not fit the
 * required format
 */
public class BadSortingTypeStringException extends RuntimeException {

    public BadSortingTypeStringException() {
        super("sorting type string should be (sorting_column)-(order), (order)={asc,dsc}");
    }
}
