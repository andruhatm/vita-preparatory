package com.company.Entity;

import java.awt.*;

public class FreeTile extends Tile {

    float height;

    public FreeTile(Color color, int x, int y, float height){
        super(color,x,y);
        this.height=height;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
