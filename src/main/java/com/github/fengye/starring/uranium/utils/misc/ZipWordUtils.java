package com.github.fengye.starring.uranium.utils.misc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipWordUtils {
    public static boolean createZipWord(String target,File[] files) {
        try (ZipOutputStream zipOut = getZipOutputStream(target)){
            if(zipOut == null) {
                return false;
            }
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true; // 创建成功
    }

    public static ZipOutputStream getZipOutputStream(String path) {
        try {
            return new ZipOutputStream(new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static BufferedReader getFileInZipWord(ZipInputStream zis,String fileName) {
        BufferedReader file = null;
        try {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if(entry.getName().equals(fileName)) {
                    file = getBufferedReader(zis);
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            return null;
        }
        return file;
    }

    public static ZipInputStream getZipInputStream(String path) {
        try {
            return new ZipInputStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static BufferedReader getBufferedReader(ZipInputStream zis) {
        return new BufferedReader(new InputStreamReader(zis));
    }

    public static boolean unzipFile(ZipInputStream zis, File destDir) {
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String filePath = destDir.getAbsolutePath() + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zis, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zis.closeEntry();
                entry = zis.getNextEntry();
            }
            zis.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static void extractFile(ZipInputStream zis, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = zis.read(buffer)) > 0) {
            bos.write(buffer, 0, length);
        }
        bos.close();
    }

    public static boolean createZipWord(File target,File[] files) {
        return createZipWord(target.getAbsolutePath(),files);
    }

    public static boolean unzipFile(String zipPath, String dirPath) {
        ZipInputStream zis = getZipInputStream(zipPath);
        if(zis == null) {
            return false;
        }
        return unzipFile(zis,new File(dirPath));
    }
}
