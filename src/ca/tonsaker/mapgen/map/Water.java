package ca.tonsaker.mapgen.map;

import ca.tonsaker.mapgen.Map;

import java.awt.*;

public class Water extends MapTile {

    public Water(Map hostMap, int x, int y, int height){
        super(hostMap, x, y, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(this.getRealX(), this.getRealY(), hostMap.gridSpace, hostMap.gridSpace);
    }
}
