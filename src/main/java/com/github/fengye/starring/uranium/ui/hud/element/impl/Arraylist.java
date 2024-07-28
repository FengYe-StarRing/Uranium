package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Horizontal;
import com.github.fengye.starring.uranium.ui.hud.element.Side;
import com.github.fengye.starring.uranium.ui.hud.element.Vertical;

public class Arraylist extends Element {
    public Arraylist() {
        super("Arraylist", 3, 3,new Side(Horizontal.Right, Vertical.Up));
        setLock(true);
    }
}
