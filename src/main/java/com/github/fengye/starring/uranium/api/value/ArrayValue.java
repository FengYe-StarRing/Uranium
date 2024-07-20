package com.github.fengye.starring.uranium.api.value;

public abstract class ArrayValue<T> extends Value<T>{
    private final T[] modes;

    public ArrayValue(String name, T[] modes, T value) {
        super(name, value);
        this.modes = modes;
    }

    public T[] getModes() {
        return modes;
    }

    public void set(T value) {
        for (T mode : getModes()) {
            if(mode.equals(value)) {
                super.set(value);
            }
        }
    }

    public abstract void set(String value);

    public void switchMode(boolean left) {
        T[] modes = getModes();
        int index = 0;
        for (T mode : modes) {
            if(get().equals(mode) || get() == mode) {
                break;
            }
            index++;
        }
        index += left ? -1 : 1;
        if(index == -1) {
            index = modes.length - 1;
        }
        if(index == modes.length) {
            index = 0;
        }
        set(modes[index]);
    }

    public abstract String[] getModesAsStr();
}
