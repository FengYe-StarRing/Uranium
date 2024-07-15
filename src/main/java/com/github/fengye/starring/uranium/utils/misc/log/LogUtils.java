package com.github.fengye.starring.uranium.utils.misc.log;

public class LogUtils {
    public static void print(String str,LogMode mode) {
        String log = '[' + mode.name() + "] " + str;
        System.out.println(log);
    }

    public static void print(String str) {
        print(str,LogMode.Client);
    }
}
