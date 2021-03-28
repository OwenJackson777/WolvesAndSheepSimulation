package entities;

import simulation.Pasture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Animal
 * - Subclass of Entity
 */

abstract public class Animal extends Entity {

    private int age = 0; // number of ticks the animal has had total to tell its age
    private int hunger = 2500; // numeric value for the level of hunger (since the last time it ate)
    private int birthDelay; // number of ticks between when the animal gives birth

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Animal(Pasture pasture, int row, int col, Color color) {
        super(pasture, row, col, color);

        Random rand = new Random();

        //Initializing the birth delay with a random int
        if(this instanceof Wolf)
            birthDelay = rand.nextInt(50) + 420;
        else if(this instanceof Sheep)
            birthDelay = rand.nextInt(50) + 300;
    }

    //SETTERS AND GETTERS

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getBirthDelay() {
        return birthDelay;
    }

    public void setBirthDelay(int birthDelay) {
        this.birthDelay = birthDelay;
    }

    /*
     * Get an ArrayList of available locations and pick one randomly
     */
    protected void move() {
        Random rand = new Random();

        ArrayList<int[]> a = pasture.getFreePositions(this);
        if (a.size() > 0) {
            int moveTo = rand.nextInt(a.size());  //choose a random location
            int[] newLoc = a.get(moveTo);
            pasture.moveAnimal(this, newLoc);

        }
    }

    /*
     * Places a new animal of its type somewhere around the Animal passed('a') if
     * - it has a space to put it
     */
    protected void giveBirth(Animal a) {

        Random rand = new Random();
        ArrayList<int[]> arr = pasture.getFreePositions(a);

        if(a instanceof Wolf && !arr.isEmpty()) {
            pasture.placeAnimal(new Wolf(pasture), arr.get(rand.nextInt(arr.size())));
        } else if(a instanceof Sheep && !arr.isEmpty()) {
            pasture.placeAnimal(new Sheep(pasture), arr.get(rand.nextInt(arr.size())));
        }
    }
}