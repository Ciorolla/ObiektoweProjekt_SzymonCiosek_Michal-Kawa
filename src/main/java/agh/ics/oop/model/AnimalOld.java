//package agh.ics.oop.model;
//
//import agh.ics.oop.interfaces.WorldElement;
//
//public class Animal implements WorldElement {
//    private MapDirection orientation;
//    private Vector2d position;
//
//    public Animal(Vector2d position) {
//        this.position = position;
//        this.orientation = MapDirection.NORTH;
//    }
//
//    public Animal() {
//        this(new Vector2d(2,2));
//    }
//
//    @Override
//    public String toString() {
//        return switch (orientation) {
//            case NORTH -> "N";
//            case SOUTH -> "S";
//            case WEST -> "W";
//            case EAST -> "E";
//        };
//    }
//
//    public boolean isAt(Vector2d position) {
//        return this.position.equals(position);
//    }
//
//    public void move(MoveDirection direction, MoveValidator validator) {
//        switch (direction) {
//            case LEFT -> orientation = orientation.previous();
//            case RIGHT -> orientation = orientation.next();
//            case FORWARD -> {
//                Vector2d newPosition = position.add(orientation.toUnitVector());
//                if (validator.canMoveTo(newPosition)) {
//                    position = newPosition;
//                }
//            }
//            case BACKWARD -> {
//                Vector2d newPosition = position.subtract(orientation.toUnitVector());
//                if (validator.canMoveTo(newPosition)) {
//                    position = newPosition;
//                }
//            }
//        }
//    }
//
//    public MapDirection getOrientation() {
//        return orientation;
//    }
//
//    @Override
//    public Vector2d getPosition() {
//        return position;
//    }
//}

