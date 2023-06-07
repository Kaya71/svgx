package models;

import java.util.Objects;

public class RectangleFigure extends Figure{
    private float x;
    private float y;
    private final float width;
    private final float height;
    private final String fill;


    public RectangleFigure(float x, float y, float height, float width, String fill) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fill = fill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RectangleFigure that = (RectangleFigure) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0 && Float.compare(that.width, width) == 0 && Float.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y, width, height);
    }

    @Override
    public String toString() {
        return "\n("+ getId()+")Rectangle:" + "\nx:"+x+" \ny:"+y+" \nheight:"+height+"\nwidth:"+width+" \ncolor:"+fill;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void translate(float horizontal, float vertical) {
        this.x+=horizontal;
        this.y+=vertical;
    }
    @Override
    public boolean isPointWithinBounds(float x, float y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getFill() {
        return fill;
    }
}
