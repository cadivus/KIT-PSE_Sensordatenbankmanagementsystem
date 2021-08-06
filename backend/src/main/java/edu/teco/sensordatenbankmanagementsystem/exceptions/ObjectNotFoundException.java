package edu.teco.sensordatenbankmanagementsystem.exceptions;

/**
 * The ObjectNotFoundException is thrown when a sensor was attempted to be found, but wasn't able to be found.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(){
        super();
    }
    public ObjectNotFoundException(String message){
        super(message);
    }
}
