package com.JeffDziad.Entities.BodyParts;

import java.awt.*;

public class Part implements BodyColorStrategy{
    /**
     * The backbone of the snake parts.
     * Is either stored in bodyParts or parts.
     *
     * Contains private X and Y values with their getters and setters.
     */
    private int x;
    private int y;

    private BodyColorStrategy colorStrat;

    public int getX() {
        return x;
    }

    /**
     * @param color is the color of the body that is randomly picked in the factory,
     * or hardcoded.
     */
    public Part(BodyColorStrategy color)
    {
        colorStrat = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the specific color based on the classes assigned BodyColorStrategy.
     */
    @Override
    public Color getColor() {
        return colorStrat.getColor();
    }
}
