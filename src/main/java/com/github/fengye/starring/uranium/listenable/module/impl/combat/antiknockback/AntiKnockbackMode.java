package com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.module.ModuleInMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.AntiKnockback;

public class AntiKnockbackMode extends ModuleInMode {
    private AntiKnockback antiKnockback;

    protected boolean canReduce() {
        if(antiKnockback == null) {
            antiKnockback = (AntiKnockback) Client.instance.moduleManager.getModule(AntiKnockback.class);
        }
        return antiKnockback.delayTimer.hasTimePassed(antiKnockback.delayValue.get().longValue());
    }

    protected void resetDelay() {
        if(antiKnockback == null) {
            antiKnockback = (AntiKnockback) Client.instance.moduleManager.getModule(AntiKnockback.class);
        }
        antiKnockback.delayTimer.reset();
    }
}
