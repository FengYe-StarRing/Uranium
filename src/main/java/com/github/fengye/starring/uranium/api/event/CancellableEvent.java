package com.github.fengye.starring.uranium.api.event;

public abstract class CancellableEvent extends Event{
    private boolean cancelled = false;

    public CancellableEvent(Class<?> aClass) {
        super(aClass);
    }

    public CancellableEvent(Class<?>[] aClasses) {
        super(aClasses);
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cancelEvent() {
        cancelled = true;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
