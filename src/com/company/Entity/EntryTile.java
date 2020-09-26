package com.company.Entity;

import java.awt.*;

public class EntryTile extends Tile {

    boolean entry;

    public boolean isEntry() {
        return entry;
    }

    public void setEntry(boolean entry) {
        this.entry = entry;
    }

    public EntryTile(Color color, int x, int y, boolean entry) {
        super(color, x, y);
        this.entry=entry;
    }
}
