package agh.ics.oop.abstractions;

import agh.ics.oop.enums.MapObstacle;
import agh.ics.oop.interfaces.ChangeListener;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;

import java.util.*;
import java.util.stream.Stream;

abstract public class AbstractWorldMap implements WorldMap {
    protected final Boundary boundary;
    protected final int plantEnergy;
    private final AbstractAnimalFactory animalFactory;
    private final int requiredEnergyToReproduce;
    private final int breedingConsumptionEnergy;
    private final UUID id = UUID.randomUUID();
    protected final Map<Vector2d, AbstractPlant> plants = Collections.synchronizedMap(new HashMap<>());
    private final List<ChangeListener> observers = new LinkedList<>();
    protected final Map<Vector2d, TreeSet<AbstractAnimal>> animals = Collections.synchronizedMap(new HashMap<>());
    private String mapInfo = "";


    public AbstractWorldMap(Boundary boundary,
                            int plantEnergy,
                            AbstractAnimalFactory animalFactory,
                            int requiredEnergyToReproduce,
                            int breedingConsumptionEnergy) {
        this.boundary = boundary;
        this.plantEnergy = plantEnergy;
        this.animalFactory = animalFactory;
        this.requiredEnergyToReproduce = requiredEnergyToReproduce;
        this.breedingConsumptionEnergy = breedingConsumptionEnergy;
    }


    public void spawnAnimals(int count) {
        for (int i = 0; i < count; ++i) {
            spawnAnimal();
        }
        mapChanged(this.mapInfo);
    }

    public void spawnPlants(int count) {
        for (int i = 0; i < count; ++i) {
            spawnPlant();
        }
        mapChanged(this.mapInfo);
    }

    public void spawnAnimal() {
        AbstractAnimal animal = animalFactory.create();
        place(animal);
    }

    public abstract void spawnPlant();

    @Override
    public void place(AbstractAnimal animal) {
        Vector2d position = animal.getPosition();
        animals.computeIfAbsent(position, k -> new TreeSet<>());
        animals.get(position).add(animal);
    }

    @Override
    public void move(AbstractAnimal animal) {
        Vector2d oldPosition = animal.getPosition();
        animals.get(oldPosition).remove(animal);
        animal.move(this);
        place(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        var set = animals.get(position);
        if (set != null && !set.isEmpty()) {
            return set.first();
        }
        return plants.get(position);
    }

    public List<AbstractAnimal> getAnimals() {
        return animals.values()
                .stream()
                .map(set -> set.stream().toList())
                .reduce(new LinkedList<>(), (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
    }

    @Override
    public List<WorldElement> getElements() {
        return Stream.concat(
                plants.values()
                    .stream()
                    .map(plant -> (WorldElement) plant),
                getAnimals()
                    .stream()
                    .map(animal -> (WorldElement) animal))
                .toList();
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void mapChanged(String message) {
        this.observers.forEach(observer -> observer.objectChanged(this, message));
    }

    @Override
    public void addObserver(ChangeListener observer) {
        this.observers.add(observer);
    }

    public List<ChangeListener> getObservers() {
        return observers;
    }

    @Override
    public List<AbstractAnimal> procreateAllAnimals() {
        List<AbstractAnimal> children = new LinkedList<>();
        for (TreeSet<AbstractAnimal> set : animals.values()) {
            if (set.size() > 1) {
                AbstractAnimal first = set.first();
                AbstractAnimal second = set.higher(first);
                while (second != null && first != null) {
                    if (first.canReproduce(second, requiredEnergyToReproduce)) {
                        AbstractAnimal child = animalFactory
                                .create(first.getPosition(), first.reproduce(second, breedingConsumptionEnergy));
                        children.add(child);
                        first.addChild(child);
                        second.addChild(child);
                    }
                    first = set.higher(second);
                    if (first == null) {
                        break;
                    }
                    second = set.higher(first);
                }
            }
        }
        for (AbstractAnimal child : children) {
            place(child);
        }
        mapChanged(this.mapInfo);
        return children;
    }

    @Override
    public List<AbstractAnimal> removeDeadAnimals(int day) {
        List<AbstractAnimal> deaths = new LinkedList<>();
        for (TreeSet<AbstractAnimal> set : animals.values()) {
            if (!set.isEmpty()) {
                Iterator<AbstractAnimal> iterator = set.iterator();
                while (iterator.hasNext()) {
                    AbstractAnimal animal = iterator.next();
                    if (!animal.checkIfAlive(day)) {
                        deaths.add(animal);
                        iterator.remove();
                    }
                }
            }
        }
        mapChanged(this.mapInfo);
        return deaths;
    }

    @Override
    public void consumePlants() {
        for (TreeSet<AbstractAnimal> set : animals.values()) {
            if (!set.isEmpty()) {
                AbstractAnimal first = set.first();
                AbstractPlant plant = plants.get(first.getPosition());
                if (plant != null) {
                    first.eatGrass(plant);
                    plants.remove(first.getPosition());
                }
            }
        }
        mapChanged(this.mapInfo);
    }

    @Override
    public MapObstacle canMoveTo(Vector2d position) {
        if (position.getX() < boundary.lowerLeft().getX() || position.getX() > boundary.upperRight().getX()) {
            return MapObstacle.LEFT_RIGHT_BOUND;
        }
        if (position.getY() < boundary.lowerLeft().getY() || position.getY() > boundary.upperRight().getY()) {
            return MapObstacle.TOP_BOTTOM_BOUND;
        }
        return MapObstacle.NONE;
    }

    @Override
    public int getFreeFieldsCount() {
        return this.boundary.upperRight().getX() *
                this.boundary.upperRight().getY() -
                getElements().stream()
                        .map(WorldElement::getPosition)
                        .distinct()
                        .toList()
                        .size();
    }

    @Override
    public Genome getMostPopularGenotype() {
        Map<Genome, Integer> genomeCount = new HashMap<>();
        getAnimals().stream()
                .map(AbstractAnimal::getGenome)
                .forEach(g -> genomeCount.put(g, genomeCount.getOrDefault(g, 0) + 1));
        return genomeCount.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    @Override
    public void setInfo(String info) {
        this.mapInfo = info;
    }


}
