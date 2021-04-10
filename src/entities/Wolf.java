package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import simulation.Pasture;

/*
 * Wolf
 * - Subclass of Animal
 */
public class Wolf extends Animal {

    private int delay;  // number of ticks between moves

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Wolf(Pasture pasture) {
        super(pasture, 0, 0,new Color(75,75,75)); // color is grey

        // Assign a random delay before next move
        Random rand = new Random();
        delay = rand.nextInt(5)+1;
    }

    /**
     *  Timing method
     *  - Wolf moves once every 1-5 ticks (int delay)
     */
    @Override
    public void tick() {

        Random rand = new Random();

        //BEHAVIORS

        //Giving birth if possible
        if(this.getBirthDelay() <= 0 && this.getHunger() >= 500) {
            this.setBirthDelay(rand.nextInt(50) + 420);
            this.giveBirth(this);
        }

        // Move if possible
        if(delay-- <= 0) {
            this.move();

            // Assign a random delay before next move
            delay = rand.nextInt(5)+1;
        }

        //eat a sheep if one is around it
        eat();

        // Giving it a random age to die at from n to m so that sheep don't die all at once
        die(rand.nextInt(200) + 5000);

        //UPDATING VARIABLES

        this.setBirthDelay(this.getBirthDelay() - 1);
        this.setHunger(this.getHunger() - 1);
        this.setAge(this.getAge() + 1);
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

    /*
     * Eats the sheep that is within a 1 block radius of it
     */
    private void eat() {
        ArrayList<Entity> arr = pasture.getAnimals();

        for(Entity a : arr) {
            if(a instanceof Sheep) {

                //if the sheep is within range
                if((this.getCol() == a.getCol() || this.getCol() + 1 == a.getCol() || this.getCol() - 1 == a.getCol()) &&
                (this.getRow() == a.getRow() || this.getRow() + 1 == a.getRow() || this.getRow() - 1 == a.getRow())) {

                    int[] loc = {a.getRow(), a.getCol()}; // x, y location
                    pasture.moveAnimal(this, loc); // move over the sheep
                    this.setHunger(2500); // setting the hunger back is initial amount
                    return;
                }
            }
        }
    }
}