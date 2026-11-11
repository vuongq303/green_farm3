package com.gameloft.glads;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.CalendarContract;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.gameloft.android.ANMP.GloftGF2F.InGameBrowserActivity;
import com.gameloft.android.ANMP.GloftGF2F.aw;
import com.gameloft.android.wrapper.au;
import com.gameloft.android.wrapper.d;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AndroidDevice {
    static int screenHeight;
    static int screenWidth;
    private static String TAG = "GLADS";
    static String userAgent = "";

    public static native void nativeModalWebViewResponse(String str, String str2);

    public static String GetLoggingPath() {
        return au.getContext().getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
    }

    public static String GetLanguage() {
        return au.Pk();
    }

    public static String GetSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String GetModel() {
        return (Build.MANUFACTURER + "_" + Build.MODEL).replaceAll(" ", "");
    }

    public static String GetHDIDFV() {
        return au.Os();
    }

    public static String GetGoogleAdID() {
        return au.Qe() == 0 ? au.NB() : "";
    }

    public static String GetGoogleOptOut() {
        return "" + au.Qe();
    }

    public static String GetNetworkCountryIso() {
        if (!au.PT()) {
            return "";
        }
        try {
            return au.getNetworkCountryIso();
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetOperatorMCC() {
        if (!au.PT()) {
            return "";
        }
        try {
            return au.getSimOperator().substring(0, 3);
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetOperatorMNC() {
        if (!au.PT()) {
            return "";
        }
        try {
            return au.getSimOperator().substring(3);
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetConnectionType() {
        if (HasInternetConnection()) {
            return au.Pg() == 1 ? "wifi" : "cellular";
        }
        return "none";
    }

    public static String GetCachePath() {
        return AndroidUtils.GetContext().getFilesDir().getAbsolutePath();
    }

    public static boolean HasInternetConnection() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) AndroidUtils.GetContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
    }

    public static boolean IsDeviceRooted() {
        String str = Build.TAGS;
        if (str != null && str.contains("test-keys")) {
            return true;
        }
        try {
            return new File("/system/app/Superuser.apk").exists();
        } catch (Exception e) {
        }
    }

    public static void LaunchBrowser(String str) {
        try {
            AndroidUtils.GetContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
        }
    }

    public static float GetScreenXDPI() {
        return GetDisplayMetrics().xdpi;
    }

    public static float GetScreenYDPI() {
        return GetDisplayMetrics().ydpi;
    }

    public static float GetDIPScaleFactor() {
        return 160.0f / GetDisplayMetrics().densityDpi;
    }

    public static float GetLogicalDensity() {
        DisplayMetrics displayMetricsGetDisplayMetrics = GetDisplayMetrics();
        return Build.VERSION.SDK_INT >= 24 ? displayMetricsGetDisplayMetrics.xdpi / 160.0f : displayMetricsGetDisplayMetrics.density;
    }

    public static int GetOrientation() {
        char c;
        Display defaultDisplay = ((WindowManager) AndroidUtils.GetContext().getSystemService("window")).getDefaultDisplay();
        int rotation = defaultDisplay.getRotation();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (((rotation != 0 && rotation != 2) || i2 <= i) && ((rotation != 1 && rotation != 3) || i <= i2)) {
            switch (rotation) {
                case 0:
                    c = 0;
                    break;
                case 1:
                    c = 1;
                    break;
                case 2:
                    c = '\b';
                    break;
                case 3:
                    c = '\t';
                    break;
                default:
                    c = 0;
                    break;
            }
        } else {
            switch (rotation) {
                case 0:
                    c = 1;
                    break;
                case 1:
                    c = 0;
                    break;
                case 2:
                    c = '\t';
                    break;
                case 3:
                    c = '\b';
                    break;
                default:
                    c = 1;
                    break;
            }
        }
        return (c == 1 || c == '\t') ? 1 : 2;
    }

    public static int GetSoundVolume() {
        AudioManager audioManager = (AudioManager) AndroidUtils.GetContext().getSystemService("audio");
        int streamMaxVolume = audioManager.getStreamMaxVolume(3);
        if (streamMaxVolume != 0) {
            return (audioManager.getStreamVolume(3) * 100) / streamMaxVolume;
        }
        return 0;
    }

    public static void PauseUserMusic() {
        Intent intent = new Intent("com.android.music.musicservicecommand");
        intent.putExtra("command", "pause");
        AndroidUtils.GetContext().sendBroadcast(intent);
    }

    public static String[] GetChildDirectories(String str) {
        String[] list = new File(str).list();
        if (list == null) {
            list = new String[0];
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            if (new File(str + "/" + list[i]).isDirectory()) {
                arrayList.add(list[i]);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static void CreateDirectory(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void DeleteDirectory(String str) {
        RemoveDirectoryRecursive(new File(str));
    }

    private static void RemoveDirectoryRecursive(File file) {
        String[] list;
        try {
            if (file.isDirectory() && (list = file.list()) != null) {
                for (String str : list) {
                    RemoveDirectoryRecursive(new File(file, str));
                }
            }
            File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(file2);
            file2.delete();
        } catch (Exception e) {
        }
    }

    public static String[] GetFiles(String str) {
        String[] list = new File(str).list();
        if (list == null) {
            list = new String[0];
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            if (!new File(str + "/" + list[i]).isDirectory()) {
                arrayList.add(list[i]);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static void RemoveFile(String str) {
        try {
            File file = new File(str);
            if (!file.isDirectory()) {
                File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(file2);
                file2.delete();
            }
        } catch (Exception e) {
        }
    }

    public static boolean RequestStoragePermission() {
        try {
            au.ei("android.permission.WRITE_EXTERNAL_STORAGE");
            return au.biy;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean HasStoragePermission() {
        return au.biy;
    }

    public static boolean RequestCameraPermission() {
        try {
            au.ei("android.permission.CAMERA");
            return au.biE;
        } catch (Exception e) {
            return false;
        }
    }

    public static void SaveScreenshot(String str) {
        try {
            byte[] bArrDecode = Base64.decode(str, 0);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "GLCode_" + System.currentTimeMillis() + ".png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArrDecode);
            fileOutputStream.close();
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(file));
            AndroidUtils.GetContext().sendBroadcast(intent);
        } catch (Exception e) {
        }
    }

    public static boolean AddCalendarEvent(String str) {
        try {
            if (str.endsWith(":")) {
                str = str.substring(0, str.length() - 1);
            }
            JSONObject jSONObject = new JSONObject(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
            Date date = simpleDateFormat.parse(jSONObject.getString("begintime"));
            Date date2 = simpleDateFormat.parse(jSONObject.getString("endtime"));
            AndroidUtils.GetContext().startActivity(new Intent("android.intent.action.INSERT").setData(CalendarContract.Events.CONTENT_URI).putExtra("beginTime", date.getTime()).putExtra("endTime", date2.getTime()).putExtra("title", jSONObject.getString("title")).putExtra("description", jSONObject.getString(FirebaseAnalytics.Param.LOCATION)).putExtra("eventLocation", jSONObject.getString("description")).putExtra("availability", 0));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void LaunchModalWebView(String str) {
        LaunchModalWebView(str, null);
    }

    public static void LaunchModalWebView(String str, Message message) {
        InGameBrowserActivity.M(str);
        InGameBrowserActivity.a(str, new aw() { // from class: com.gameloft.glads.AndroidDevice.1
            @Override // com.gameloft.android.ANMP.GloftGF2F.aw
            public void ModalResponse(String str2, String str3) {
                try {
                    AndroidDevice.nativeModalWebViewResponse(str2, str3);
                } catch (Exception e) {
                }
            }
        }, message);
    }

    private static DisplayMetrics GetDisplayMetrics() {
        Display defaultDisplay = ((WindowManager) AndroidUtils.GetContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float GetUsedMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) AndroidUtils.GetContext().getSystemService("activity")).getMemoryInfo(memoryInfo);
        if (Build.VERSION.SDK_INT >= 16) {
            return memoryInfo.totalMem - memoryInfo.availMem;
        }
        return 0.0f;
    }

    public static float GetTotalMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) AndroidUtils.GetContext().getSystemService("activity")).getMemoryInfo(memoryInfo);
        if (Build.VERSION.SDK_INT >= 16) {
            return memoryInfo.totalMem;
        }
        return 0.0f;
    }

    public static String GetUserAgent() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidDevice.2
            @Override // java.lang.Runnable
            public void run() {
                AndroidDevice.userAgent = au.getUserAgent();
            }
        });
        return userAgent;
    }

    public static void LaunchInGameBrowser(String str) {
        InGameBrowserActivity.N(GLAds.gameLanguage.toUpperCase());
        InGameBrowserActivity.M(str);
        InGameBrowserActivity.P(str);
    }

    public static void QuitIGB() {
        InGameBrowserActivity.QuitIGB();
    }

    public static String GetCountry() {
        return Locale.getDefault().getCountry();
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00a8 A[PHI: r1 r2
  0x00a8: PHI (r1v3 int) = (r1v2 int), (r1v2 int), (r1v8 int), (r1v8 int) binds: [B:27:0x0097, B:28:0x0099, B:19:0x006b, B:20:0x006d] A[DONT_GENERATE, DONT_INLINE]
  0x00a8: PHI (r2v1 int) = (r2v0 int), (r2v0 int), (r2v3 int), (r2v3 int) binds: [B:27:0x0097, B:28:0x0099, B:19:0x006b, B:20:0x006d] A[DONT_GENERATE, DONT_INLINE]] */
    public static void CalculateScreenSize() {
        int i;
        int i2;
        int i3;
        int i4;
        Display defaultDisplay = ((WindowManager) au.getContext().getSystemService("window")).getDefaultDisplay();
        if (d.beM) {
            Point point = new Point();
            try {
                Method declaredMethod = Class.forName("android.view.Display").getDeclaredMethod("getRealSize", Point.class);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                declaredMethod.invoke(defaultDisplay, point);
            } catch (Exception e) {
            }
            i = point.x;
            i2 = point.y;
            if (d.beG.compareTo("sensorLandscape") == 0 && i < i2) {
                i3 = i;
                i4 = i2;
            } else if (d.beG.compareTo("portrait") != 0 || i <= i2) {
                i3 = i2;
                i4 = i;
            } else {
                i3 = i;
                i4 = i2;
            }
        } else {
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            i = displayMetrics.widthPixels;
            i2 = displayMetrics.heightPixels;
            if (d.beG.compareTo("sensorLandscape") == 0 && i < i2) {
                i3 = i;
                i4 = i2;
            } else if (d.beG.compareTo("portrait") != 0 || i <= i2) {
                i3 = i2;
                i4 = i;
            } else {
                i3 = i;
                i4 = i2;
            }
        }
        if (d.bfm != 0) {
            i4 -= d.bfm;
        } else if (d.bfn != 0) {
            i3 -= d.bfn;
        }
        screenWidth = i4;
        screenHeight = i3;
    }

    public static int GetWidth() {
        return screenWidth;
    }

    public static int GetHeight() {
        return screenHeight;
    }

    public static int GetLeftSpacing() {
        return d.bfm;
    }

    public static int GetTopSpacing() {
        return d.bfn;
    }

    public static String GenerateUUID() {
        return au.GenerateUUID();
    }

    public static int GetUserLocationStatus() {
        return au.PO();
    }

    public static float GetUserLocationLatitude() {
        return (float) au.PP();
    }

    public static float GetUserLocationLongitude() {
        return (float) au.PQ();
    }

    public static float GetUserLocationAccuracy() {
        return au.PR();
    }

    public static String GetSDFolder() {
        return Environment.getExternalStorageDirectory() + "";
    }

    public static void SetAdServerCustomID(String str) {
        if (str != null && !str.equals("")) {
            GLAds.b_hasCustomId = true;
            InGameBrowserActivity.SetAdServerCustomID(str);
        }
    }
}
