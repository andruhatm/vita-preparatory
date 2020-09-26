package com.company;

import com.company.Entity.EntryTile;
import com.company.Entity.FreeTile;
import com.company.Entity.ObstacleTile;
import com.company.Entity.Tile;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private int currentTurn;
    private Scanner m;
    private Tile Map[][] = new Tile[8][8];

    public void setCurrentTurn() {
        try {
           m = new Scanner(new File("turn.txt"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        currentTurn = m.nextInt();
    }

    public Map(){
        openFile("board_data.txt");
        readFile();
        setCurrentTurn();
    }

    public void setHeights(){
        openFile("heights.txt");
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if((Map[y][x] instanceof FreeTile)){
                    ((FreeTile)Map[y][x]).setHeight((float) m.nextDouble());
                }
                if((Map[y][x] instanceof ObstacleTile)){
                    ((ObstacleTile)Map[y][x]).setHeight((float) m.nextDouble());
                }
            }
        }
    }

    public int getStartTurn() {
        return currentTurn;
    }

    public float getHeightVal(){
        float height = (float) (0.5 + Math.random()*1.1);
        height= (float) (Math.round(height*100d)/100d);
        return height;
    }

    public static ArrayList<Integer> getPeriods() {
        ArrayList<Integer> periods = new ArrayList<>();

        File file = new File("obstacles.txt");
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextInt())
                periods.add(scanner.nextInt());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return periods;
    }

    public Tile getTile(int x, int y){
        Tile index = Map[y][x];
        return index;
    }

    public Tile[][] getMap(){
        return Map;
    }

    public void openFile(String path){
        try{
            m = new Scanner(new File(path));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }
    }

    public void readFile() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                switch (m.nextInt()) {
                    case 0 -> Map[y][x] = new FreeTile(Color.white, y, x, getHeightVal());
                    //closing obstacle
                    case 1 -> Map[y][x] = new ObstacleTile(Color.white, y, x, false, getPeriods().get(0), getHeightVal());
                    case 2 -> Map[y][x] = new EntryTile(Color.yellow, y, x, true);
                    case 3 -> Map[y][x] = new EntryTile(Color.yellow, y, x, false);
                    //opening obstacle
                    case 4 -> Map[y][x] = new ObstacleTile(Color.black, y, x, true, getPeriods().get(1), getHeightVal());
                }
            }
        }
    }
    
}
