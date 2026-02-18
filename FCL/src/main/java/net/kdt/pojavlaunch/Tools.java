package net.kdt.pojavlaunch;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Minimal helper facade required by the Pojav custom control code.
 * Only the pieces actually used inside {@code net.kdt.pojavlaunch.customcontrols}
 * are implemented to keep the integration lightweight and self contained.
 */
public final class Tools {

    /** Global gson instance for control layout (de)serialization. */
    public static final Gson GLOBAL_GSON = new GsonBuilder().setPrettyPrinting().create();

    /** Display metrics used by the control editor/runtime. */
    public static DisplayMetrics currentDisplayMetrics = android.content.res.Resources.getSystem().getDisplayMetrics();

    /** App-private base directory – set once a Context is available. */
    public static String DIR_DATA = new File("/data/data/net.kdt.pojavlaunch").getAbsolutePath();

    /** Control layout storage location. */
    public static String CTRLMAP_PATH = new File(DIR_DATA, "controlmap").getAbsolutePath();

    /** Main thread handler for UI posting. */
    public static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    private Tools() {}

    /** Initialize paths/metrics from the current context. Call early in app startup. */
    public static void init(Context context) {
        if (context != null) {
            currentDisplayMetrics = context.getResources().getDisplayMetrics();
            DIR_DATA = context.getFilesDir().getAbsolutePath();
            CTRLMAP_PATH = new File(context.getFilesDir(), "controlmap").getAbsolutePath();
            // Ensure directory exists
            new File(CTRLMAP_PATH).mkdirs();
        }
    }

    public static float dpToPx(float dp) {
        return dp * currentDisplayMetrics.density;
    }

    public static float pxToDp(float px) {
        return px / currentDisplayMetrics.density;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            MAIN_HANDLER.post(runnable);
        }
    }

    public static void showError(Context ctx, Throwable throwable) {
        showError(ctx, throwable, true);
    }

    public static void showError(Context ctx, Throwable throwable, boolean toast) {
        Log.e("Tools", "Control error", throwable);
        if (toast && ctx != null && throwable != null) {
            String message = throwable.getMessage();
            if (message == null || message.isEmpty()) message = throwable.toString();
            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isValidString(String s) {
        return s != null && !s.isEmpty();
    }

    public static String read(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] data = fis.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = fis.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String path, String content) {
        try {
            File outFile = new File(path);
            File parent = outFile.getParentFile();
            if (parent != null) parent.mkdirs();
            try (FileOutputStream fos = new FileOutputStream(outFile)) {
                fos.write(content.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
