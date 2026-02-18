package net.kdt.pojavlaunch.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.Set;

public class LauncherPreferences {
    public static float PREF_BUTTONSIZE = 100f;
    public static float PREF_DEADZONE_SCALE = 1f;
    public static float PREF_SCALE_FACTOR = 1f;
    public static boolean PREF_DISABLE_SWAP_HAND = false;
    public static boolean PREF_DISABLE_GESTURES = false;
    public static boolean PREF_BUTTON_ALL_CAPS = false;
    public static int PREF_LONGPRESS_TRIGGER = 400;
    public static boolean PREF_GYRO_SMOOTHING = false;
    public static int PREF_GYRO_SAMPLE_RATE = 16;
    public static float PREF_GYRO_SENSITIVITY = 1f;
    public static boolean PREF_GYRO_INVERT_X = false;
    public static boolean PREF_GYRO_INVERT_Y = false;
    public static float PREF_MOUSESCALE = 1f;
    public static float PREF_MOUSESPEED = 1f;
    public static boolean PREF_IGNORE_NOTCH = false;
    public static int PREF_NOTCH_SIZE = 0;
    public static String PREF_DEFAULTCTRL_PATH = null;

    public static SharedPreferences DEFAULT_PREF = new InMemoryPrefs();

    public static void init(Context context) {
        // placeholder for real preference loading
    }

    /** Very small in-memory SharedPreferences implementation used only for default control path. */
    private static class InMemoryPrefs implements SharedPreferences {
        private String defaultCtrl;

        @Override public Map<String, ?> getAll() { return java.util.Collections.emptyMap(); }
        @Override public String getString(String key, String defValue) { return defaultCtrl == null ? defValue : defaultCtrl; }
        @Override public Set<String> getStringSet(String key, Set<String> defValues) { return defValues; }
        @Override public int getInt(String key, int defValue) { return defValue; }
        @Override public long getLong(String key, long defValue) { return defValue; }
        @Override public float getFloat(String key, float defValue) { return defValue; }
        @Override public boolean getBoolean(String key, boolean defValue) { return defValue; }
        @Override public boolean contains(String key) { return false; }
        @Override public Editor edit() { return new Editor() {
            @Override public Editor putString(String key, String value) { if ("defaultCtrl".equals(key)) defaultCtrl = value; return this; }
            @Override public Editor putStringSet(String key, Set<String> values) { return this; }
            @Override public Editor putInt(String key, int value) { return this; }
            @Override public Editor putLong(String key, long value) { return this; }
            @Override public Editor putFloat(String key, float value) { return this; }
            @Override public Editor putBoolean(String key, boolean value) { return this; }
            @Override public Editor remove(String key) { return this; }
            @Override public Editor clear() { return this; }
            @Override public boolean commit() { return true; }
            @Override public void apply() {}
        }; }
        @Override public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {}
        @Override public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {}
    }
}
