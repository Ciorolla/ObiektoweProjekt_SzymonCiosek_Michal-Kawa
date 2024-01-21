package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.abstractions.AbstractWorldMap;


public class Globe extends AbstractWorldMap {
    private final Boundary jungle;
    public Globe(Boundary boundary,
                 int plantEnergy,
                 AbstractAnimalFactory animalFactory,
                 int requiredEnergyToReproduce,
                 int breedingConsumptionEnergy) {
        super(boundary, plantEnergy, animalFactory, requiredEnergyToReproduce, breedingConsumptionEnergy);
        int y = boundary.upperRight().getY() / 5;
        int x = boundary.upperRight().getX();
        this.jungle = new Boundary(new Vector2d(0, y*2), new Vector2d(x, y*3));
        System.out.println("Jungle: " + jungle);
    }

    @Override
    public void spawnPlant() {
        if (Math.random() < 0.8) {
            Vector2d position = Vector2d.getRandomVector2d(jungle);
            plants.put(position, new Grass(position, plantEnergy));
        } else {
            Vector2d position = Vector2d.getRandomVector2d(boundary);
            plants.put(position, new Grass(position, plantEnergy));
        }
    }
}
