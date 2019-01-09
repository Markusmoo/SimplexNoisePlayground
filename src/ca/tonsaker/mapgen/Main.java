package ca.tonsaker.mapgen;

import ca.tonsaker.mapgen.debug.DebugWindow;
import ca.tonsaker.mapgen.debug.Log;

import javax.swing.*;

/**
 * Created by Markus on 2/11/2017.
 */
public class Main extends JFrame {

    public Map world;
    public DebugWindow debugWindow;

    private Timer updateTimer;

    public static void main(String[] args){
        new Main();
    }

    private Main(){
        super("RANDOM MAP GENERATION");
        this.setLocation(100,100);
        this.setSize(1000,700);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        world = new Map(this, 2);

        //updateTimer = new Timer(100, e -> update());
        //updateTimer.start();

        debugWindow = new DebugWindow(this);
        Log.setDebugWindow(debugWindow);

        this.setVisible(true);
    }

    public void remapGridSize(int gridSpace){
        world.setGridSpace(gridSpace);
    }

    public void remap(){
        world.randomizeMap();
        world.repaint();
    }
}
