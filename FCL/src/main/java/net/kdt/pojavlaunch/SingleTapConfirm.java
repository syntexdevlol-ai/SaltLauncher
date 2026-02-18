package net.kdt.pojavlaunch;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Minimal GestureDetector.SimpleOnGestureListener that always confirms a single tap.
 */
public class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
