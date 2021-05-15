package com.JeffDziad.GUI;

import javax.swing.*;

public class GameFrame extends JFrame {
    /**
     * GameFrame() is called once when program is started.
     * Adds a new GamePanel() to the frame and sets the frame up.
     */
    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Rainbow Snake!");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
