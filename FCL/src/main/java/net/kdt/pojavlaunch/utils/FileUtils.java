package net.kdt.pojavlaunch.utils;

import java.io.File;

public class FileUtils {
    public static void ensureParentDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
