package ca.tonsaker.mapgen.map;

import ca.tonsaker.mapgen.Map;

import java.awt.*;

public class CustomColor extends MapTile{

    private Color color;

    public CustomColor(Map hostMap, int x, int y, int height, Color color){
        super(hostMap, x, y, height);
        this.color = color;
    }

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(color);
        g.fillRect(this.getRealX()+1, this.getRealY()+1, hostMap.gridSpace -1, hostMap.gridSpace -1);
        g.setColor(c);
    }

}
