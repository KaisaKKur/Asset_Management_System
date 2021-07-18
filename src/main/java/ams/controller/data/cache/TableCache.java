package ams.controller.data.cache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class TableCache {
    public final static void outputTableCache(String[][] data, String file) {
        String pathnameDir = System.getProperty("user.home") + "/.ams";
        File fileDir = new File(pathnameDir);

        // 判断目录是否存在 不存在则创建 .ams 目录
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String pathnamefile = pathnameDir + "/" + file;
        Path path = Paths.get(pathnamefile);

        // 判断原文件是否存在 如果存在 删除此文件
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", path);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
        }

        // 写入文件
        try (BufferedWriter bw = Files.newBufferedWriter(path, Charset.forName("UTF-8"), StandardOpenOption.CREATE)) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j].equals("")) {
                        bw.write(" |");
                    } else {
                        bw.write(data[i][j], 0, data[i][j].length());
                        bw.write("|");
                    }
                }
                bw.newLine();
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public final static String[][] inputTableCache(String file) {
        String pathnameDir = System.getProperty("user.home") + "/.ams";
        File fileDir = new File(pathnameDir);

        // 判断目录是否存在 不存在则创建 .ams 目录
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        
        String pathnamefile = pathnameDir + "/" + file;
        Path path = Paths.get(pathnamefile);

        // 判断原文件是否存在 不存在则创建文件
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[][] data = null;
        int columnMax = 0;

        // 读取文件
        try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String string;
            ArrayList<String[]> arrayList = new ArrayList<>();

            while ((string = br.readLine()) != null) {
                int i = 0;
                String[] strings = string.split("\\|");
                String[] stringsAarry = new String[strings.length];
                for (String str : strings) {
                    if (str != null) {
                        stringsAarry[i++] = str;
                    }
                }
                arrayList.add(stringsAarry);
            }

            for (int i = 0; i < arrayList.size(); i++) {
                if (columnMax < arrayList.get(i).length) {
                    columnMax = arrayList.get(i).length;
                }
            }

            // 写入二维数组
            data = new String[arrayList.size()][columnMax];

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < arrayList.get(i).length; j++) {
                    if (arrayList.get(i)[j].equals(" ")) {
                        data[i][j] = "";
                    } else {
                        data[i][j] = arrayList.get(i)[j];
                    }
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return data;
    }

    public final static void outputTableCache(String[] data, String file) {
        String pathnameDir = System.getProperty("user.home") + "/.ams";
        File fileDir = new File(pathnameDir);

        // 判断目录是否存在 不存在则创建 .ams 目录
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String pathnamefile = pathnameDir + "/" + file;
        Path path = Paths.get(pathnamefile);

        // 判断文件是否存在 不存在则创建该文件
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 写入文件
        try (BufferedWriter bw = Files.newBufferedWriter(path, Charset.forName("UTF-8"), StandardOpenOption.APPEND)) {
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("")) {
                    bw.write(" |");
                } else {
                    bw.write(data[i], 0, data[i].length());
                    bw.write("|");
                }
            }
            bw.newLine();
        } catch (IOException x) {
            System.err.println(x);
        }
    }

}