package agh.ics.oop.util;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;


public class CrazyAnimalFactory extends AbstractAnimalFactory {
    public CrazyAnimalFactory(int startEnergy, GenomePattern genomePattern, int breedingEnergy, Boundary boundary) {
        super(startEnergy, genomePattern, breedingEnergy, boundary);
    }

    @Override
    public AbstractAnimal create() {
        return new CrazyAnimal(Vector2d.getRandomVector2d(this.boundary),
                this.startEnergy,
                this.genomePattern.create());
    }

    @Override
    public AbstractAnimal create(Vector2d parentPosition, List<Integer> genes) {
        return new CrazyAnimal(parentPosition,
                this.breedingEnergy*2,
                this.genomePattern.create(new ArrayList<>(genes)));
    }
}
