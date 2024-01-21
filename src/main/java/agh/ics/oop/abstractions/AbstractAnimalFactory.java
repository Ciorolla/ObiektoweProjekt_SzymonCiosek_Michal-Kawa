package agh.ics.oop.abstractions;

import agh.ics.oop.interfaces.AbstractFactory;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.util.GenomePattern;
import agh.ics.oop.model.Vector2d;
import java.util.List;

public abstract class AbstractAnimalFactory implements AbstractFactory<AbstractAnimal> {
    protected final int startEnergy;
    protected final int breedingEnergy;
    protected final Boundary boundary;
    protected final GenomePattern genomePattern;

    protected AbstractAnimalFactory(int startEnergy, GenomePattern genomePattern, int breedingEnergy, Boundary boundary) {
        this.startEnergy = startEnergy;
        this.genomePattern = genomePattern;
        this.breedingEnergy = breedingEnergy;
        this.boundary = boundary;
    }

    abstract public AbstractAnimal create();
    abstract public AbstractAnimal create(Vector2d parentPosition, List<Integer> genes);
}
