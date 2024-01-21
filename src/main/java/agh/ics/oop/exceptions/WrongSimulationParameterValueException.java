package agh.ics.oop.exceptions;

/**
 * exception for when wrong simulation parameter value is passed
 */
public class WrongSimulationParameterValueException extends Exception {
    public WrongSimulationParameterValueException(String message) {
        super(message);
    }
}
