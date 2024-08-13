package com.github.fengye.starring.uranium.utils;

import com.github.fengye.starring.uranium.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public class SoundFxPlayer {
    public static void playSound(SoundType st, float volume) {
        new Thread(() -> {
            AudioInputStream as;
            try {
                as = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(Minecraft.getMinecraft().getResourceManager()
                        .getResource(new ResourceLocation(Client.RESOURCES + "/Sound/" + st.getFileName()))
                        .getInputStream())));
                Clip clip = AudioSystem.getClip();
                clip.open(as);
                clip.start();
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {}
        }).start();
    }

    public static void playSound(SoundType st) {
        playSound(st,-5);
    }

    public enum SoundType {
        Enable("Enable.wav"),
        Disable("Disable.wav");

        final String fileName;

        SoundType(String fileName) {
            this.fileName = fileName;
        }

        String getFileName() {
            return fileName;
        }
    }
}
