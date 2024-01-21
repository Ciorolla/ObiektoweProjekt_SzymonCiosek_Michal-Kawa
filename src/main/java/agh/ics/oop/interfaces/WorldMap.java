package agh.ics.oop.interfaces;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface WorldMap extends MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void place(AbstractAnimal animal);
    void move(AbstractAnimal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    /**
     * Return a list of all animals on the map.
     *
     * @return List of all animals on the map.
     */
    List<WorldElement> getElements();

    void spawnPlants(int count);

    UUID getId();

    void mapChanged(String message);

    void addObserver(ChangeListener observer);

    List<ChangeListener> getObservers();

    List<AbstractAnimal> procreateAllAnimals();

    List<AbstractAnimal> removeDeadAnimals(int day);

    void consumePlants();

    List<AbstractAnimal> getAnimals();

    int getFreeFieldsCount();

    Genome getMostPopularGenotype();

    void setInfo(String info);
}
