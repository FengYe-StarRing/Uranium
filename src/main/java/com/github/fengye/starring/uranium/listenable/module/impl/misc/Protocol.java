package com.github.fengye.starring.uranium.listenable.module.impl.misc;

import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.utils.ProtocolUtils;

@ModuleInfo(name = "Protocol",category = Category.Misc)
public class Protocol extends Module {
    public static final ModeValue modeValue = new ModeValue("Mode",ProtocolUtils.Versions.values(), ProtocolUtils.Versions.v1_8);

    @Override
    public String getTag() {
        return ProtocolUtils.Versions.valueOf(modeValue.getAsString()).getName();
    }
}
