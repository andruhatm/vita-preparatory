package com.company.Entity;


import java.awt.*;

public class ObstacleTile extends Tile {

    boolean opening;
    int counter;
    float height;

    public ObstacleTile(Color color, int x, int y, boolean opening, int counter, float height){

        super(color,x,y);
        this.opening=opening;
        this.counter=counter;
        this.height=height;
}
    public float getHeight() { return height; }

    public void setHeight(float height) { this.height = height; }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }
}
