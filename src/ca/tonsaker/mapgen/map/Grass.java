package ca.tonsaker.mapgen.map;

import ca.tonsaker.mapgen.Map;

import java.awt.*;

/**
 * Created by Markus on 2/11/2017.
 */
public class Grass extends MapTile{


    public Grass(Map hostMap, int x, int y, int height){
        super(hostMap, x, y, height);
    }

    public void paint(Graphics g){
        if(height < 0 || height > 255){
            g.setColor(Color.GREEN);
        }else{
            g.setColor(new Color(0, height, 0));
        }
        g.fillRect(this.getRealX(), this.getRealY(), hostMap.gridSpace, hostMap.gridSpace);
    }
}
