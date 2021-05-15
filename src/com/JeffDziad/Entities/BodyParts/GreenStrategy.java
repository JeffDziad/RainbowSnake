package com.JeffDziad.Entities.BodyParts;

import java.awt.*;

public class GreenStrategy implements BodyColorStrategy{
    /**
     * @return color Green.
     * Only used by the head of snake.
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
