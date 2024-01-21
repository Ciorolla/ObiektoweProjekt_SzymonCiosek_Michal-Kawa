package agh.ics.oop.model;

public record Boundary(Vector2d lowerLeft, Vector2d upperRight) {
    public boolean isInBoundary(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }
}
