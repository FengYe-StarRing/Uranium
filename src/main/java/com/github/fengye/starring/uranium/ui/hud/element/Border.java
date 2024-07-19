package com.github.fengye.starring.uranium.ui.hud.element;

public class Border {
    private float x,y;
    private float width,height;

    public Border(Number x,Number y,Number width,Number height) {
        this.x = x.floatValue();
        this.y = y.floatValue();
        this.width = width.floatValue();
        this.height = height.floatValue();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
