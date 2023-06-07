package models;

import java.util.Objects;

public class LineFigure extends Figure{
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private final String stroke;


    public LineFigure(float x1, float y1, float x2, float y2, String stroke) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.stroke=stroke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LineFigure that = (LineFigure) o;
        return Float.compare(that.x1, x1) == 0 && Float.compare(that.y1, y1) == 0 && Float.compare(that.x2, x2) == 0 && Float.compare(that.y2, y2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x1, y1, x2, y2);
    }

    @Override
    public String toString() {
        return "\n("+ getId()+")Line: " + "\nx1:"+ x1 + "\ny1: "+ y1+" \nx2:"+x2+" \ny2:"+y2+"\ncolor: "+stroke;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public String getStroke() {
        return stroke;
    }

    @Override
    public void translate(float vertical, float horizontal) {
        this.x1+=horizontal;
        this.x2+=horizontal;
        this.y1+=vertical;
        this.y2+=vertical;
    }

    @Override
    public boolean isPointWithinBounds(float x, float y) {
        // Check if the point lies on the line segment
        float minX = Math.min(x1, x2);
        float maxX = Math.max(x1, x2);
        float minY = Math.min(y1, y2);
        float maxY = Math.max(y1, y2);

        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    @Override
    public float getWidth() {
        return Math.abs(x2 - x1);
    }

    @Override
    public float getHeight() {
        return Math.abs(y2 - y1);
    }
}
