package com.company.Entity;

import java.awt.*;

public class Tile {
    Color color;
    int x;
    int y;

    public Tile( Color color,int x,int y){
        this.color = color;
        this.x=x;
        this.y=y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
