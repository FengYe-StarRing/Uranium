package com.github.fengye.starring.uranium.ui.hud.element;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.render.ScreenUtils;

import java.util.List;

public abstract class Element {
    public final String T_NAME;
    private final String name;
    private float x,y;
    private final Side side;
    private Border border;
    private boolean lock;

    public Element(String name,float x,float y,Side side) {
        T_NAME = name;
        this.name = Client.instance.languageManager.getText("HUD.Element." + T_NAME);
        this.x = x;
        this.y = y;
        this.side = side;
    }

    public Border render() {
        return null;
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

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void lock(float x,float y) {
        if(!isLock()) {
            return;
        }
        Side side = getSide();
        Horizontal horizontal = side.getHorizontal();
        Vertical vertical = side.getVertical();
        if(isLock()) {
            switch (horizontal) {
                case Left:
                    break;
                case Right:
                    setX(ScreenUtils.getWidth() - x);
                    break;
            }
            switch (vertical) {
                case Up:
                    break;
                case Down:
                    setY(ScreenUtils.getHeight() - y);
                    break;
            }
        }
    }
}
