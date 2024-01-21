package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.enums.SimulationState;
import agh.ics.oop.gui.presenter.SimulationPresenter;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.util.CSVFileWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantGrowthPerDay;
    private final List<AbstractAnimal> activeAnimals = new LinkedList<>();
    private final List<AbstractAnimal> deadAnimals = new LinkedList<>();
    private int day = 0;
    private final List<CSVFileWriter> observers = new LinkedList<>();
    private SimulationState state = SimulationState.INACTIVE;


    public Simulation(AbstractWorldMap map, int startingPlantCount, int startingAnimalCount, int plantGrowthPerDay) {
        this.map = map;
        this.plantGrowthPerDay = plantGrowthPerDay;
        map.spawnAnimals(startingAnimalCount);
        map.spawnPlants(startingPlantCount);
        activeAnimals.addAll(map.getAnimals());
    }


    @Override
    public void run() {
        setState(SimulationState.RUNNING);
        while (true) {
            switch (state) {
                case RUNNING -> {
                    var dead = map.removeDeadAnimals(day);
                    deadAnimals.addAll(dead);
                    activeAnimals.removeAll(dead);
                    if (activeAnimals.isEmpty()) {
                        setState(SimulationState.FINISHED);
                        continue;
                    }
                    for (AbstractAnimal animal : activeAnimals) {
                        animal.move(map);
                    }
                    map.consumePlants();
                    activeAnimals.addAll(map.procreateAllAnimals());
                    map.spawnPlants(plantGrowthPerDay);
                    activeAnimals.forEach(AbstractAnimal::incrementAge);
                    day++;
                    simulationChanged(String.join("\n",
                            activeAnimals.stream()
                                    .map(AbstractAnimal::toString)
                                    .toList()) + "\n");
                    map.setInfo(this.toString());
                    suspendSimulation();
                }
                case PAUSED, INACTIVE -> suspendSimulation();
                case INTERRUPTED, FINISHED -> {
                    shutdown();
                    return;
                }
            }
        }
    }

    public void shutdown() {
        this.observers.forEach(o -> {
            try {
                o.closeFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
        System.out.println("Simulation ended on day " + day);
    }

    private void simulationChanged(String message) {
        for (CSVFileWriter observer : observers) {
            observer.objectChanged(this, message);
        }
    }

    public void addObserver(CSVFileWriter observer) {
        this.observers.add(observer);
    }

    public void suspendSimulation() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.join("\n",
                "Day: " + day,
                "Animal count: " + activeAnimals.size(),
                "Free fields: " + map.getFreeFieldsCount(),
                "Most popular genotype: " + map.getMostPopularGenotype(),
                "Average animal energy: " + activeAnimals.stream()
                        .mapToInt(AbstractAnimal::getEnergy)
                        .average()
                        .orElse(0),
                "Average death day" + deadAnimals.stream()
                        .mapToInt(AbstractAnimal::getDeathDay)
                        .average()
                        .orElse(0),
                "Average children number: " + (activeAnimals.stream()
                        .mapToInt(AbstractAnimal::getChildrenNumber)
                        .average()
                        .orElse(0) + deadAnimals.stream()
                .mapToInt(AbstractAnimal::getChildrenNumber)
                .average()
                .orElse(0)));
    }

    public void setState(SimulationState state) {
        this.state = state;
    }

    public SimulationState getState() {
        return state;
    }
}
