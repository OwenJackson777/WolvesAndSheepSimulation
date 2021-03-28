package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import simulation.Pasture;

/*
 * Sheep
 * - Subclass of Animal
 */
public class Sheep extends Animal {

    private int delay;  // number of ticks between moves

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Sheep(Pasture pasture) {
        super(pasture, 0, 0,new Color(255,255,255)); // color is white

        // Assign a random delay before next move
        Random rand = new Random();
        delay = rand.nextInt(5)+1;
    }

    /**
     *  Timing method
     *  - Sheep moves once every 1-5 ticks (int delay)
     */
    @Override
    public void tick() {

        Random rand = new Random();

        //BEHAVIORS

        //Giving birth if possible
        if(this.getBirthDelay() <= 0 && this.getHunger() >= 500) {
            this.setBirthDelay(rand.nextInt(50) + 150);
            this.giveBirth(this);
        }

        //Move if possible
        if(delay-- <= 0) {
            this.move();

            // Assign a random delay before next move
            delay = rand.nextInt(5)+1;
        }

        //Eat grass if it has grass below it
        eat();

        //Giving it a random age to die at from n to m so that sheep don't die all at once
        die(rand.nextInt(200) + 5000);

        //UPDATING VARIABLES

        this.setBirthDelay(this.getBirthDelay() - 1); // sheep gets closer to giving birth
        this.setHunger(this.getHunger() - 1); // sheep becomes 1 unit more hungry
        this.setAge(this.getAge() + 1); // sheep ages 1 unit
    }

    /*
     * This sheep will eat the grass, making it no longer hungry and will
     * make the gras below disappear(become null in the pasture)
     */
    private void eat() {

        ArrayList<Entity> grass = pasture.getGrass();

        for(Entity g : grass) {

            // if the sheep is standing on grass
            if(g.getRow() == this.getRow() && g.getCol() == this.getCol()) {
                pasture.removeEntity(g);
                this.setHunger(2500); // reverting the animals hunger back to its max
                break;
            }
        }
    }

    /*
     * Removes the animal from the pasture if
     * - it gets to old
     * - it gets to hungry
     */
    private void die(int age) {
        if(this.getAge() >= age || this.getHunger() <= 0) {
            pasture.removeEntity(this);
        }
    }
}