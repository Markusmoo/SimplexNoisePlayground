package ca.tonsaker.mapgen.map;

import ca.tonsaker.mapgen.Map;

import java.awt.*;

public class Rock extends MapTile {

    public Rock(Map hostMap, int x, int y, int height){
        super(hostMap, x, y, height);
    }

    public void paint(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(this.getRealX()+1, this.getRealY()+1, hostMap.gridSpace, hostMap.gridSpace);
    }
}
