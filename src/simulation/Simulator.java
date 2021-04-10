package simulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Entity;
import entities.Wolf;
import entities.Sheep;

/*
 * Simulator
 * - Creates the GUI
 * - ticks every SPEED milliseconds
 */

@SuppressWarnings("serial")
public class Simulator extends JPanel implements ActionListener {

    /*----------PARAMETERS-----------*/
    private int SPEED = 40;	// 0 (fast) --> 1000 (slow)
    private final int NUM_ROWS = 60;
    private final int NUM_COLS = 80;
    private final Color DIRT = new Color(177, 122, 86);  // ground colour

    /*------------FIELDS-------------*/
    Pasture pasture;
    Timer time;

    /**
     * Constructor
     * - Create a new Pasture and initialize timer.
     * - Adjust animation speed by changing SPEED field above
     */
    public Simulator() {

        //Populate a new world
        this.pasture = new Pasture(NUM_ROWS, NUM_COLS);

        //Start the simulation
        time = new Timer(SPEED, this);
        time.start();
    }

    /*
     * Getters
     */

    public int getNUM_COLS() {
        return NUM_COLS;
    }

    public int getNUM_ROWS() {
        return NUM_ROWS;
    }


    /**
     * Update each Entity in the Pasture
     */
    @Override
    public void actionPerformed(ActionEvent e1) {
        //1 unit of time passes
        ArrayList<Entity> animals = pasture.getAnimals();
        ArrayList<Entity> grass = pasture.getGrass();

        for(Entity e : animals) {
            e.tick();
        }
        for(Entity e : grass) {
            e.tick();
        }

        //Redraw pasture
        repaint();
    }

    /**
     * Redraw Screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw the background
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, NUM_COLS * 10, NUM_ROWS * 12);

        //Draw the ground
        g.setColor(DIRT);
        g.fillRect(0, 0, NUM_COLS * 10, NUM_ROWS * 10);

        //Keeping track of number of entities
        int grassCount = 0, sheepCount = 0, wolfCount = 0;

        //Draw the grass
        ArrayList<Entity> grass;
        grass = pasture.getGrass();

        for(Entity e : grass) {
            g.setColor(e.getColor());
            g.fillRect(e.getCol()*10, e.getRow()*10, 10, 10);
            grassCount++;
        }

        //Draw the animals
        ArrayList<Entity> animals;
        animals = pasture.getAnimals();

        for(Entity e : animals) {
            if(e instanceof Wolf) wolfCount++;
            if(e instanceof Sheep) sheepCount++;
            g.setColor(e.getColor());
            g.fillOval(e.getCol()*10, e.getRow()*10, 10, 10);

            //Giving the animals an outline for athletics
            if(e instanceof Wolf)
                g.setColor(Color.WHITE);
            else
                g.setColor(Color.BLACK);
            g.drawOval(e.getCol()*10, e.getRow()*10, 10, 10);
        }

        //Draw the updated entity counters

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));

        int total = grassCount + wolfCount + sheepCount;
        g.drawString("Total Entities: " + total, 630, 655);

        g.drawString("Number of Sheep: " + sheepCount, 30, 655);
        g.drawString("Number of Wolves: " + wolfCount, 230, 655);
        g.drawString("Number of Grass: " + grassCount, 430, 655);
    }
}