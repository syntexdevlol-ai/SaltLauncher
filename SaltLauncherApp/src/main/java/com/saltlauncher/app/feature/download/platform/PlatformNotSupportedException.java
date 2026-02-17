package com.saltlauncher.app.feature.download.platform;

public class PlatformNotSupportedException extends RuntimeException {
    public PlatformNotSupportedException(String message) {
        super(message);
    }
}
