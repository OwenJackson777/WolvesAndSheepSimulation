package simulation;

import javax.swing.JFrame;

/*
 * Author: Owen Jackson
 * Unit: 3 (Objects)
 * Due Date: 2021-03-15
 * Assignment: Sheep and Wolves
 */

/*
 * Features:
 * - all basic features
 * - wolf chase sheep if in a 1 block radius
 * - an entity counter
 * - atheistic improvements
 */

public class Main {

    public static void main(String[] args) {
        Simulator s = new Simulator();
        JFrame frame = new JFrame("Owen Jackson's Pasture!");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(s.getNUM_COLS() * 10 + 16, s.getNUM_ROWS() * 10 + 38 + 100); //816 x 738
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(s);
    }
}