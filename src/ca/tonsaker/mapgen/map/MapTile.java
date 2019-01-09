package ca.tonsaker.mapgen.map;

import ca.tonsaker.mapgen.Map;
import ca.tonsaker.mapgen.debug.Log;

import java.awt.*;

/**
 * Created by Markus on 2/11/2017.
 */
public abstract class MapTile {

    Map hostMap;
    int gridX, gridY, height;

    public MapTile(Map hostMap, int gridX, int gridY, int height){
        this.hostMap = hostMap;
        this.gridX = gridX;
        this.gridY = gridY;
        this.height = height;
        if(height < 0 || height > 255){
            Log.d("Height out of range.. ("+height+")");
        }
    }

    public void setPos(Point p){
        this.gridX = p.x;
        this.gridY = p.y;
    }

    public void setPos(int x, int y){
        gridX = x;
        gridY = y;
    }

    public Point getPos(){
        return new Point(gridX, gridY);
    }

    public int getRealX(){
        return hostMap.gridSpace * gridX;
    }

    public int getRealY() { return hostMap.gridSpace * gridY; }

    public int getHeight(){ return this.height; }

    public boolean equals(Object object){
        return object.getClass().equals(this.getClass());
    }

    public final void drawGraphics(Graphics g){
        Color c = g.getColor();
        this.paint(g);
        g.setColor(Color.BLACK);
        if(Map.SHOW_HEIGHT_ON_MAP) g.drawString(String.valueOf(this.getHeight()), getRealX()+1, getRealY()+hostMap.gridSpace);
        g.setColor(c);
    }

    public abstract void paint(Graphics g);

}
