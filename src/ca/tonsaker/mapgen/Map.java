package ca.tonsaker.mapgen;

import ca.tonsaker.mapgen.map.*;
import ca.tonsaker.mapgen.utilities.OpenSimplexNoise;
import ca.tonsaker.mapgen.utilities.XtraMath;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Markus on 2/11/2017.
 */

public class Map extends JPanel{

    public static boolean SHOW_HEIGHT_ON_MAP = true;

    public static boolean ENABLED_WATER = true;
    public static boolean ENABLED_GRASS = true;
    public static boolean ENABLED_ROCK = true;

    public static int HEIGHT_WATER = 127;
    public static int HEIGHT_GRASS = 38;
    public static int HEIGHT_ROCK = 222;

    public enum Direction{
        NORTH,EAST,SOUTH,WEST,ANY_DIRECTION;
    }

    public OpenSimplexNoise simplexNoise;

    public int gridSpace;
    public int gridMaxX;
    public int gridMaxY;

    JFrame frame;
    HashMap<Integer, MapTile> mapTiles;

    public Map(JFrame frame, int gridSpace){
        this.setBackground(Color.BLACK);
        this.frame = frame;

        frame.add(this);

        mapTiles = new HashMap<>();

        setGridSpace(gridSpace);
        randomizeMap();
    }

    public void randomizeMap(){
        int frequency = 1;
        Random randGen = new Random();
        simplexNoise = new OpenSimplexNoise(randGen.nextLong());

        for(int y = 0; y <= gridMaxY*frequency; y+=frequency){
            for(int x = 0; x <= gridMaxX*frequency; x+=frequency){
//                double noiseRaw = Math.abs(simplexNoise.eval(x, y));
                double noiseRaw = simplexNoise.eval(x, y);
                int noise = (int) XtraMath.map(noiseRaw, -1.0, 1.0, 0.0, 255.0);

                if(Map.ENABLED_WATER && noise < HEIGHT_WATER){
                    addTile(new Water(this,x/frequency,y/frequency,noise),x/frequency,y/frequency);
                }else if(Map.ENABLED_ROCK && noise > HEIGHT_ROCK){
                    addTile(new Rock(this,x/frequency,y/frequency,noise),x/frequency,y/frequency);
                }else if(Map.ENABLED_GRASS){
                    addTile(new Grass(this,x/frequency,y/frequency,noise),x/frequency,y/frequency);
                }
            }
        }
    }

    //TODO Test recursive
    public boolean isAdjacentTileEqual(MapTile tile, Direction dir){
        switch(dir){
            case NORTH: return tile.equals(getAdjacentTile(tile, Direction.NORTH));
            case EAST: return tile.equals(getAdjacentTile(tile, Direction.EAST));
            case SOUTH: return tile.equals(getAdjacentTile(tile, Direction.SOUTH));
            case WEST: return tile.equals(getAdjacentTile(tile, Direction.WEST));
        }
        if(dir.equals(Direction.ANY_DIRECTION)){
            return (isAdjacentTileEqual(tile, Direction.NORTH) ||
                    isAdjacentTileEqual(tile, Direction.EAST) ||
                    isAdjacentTileEqual(tile, Direction.SOUTH) ||
                    isAdjacentTileEqual(tile, Direction.WEST));
        }
        System.err.println("Direction must be either NORTH, EAST, SOUTH, WEST, ANY_DIRECTION.");
        return false;
    }

    public MapTile getAdjacentTile(MapTile tile, Direction dir){
        return getAdjacentTile(tile.getPos().x, tile.getPos().y, dir);
    }

    public MapTile getAdjacentTile(int x, int y, Direction dir){
        switch(dir){
            case NORTH: return getTile(x, y-1);
            case EAST: return getTile(x+1, y);
            case SOUTH: return getTile(x, y+1);
            case WEST: return getTile(x-1, y-1);
            default: System.err.println("Direction must be either NORTH, EAST, SOUTH, WEST.");
        }
        return null;
    }

    public void addTile(MapTile tile, int x, int y){
        mapTiles.put(getKey(x,y), tile);
    }

    public MapTile getTile(int x, int y){
        return mapTiles.get(getKey(x,y));
    }

    public int gridX(int grid){
        return grid/gridSpace;
    }

    public int gridY(int grid){
        return grid/gridSpace;
    }

    public void setGridSpace(int gridSpace){
        this.gridSpace = gridSpace;
        gridMaxX = frame.getWidth()/ gridSpace;
        gridMaxY = frame.getHeight()/ gridSpace;
    }

    private void drawGrid(@NotNull Graphics g){
        g.setColor(new Color(20,20,20));
        for(int x = 0; x <= frame.getWidth(); x += gridSpace){
            g.drawLine(x, 0, x, frame.getHeight());
        }
        for(int y = 0; y <= frame.getHeight(); y += gridSpace){
            g.drawLine(0, y, frame.getWidth(), y);
        }
    }

    private void drawTiles(@NotNull Graphics g){
        for(int x = 0; x <= gridMaxX; x++){
            for(int y = 0; y <= gridMaxY; y++){
                MapTile tmp = mapTiles.get(getKey(x,y));
                if(tmp != null) tmp.drawGraphics(g);
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        Color c = g.getColor();

        drawTiles(g);

        g.setColor(c);
    }

    public static int getKey(int x, int y){
        return (x + " " + y).hashCode();
    }
}
