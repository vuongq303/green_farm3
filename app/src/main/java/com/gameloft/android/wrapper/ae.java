package com.gameloft.android.wrapper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.adsmanager.JavaUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: MultipleScreen.java */
/* JADX INFO: loaded from: classes.dex */
public class ae {
    static Activity bgg;
    static String bgj;
    static float bfP = 0.01f;
    static int bfQ = 800;
    static int bfR = 480;
    public static int bfS = 800;
    public static int bfT = 480;
    public static String bfU = "";
    static float[][] bfV = {new float[]{640.0f, 480.0f, 800.0f, 480.0f, 0.8f, 0.0f, 0.0f}, new float[]{782.0f, 480.0f, 800.0f, 491.0f, 0.98f, 0.0f, 0.0f}, new float[]{791.0f, 480.0f, 800.0f, 485.0f, 0.99f, 0.0f, 0.0f}, new float[]{800.0f, 480.0f, 800.0f, 480.0f, 1.0f, 0.0f, 0.0f}, new float[]{854.0f, 480.0f, 854.0f, 480.0f, 1.0f, 0.0f, 0.0f}, new float[]{960.0f, 480.0f, 800.0f, 480.0f, 1.0f, 0.0f, 0.0f}, new float[]{896.0f, 540.0f, 800.0f, 480.0f, 1.12f, 0.0f, 0.0f}, new float[]{897.0f, 540.0f, 800.0f, 480.0f, 1.12f, 0.0f, 0.0f}, new float[]{960.0f, 540.0f, 854.0f, 480.0f, 1.13f, 0.0f, 0.0f}, new float[]{960.0f, 640.0f, 800.0f, 480.0f, 1.2f, 0.0f, 0.0f}, new float[]{1024.0f, 552.0f, 854.0f, 480.0f, 1.15f, 0.0f, 0.0f}, new float[]{1024.0f, 580.0f, 847.0f, 480.0f, 1.21f, 0.0f, 0.0f}, new float[]{1024.0f, 600.0f, 820.0f, 480.0f, 1.25f, 0.0f, 0.0f}, new float[]{1024.0f, 672.0f, 800.0f, 480.0f, 1.28f, 0.0f, 0.0f}, new float[]{1024.0f, 720.0f, 800.0f, 480.0f, 1.28f, 0.0f, 0.0f}, new float[]{1024.0f, 768.0f, 800.0f, 480.0f, 1.28f, 0.0f, 0.0f}, new float[]{1196.0f, 720.0f, 800.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1196.0f, 768.0f, 800.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1196.0f, 800.0f, 800.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1202.0f, 720.0f, 800.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1216.0f, 800.0f, 800.0f, 480.0f, 1.52f, 0.0f, 0.0f}, new float[]{1217.0f, 720.0f, 812.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1280.0f, 720.0f, 854.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1280.0f, 736.0f, 835.0f, 480.0f, 1.54f, 0.0f, 0.0f}, new float[]{1280.0f, 752.0f, 817.0f, 480.0f, 1.57f, 0.0f, 0.0f}, new float[]{1280.0f, 768.0f, 800.0f, 480.0f, 1.6f, 0.0f, 0.0f}, new float[]{1280.0f, 800.0f, 800.0f, 480.0f, 1.6f, 0.0f, 0.0f}, new float[]{1280.0f, 960.0f, 800.0f, 480.0f, 1.6f, 0.0f, 0.0f}, new float[]{1366.0f, 768.0f, 854.0f, 480.0f, 1.6f, 0.0f, 0.0f}, new float[]{1440.0f, 816.0f, 847.0f, 480.0f, 1.7f, 0.0f, 0.0f}, new float[]{1440.0f, 828.0f, 833.0f, 480.0f, 1.73f, 0.0f, 0.0f}, new float[]{1440.0f, 900.0f, 800.0f, 480.0f, 1.8f, 0.0f, 0.0f}, new float[]{1794.0f, 1080.0f, 800.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1800.0f, 1080.0f, 800.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1836.0f, 1080.0f, 817.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1920.0f, 1080.0f, 854.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1920.0f, 1104.0f, 835.0f, 480.0f, 2.3f, 0.0f, 0.0f}, new float[]{1920.0f, 1128.0f, 817.0f, 480.0f, 2.35f, 0.0f, 0.0f}, new float[]{1920.0f, 1152.0f, 800.0f, 480.0f, 2.4f, 0.0f, 0.0f}, new float[]{1920.0f, 1196.0f, 800.0f, 480.0f, 2.4f, 0.0f, 0.0f}, new float[]{1920.0f, 1200.0f, 800.0f, 480.0f, 2.4f, 0.0f, 0.0f}, new float[]{1920.0f, 1280.0f, 800.0f, 480.0f, 2.4f, 0.0f, 0.0f}, new float[]{2048.0f, 1536.0f, 800.0f, 480.0f, 2.56f, 0.0f, 0.0f}, new float[]{2392.0f, 1440.0f, 800.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{2560.0f, 1440.0f, 854.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{2560.0f, 1504.0f, 817.0f, 480.0f, 3.14f, 0.0f, 0.0f}, new float[]{2560.0f, 1532.0f, 800.0f, 480.0f, 3.2f, 0.0f, 0.0f}, new float[]{2560.0f, 1600.0f, 800.0f, 480.0f, 3.2f, 0.0f, 0.0f}, new float[]{2880.0f, 1440.0f, 854.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{2960.0f, 1440.0f, 800.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{3840.0f, 2160.0f, 854.0f, 480.0f, 4.5f, 0.0f, 0.0f}};
    static float[][] bfW = {new float[]{640.0f, 480.0f, 800.0f, 600.0f, 0.8f, 0.0f, 0.0f}, new float[]{782.0f, 480.0f, 800.0f, 491.0f, 0.98f, 0.0f, 0.0f}, new float[]{800.0f, 480.0f, 800.0f, 480.0f, 1.0f, 0.0f, 0.0f}, new float[]{854.0f, 480.0f, 854.0f, 480.0f, 1.0f, 0.0f, 0.0f}, new float[]{791.0f, 480.0f, 800.0f, 485.0f, 0.99f, 0.0f, 0.0f}, new float[]{896.0f, 540.0f, 800.0f, 480.0f, 1.12f, 0.0f, 0.0f}, new float[]{897.0f, 540.0f, 800.0f, 480.0f, 1.12f, 0.0f, 0.0f}, new float[]{960.0f, 480.0f, 800.0f, 480.0f, 1.0f, 0.0f, 0.0f}, new float[]{960.0f, 540.0f, 888.0f, 500.0f, 1.08f, 0.0f, 0.0f}, new float[]{960.0f, 640.0f, 882.0f, 588.0f, 1.09f, 0.0f, 0.0f}, new float[]{1024.0f, 552.0f, 888.0f, 480.0f, 1.15f, 0.0f, 0.0f}, new float[]{1024.0f, 580.0f, 847.0f, 480.0f, 1.21f, 0.0f, 0.0f}, new float[]{1024.0f, 600.0f, 820.0f, 480.0f, 1.25f, 0.0f, 0.0f}, new float[]{1024.0f, 672.0f, 883.0f, 580.0f, 1.16f, 0.0f, 0.0f}, new float[]{1024.0f, 720.0f, 853.0f, 600.0f, 1.2f, 0.0f, 0.0f}, new float[]{1024.0f, 768.0f, 800.0f, 600.0f, 1.28f, 0.0f, 0.0f}, new float[]{1196.0f, 720.0f, 800.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1196.0f, 768.0f, 886.0f, 570.0f, 1.35f, 0.0f, 0.0f}, new float[]{1196.0f, 800.0f, 886.0f, 594.0f, 1.35f, 0.0f, 0.0f}, new float[]{1202.0f, 720.0f, 800.0f, 480.0f, 1.51f, 0.0f, 0.0f}, new float[]{1216.0f, 800.0f, 888.0f, 584.0f, 1.37f, 0.0f, 0.0f}, new float[]{1217.0f, 720.0f, 812.0f, 480.0f, 1.521f, 0.0f, 0.0f}, new float[]{1280.0f, 720.0f, 853.0f, 480.0f, 1.5f, 0.0f, 0.0f}, new float[]{1280.0f, 736.0f, 835.0f, 480.0f, 1.54f, 0.0f, 0.0f}, new float[]{1280.0f, 752.0f, 817.0f, 480.0f, 1.57f, 0.0f, 0.0f}, new float[]{1280.0f, 768.0f, 800.0f, 480.0f, 1.6f, 0.0f, 0.0f}, new float[]{1280.0f, 800.0f, 888.0f, 555.0f, 1.44f, 0.0f, 0.0f}, new float[]{1280.0f, 960.0f, 800.0f, 600.0f, 1.6f, 0.0f, 0.0f}, new float[]{1366.0f, 768.0f, 854.0f, 480.0f, 1.6f, 0.0f, 0.0f}, new float[]{1440.0f, 816.0f, 847.0f, 480.0f, 1.7f, 0.0f, 0.0f}, new float[]{1440.0f, 828.0f, 833.0f, 480.0f, 1.73f, 0.0f, 0.0f}, new float[]{1440.0f, 900.0f, 884.0f, 553.0f, 1.63f, 0.0f, 0.0f}, new float[]{1794.0f, 1080.0f, 800.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1800.0f, 1080.0f, 800.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1836.0f, 1080.0f, 817.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1920.0f, 1080.0f, 853.0f, 480.0f, 2.25f, 0.0f, 0.0f}, new float[]{1920.0f, 1104.0f, 835.0f, 480.0f, 2.3f, 0.0f, 0.0f}, new float[]{1920.0f, 1128.0f, 885.0f, 520.0f, 2.17f, 0.0f, 0.0f}, new float[]{1920.0f, 1152.0f, 800.0f, 480.0f, 2.4f, 0.0f, 0.0f}, new float[]{1920.0f, 1196.0f, 885.0f, 553.0f, 2.17f, 0.0f, 0.0f}, new float[]{1920.0f, 1200.0f, 885.0f, 553.0f, 2.17f, 0.0f, 0.0f}, new float[]{2048.0f, 1536.0f, 800.0f, 600.0f, 2.56f, 0.0f, 0.0f}, new float[]{2392.0f, 1440.0f, 800.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{2560.0f, 1440.0f, 854.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{2560.0f, 1504.0f, 817.0f, 480.0f, 3.14f, 0.0f, 0.0f}, new float[]{2560.0f, 1532.0f, 800.0f, 480.0f, 3.2f, 0.0f, 0.0f}, new float[]{2560.0f, 1600.0f, 886.0f, 554.0f, 2.89f, 0.0f, 0.0f}, new float[]{2880.0f, 1440.0f, 960.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{2960.0f, 1440.0f, 800.0f, 480.0f, 3.0f, 0.0f, 0.0f}, new float[]{3840.0f, 2160.0f, 854.0f, 480.0f, 4.5f, 0.0f, 0.0f}};
    static float[][] bfX = {new float[]{480.0f, 640.0f, 480.0f, 800.0f, 0.8f, 0.0f, 0.0f}, new float[]{480.0f, 782.0f, 491.0f, 800.0f, 0.98f, 0.0f, 0.0f}, new float[]{480.0f, 800.0f, 480.0f, 800.0f, 1.0f, 0.0f, 0.0f}, new float[]{480.0f, 854.0f, 480.0f, 854.0f, 1.0f, 0.0f, 0.0f}, new float[]{540.0f, 888.0f, 480.0f, 800.0f, 1.11f, 0.0f, 0.0f}, new float[]{540.0f, 896.0f, 480.0f, 800.0f, 1.12f, 0.0f, 0.0f}, new float[]{540.0f, 897.0f, 480.0f, 854.0f, 1.12f, 0.0f, 0.0f}, new float[]{540.0f, 960.0f, 480.0f, 854.0f, 1.13f, 0.0f, 0.0f}, new float[]{552.0f, 1024.0f, 480.0f, 854.0f, 1.15f, 0.0f, 0.0f}, new float[]{580.0f, 1024.0f, 480.0f, 847.0f, 1.21f, 0.0f, 0.0f}, new float[]{600.0f, 976.0f, 480.0f, 800.0f, 1.22f, 0.0f, 0.0f}, new float[]{600.0f, 1004.0f, 480.0f, 804.0f, 1.25f, 0.0f, 0.0f}, new float[]{600.0f, 1024.0f, 480.0f, 820.0f, 1.25f, 0.0f, 0.0f}, new float[]{640.0f, 960.0f, 480.0f, 800.0f, 1.2f, 0.0f, 0.0f}, new float[]{672.0f, 1024.0f, 480.0f, 800.0f, 1.28f, 0.0f, 0.0f}, new float[]{720.0f, 1184.0f, 480.0f, 800.0f, 1.48f, 0.0f, 0.0f}, new float[]{720.0f, 1196.0f, 480.0f, 800.0f, 1.5f, 0.0f, 0.0f}, new float[]{720.0f, 1024.0f, 480.0f, 800.0f, 1.28f, 0.0f, 0.0f}, new float[]{720.0f, 1202.0f, 480.0f, 800.0f, 1.5f, 0.0f, 0.0f}, new float[]{720.0f, 1208.0f, 480.0f, 806.0f, 1.5f, 0.0f, 0.0f}, new float[]{720.0f, 1217.0f, 480.0f, 812.0f, 1.5f, 0.0f, 0.0f}, new float[]{720.0f, 1280.0f, 480.0f, 854.0f, 1.5f, 0.0f, 0.0f}, new float[]{736.0f, 1280.0f, 480.0f, 835.0f, 1.54f, 0.0f, 0.0f}, new float[]{752.0f, 1280.0f, 480.0f, 817.0f, 1.57f, 0.0f, 0.0f}, new float[]{768.0f, 976.0f, 480.0f, 800.0f, 1.22f, 0.0f, 0.0f}, new float[]{768.0f, 1024.0f, 480.0f, 800.0f, 1.28f, 0.0f, 0.0f}, new float[]{768.0f, 1184.0f, 480.0f, 800.0f, 1.48f, 0.0f, 0.0f}, new float[]{768.0f, 1196.0f, 480.0f, 800.0f, 1.5f, 0.0f, 0.0f}, new float[]{768.0f, 1280.0f, 480.0f, 800.0f, 1.6f, 0.0f, 0.0f}, new float[]{768.0f, 1366.0f, 480.0f, 854.0f, 1.6f, 0.0f, 0.0f}, new float[]{800.0f, 1205.0f, 480.0f, 800.0f, 1.51f, 0.0f, 0.0f}, new float[]{800.0f, 1216.0f, 480.0f, 800.0f, 1.52f, 0.0f, 0.0f}, new float[]{800.0f, 1232.0f, 480.0f, 800.0f, 1.54f, 0.0f, 0.0f}, new float[]{800.0f, 1280.0f, 480.0f, 800.0f, 1.6f, 0.0f, 0.0f}, new float[]{816.0f, 1440.0f, 480.0f, 847.0f, 1.7f, 0.0f, 0.0f}, new float[]{828.0f, 1440.0f, 480.0f, 833.0f, 1.73f, 0.0f, 0.0f}, new float[]{900.0f, 1356.0f, 480.0f, 800.0f, 1.7f, 0.0f, 0.0f}, new float[]{900.0f, 1368.0f, 480.0f, 800.0f, 1.71f, 0.0f, 0.0f}, new float[]{900.0f, 1440.0f, 480.0f, 800.0f, 1.8f, 0.0f, 0.0f}, new float[]{960.0f, 1280.0f, 480.0f, 800.0f, 1.6f, 0.0f, 0.0f}, new float[]{1080.0f, 1776.0f, 480.0f, 800.0f, 2.22f, 0.0f, 0.0f}, new float[]{1080.0f, 1794.0f, 480.0f, 800.0f, 2.25f, 0.0f, 0.0f}, new float[]{1080.0f, 1800.0f, 480.0f, 800.0f, 2.25f, 0.0f, 0.0f}, new float[]{1080.0f, 1824.0f, 480.0f, 811.0f, 2.25f, 0.0f, 0.0f}, new float[]{1080.0f, 1836.0f, 480.0f, 817.0f, 2.25f, 0.0f, 0.0f}, new float[]{1080.0f, 1920.0f, 480.0f, 854.0f, 2.25f, 0.0f, 0.0f}, new float[]{1104.0f, 1920.0f, 480.0f, 835.0f, 2.3f, 0.0f, 0.0f}, new float[]{1128.0f, 1920.0f, 480.0f, 817.0f, 2.35f, 0.0f, 0.0f}, new float[]{1152.0f, 1920.0f, 480.0f, 800.0f, 2.4f, 0.0f, 0.0f}, new float[]{1196.0f, 1920.0f, 480.0f, 800.0f, 2.4f, 0.0f, 0.0f}, new float[]{1200.0f, 1824.0f, 480.0f, 800.0f, 2.28f, 0.0f, 0.0f}, new float[]{1200.0f, 1848.0f, 480.0f, 800.0f, 2.31f, 0.0f, 0.0f}, new float[]{1200.0f, 1920.0f, 480.0f, 800.0f, 2.4f, 0.0f, 0.0f}, new float[]{1280.0f, 1836.0f, 480.0f, 800.0f, 2.3f, 0.0f, 0.0f}, new float[]{1440.0f, 2392.0f, 480.0f, 800.0f, 3.0f, 0.0f, 0.0f}, new float[]{1440.0f, 2560.0f, 480.0f, 854.0f, 3.0f, 0.0f, 0.0f}, new float[]{1440.0f, 2880.0f, 480.0f, 854.0f, 3.0f, 0.0f, 0.0f}, new float[]{1440.0f, 2960.0f, 480.0f, 854.0f, 3.0f, 0.0f, 0.0f}, new float[]{1504.0f, 2560.0f, 480.0f, 800.0f, 3.14f, 0.0f, 0.0f}, new float[]{1532.0f, 2560.0f, 480.0f, 800.0f, 3.2f, 0.0f, 0.0f}, new float[]{1600.0f, 2464.0f, 480.0f, 800.0f, 3.08f, 0.0f, 0.0f}, new float[]{1600.0f, 2560.0f, 480.0f, 800.0f, 3.2f, 0.0f, 0.0f}, new float[]{2160.0f, 3840.0f, 480.0f, 854.0f, 4.5f, 0.0f, 0.0f}};
    static float[][] bfY = {new float[]{480.0f, 782.0f, 490.0f, 800.0f, 0.98f, 0.0f, 0.0f}, new float[]{480.0f, 800.0f, 480.0f, 800.0f, 1.0f, 0.0f, 0.0f}, new float[]{480.0f, 854.0f, 480.0f, 854.0f, 1.0f, 0.0f, 0.0f}, new float[]{540.0f, 888.0f, 540.0f, 888.0f, 1.0f, 0.0f, 0.0f}, new float[]{540.0f, 896.0f, 480.0f, 800.0f, 1.13f, 0.0f, 0.0f}, new float[]{540.0f, 960.0f, 500.0f, 888.0f, 1.08f, 0.0f, 0.0f}, new float[]{600.0f, 1004.0f, 480.0f, 803.0f, 1.25f, 0.0f, 0.0f}, new float[]{600.0f, 1024.0f, 480.0f, 820.0f, 1.25f, 0.0f, 0.0f}, new float[]{720.0f, 1202.0f, 480.0f, 800.0f, 1.51f, 0.0f, 0.0f}, new float[]{720.0f, 1208.0f, 480.0f, 806.0f, 1.5f, 0.0f, 0.0f}, new float[]{720.0f, 1280.0f, 480.0f, 853.0f, 1.5f, 0.0f, 0.0f}, new float[]{768.0f, 1280.0f, 480.0f, 800.0f, 1.6f, 0.0f, 0.0f}, new float[]{960.0f, 1280.0f, 600.0f, 800.0f, 1.6f, 0.0f, 0.0f}, new float[]{900.0f, 1440.0f, 553.0f, 884.0f, 1.63f, 0.0f, 0.0f}, new float[]{1080.0f, 1800.0f, 480.0f, 800.0f, 2.25f, 0.0f, 0.0f}, new float[]{1080.0f, 1824.0f, 480.0f, 811.0f, 2.25f, 0.0f, 0.0f}, new float[]{1080.0f, 1920.0f, 480.0f, 853.0f, 2.25f, 0.0f, 0.0f}, new float[]{1152.0f, 1920.0f, 480.0f, 800.0f, 2.4f, 0.0f, 0.0f}, new float[]{480.0f, 640.0f, 600.0f, 800.0f, 0.8f, 0.0f, 0.0f}, new float[]{480.0f, 791.0f, 485.0f, 800.0f, 0.99f, 0.0f, 0.0f}, new float[]{600.0f, 976.0f, 546.0f, 888.0f, 1.1f, 0.0f, 0.0f}, new float[]{640.0f, 960.0f, 592.0f, 888.0f, 1.08f, 0.0f, 0.0f}, new float[]{672.0f, 1024.0f, 583.0f, 888.0f, 1.15f, 0.0f, 0.0f}, new float[]{720.0f, 1184.0f, 540.0f, 888.0f, 1.33f, 0.0f, 0.0f}, new float[]{720.0f, 1217.0f, 480.0f, 812.0f, 1.521f, 0.0f, 0.0f}, new float[]{720.0f, 1480.0f, 480.0f, 800.0f, 1.5f, 0.0f, 0.0f}, new float[]{768.0f, 976.0f, 630.0f, 800.0f, 1.22f, 0.0f, 0.0f}, new float[]{768.0f, 1024.0f, 600.0f, 800.0f, 1.28f, 0.0f, 0.0f}, new float[]{768.0f, 1184.0f, 574.0f, 888.0f, 1.34f, 0.0f, 0.0f}, new float[]{768.0f, 1366.0f, 480.0f, 854.0f, 1.6f, 0.0f, 0.0f}, new float[]{800.0f, 1216.0f, 584.0f, 888.0f, 1.37f, 0.0f, 0.0f}, new float[]{800.0f, 1205.0f, 589.0f, 888.0f, 1.36f, 0.0f, 0.0f}, new float[]{800.0f, 1232.0f, 576.0f, 888.0f, 1.39f, 0.0f, 0.0f}, new float[]{800.0f, 1280.0f, 555.0f, 888.0f, 1.44f, 0.0f, 0.0f}, new float[]{900.0f, 1356.0f, 589.0f, 887.0f, 1.53f, 0.0f, 0.0f}, new float[]{900.0f, 1368.0f, 585.0f, 888.0f, 1.54f, 0.0f, 0.0f}, new float[]{1080.0f, 1776.0f, 540.0f, 888.0f, 2.0f, 0.0f, 0.0f}, new float[]{1080.0f, 2160.0f, 480.0f, 800.0f, 2.25f, 0.0f, 0.0f}, new float[]{1200.0f, 1824.0f, 586.0f, 888.0f, 2.05f, 0.0f, 0.0f}, new float[]{1200.0f, 1848.0f, 577.0f, 888.0f, 2.08f, 0.0f, 0.0f}, new float[]{1200.0f, 1920.0f, 553.0f, 885.0f, 2.17f, 0.0f, 0.0f}, new float[]{1280.0f, 1920.0f, 590.0f, 885.0f, 2.17f, 0.0f, 0.0f}, new float[]{1280.0f, 1836.0f, 598.0f, 858.0f, 2.14f, 0.0f, 0.0f}, new float[]{1440.0f, 2392.0f, 480.0f, 800.0f, 3.0f, 0.0f, 0.0f}, new float[]{1440.0f, 2560.0f, 480.0f, 854.0f, 3.0f, 0.0f, 0.0f}, new float[]{1440.0f, 2880.0f, 480.0f, 800.0f, 3.0f, 0.0f, 0.0f}, new float[]{1440.0f, 2960.0f, 480.0f, 986.0f, 3.0f, 0.0f, 0.0f}, new float[]{1532.0f, 2560.0f, 480.0f, 800.0f, 3.2f, 0.0f, 0.0f}, new float[]{1600.0f, 2464.0f, 576.0f, 887.0f, 2.78f, 0.0f, 0.0f}, new float[]{1600.0f, 2560.0f, 554.0f, 886.0f, 2.89f, 0.0f, 0.0f}, new float[]{2160.0f, 3840.0f, 480.0f, 854.0f, 4.5f, 0.0f, 0.0f}};
    public static boolean bfZ = true;
    public static int bga = 0;
    public static int bgb = 0;
    public static String bgc = "";
    public static String bgd = "";
    public static int bge = 0;
    public static ArrayList<af> bgf = new ArrayList<>();
    public static String[] bgh = {""};
    public static String[] bgi = {""};

    public ae(Activity activity, float f, String[] strArr, String[] strArr2, String str) {
        bgg = activity;
        bfP = f;
        bgh = strArr;
        bgi = strArr2;
        bgj = str;
    }

    public static String OC() {
        new Build();
        return Build.MODEL;
    }

    public static boolean b(String str, String[] strArr) {
        String str2 = " DEVICE MODEL: " + str;
        for (String str3 : strArr) {
            if (str.compareTo(str3) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void a(PackageManager packageManager, int i, int i2) {
        bga = i;
        bgb = i2;
        String str = " getNavigationBarHeight === " + OE();
        if (d.beG.compareTo("sensorLandscape") == 0 && bga < bgb) {
            int i3 = bga;
            bga = bgb;
            bgb = i3;
            if (OD() >= 6.0d) {
                bga += OE();
                bgb -= OE();
            }
        } else if (d.beG.compareTo("portrait") == 0 && bga > bgb) {
            int i4 = bga;
            bga = bgb;
            bgb = i4;
            if (OD() >= 6.0d) {
                bga += OE();
                bgb -= OE();
            }
        }
        String str2 = " Width = " + bga + " Height = " + bgb;
        if (b(bga + "x" + bgb, bgi)) {
            javax.microedition.lcdui.ao.cnh = true;
        }
        if (b(bgb + "x" + bga, bgi)) {
            javax.microedition.lcdui.ao.cnh = true;
        }
        if (b(OC(), bgh)) {
            javax.microedition.lcdui.ao.cnh = true;
        }
        if (MIDlet.cos) {
            a(packageManager);
        } else if (bfS > bfT) {
            bgf.add(new af(bgj + "." + bfU, 1280, 720));
            bgf.add(new af(bgj + "." + bfU, 800, 480));
            bgf.add(new af(bgj + "." + bfU, 480, JavaUtils.Constants.BANNER_WIDTH_DP));
            bgf.add(new af(bgj + "." + bfU, JavaUtils.Constants.BANNER_WIDTH_DP, 240));
        } else {
            bgf.add(new af(bgj + "." + bfU, 720, 1280));
            bgf.add(new af(bgj + "." + bfU, 480, 800));
            bgf.add(new af(bgj + "." + bfU, JavaUtils.Constants.BANNER_WIDTH_DP, 480));
            bgf.add(new af(bgj + "." + bfU, 240, JavaUtils.Constants.BANNER_WIDTH_DP));
        }
        if (OC().equals("Kindle Fire")) {
            bgb -= 20;
        }
        if (d.bfm != 0) {
            bga -= d.bfm;
        } else if (d.bfn != 0) {
            bgb -= d.bfn;
        }
        bge = cl(bga, bgb);
        if (bge != -1) {
            a(bgf.get(bge));
        }
    }

    public static void a(PackageManager packageManager) {
        try {
            if (bgf.size() <= 0) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(bgj, 0);
                Intent intent = new Intent();
                intent.setPackage(applicationInfo.packageName);
                List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
                String str = "List activites:" + listQueryIntentActivities;
                Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
                while (it.hasNext()) {
                    String strEf = ef(it.next().toString());
                    if (strEf != null && !strEf.equalsIgnoreCase("")) {
                        int iL = l(strEf, true);
                        int iL2 = l(strEf, false);
                        if (iL != -1 && iL2 != -1) {
                            bgf.add(new af(strEf, iL, iL2));
                            String str2 = "Activities Game: " + strEf + " w = " + iL + " h = " + iL2;
                        }
                    }
                }
            }
        } catch (Exception e) {
            String str3 = "getAllActivitiesError " + e.toString();
        }
    }

    public static String ef(String str) {
        int iIndexOf = str.indexOf(bgj);
        if (iIndexOf > 0) {
            int iIndexOf2 = str.indexOf(" ", iIndexOf);
            if (iIndexOf2 <= 0) {
                iIndexOf2 = str.length();
            }
            return str.substring(iIndexOf, iIndexOf2).replace("/", "");
        }
        return null;
    }

    public static int l(String str, boolean z) {
        for (String str2 : str.split("[^[0-9][x][0-9]]")) {
            String[] strArrSplit = str2.split("x");
            if (strArrSplit.length == 2) {
                try {
                    int i = Integer.parseInt(strArrSplit[z ? (char) 0 : (char) 1]);
                    if (i > 0) {
                        return i;
                    }
                } catch (Exception e) {
                    String str3 = "getWidthHeightParammeterError " + e;
                }
            }
        }
        return -1;
    }

    public static int cl(int i, int i2) {
        int iK = k(i, i2, true);
        if (iK <= -1) {
            int iK2 = k(i, i2, false);
            if (iK2 > -1) {
                return iK2;
            }
            return -1;
        }
        return iK;
    }

    /* JADX WARN: Code duplicated, block: B:116:0x034f  */
    /* JADX WARN: Code duplicated, block: B:123:0x0393  */
    /* JADX WARN: Code duplicated, block: B:125:0x0399  */
    public static int k(int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = 0;
        String str = "MultipleScreen GetGameModule w=" + i + " h=" + i2;
        float f = 1000.0f;
        for (int i9 = 0; i9 < bgf.size(); i9++) {
            bgf.get(i9).l(i, i2, z);
        }
        int i10 = -1;
        for (int i11 = 0; i11 < bgf.size() && bfZ && i < 1000; i11++) {
            if (i >= bgf.get(i11).bgl && bgf.get(i11).bgr == 0 && bgf.get(i11).bgs == 0 && bgf.get(i11).bgp < f) {
                f = bgf.get(i11).bgp;
                i10 = i11;
            }
        }
        if (i10 <= -1) {
            if (d.beH || d.beI) {
                int i12 = 0;
                while (true) {
                    if (i12 >= bgf.size()) {
                        i3 = -1;
                        break;
                    }
                    if ((bgf.get(i12).bgl == bfR && bgf.get(i12).bgm == bfQ) || (bgf.get(i12).bgl == bfQ && bgf.get(i12).bgm == bfR)) {
                        i3 = i12;
                        break;
                    }
                    i12++;
                }
                String str2 = "MultipleScreen use_Single_axis_auto_align " + d.beH + " index:" + i3;
                if (i3 > -1) {
                    if (!d.beH) {
                        i5 = i3;
                    } else if (bga > bgb) {
                        int i13 = 0;
                        while (i13 < bfV.length) {
                            if (bfV[i13][0] != bga || bfV[i13][1] != bgb) {
                                i13++;
                            } else {
                                bgf.get(i3).bgn = (int) bfV[i13][2];
                                bgf.get(i3).bgo = (int) bfV[i13][3];
                                bgf.get(i3).n(bga, bgb, z);
                                break;
                            }
                        }
                        if (i13 == bfV.length) {
                            i5 = -1;
                        } else {
                            i5 = i3;
                        }
                    } else {
                        int i14 = 0;
                        while (i14 < bfX.length) {
                            if (bfX[i14][0] != bga || bfX[i14][1] != bgb) {
                                i14++;
                            } else {
                                bgf.get(i3).bgn = (int) bfX[i14][2];
                                bgf.get(i3).bgo = (int) bfX[i14][3];
                                bgf.get(i3).n(bga, bgb, z);
                                break;
                            }
                        }
                        if (i14 == bfX.length) {
                            i5 = -1;
                        } else {
                            i5 = i3;
                        }
                    }
                    if (!d.beI) {
                        i6 = i5;
                    } else if (bga > bgb) {
                        int i15 = 0;
                        while (i15 < bfW.length) {
                            if (bfW[i15][0] != bga || bfW[i15][1] != bgb) {
                                i15++;
                            } else {
                                bgf.get(i5).bgn = (int) bfW[i15][2];
                                bgf.get(i5).bgo = (int) bfW[i15][3];
                                bgf.get(i5).n(bga, bgb, z);
                                break;
                            }
                        }
                        if (i15 == bfW.length) {
                            i6 = -1;
                        } else {
                            i6 = i5;
                        }
                    } else {
                        int i16 = 0;
                        while (i16 < bfY.length) {
                            if (bfY[i16][0] != bga || bfY[i16][1] != bgb) {
                                i16++;
                            } else {
                                bgf.get(i5).bgn = (int) bfY[i16][2];
                                bgf.get(i5).bgo = (int) bfY[i16][3];
                                bgf.get(i5).n(bga, bgb, z);
                                break;
                            }
                        }
                        if (i16 == bfY.length) {
                            i6 = -1;
                        } else {
                            i6 = i5;
                        }
                    }
                    i4 = (i6 == -1 && i3 != -1 && a(bga, bgb, 800, 480, 888, 630, 854, false, i3, z)) ? i3 : i6;
                } else {
                    i4 = i3;
                }
                if (i4 > -1) {
                    String str3 = "MultipleScreen AUTO ALIGNMENT  " + i4;
                    return i4;
                }
            }
            for (int i17 = 0; i17 < bgf.size(); i17++) {
                bgf.get(i17).m(i, i2, z);
            }
            int i18 = i * i2;
            int i19 = -1;
            while (i8 < bgf.size()) {
                if (i19 == -1) {
                    i7 = (i * i2) - ((int) (bgf.get(i8).bgq * ((bgf.get(i8).bgl * bgf.get(i8).bgp) * bgf.get(i8).bgm)));
                    i19 = i8;
                } else if (i18 > (i * i2) - ((int) (bgf.get(i8).bgq * ((bgf.get(i8).bgl * bgf.get(i8).bgp) * bgf.get(i8).bgm)))) {
                    i7 = (i * i2) - ((int) (bgf.get(i8).bgq * ((bgf.get(i8).bgl * bgf.get(i8).bgp) * bgf.get(i8).bgm)));
                    i19 = i8;
                } else {
                    i7 = i18;
                }
                i8++;
                i18 = i7;
            }
            if (i19 > -1) {
                return i19;
            }
            return -1;
        }
        String str4 = "MultipleScreen MATCH RATIO  " + i10;
        return i10;
    }

    public static void a(af afVar) {
        if (bgg != null) {
            String str = "StartGamePackageName = " + afVar.bgk + " Data Width=" + afVar.bgl + " Data Height=" + afVar.bgm + " ScaleW = " + afVar.bgp + " ScaleH = " + afVar.bgq + " TranslateX= " + afVar.bgr + " TranslateY= " + afVar.bgs + " CanvasWidth= " + afVar.bgn + " CanvasHeight= " + afVar.bgo + " Inch Screen = " + OD();
            MIDlet.coy = afVar.bgn;
            MIDlet.coz = afVar.bgo;
            MIDlet.cow = bga;
            MIDlet.cox = bgb;
            MIDlet.cov = OD();
            if (MIDlet.cos) {
                bgd = afVar.bgl + "x" + afVar.bgm;
                au.bhM = bgd + "/";
                if (bgc.length() > 0) {
                    bgc += "/";
                }
            }
            au.bhM = afVar.bgl + "x" + afVar.bgm;
            if (bfZ) {
                javax.microedition.lcdui.f.setScale(afVar.bgp, afVar.bgq);
            } else {
                javax.microedition.lcdui.f.Q(afVar.bgr, afVar.bgs, afVar.bgl, afVar.bgm);
                javax.microedition.lcdui.f.cJ(afVar.bgl, afVar.bgm);
            }
            if (afVar.bgp != 1.0f && afVar.bgq != 1.0f) {
                javax.microedition.lcdui.f.cm(true);
            }
            if (bfZ && (d.beH || d.beI)) {
                javax.microedition.lcdui.f.Q(afVar.bgr, afVar.bgs, afVar.bgn, afVar.bgo);
                javax.microedition.lcdui.f.cJ(afVar.bgn, afVar.bgo);
            } else if (bfZ) {
                javax.microedition.lcdui.f.Q(afVar.bgr, afVar.bgs, afVar.bgl, afVar.bgm);
                javax.microedition.lcdui.f.cJ(afVar.bgl, afVar.bgm);
            }
            if (afVar.bgn >= 800 || afVar.bgo >= 800) {
                String str2 = "Virtual screen " + afVar.bgn + "x" + afVar.bgo;
                MIDlet.coy = afVar.bgn;
                MIDlet.coz = afVar.bgo;
            }
            Intent intent = bgg.getIntent();
            intent.setClassName(bgg.getPackageName(), afVar.bgk);
            intent.addFlags(537001984);
            if (afVar.bgl > afVar.bgm) {
                intent.putExtra("SCREEN_ORIENTATION", 6);
            } else {
                intent.putExtra("SCREEN_ORIENTATION", 1);
            }
            if (intent.getExtras() != null && intent.getExtras().getBundle("pn_data_bundle") != null) {
                intent.putExtra("START_FROM_PUSH", true);
            }
            if (MIDlet.coi) {
                if (bgg.getIntent().getStringExtra("IS_DEMO") != null) {
                    intent.putExtra("IS_DEMO", bgg.getIntent().getStringExtra("IS_DEMO"));
                }
                if (bgg.getIntent().getStringExtra("BLOCK_RECORD") != null) {
                    intent.putExtra("BLOCK_RECORD", bgg.getIntent().getStringExtra("BLOCK_RECORD"));
                }
                bgg.startActivity(intent);
                return;
            }
            bgg.startActivity(intent);
        }
    }

    public static double OD() {
        DisplayMetrics displayMetrics = bgg.getResources().getDisplayMetrics();
        double dSqrt = Math.sqrt(Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2.0d) + Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2.0d));
        String str = "Screen inch of devices : " + dSqrt;
        return dSqrt;
    }

    public static int OE() {
        int identifier;
        if (OC().indexOf("R960") >= 0 || OC().indexOf("HTC One X") >= 0 || OC().indexOf("STARTRAIL 4") >= 0 || OC().indexOf("C1905") >= 0 || OC().indexOf("C2104") >= 0 || OC().indexOf("C2105") >= 0 || OC().indexOf("N861") >= 0 || OC().indexOf("D5303") >= 0 || OC().indexOf("ONE TOUCH 6012A") >= 0 || (identifier = bgg.getResources().getIdentifier("navigation_bar_height", "dimen", Constants.PLATFORM)) <= 0) {
            return 0;
        }
        return bgg.getResources().getDimensionPixelSize(identifier);
    }

    /* JADX WARN: Code duplicated, block: B:92:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:96:0x01dd  */
    public static boolean a(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, int i8, boolean z2) {
        int i9 = 0;
        int i10 = 0;
        float f = 0.0f;
        boolean z3 = false;
        if (i > i2) {
            if (d.beI) {
                f = 1.0f;
                float f2 = 1000.0f;
                int i11 = i5;
                i10 = i6;
                i9 = i5;
                while (i11 >= i3) {
                    float f3 = i / i11;
                    if (i2 / f3 < i4 || i2 / f3 > i6) {
                        f3 = f;
                    } else {
                        float f4 = i / f3;
                        float f5 = i2 / f3;
                        if (f4 < i3 || f4 - f5 >= f2) {
                            f3 = f;
                        } else {
                            f2 = f4 - f5;
                            i9 = (int) f4;
                            int i12 = (int) f5;
                            z3 = true;
                            i10 = (f5 - ((float) i12) <= 0.0f || i12 >= i6) ? i12 : i12 + 1;
                        }
                    }
                    i11--;
                    f = f3;
                }
            }
            if (d.beH) {
                if (!z) {
                    if (i < i7) {
                    }
                    f = i2 / i4;
                    float f6 = i / f;
                    int i13 = (int) f6;
                    if (f6 - i13 >= 0.0f) {
                        i13++;
                    }
                    if (i13 < i3) {
                        i13 = i3;
                    }
                    if (i13 <= i7) {
                        i7 = i13;
                    }
                    z3 = true;
                    i10 = i4;
                    i9 = i7;
                } else {
                    if (i2 < i7) {
                    }
                    f = i / i3;
                    float f7 = i2 / f;
                    int i14 = (int) f7;
                    if (f7 - i14 >= 0.0f) {
                        i14++;
                    }
                    if (i14 < i4) {
                        i14 = i4;
                    }
                    if (i14 <= i7) {
                        i7 = i14;
                    }
                    z3 = true;
                    i10 = i7;
                    i9 = i3;
                }
            }
        } else {
            if (d.beI) {
                f = 1.0f;
                float f8 = 1000.0f;
                int i15 = i5;
                i10 = i5;
                i9 = i6;
                while (i15 >= i3) {
                    float f9 = i2 / i15;
                    if (i / f9 < i4 || i / f9 > i6) {
                        f9 = f;
                    } else {
                        float f10 = i / f9;
                        float f11 = i2 / f9;
                        if (f11 < i3 || f11 - f10 >= f8) {
                            f9 = f;
                        } else {
                            f8 = f11 - f10;
                            i9 = (int) f10;
                            int i16 = (int) f11;
                            z3 = true;
                            if (f10 - i9 <= 0.0f || i9 >= i6) {
                                i10 = i16;
                            } else {
                                i9++;
                                i10 = i16;
                            }
                        }
                    }
                    i15--;
                    f = f9;
                }
            }
            if (d.beH) {
                if (!z) {
                    if (i < i7) {
                    }
                    f = i / i4;
                    float f12 = i2 / f;
                    int i17 = (int) f12;
                    if (f12 - i17 >= 0.0f) {
                        i17++;
                    }
                    if (i17 < i3) {
                        i17 = i3;
                    }
                    if (i17 <= i7) {
                        i7 = i17;
                    }
                    z3 = true;
                    i10 = i7;
                    i9 = i4;
                } else {
                    if (i2 < i7) {
                    }
                    f = i2 / i3;
                    float f13 = i / f;
                    int i18 = (int) f13;
                    if (f13 - i18 >= 0.0f) {
                        i18++;
                    }
                    if (i18 < i4) {
                        i18 = i4;
                    }
                    if (i18 <= i7) {
                        i7 = i18;
                    }
                    z3 = true;
                    i10 = i3;
                    i9 = i7;
                }
            }
        }
        if (z3) {
            bgf.get(i8).bgn = i9;
            bgf.get(i8).bgo = i10;
            bgf.get(i8).n(bga, bgb, z2);
            String str = "Phone: (Width x Height) = (" + bga + " x " + bgb + ")";
            String str2 = "Canvas: (Width x Height) = (" + i9 + " x " + i10 + ")";
            String str3 = "Scale: " + f;
        }
        return z3;
    }
}
