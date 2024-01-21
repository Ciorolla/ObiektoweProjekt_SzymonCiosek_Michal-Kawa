package agh.ics.oop.model;


import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.enums.MapObstacle;

import java.util.Random;

public class ToxicMap extends AbstractWorldMap {
    private final Boundary toxicField;

    public ToxicMap(Boundary boundary,
                    int plantEnergy,
                    AbstractAnimalFactory animalFactory,
                    int requiredEnergyToReproduce,
                    int breedingConsumptionEnergy) {
        super(boundary, plantEnergy, animalFactory, requiredEnergyToReproduce, breedingConsumptionEnergy);
        var random = new Random();
        int x = boundary.upperRight().getX() / 2;
        int y = boundary.upperRight().getY() / 2;
        Vector2d lowerLeft = new Vector2d(random.nextInt(1, x-1), random.nextInt(1, y-1));
        Vector2d upperRight = new Vector2d(lowerLeft.getX() + x, lowerLeft.getY() + y);
        toxicField = new Boundary(lowerLeft, upperRight);
        System.out.println("Toxic field: " + toxicField);
    }

    @Override
    public void spawnPlant() {
        Vector2d position = Vector2d.getRandomVector2d(boundary);
        if (toxicField.isInBoundary(position)) {
            plants.put(position, new ToxicPlant(position, plantEnergy));
        } else {
            plants.put(position, new Grass(position, plantEnergy));
        }
    }

    @Override
    public MapObstacle canMoveTo(Vector2d position) {
        if (plants.get(position) instanceof ToxicPlant) {
            return MapObstacle.TOXIC_PLANT;
        }
        return super.canMoveTo(position);
    }

}
