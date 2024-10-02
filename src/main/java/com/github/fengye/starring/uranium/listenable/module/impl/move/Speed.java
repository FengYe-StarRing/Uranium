package com.github.fengye.starring.uranium.listenable.module.impl.move;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.move.speed.SpeedMode;
import com.github.fengye.starring.uranium.listenable.module.impl.move.speed.impl.EntityMode;

import java.util.List;

@ModuleInfo(name = "Speed",category = Category.Movement)
public class Speed extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Entity);

    @Override
    public void updateAddedValues() {
        updateAddedValues(Modes.values());
    }

    @Override
    public void updateValues(List<Value<?>> values) {
        updateValues(modeValue,values);
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        getMode().onUpdate(event);
    }

    private SpeedMode getMode() {
        return Modes.valueOf(modeValue.getAsString()).getMode();
    }

    @Override
    public String getTag() {
        return modeValue.getAsString();
    }

    private enum Modes {
        Entity(new EntityMode());

        private final SpeedMode MODE;

        public SpeedMode getMode() {
            return MODE;
        }

        Modes(SpeedMode mode) {
            MODE = mode;
        }
    }
}
