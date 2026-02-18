package net.kdt.pojavlaunch.utils;

import java.util.HashSet;
import java.util.Set;

public class MCOptionUtils {
    public interface MCOptionListener {
        void onOptionChanged();
    }

    private static final Set<MCOptionListener> LISTENERS = new HashSet<>();
    private static int mcScale = 1;

    public static int getMcScale() {
        return mcScale;
    }

    public static void setMcScale(int scale) {
        mcScale = scale;
        for (MCOptionListener l : LISTENERS) {
            l.onOptionChanged();
        }
    }

    public static void addMCOptionListener(MCOptionListener listener) {
        LISTENERS.add(listener);
    }

    public static void removeMCOptionListener(MCOptionListener listener) {
        LISTENERS.remove(listener);
    }
}
