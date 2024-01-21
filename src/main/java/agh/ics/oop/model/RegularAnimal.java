package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import javafx.scene.image.Image;


public class RegularAnimal extends AbstractAnimal {
    private static final String IMAGE_PATH = "/animal.jpeg";
    private static final String STRIPPED_IMAGE_PATH = IMAGE_PATH.substring(0, IMAGE_PATH.length() - 5);
    private static final Image IMAGE_HIGH_ENERGY = new Image(STRIPPED_IMAGE_PATH + "HighEnergy.jpg");
    private static final Image IMAGE_LOW_ENERGY = new Image(STRIPPED_IMAGE_PATH + "LowEnergy.jpg");
    private static final Image IMAGE = new Image(IMAGE_PATH);


    public RegularAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        super(initialPosition, initialEnergy, genome);
    }

    @Override
    public Image getClassImage() {
        if (this.getEnergy() > 10*REQUIRED_ENERGY_TO_MOVE)
            return IMAGE_HIGH_ENERGY;
        else if (this.getEnergy() > 5*REQUIRED_ENERGY_TO_MOVE)
            return IMAGE;
        else
            return IMAGE_LOW_ENERGY;
    }
}

