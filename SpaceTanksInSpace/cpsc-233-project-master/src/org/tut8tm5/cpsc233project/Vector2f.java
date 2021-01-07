package org.tut8tm5.cpsc233project;

// immutable, no need to worry about privacy leaks
// you NEVER need to copy a Vector2f
public class Vector2f {

    // useful constants
    public static final Vector2f zero = new Vector2f(0, 0);
    public static final Vector2f one = new Vector2f(1, 1);
    public static final Vector2f right = new Vector2f(1, 0);
    public static final Vector2f up = new Vector2f(0, 1);

    // components
    public final float x;
    public final float y;

    // constructor
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // creates Vector2f aiming at a specific angle
    public Vector2f(float angle) {
        x = (float) Math.cos(angle);
        y = (float) Math.sin(angle);
    }

    // self explanatory
    public boolean equals(Vector2f other) {
        return x == other.x && y == other.y;
    }

    // adds each component
    public Vector2f plus(Vector2f other) {
        return new Vector2f(x + other.x, y + other.y);
    }

    // subtracts each component
    public Vector2f minus(Vector2f other) {
        return this.plus(other.times(-1f));
    }

    // multiplies each component by a coefficient
    public Vector2f times(float coefficient) {
        return new Vector2f(x * coefficient, y * coefficient);
    }

    // multiplies each component
    public Vector2f times(Vector2f other) {
        return new Vector2f(x * other.x, y * other.y);
    }

    // divides each component by a coefficient
    public Vector2f over(float coefficient) {
        return this.times(1f / coefficient);
    }

    // divides each component
    public Vector2f over(Vector2f other) {
        return new Vector2f(x / other.x, y / other.y);
    }

    // returns a vector pointing to `other` with a length equal to their distance
    public Vector2f to(Vector2f other) {
        return other.minus(this);
    }

    // magnitude squared
    public float squareMagnitude() {
        return x * x + y * y;
    }

    // self explanatory
    public float magnitude() {
        return (float) Math.sqrt(squareMagnitude());
    }

    // alias for magnitude
    public float length() {
        return magnitude();
    }

    // returns a vector with the same direction but a length equal to 1
    public Vector2f normalized() {
        return this.over(magnitude());
    }

    // alias for normalized
    public Vector2f direction() {
        return this.normalized();
    }

    // returns the distance to `other`
    public float distanceTo(Vector2f other) {
        return this.to(other).magnitude();
    }

    // returns a point X units in the direction of this vector
    public Vector2f walk(float distance) {
        return this.normalized().times(distance);
    }

    // self explanatory
    public float dot(Vector2f other) {
        return x * other.x + y * other.y;
    }

    // reflects this vector off a plane at a certain normal
    public Vector2f reflectedOff(Vector2f normal) {
        float factor = -2f * normal.dot(this);
        return new Vector2f(factor * normal.x + x, factor * normal.y + y);
    }

    // returns the angle in standard position of this vector
    public float angle() {
        return (float) Math.atan2(y, x);
    }

    // format: ( x, y )
    public String toString() {
        return "( " + x + ", " + y + " )";
    }

    // creates a new Vector2f with a certain X position but the same Y position
    public Vector2f setX(float newX) {
        return new Vector2f(newX, this.y);
    }

    // same thing but for the Y
    public Vector2f setY(float newY) {
        return new Vector2f(this.x, newY);
    }
}