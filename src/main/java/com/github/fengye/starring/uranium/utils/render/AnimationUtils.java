package com.github.fengye.starring.uranium.utils.render;

import com.github.fengye.starring.uranium.utils.MinecraftInstance;

public class AnimationUtils {
    public static int move(int cur, int target, int speed) {
        int fps = MinecraftInstance.getFPS();
        if(cur != target && speed != 0) {
            float bufferMove = (float) speed / fps;
            int move = (bufferMove < 1 && bufferMove > 0) ? 1 : (int) bufferMove;
            int ret = cur + (target > cur ? move : -move);
            if((target > cur && ret > target) || (target < cur && ret < target)) {
                return target;
            }
            return ret;
        } else {
            return cur;
        }
    }
}
