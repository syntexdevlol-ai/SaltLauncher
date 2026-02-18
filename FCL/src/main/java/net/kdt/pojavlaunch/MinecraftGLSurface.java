package net.kdt.pojavlaunch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Placeholder surface class; real rendering surface is provided by the
 * underlying game runtime. This stub satisfies the control overlay
 * dependencies without altering behaviour.
 */
public class MinecraftGLSurface extends SurfaceView {
    public MinecraftGLSurface(Context context) {
        super(context);
    }

    public MinecraftGLSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
