package com.tungsten.fcl.upgrade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tungsten.fcl.R;
import com.tungsten.fclcore.task.Schedulers;
import com.tungsten.fclcore.task.Task;
import com.tungsten.fclcore.util.gson.JsonUtils;
import com.tungsten.fclcore.util.io.NetworkUtils;
import com.tungsten.fcllibrary.util.LocaleUtils;

import java.util.ArrayList;

public class UpdateChecker {

    public static final String UPDATE_CHECK_URL = "https://raw.githubusercontent.com/syntexdevlol-ai/SaltLauncher/main/version_map.json";
    // Use the same source for all regions to avoid pointing to the old project.
    public static final String UPDATE_CHECK_URL_CN = UPDATE_CHECK_URL;

    private static UpdateChecker instance;

    public static UpdateChecker getInstance() {
        if (instance == null) {
            instance = new UpdateChecker();
        }
        return instance;
    }

    private boolean isChecking = false;

    public boolean isChecking() {
        return isChecking;
    }

    public UpdateChecker() {

    }

    public Task<?> checkManually(Context context) {
        return check(context, true, true);
    }

    public Task<?> checkAuto(Context context) {
        return check(context, false, false);
    }

    public Task<?> check(Context context, boolean showBeta, boolean showAlert) {
        // Disabled update checker (Salt Launcher manages updates via store/CI).
        return Task.runAsync(() -> {
            isChecking = false;
            if (showAlert) {
                Schedulers.androidUIThread().execute(() -> Toast.makeText(context, context.getString(R.string.update_not_exist), Toast.LENGTH_SHORT).show());
            }
        });
    }

    public static int getCurrentVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't get current version code");
        }
    }

    private void showUpdateDialog(Context context, RemoteVersion version) {
        Schedulers.androidUIThread().execute(() -> {
            UpdateDialog dialog = new UpdateDialog(context, version);
            dialog.show();
        });
    }

    public static boolean isIgnore(Context context, int code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("launcher", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("ignore_update", -1) == code;
    }

    public static void setIgnore(Context context, int code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("launcher", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ignore_update", code);
        editor.apply();
    }

}
