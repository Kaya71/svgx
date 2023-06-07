package models;

import java.util.Objects;

public abstract class Figure {
    private int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return id == figure.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public abstract void translate(float horizontal, float vertical);

    public abstract boolean isPointWithinBounds(float x, float y);

    public abstract float getWidth();
    public abstract float getHeight();

    public boolean areBoundsWithinFigure(Figure figure) {
        // Check if all corners of the second figure are within the bounds of the first figure
        return figure.isPointWithinBounds(this.getWidth(), this.getHeight()) &&
                figure.isPointWithinBounds(this.getWidth() + this.getWidth(), this.getHeight()) &&
                figure.isPointWithinBounds(this.getWidth(), this.getHeight() + this.getHeight()) &&
                figure.isPointWithinBounds(this.getWidth() + this.getWidth(), this.getHeight() + this.getHeight());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
