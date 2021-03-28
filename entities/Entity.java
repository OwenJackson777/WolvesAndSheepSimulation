package entities;

import java.awt.Color;

import simulation.Pasture;

/*
 * Entity
 * - The superclass for all the Animal & Grass objects
 */
abstract public class Entity {

    //Reference to the objects location in the pasture
    private int row;
    private int col;

    private Color colour;
    protected Pasture pasture;  // A reference to the object that created this Entity

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     * @param row int
     * @param col int
     * @param colour RGB Color
     */
    public Entity(Pasture pasture, int row, int col, Color colour) {
        this.row = row;
        this.col = col;
        this.colour = colour;
        this.pasture = pasture;
    }

    /*
     * Method used for timing
     * Must be overridden by subclasses
     */
    abstract public void tick();

    //GETTERS AND SETTERS

    public Color getColor() {
        return colour;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
