package com.github.fengye.starring.uranium.api.event;

public class CancellableEvent extends Event{
    private boolean cancelled = false;

    public CancellableEvent() {
        super(CancellableEvent.class);
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
