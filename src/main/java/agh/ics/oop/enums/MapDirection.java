package agh.ics.oop.enums;

import agh.ics.oop.model.Vector2d;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case SOUTHEAST -> new Vector2d(1, -1);
        };
    }

    public MapDirection next(int rotation) {
        return MapDirection.values()[(rotation + this.ordinal()) % 8];
    }

    public static MapDirection random() {
        return MapDirection.values()[new Random().nextInt(0,8)];
    }
}
