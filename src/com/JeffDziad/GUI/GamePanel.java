package com.JeffDziad.GUI;

import com.JeffDziad.Entities.BodyParts.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    /**
     * Final variables for screen width, screen size, grid size, and timer delay.
     */
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 30;
    private static final int DELAY = 100;
    /**
     * All of the components for the snake game are here.
     * All are private as they are only accessed in this class.
     */
    private int bodyPartsAdded = 0;
    private char direction = 'R';
    private boolean running = false;
    private boolean gameOver = false;
    private Timer timer;
    private Random random;
    private java.util.List<Part> bodyParts;
    private java.util.List<Part> parts;
    private PartFactory factory;
    /**
     * GamePanel() instantiates random, factory, bodyParts, and parts.
     * Sets the panel up for the game and executes setupStartingSnake(),
     * before starting the game to fill the List with the snakes body.
     *
     * Executes startGame() last to ensure the snake has a body.
     */
    GamePanel() {
        random = new Random();
        factory = PartFactory.getInstance();
        bodyParts = new ArrayList<>();
        parts = new ArrayList<>();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        setupStartingSnake();
        startGame();
    }
    /**
     * Adds default parts to the snakes body.
     * Done before the game timer is started.
     */
    public void setupStartingSnake() {
        //Create Head
        bodyParts.add(new Part(new GreenStrategy()));
        //Create attached parts
        bodyParts.add(new Part(new BlueStrategy()));
        bodyParts.add(new Part(new OrangeStrategy()));
        bodyParts.add(new Part(new PurpleStrategy()));
        bodyParts.add(new Part(new RedStrategy()));
        bodyParts.add(new Part(new YellowStrategy()));
    }
    /**
     * Instantiates the timer variable and starts the game timer.
     * Uses a DELAY to keep the game at a steady pace.
     */
    public void startGame() {
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    /**
     *
     * @param g the panels graphics object
     * Draws the startingScreen is running and gameOver are false.
     * If running is true - draws the snake and the parts to grab.
     * If running is false but game over is true - draws the gameOver screen.
     */
    public void draw(Graphics g) {
        if(!running && !gameOver){
            startingScreen(g);
        }
        if(running) {
            for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            //place all obtainable parts
            for(Part part: parts) {
                g.setColor(part.getColor());
                g.fillOval(part.getX(), part.getY(), UNIT_SIZE, UNIT_SIZE);
            }

            for(int i = 0; i < bodyParts.size() - 1; i++) {
                g.setColor(bodyParts.get(i).getColor());
                g.fillRect(bodyParts.get(i).getX(), bodyParts.get(i).getY(), UNIT_SIZE, UNIT_SIZE);
            }
            addPart();
        } else {
            if(gameOver)
            {
                gameOver(g);
            }
        }
    }
    /**
     * Adds parts to the parts list by using the PartFactory class.
     * Each part is given a random X, and Y value.
     * If there are more than 5 parts on the grid, addPart() does nothing.
     */
    public void addPart() {
        if(parts.size() < 5)
        {
            Part newPart = factory.getPart();
            newPart.setX(random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE);
            newPart.setY(random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);
            parts.add(newPart);
        }
    }
    /**
     * Takes the parts in the bodyParts list and iterates over them in reverse.
     * Takes the last part in bodyParts and changes is X and Y to the X and Y of the bodyPart one index away in the negative direction.
     * Changes the direction of the snake based on boolean direction's value.
     */
    public void move() {
        //moves all body parts over one
        for(int i = bodyParts.size() - 1; i > 0; i--) {
            bodyParts.get(i).setX(bodyParts.get(i - 1).getX());
            bodyParts.get(i).setY(bodyParts.get(i - 1).getY());
        }
        switch(direction) {
            case 'U': bodyParts.get(0).setY(bodyParts.get(0).getY() - UNIT_SIZE);
                break;
            case 'D': bodyParts.get(0).setY(bodyParts.get(0).getY() + UNIT_SIZE);
                break;
            case 'L': bodyParts.get(0).setX(bodyParts.get(0).getX() - UNIT_SIZE);
                break;
            case 'R': bodyParts.get(0).setX(bodyParts.get(0).getX() + UNIT_SIZE);
                break;
        }
    }
    /**
     * Iterates over each part on the grid and checks if the head (index 0 of bodyParts)
     * has the same X and Y values.
     * If there is a collision, the index of the part is stored in collisionId and the part is added to the bodyParts.
     * After the loop is over, and since only one part can be collided with at once, the part that was collided with
     * is removed from the parts list.
     */
    public void checkPartCollision() {
        int count = 0;
        int collisionId = -1;
        for(Part part: parts) {
            if(bodyParts.get(0).getX() == part.getX() && bodyParts.get(0).getY() == part.getY()) {
                bodyParts.add(part);
                collisionId = count;
                bodyPartsAdded++;
            }
            count++;
        }
        if(collisionId != -1) { parts.remove(collisionId); }
    }
    /**
     * Iterates over the bodyParts and checks if the head has the same X and Y values and any of its following bodyParts.
     * If there is a collision, set gameOver to true, and running to false.
     * Checks if the X and Y values of the head equal the boarders of the grid to signify a collision with a wall.
     */
    public void checkCollisions() {
        for(int i = bodyParts.size() - 1; i>0; i--) {
            if((bodyParts.get(0).getX() == bodyParts.get(i).getX()) && (bodyParts.get(0).getY() == bodyParts.get(i).getY())) {
                gameOver = true;
                running = false;
            }
        }
        //left side collision
        if(bodyParts.get(0).getX() < 0) {
            gameOver = true;
            running = false;
        }
        //right side collision
        if(bodyParts.get(0).getX() > SCREEN_WIDTH) {
            gameOver = true;
            running = false;
        }
        //top side collision
        if(bodyParts.get(0).getY() < 0) {
            gameOver = true;
            running = false;
        }
        //bottom side collision
        if(bodyParts.get(0).getY() > SCREEN_WIDTH) {
            gameOver = true;
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }
    /**
     *
     * @param g passed in during the draw() method. Used to edit the graphics of the screen.
     * Prints out name of game and how to start.
     */
    public void startingScreen(Graphics g) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("Monospaced Bold", Font.BOLD, 25));
        FontMetrics fm = getFontMetrics(g.getFont());
        g.drawString("Rainbow Snake!", (SCREEN_WIDTH - fm.stringWidth("Rainbow Snake")) / 2, SCREEN_HEIGHT / 5);
        g.drawString("Press SPACE to start!", (SCREEN_WIDTH - fm.stringWidth("Press SPACE to start!")) / 2, SCREEN_HEIGHT / 2);
    }
    /**
     *
     * @param g passed in during the draw() method. Used to edit the graphics of the screen.
     * Prints out that you died and the amount of parts you collected.
     */
    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced Bold", Font.BOLD, 75));
        FontMetrics fm = getFontMetrics(g.getFont());
        g.drawString("You Died!", (SCREEN_WIDTH - fm.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced Bold", Font.BOLD, 40));
        g.drawString("Parts Collected: " + bodyPartsAdded, (SCREEN_WIDTH - fm.stringWidth("Parts Collected: " + bodyPartsAdded) + 150) ,SCREEN_HEIGHT / 2 + 100);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkCollisions();
            checkPartCollision();
        }
        repaint();
    }
    /**
     * Our Key Listener.
     * Based on what key is pressed. MyKeyAdapter will assign the corresponding direction based on the key pressed.
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    running = true;
            }
        }
    }
}
