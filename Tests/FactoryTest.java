import com.JeffDziad.Entities.BodyParts.BodyColorStrategy;
import com.JeffDziad.Entities.BodyParts.PartFactory;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class FactoryTest {

    private BodyColorStrategy strat;
    private PartFactory factory = PartFactory.getInstance();
    private boolean[] possibleColors = new boolean[6];

    private boolean foundAtLeastOne = false;

    @Test
    public void startTest() {
        strat = factory.getPart();
        assertTrue(checkForColors());
    }

    public boolean checkForColors() {
        isBlue();
        isGreen();
        isOrange();
        isPurple();
        isRed();
        isYellow();
        for(boolean bool : possibleColors) {
            if(bool == true) {
                foundAtLeastOne = true;
            }
        }
        return foundAtLeastOne;
    }

    public void isBlue() {
        if(strat.getColor() == Color.BLUE) { possibleColors[0] = true; }
    }
    public void isGreen() {
        if(strat.getColor() == Color.GREEN) { possibleColors[1] = true; }
    }
    public void isOrange() {
        if(strat.getColor() == Color.ORANGE) { possibleColors[2] = true; }
    }
    public void isPurple() {
        if(strat.getColor().equals(new Color(178, 0, 160))) { possibleColors[3] = true; }
    }
    public void isRed() {
        if(strat.getColor() == Color.RED) { possibleColors[4] = true; }
    }
    public void isYellow() {
        if(strat.getColor() == Color.YELLOW) { possibleColors[5] = true; }
    }


}
