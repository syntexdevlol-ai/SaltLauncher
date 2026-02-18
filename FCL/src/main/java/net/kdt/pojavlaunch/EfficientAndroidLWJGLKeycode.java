package net.kdt.pojavlaunch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Minimal stub that exposes a key name list and lookup helpers for the
 * custom control editor. It maps directly to the GLWF keycodes from
 * the wrapped {@link LwjglGlfwKeycode}.
 */
public class EfficientAndroidLWJGLKeycode {
    private static final List<String> KEY_NAMES;

    static {
        // Build a small deterministic list; real mapping is not critical for now.
        List<String> names = new ArrayList<>();
        names.add("Unknown");
        names.add("A");
        names.add("B");
        names.add("C");
        names.add("D");
        names.add("E");
        names.add("F");
        names.add("G");
        names.add("H");
        names.add("I");
        names.add("J");
        names.add("K");
        names.add("L");
        names.add("M");
        names.add("N");
        names.add("O");
        names.add("P");
        names.add("Q");
        names.add("R");
        names.add("S");
        names.add("T");
        names.add("U");
        names.add("V");
        names.add("W");
        names.add("X");
        names.add("Y");
        names.add("Z");
        KEY_NAMES = Collections.unmodifiableList(names);
    }

    public static List<String> getKeyNameList() {
        return KEY_NAMES;
    }

    public static int getIndexByValue(int keycode) {
        // Very small heuristic mapping for common letters
        if (keycode >= LwjglGlfwKeycode.GLFW_KEY_A && keycode <= LwjglGlfwKeycode.GLFW_KEY_Z) {
            return (keycode - LwjglGlfwKeycode.GLFW_KEY_A) + 1;
        }
        return 0; // Unknown
    }

    public static List<String> generateKeyName() {
        return KEY_NAMES;
    }

    public static String getNameByValue(int keycode) {
        int idx = getIndexByValue(keycode);
        if (idx >= 0 && idx < KEY_NAMES.size()) return KEY_NAMES.get(idx);
        return "Unknown(" + keycode + ")";
    }
}
