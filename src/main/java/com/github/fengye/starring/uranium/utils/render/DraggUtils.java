package com.github.fengye.starring.uranium.utils.render;

import com.github.fengye.starring.uranium.utils.misc.MouseUtils;

public class DraggUtils {
    private int lastX = getDefault(), lastY = getDefault(), moveX = 0, moveY = 0;
    private int clickButton = 0;
    private int x1,y1,x2,y2;
    private boolean drag = false;

    public void drag(int mouseX, int mouseY,boolean pre) {
        boolean isHovered = MouseUtils.isHovered(x1,y1,x2,y2,mouseX,mouseY);
        boolean click = MouseUtils.isButtonDown(clickButton);
        if(isHovered && click) {
            drag = true;
        }
        if(drag && !click) {
            drag = false;
        }
        if(lastX != getDefault() && lastY != getDefault()) {
            moveX = mouseX - lastX;
            moveY = mouseY - lastY;
        }
        lastX = mouseX;
        lastY = mouseY;
        if(!drag) {
            lastX = getDefault();
            lastY = getDefault();
            if(!pre) {
                moveX = 0;
                moveY = 0;
            }
        }
    }

    public void drag(boolean pre) {
        drag(MouseUtils.getMouseX(),MouseUtils.getMouseY(),pre);
    }

    private int getDefault() {
        return Integer.MAX_VALUE;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setDragCondition(int click,int x, int y, int x2, int y2) {
        clickButton = click;
        x1 = x;
        y1 = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setDragCondition(int click,float x, float y, float x2, float y2) {
        setDragCondition(click,(int)x,(int)y,(int)x2,(int)y2);
    }
}
