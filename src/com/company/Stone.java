package com.company;

import java.awt.*;

public class Stone {
    private int x,y;
    private int mapX,mapY;
    Color color;

    public Stone(int x, int y, int mapX, int mapY,Color color){

        this.x=x;
        this.y=y;
        this.color = color;
        this.mapX=mapX;
        this.mapY=mapY;
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

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public void move(int dx, int dy, int mapx, int mapy){
        x+= dx;
        y+= dy;
        mapX+=mapx;
        mapY+=mapy;
    }
}
