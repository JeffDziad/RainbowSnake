package com.JeffDziad.Entities.BodyParts;

import java.awt.*;

public class PurpleStrategy implements BodyColorStrategy{
    /**
     * @return rgba value of Purple.
     */
    @Override
    public Color getColor() {
        return new Color(178, 0, 160);
    }
}
