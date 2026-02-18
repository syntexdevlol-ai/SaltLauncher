package net.kdt.pojavlaunch;

/**
 * Minimal grab listener used by the custom control system.
 */
public interface GrabListener {
    default void onGrabState(boolean grabbing) {
        // no-op
    }
}
