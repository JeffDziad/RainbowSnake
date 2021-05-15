package com.JeffDziad.Entities.BodyParts;

import java.util.Random;

public class PartFactory {
    /**
     * Singleton Class / Factory Class
     * instance is null until PartFactory's static method getInstace() is called.
     */
    private static PartFactory instance = null;
    private final int MAX_POSSIBILITIES = 5;
    private final int MIN_POSSIBILITIES = 1;

    private PartFactory() {}

    public static PartFactory getInstance() {
        if(instance == null) { instance = new PartFactory(); }
        return instance;
    }
    /**
     * @return random part with colorStrategy
     */
    public Part getPart() {
        int num = getRandomNum();
        if(num == 1) {
            return new Part(new BlueStrategy());
        }else if(num == 2) {
            return new Part(new OrangeStrategy());
        }else if(num == 3) {
            return new Part(new PurpleStrategy());
        }else if(num == 4) {
            return new Part(new RedStrategy());
        }else {
            return new Part(new YellowStrategy());
        }
    }
    private int getRandomNum() {
        Random random = new Random();
        return random.nextInt(MAX_POSSIBILITIES - MIN_POSSIBILITIES + 1) + MIN_POSSIBILITIES;
    }

}
