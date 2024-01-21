package agh.ics.oop.interfaces;

/**
 * interface for map observers
 */

public interface ChangeListener <T>{
    void objectChanged(T object, String message);
}
