package com.github.fengye.starring.uranium.ui.hud;

public abstract class Element {
    private String name;
    private float x,y;
    private Side side;

    public Element(String name,float x,float y,Side side) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.side = side;
    }

    public void render() {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public Side getSide() {
        return side;
    }
}
