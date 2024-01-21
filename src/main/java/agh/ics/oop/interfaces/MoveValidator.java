package agh.ics.oop.interfaces;

import agh.ics.oop.enums.MapObstacle;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Vector2d;

public interface MoveValidator {

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    MapObstacle canMoveTo(Vector2d position);

    /**
     * Return current map boundries.
     * @return Boundary object that specifies map boundries.
     */
    Boundary getCurrentBounds();
}
