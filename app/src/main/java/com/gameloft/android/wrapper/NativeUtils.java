package com.gameloft.android.wrapper;

/* JADX INFO: loaded from: classes.dex */
public class NativeUtils {
    public static native boolean CheckRoot();

    public static native boolean checkPlatformService(long j);

    public static native float getClock();

    public static native long getClockMillis();

    public static native long getClockNano();

    public static native long sd();

    public static native void sp(long j);

    public static native long tm();

    static {
        System.loadLibrary("nativeU");
    }
}
