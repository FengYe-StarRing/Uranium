package com.github.fengye.starring.uranium.ui.hud;

public class Side {
    public Horizontal horizontal;
    public Vertical vertical;

    public Side(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public static Side toDefault() {
        return new Side(Horizontal.Normal,Vertical.Normal);
    }
}
