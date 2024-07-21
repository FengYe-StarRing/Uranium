package com.github.fengye.starring.uranium.utils.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public final static String renderFormat = "yyyy/MM/dd-HH:mm:ss";
    public final static String dataFormat = "yyyy:MM:dd:HH:mm:ss";
    public final static String fileFormat = "yyyy-MM-dd-HH-mm-ss";

    public static String getInternetTime() {
        return getInternetTime(renderFormat);
    }

    public static String getInternetTime(String format) {
        String formattedDate = null;
        try {
            URL url = new URL("https://www.baidu.com");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if (inputLine.contains("serverTime")) {
                    String currentTime = inputLine.substring(inputLine.indexOf("serverTime") + 12, inputLine.indexOf(",") - 3);
                    // Convert currentTime to Date object
                    long timestamp = Long.parseLong(currentTime) * 1000;
                    Date date = new Date(timestamp);
                    // Format the date to get year and month
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    formattedDate = sdf.format(date);
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        return formattedDate;
    }

    public static String getTime() {
        return getTime(renderFormat);
    }

    public static String getTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDefaultTime(String format) {
        return getTime(format);
    }

    public static String getDefaultTime() {
        return getDefaultTime(renderFormat);
    }
}
