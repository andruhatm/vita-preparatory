package com.company;

import javax.swing.*;

public class Main {
    static JFrame f;
    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        f = new JFrame();
        f.setTitle("Maze");
        f.add(new Board());
        f.setSize(750,500);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public static JFrame getFrame() {
        return f;
    }
}
