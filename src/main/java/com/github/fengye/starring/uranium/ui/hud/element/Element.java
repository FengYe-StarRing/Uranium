package com.github.fengye.starring.uranium.ui.hud.element;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.util.List;

public abstract class Element {
    public final String T_NAME;
    private final String name;
    private float x,y;
    private final Side side;

    public Element(String name,float x,float y,Side side) {
        T_NAME = name;
        this.name = Client.instance.languageManager.getText("HUD.Element." + T_NAME);
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

    public List<Value<?>> getValues() {
        return JavaUtils.getValues(this);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
