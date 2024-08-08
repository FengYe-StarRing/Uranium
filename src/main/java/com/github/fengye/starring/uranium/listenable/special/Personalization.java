package com.github.fengye.starring.uranium.listenable.special;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.client.SaveEvent;
import com.github.fengye.starring.uranium.api.file.ClientFile;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.manager.impl.LanguageManager;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import net.minecraft.util.ResourceLocation;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Personalization implements Listenable {
    private static final ModeValue backgroundsValue = new ModeValue("Backgrounds",Backgrounds.values(),Backgrounds.BG1);
    public static final OptionValue backgroundShakeValue = new OptionValue("BackgroundShake",true);
    public static final OptionValue enableBlurValue = new OptionValue("EnableBlur",true);

    private static ClientFile file;

    public Personalization() {
        file = new ClientFile(Client.instance.fileManager.userDataDir, "Personalization.txt");
        for (String line : file.readAllLines()) {
            String[] strings = line.split("\\|");
            String name = strings[0];
            String valueData = strings[1];
            for (Value<?> value : getValues()) {
                if(value.getName().equals(name)) {
                    value.setAuto(valueData);
                }
            }
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    public static ResourceLocation getBackground() {
        Backgrounds mode = Backgrounds.valueOf(backgroundsValue.getAsString());
        mode.update();
        return mode.bg;
    }

    public List<Value<?>> getValues() {
        List<Value<?>> values = new ArrayList<>(JavaUtils.getValues(this));
        values.add(LanguageManager.languageValue);
        return values;
    }

    @EventHandle
    private void onSave(SaveEvent event) {
        file.create();
        for (Value<?> value : getValues()) {
            String name = value.getName();
            String valueData = value.getAsString();
            if (name.equals("Language")) {
                LanguageManager.settingFile.write(valueData,StandardOpenOption.CREATE);
            } else {
                file.writeLine(name + "|" + valueData);
            }
        }
    }

    private enum Backgrounds {
        BG1("1"),
        BG2("2");

        private ResourceLocation bg;
        private final int type;

        Backgrounds(String path) {
            bg = MinecraftInstance.getResourceLocation("/Backgrounds/" + path + ".png");
            type = 0;
        }

        public void update() {

        }
    }
}
