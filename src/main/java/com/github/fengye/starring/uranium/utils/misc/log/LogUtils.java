package com.github.fengye.starring.uranium.utils.misc.log;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.file.ClientFile;
import com.github.fengye.starring.uranium.manager.impl.FileManager;
import com.github.fengye.starring.uranium.utils.misc.DateUtils;

public class LogUtils {
    private static String buffer = "";

    public static void print(String str, LogMode mode, ClientFile file) {
        String log = '[' + DateUtils.getDefaultTime() + "][" + mode.name() + "] " + str + '\n';
        if(file == null) {
            buffer = buffer.concat(log);
        } else {
            if(!buffer.isEmpty()) {
                log = buffer.concat(log);
            }
            file.write(log);
        }
        System.out.print(log);
    }

    public static void print(String str) {
        print(str, LogMode.Client);
    }

    public static void print(String str, LogMode mode) {
        FileManager fileManager = getManager();
        if(fileManager == null) {
            print(str,mode, null);
        } else {
            print(str,mode, fileManager.logsFile);
        }
    }

    private static FileManager getManager() {
        return Client.instance.fileManager;
    }
}
