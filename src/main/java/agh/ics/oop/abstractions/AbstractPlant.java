package agh.ics.oop.abstractions;

import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.model.Vector2d;

public abstract class AbstractPlant implements WorldElement {
    private final Vector2d position;
    private final int energy;

    public AbstractPlant(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }
}
