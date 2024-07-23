package com.github.fengye.starring.uranium.ui.hud.element;

public class Side {
    private Horizontal horizontal;
    private Vertical vertical;

    public Side(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public static Side toDefault() {
        return new Side(Horizontal.Left,Vertical.Up);
    }

    public Horizontal getHorizontal() {
        return horizontal;
    }

    public Vertical getVertical() {
        return vertical;
    }

    public void setHorizontal(Horizontal horizontal) {
        this.horizontal = horizontal;
    }

    public void setVertical(Vertical vertical) {
        this.vertical = vertical;
    }
}
