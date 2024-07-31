package com.github.fengye.starring.uranium.utils.mouse;

public class ScrollBarUtils {
    private int usedIndex = 0;
    private int validIndex = 0;
    private int index = 0;

    public void set(int validIndex,int index) {
        this.validIndex = validIndex;
        this.index = index;
        this.usedIndex = 0;
    }

    public void update() {
        int button = MouseUtils.getDWheel();
        if(button < 0 && (index - usedIndex - 1 > validIndex && index > validIndex)) {
            usedIndex++;
        }
        if(button > 0 && usedIndex > 0) {
            usedIndex--;
        }
    }

    public int getUsedIndex() {
        return usedIndex;
    }

    public int getValidIndex() {
        return validIndex;
    }
}
