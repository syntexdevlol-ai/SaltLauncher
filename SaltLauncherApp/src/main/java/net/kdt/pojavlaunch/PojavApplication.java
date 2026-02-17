package net.kdt.pojavlaunch;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.saltlauncher.app.utils.ZHTools.getVersionCode;
import static com.saltlauncher.app.utils.ZHTools.getVersionName;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.saltlauncher.app.InfoDistributor;
import com.saltlauncher.app.context.ContextExecutor;
import com.saltlauncher.app.context.LocaleHelper;
import com.saltlauncher.app.feature.log.Logging;
import com.saltlauncher.app.setting.AllSettings;
import com.saltlauncher.app.ui.activity.ErrorActivity;
import com.saltlauncher.app.utils.path.PathManager;
import com.saltlauncher.app.utils.ZHTools;

import net.kdt.pojavlaunch.utils.FileUtils;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class PojavApplication extends Application {
	public static final String CRASH_REPORT_TAG = "SaltCrashReport";

	@Override
	public void onCreate() {
		ContextExecutor.setApplication(this);

		Thread.setDefaultUncaughtExceptionHandler((thread, th) -> {
			boolean storagePermAllowed = (Build.VERSION.SDK_INT >= 29 || ActivityCompat.checkSelfPermission(PojavApplication.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && Tools.checkStorageRoot();
			File crashFile = new File(storagePermAllowed ? PathManager.DIR_LAUNCHER_LOG : PathManager.DIR_DATA, "latestcrash.txt");
			// Also write a copy to the public Downloads folder for easier user access
			File downloadCrashFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "saltlauncher-crash.txt");
			try {
				// Write to file, since some devices may not able to show error
				FileUtils.ensureParentDirectory(crashFile);
				String crashText = InfoDistributor.APP_NAME + " crash report\n" +
						" - Time: " + DateFormat.getDateTimeInstance().format(new Date()) + "\n" +
						" - Device: " + Build.PRODUCT + " " + Build.MODEL + "\n" +
						" - Android version: " + Build.VERSION.RELEASE + "\n" +
						" - Launcher version: " + getVersionName() + " (" + getVersionCode() + ")\n" +
						" - Crash stack trace:\n" + Log.getStackTraceString(th);

				try (PrintStream crashStream = new PrintStream(crashFile)) {
					crashStream.append(crashText);
				}

				// Best-effort copy to Downloads (accessible without needing Android/data access)
				runCatching(() -> {
					FileUtils.ensureParentDirectory(downloadCrashFile);
					try (PrintStream downloadStream = new PrintStream(downloadCrashFile)) {
						downloadStream.append(crashText);
					}
					return null;
				});
			} catch (Throwable throwable) {
				Logging.e(CRASH_REPORT_TAG, " - Exception attempt saving crash stack trace:", throwable);
				Logging.e(CRASH_REPORT_TAG, " - The crash stack trace was:", th);
			}

			ErrorActivity.showLauncherCrash(PojavApplication.this, crashFile.getAbsolutePath(), th);
			ZHTools.killProcess();
		});
		
		try {
			super.onCreate();
			PathManager.DIR_DATA = getDir("files", MODE_PRIVATE).getParent();
			PathManager.DIR_CACHE = getCacheDir();
			PathManager.DIR_ACCOUNT_NEW = PathManager.DIR_DATA + "/accounts";
			Tools.DEVICE_ARCHITECTURE = Architecture.getDeviceArchitecture();
			//Force x86 lib directory for Asus x86 based zenfones
			if(Architecture.isx86Device() && Architecture.is32BitsDevice()){
				String originalJNIDirectory = getApplicationInfo().nativeLibraryDir;
				getApplicationInfo().nativeLibraryDir = originalJNIDirectory.substring(0,
												originalJNIDirectory.lastIndexOf("/"))
												.concat("/x86");
			}
		} catch (Throwable throwable) {
			Intent ferrorIntent = new Intent(this, ErrorActivity.class);
			ferrorIntent.putExtra("throwable", throwable);
			ferrorIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
			startActivity(ferrorIntent);
		}

		//设置主题
		String launcherTheme = AllSettings.getLauncherTheme().getValue();
		if (!Objects.equals(launcherTheme, "system")) {
			switch (launcherTheme) {
				case "light" :
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
					break;
				case "dark" :
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
					break;
			}
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ContextExecutor.clearApplication();
	}

	@Override
    protected void attachBaseContext(Context base) {
		ContextExecutor.setApplication(this);
        super.attachBaseContext(LocaleHelper.Companion.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
		ContextExecutor.setApplication(this);
		LocaleHelper.Companion.setLocale(this);
    }
}
