package com.tungsten.fcl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class FCLApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static WeakReference<Activity> currentActivity;

    @Override
    public void onCreate() {
        // enabledStrictMode();
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        // Write crashes to external files so users can share logs easily.
        Thread.UncaughtExceptionHandler previousHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            try {
                File dir = getExternalFilesDir(null);
                if (dir != null && (dir.exists() || dir.mkdirs())) {
                    File logFile = new File(dir, "saltlauncher-crash.txt");
                    StringWriter sw = new StringWriter();
                    throwable.printStackTrace(new PrintWriter(sw));
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    try (FileWriter writer = new FileWriter(logFile, true)) {
                        writer.write("==== Crash " + time + " ====\n");
                        writer.write(sw.toString());
                        writer.write("\n\n");
                    }
                } else {
                    Log.e("SaltLauncher", "Cannot create crash log directory");
                }
            } catch (Exception e) {
                Log.e("SaltLauncher", "Failed to write crash log", e);
            }
            // Preserve default behavior (shows system crash dialog)
            if (previousHandler != null) {
                previousHandler.uncaughtException(thread, throwable);
            }
        });
//        PerfUtil.install();
    }

    public static Activity getCurrentActivity() {
        if (currentActivity != null) {
            return currentActivity.get();
        }
        return null;
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork()
                .detectCustomSlowCalls()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .detectActivityLeaks()
                .detectAll()
                .penaltyLog()
                .build());
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        currentActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (currentActivity != null && currentActivity.get() == activity) {
            currentActivity = null;
        }
    }
}
