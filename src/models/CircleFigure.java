package models;

import java.util.Objects;

public class CircleFigure extends Figure{
    private float cx;
    private float cy;
    private final float radius;
    private final String fill;


    public CircleFigure(float cx, float cy, float radius, String fill) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.fill = fill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CircleFigure that = (CircleFigure) o;
        return Float.compare(that.cx, cx) == 0 && Float.compare(that.cy, cy) == 0 && Float.compare(that.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cx, cy, radius);
    }

    @Override
    public String toString() {
        return "\n("+ getId()+")Circle:" + "\ncx:"+cx+" \ncy:"+cy+" \nradius:"+radius+" \ncolor:"+fill;
    }

    @Override
    public void translate(float horizontal, float vertical) {
        this.cx+=horizontal;
        this.cy+=vertical;
    }

    @Override
    public boolean isPointWithinBounds(float x, float y) {
        // Calculate the distance between the point and the center of the circle
        double distance = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));

        return distance <= radius;
    }

    @Override
    public float getWidth() {
        return radius * 2;
    }

    @Override
    public float getHeight() {
        return radius * 2;
    }

    public float getCx() {
        return cx;
    }
    public float getCy() {
        return cy;
    }
    public float getRadius() {
        return radius;
    }
    public String getFill() {
        return fill;
    }
}
