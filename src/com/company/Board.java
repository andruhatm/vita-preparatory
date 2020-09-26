package com.company;

import com.company.Entity.EntryTile;
import com.company.Entity.FreeTile;
import com.company.Entity.ObstacleTile;
import com.company.Entity.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    private Map M;
    private Stone stone;
    private int Turn;
    private JButton step;
    final int periodClose = Map.getPeriods().get(0);
    final int periodOpen = Map.getPeriods().get(1);
    boolean exit = false;

    Board(){
        M = new Map();
        M.setHeights();
        Turn = M.getStartTurn();
        initializeObstacles();
        stone = new Stone(getStartingXY().get(0),getStartingXY().get(1),42,42,Color.blue);
        System.out.println("Stone: x "+getStartingXY().get(0)+" y "+getStartingXY().get(1));

        step = new JButton("Шаг");

        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(exit){
                    Main.getFrame().dispose();
                }
                //прибавить счетчики перегородок
                obstacleIterator();

                repaint();
                Turn++;
                analyze();
            }
        });

        this.setLayout(null);
        step.setBounds(500,160,125,45);
        this.add(step);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    //analyze current board info and ends program if needed
    public void analyze(){

        //filling arrayList with nearest tiles
        int defX=0,defY=0,coordX=0,coordY=0;
        ArrayList<Tile> tiles= new ArrayList<Tile>();

        defX=stone.getX();
        defY=stone.getY();
        System.out.println(coordX+" "+coordY);

        tiles.add(M.getTile(defX,defY));
        if(M.getTile(defX,defY).getColor()==Color.BLACK){
            stone.setColor(Color.red);
            exit("D:/result.txt","Камень был безжалостно заблокирован препятствием во время хода: ",Turn);
        }

        coordX=defX;coordY=defY-1;
        if(coordX>=0&&coordX<=7&&coordY>=0&&coordY<=7)
        tiles.add(M.getTile(coordX,coordY));

        coordX=defX-1;coordY=defY;
        if(coordX>=0&&coordX<=7&&coordY>=0&&coordY<=7)
        tiles.add(M.getTile(coordX,coordY));

        coordX=defX;coordY=defY+1;
        if(coordX>=0&&coordX<=7&&coordY>=0&&coordY<=7)
        tiles.add(M.getTile(coordX,coordY));

        coordX=defX+1;coordY=defY;
        if(coordX>=0&&coordX<=7&&coordY>=0&&coordY<=7)
        tiles.add(M.getTile(coordX,coordY));


        //choosing starting minValue
        float minHeight = 0;
        if(tiles.get(0) instanceof EntryTile){
            minHeight= (float) 2.0;
        }
        else if(tiles.get(0) instanceof ObstacleTile){
            minHeight = ((ObstacleTile) tiles.get(0)).getHeight();
        }
        else if(tiles.get(0) instanceof FreeTile){
            minHeight = ((FreeTile) tiles.get(0)).getHeight();
        }

        //checking tiles
        int minCount=0;
        for(int i=0;i<tiles.size();i++){
            if(tiles.get(i).getColor() == Color.YELLOW && !((EntryTile)tiles.get(i)).isEntry()){
                minCount=i;
                exit("D:/result.txt","Шар чудом достиг финиша. Ход: ",Turn-1);
            }
            if(tiles.get(i).getColor() == Color.white){
                if(tiles.get(i) instanceof FreeTile){
                    if(((FreeTile) tiles.get(i)).getHeight() < minHeight){
                        minHeight = ((FreeTile) tiles.get(i)).getHeight();
                        minCount = i;
                    }
                }
                if(tiles.get(i) instanceof ObstacleTile) {
                    if (((ObstacleTile) tiles.get(i)).getHeight() < minHeight) {
                        minHeight = ((ObstacleTile) tiles.get(i)).getHeight();
                        minCount = i;
                    }
                }
            }
        }

        if(minCount==0&&!exit){
            stone.setColor(Color.green);
            exit("D:/result.txt","Шар закатился в тупик. Ход: ",Turn);
        }
        else{
            System.out.println("id tile "+minCount+" x "+tiles.get(minCount).getY()+" y "+tiles.get(minCount).getX());
            stone.setX(tiles.get(minCount).getY());
            stone.setY(tiles.get(minCount).getX());
        }

    }

    //loads data to obstacles
    public void initializeObstacles(){
        for(int y=0;y<8;y++) {
            for (int x = 0; x < 8; x++) {
                if(M.getTile(x,y) instanceof ObstacleTile){
                    //closing
                    if(!((ObstacleTile) M.getTile(x, y)).isOpening()){
                        ((ObstacleTile) M.getTile(x, y)).setCounter(periodClose - (Turn % periodClose));
                    }
                    //opening
                    if(((ObstacleTile) M.getTile(x, y)).isOpening()){
                        ((ObstacleTile) M.getTile(x, y)).setCounter(periodOpen - (Turn % periodOpen));
                    }
                }
            }
        }
    }

    //iterate obstacle counters on each turn
    public void obstacleIterator(){

        for(int y=0;y<8;y++) {
            for (int x = 0; x < 8; x++) {
                if(M.getTile(x,y) instanceof ObstacleTile){
                    //closing
                    if(!((ObstacleTile) M.getTile(x, y)).isOpening()){
                        ((ObstacleTile) M.getTile(x, y)).setCounter(((ObstacleTile) M.getTile(x, y)).getCounter()-1);
                        if(((ObstacleTile) M.getTile(x, y)).getCounter()==0){
                            M.getTile(x, y).setColor(Color.black);
                        }
                        if(((ObstacleTile) M.getTile(x, y)).getCounter()==-1){
                            M.getTile(x, y).setColor(Color.white);
                            ((ObstacleTile) M.getTile(x, y)).setCounter(periodClose-1);
                        }
                    }
                    //opening
                    if(((ObstacleTile) M.getTile(x, y)).isOpening()){
                        ((ObstacleTile) M.getTile(x, y)).setCounter(((ObstacleTile) M.getTile(x, y)).getCounter()-1);
                        if(((ObstacleTile) M.getTile(x, y)).getCounter()==0){
                            M.getTile(x, y).setColor(Color.white);
                        }
                        if(((ObstacleTile) M.getTile(x, y)).getCounter()==-1){
                            M.getTile(x, y).setColor(Color.black);
                            ((ObstacleTile) M.getTile(x, y)).setCounter(periodOpen-1);
                        }
                    }
                }
            }
        }
    }

    //returns starting tile coordinates
    public ArrayList<Integer> getStartingXY(){
        ArrayList<Integer> coordinates = new ArrayList<>();
        for(int y=0;y<8;y++) {
            for (int x = 0; x < 8; x++) {
                if (M.getTile(x,y).getColor() == Color.yellow && ((EntryTile) M.getTile(x,y)).isEntry()) {
                    coordinates.add(0,x);
                    coordinates.add(1,y);
                }
            }
        }
        return coordinates;
    }

    //writes exit info
    public void exit(String path,String text,int Turn){
        exit = true;
        try{
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(text.getBytes());
            fos.write(String.valueOf(Turn).getBytes());
            fos.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //paint on map info
    public void paint(Graphics graphics){
        super.paint(graphics);

        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                if(M.getTile(x,y).getColor()==Color.YELLOW){
                    graphics.setColor(Color.YELLOW);
                }
                else if(M.getTile(x,y).getColor()==Color.WHITE){
                    graphics.setColor(Color.WHITE);
                }
                else if(M.getTile(x,y).getColor()==Color.black){
                    graphics.setColor(Color.black);
                }
                graphics.fillRect(42*x+50,42*y+50,42,42);
                graphics.setColor(Color.BLUE);
                graphics.drawRect(42*x+50,42*y+50,42,42);

            }
        }
        graphics.setColor(stone.getColor());
        graphics.drawOval(stone.getX()*42+50,stone.getY()*42+50,stone.getMapX(),stone.getMapY());
        graphics.fillOval(stone.getX()*42+50,stone.getY()*42+50,stone.getMapX(),stone.getMapY());
    }
}
