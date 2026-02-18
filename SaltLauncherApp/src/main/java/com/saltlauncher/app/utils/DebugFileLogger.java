package com.saltlauncher.app.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Simple logger that writes breadcrumbs to a readable file for crash diagnosis.
 */
public class DebugFileLogger {
    private static final String FILENAME = "saltlauncher-debug-log.txt";
    private static final SimpleDateFormat TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

    public static void log(Context ctx, String message) {
        try {
            File target = resolveFile(ctx);
            try (FileWriter fw = new FileWriter(target, true)) {
                fw.append(TS.format(new Date()))
                        .append(" ")
                        .append(message)
                        .append("\n");
            }
        } catch (IOException ignored) {
            // do not crash on logging failure
        }
    }

    private static File resolveFile(Context ctx) {
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloads != null) {
            downloads.mkdirs();
            if (downloads.canWrite()) {
                return new File(downloads, FILENAME);
            }
        }
        File ext = ctx.getExternalFilesDir(null);
        if (ext != null) return new File(ext, FILENAME);
        return new File(ctx.getFilesDir(), FILENAME);
    }
}
