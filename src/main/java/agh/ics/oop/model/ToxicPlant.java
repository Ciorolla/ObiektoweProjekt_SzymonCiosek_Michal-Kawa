package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractPlant;
import javafx.scene.image.Image;

public class ToxicPlant extends AbstractPlant {
    public static final String IMAGE_PATH = "/toxicPlant.jpg";
    public static final Image IMAGE = new Image(IMAGE_PATH);

    public ToxicPlant(Vector2d position, int energy) {
        super(position, (-1)*energy);
    }


    @Override
    public String toString() {
        return "!";
    }

    @Override
    public Image getClassImage(){
        return IMAGE;
    }
}
