package simulation;

import java.util.ArrayList;
import java.util.Random;

import entities.*;

/*
 * Pasture
 * - maintains an array of the field
 * - provides functionality for Entity Objects to interact with the array
 *   (movement, determining nearby objects, etc.)
 */
public class Pasture {

    /*----------PARAMETERS-----------*/
    private final int NUM_SHEEP = 45;  // Initial sheep population
    private final int NUM_WOLF = 15; // Initial wolf population
    private final int NUM_GRASS = 210; // Initial grass block population

    private final int ANIMALS = 0; // final int to reference entities[row][col][0] which contains the animals
    private final int GRASS = 1; // final int to reference entities[row][col][1] which contains the grass


    /*------------FIELDS-------------*/
    private Entity[][][] entities;
    private final int NUM_COLS;
    private final int NUM_ROWS;


    /**
     * Constructor
     *
     * @param NUM_ROWS (int)
     * @param NUM_COLS (int)
     */
    public Pasture(int NUM_ROWS, int NUM_COLS) {
        this.NUM_ROWS = NUM_ROWS;
        this.NUM_COLS = NUM_COLS;
        entities = new Entity[NUM_ROWS][NUM_COLS][2];

        // create starting Sheep population
        for (int i = 0; i < NUM_SHEEP; i++) {
            placeInitialEntity(new Sheep(this)); // place a Sheep
        }
        // create starting Wolf population
        for(int i = 0; i < NUM_WOLF; i++) {
            placeInitialEntity(new Wolf(this)); // place a Wolf
        }
        // create starting Grass population
        for(int i = 0; i < NUM_GRASS; i++) {
            placeInitialEntity(new Grass(this)); // place a spot of Grass
        }
    }

    /*
     *  Determine if a space is already taken by an Animal
     */
    private boolean hasAnimal(int row, int col) {
        if (entities[row][col][ANIMALS] != null)
            return true;
        return false;
    }

    /*
     * Determine if a space is already covered by grass
     */
    private boolean hasGrass(int row, int col) {
        if (entities[row][col][GRASS] != null)
            return true;
        return false;
    }

    /*
     * Methods to determine if a specific row or column is valid
     */
    private boolean inRowRange(int r) {
        return r >= 0 && r < NUM_ROWS;
    }

    private boolean inColRange(int c) {
        return c >= 0 && c < NUM_COLS;
    }

    /*
     * Add entity to pasture ina random location
     * - Only used when placing initial entities
     * - highly inefficient for large populations!
     * - will freeze the program if no spaces remain!
     */
    private void placeInitialEntity(Entity e) {
        Random rand = new Random();
        int row = rand.nextInt(NUM_ROWS);
        int col = rand.nextInt(NUM_COLS);

        // Find an empty spot
        while (this.hasAnimal(row, col)) {
            row = rand.nextInt(NUM_ROWS);
            col = rand.nextInt(NUM_COLS);
        }

        //Place Entity in location (row, col)
        e.setRow(row);
        e.setCol(col);

        if(e instanceof Grass) {
            entities[row][col][GRASS] = e;
        } else if(e instanceof Animal) {
            entities[row][col][ANIMALS] = e;
        }
    }

    /**
     * Moves an Animal to animal new location
     * - Note: not currently checking for valid moves
     *
     * @param animal Animal to be moved
     * @param newLoc int[row, col]
     */
    public void moveAnimal(Animal animal, int[] newLoc) {
        // Temporarily store the previous row and column
        int oldRow = animal.getRow();
        int oldCol = animal.getCol();

        // Update this Entity's row and column to the new location
        animal.setRow(newLoc[0]);
        animal.setCol(newLoc[1]);

        // Move the Entity to the new location and set its old location to null
        entities[animal.getRow()][animal.getCol()][ANIMALS] = animal;
        entities[oldRow][oldCol][ANIMALS] = null;
    }

    /**
     * Places an animal at the specified 'location' if it is not occupied
     *
     * @param animal animal you want to place
     * @param location an int[] that hold the row,col coordinate of the place you put the animal
     */
    public void placeAnimal(Animal animal, int[] location) {
        if(hasAnimal(location[0], location[1]))
            return; // simply return because an Animal cannot be place there

        // add the passed animal to the pasture
        animal.setRow(location[0]);
        animal.setCol(location[1]);
        entities[location[0]][location[1]][ANIMALS] = animal;
    }

    /**
     * Places grass a specified 'location'
     *
     * @param location
     */
    public void placeGrass(int[] location) {
        if(hasGrass(location[0], location[1]))
            return; // simply return because there is already grass there

        // add new grass to the pasture
        Entity g = new Grass(this);
        g.setRow(location[0]);
        g.setCol(location[1]);
        entities[g.getRow()][g.getCol()][GRASS] = g;
    }

    /**
     * Removes Entity 'e' from the pasture
     *
     * @param e Entity
     */
    public void removeEntity(Entity e) {
        if(e instanceof Animal) {
            entities[e.getRow()][e.getCol()][ANIMALS] = null;
        } else {
            entities[e.getRow()][e.getCol()][GRASS] = null;
        }
    }

    /**
     * Returns a List of all Animal objects in the entities array
     *
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> getAnimals() {
        ArrayList<Entity> a = new ArrayList<>();
        Random rand = new Random();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Entity e = entities[row][col][ANIMALS];
                if (e != null) {
                    // Add to a random location to remove bias
                    int p = rand.nextInt(a.size() + 1);
                    a.add(p, e);
                }
            }
        }
        return a;
    }

    /**
     * Returns a lost of all teh Grass objects in the array
     *
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> getGrass() {
        ArrayList<Entity> a = new ArrayList<>();
        Random rand = new Random();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Entity e = entities[row][col][GRASS];
                if (e != null) {
                    // Add to a random location to remove bias
                    int p = rand.nextInt(a.size() + 1);
                    a.add(p, e);
                }
            }
        }
        return a;
    }

    /**
     * Returns a List of empty positions in the 8 adjacent cells to the target Entity
     *
     * @param e Entity checking for locations in adjacent positions
     * @return ArrayList<int[row, col]> of positions containing no Entities
     */
    public ArrayList<int[]> getFreePositions(Entity e) {

        ArrayList<int[]> a = new ArrayList<>(); //array of x,y positions

        int row = e.getRow();
        int col = e.getCol();

        for(int r = row - 1; r <= row + 1; r++) {
            for(int c = col - 1; c <= col + 1; c++) {
                if(e instanceof Animal) {
                    if(inRowRange(r) && inColRange(c) && !(r == row && c == col) && !hasAnimal(r, c)) {
                        int[] i = {r, c};
                        a.add(i);
                    }
                } else if(e instanceof Grass) {
                    if (inRowRange(r) && inColRange(c) && !(r == row && c == col) && !hasGrass(r,c)) {
                        int[] i = {r,c};
                        a.add(i);
                    }
                }
            }
        }
        return a;
    }
}