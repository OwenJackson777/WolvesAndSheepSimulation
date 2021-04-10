package entities;

import simulation.Pasture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Grass
 * - A subclass of entity
 */

public class Grass extends Entity {

    private int delay;

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Grass(Pasture pasture) {
        super(pasture, 0, 0, new Color(0, 255, 0));
        Random rand = new Random();
        delay = rand.nextInt(50) + 200;
    }

    public void tick() {
        // Move when delay counts down to 0
        if(delay-- == 0) {
            growAdjacent(pasture);

            // Assign a random delay before next move
            Random rand = new Random();
            delay = rand.nextInt(50) + 200;
        }

        // Other stuff this Entity does goes here
    }

    private void growAdjacent(Pasture pasture) {
        Random rand = new Random();

        ArrayList<int[]> a = pasture.getFreePositions(this);
        if (a.size() > 0) {
            int moveTo = rand.nextInt(a.size());  //choose a random location
            int[] newLoc = a.get(moveTo);
            pasture.placeGrass(newLoc);
        }
    }
}