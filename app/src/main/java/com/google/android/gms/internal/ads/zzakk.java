package com.google.android.gms.internal.ads;

import android.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.common.d.g;
import com.google.android.gms.common.d.o;
import com.google.android.gms.common.d.s;
import com.google.android.gms.common.e.c;
import com.unity3d.services.ads.adunit.AdUnitActivity;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzakk {
    public static final Handler zzcrm = new zzakc(Looper.getMainLooper());

    @GuardedBy("mLock")
    private String zzcpq;

    @GuardedBy("this")
    private Pattern zzcrq;

    @GuardedBy("this")
    private Pattern zzcrr;
    private final Object mLock = new Object();
    private boolean zzcrn = true;
    private boolean zzcro = false;
    private boolean zzcrp = false;

    public static Bundle zza(zzgk zzgkVar) {
        String strZzqv;
        String strZzqx;
        String strZzgo;
        if (zzgkVar == null) {
            return null;
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzawq)).booleanValue()) {
            if (!((Boolean) zzkb.zzik().zzd(zznk.zzaws)).booleanValue()) {
                return null;
            }
        }
        if (com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqu() && com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqw()) {
            return null;
        }
        if (zzgkVar.zzha()) {
            zzgkVar.wakeup();
        }
        zzge zzgeVarZzgy = zzgkVar.zzgy();
        if (zzgeVarZzgy != null) {
            strZzqv = zzgeVarZzgy.getSignature();
            strZzgo = zzgeVarZzgy.zzgo();
            String strZzgp = zzgeVarZzgy.zzgp();
            if (strZzqv != null) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzcn(strZzqv);
            }
            if (strZzgp != null) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzco(strZzgp);
                strZzqx = strZzgp;
            } else {
                strZzqx = strZzgp;
            }
        } else {
            strZzqv = com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqv();
            strZzqx = com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqx();
            strZzgo = null;
        }
        Bundle bundle = new Bundle(1);
        if (strZzqx != null) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzaws)).booleanValue() && !com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqw()) {
                bundle.putString("v_fp_vertical", strZzqx);
            }
        }
        if (strZzqv != null) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzawq)).booleanValue() && !com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqu()) {
                bundle.putString("fingerprint", strZzqv);
                if (!strZzqv.equals(strZzgo)) {
                    bundle.putString("v_fp", strZzgo);
                }
            }
        }
        if (bundle.isEmpty()) {
            return null;
        }
        return bundle;
    }

    public static DisplayMetrics zza(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static PopupWindow zza(View view, int i, int i2, boolean z) {
        return new PopupWindow(view, i, i2, false);
    }

    public static String zza(Context context, View view, zzjn zzjnVar) {
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzaxi)).booleanValue()) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("width", zzjnVar.width);
            jSONObject2.put("height", zzjnVar.height);
            jSONObject.put("size", jSONObject2);
            jSONObject.put("activity", zzap(context));
            if (!zzjnVar.zzarc) {
                JSONArray jSONArray = new JSONArray();
                while (view != null) {
                    Object parent = view.getParent();
                    if (parent != null) {
                        int iIndexOfChild = parent instanceof ViewGroup ? ((ViewGroup) parent).indexOfChild(view) : -1;
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("type", parent.getClass().getName());
                        jSONObject3.put("index_of_child", iIndexOfChild);
                        jSONArray.put(jSONObject3);
                    }
                    view = (parent == null || !(parent instanceof View)) ? null : (View) parent;
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("parents", jSONArray);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            zzakb.zzc("Fail to get view hierarchy json", e);
            return null;
        }
    }

    public static String zza(InputStreamReader inputStreamReader) {
        StringBuilder sb = new StringBuilder(Utility.DEFAULT_STREAM_BUFFER_SIZE);
        char[] cArr = new char[2048];
        while (true) {
            int i = inputStreamReader.read(cArr);
            if (i == -1) {
                return sb.toString();
            }
            sb.append(cArr, 0, i);
        }
    }

    private final JSONArray zza(Collection<?> collection) {
        JSONArray jSONArray = new JSONArray();
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            zza(jSONArray, it.next());
        }
        return jSONArray;
    }

    public static void zza(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Throwable th) {
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public static void zza(Context context, Uri uri) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbdy)).booleanValue()) {
                zzb(context, intent);
            }
            bundle.putString("com.android.browser.application_id", context.getPackageName());
            context.startActivity(intent);
            String string = uri.toString();
            zzakb.zzck(new StringBuilder(String.valueOf(string).length() + 26).append("Opening ").append(string).append(" in a new browser.").toString());
        } catch (ActivityNotFoundException e) {
            zzakb.zzb("No browser is found.", e);
        }
    }

    public static void zza(Context context, String str, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            new zzami(context, str, it.next()).zznt();
        }
    }

    public static void zza(Context context, Throwable th) {
        boolean zBooleanValue;
        if (context == null) {
            return;
        }
        try {
            zBooleanValue = ((Boolean) zzkb.zzik().zzd(zznk.zzaui)).booleanValue();
        } catch (IllegalStateException e) {
            zBooleanValue = false;
        }
        if (zBooleanValue) {
            g.a(context, th);
        }
    }

    private final void zza(JSONArray jSONArray, Object obj) {
        if (obj instanceof Bundle) {
            jSONArray.put(zzf((Bundle) obj));
            return;
        }
        if (obj instanceof Map) {
            jSONArray.put(zzn((Map) obj));
            return;
        }
        if (obj instanceof Collection) {
            jSONArray.put(zza((Collection<?>) obj));
            return;
        }
        if (!(obj instanceof Object[])) {
            jSONArray.put(obj);
            return;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (Object obj2 : (Object[]) obj) {
            zza(jSONArray2, obj2);
        }
        jSONArray.put(jSONArray2);
    }

    private final void zza(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONObject.put(str, zzf((Bundle) obj));
            return;
        }
        if (obj instanceof Map) {
            jSONObject.put(str, zzn((Map) obj));
            return;
        }
        if (obj instanceof Collection) {
            if (str == null) {
                str = "null";
            }
            jSONObject.put(str, zza((Collection<?>) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, zza(Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    public static boolean zza(Activity activity, Configuration configuration) {
        zzkb.zzif();
        int iZza = zzamu.zza(activity, configuration.screenHeightDp);
        int iZza2 = zzamu.zza(activity, configuration.screenWidthDp);
        DisplayMetrics displayMetricsZza = zza((WindowManager) activity.getApplicationContext().getSystemService("window"));
        int i = displayMetricsZza.heightPixels;
        int i2 = displayMetricsZza.widthPixels;
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", Constants.PLATFORM);
        int dimensionPixelSize = identifier > 0 ? activity.getResources().getDimensionPixelSize(identifier) : 0;
        int iIntValue = ((Integer) zzkb.zzik().zzd(zznk.zzbek)).intValue() * ((int) Math.round(((double) activity.getResources().getDisplayMetrics().density) + 0.5d));
        return zzb(i, dimensionPixelSize + iZza, iIntValue) && zzb(i2, iZza2, iIntValue);
    }

    public static boolean zza(ClassLoader classLoader, Class<?> cls, String str) {
        try {
            return cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean zzaj(Context context) {
        boolean z;
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        try {
            ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null) {
                zzakb.zzdk("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
                return false;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & 16) == 0) {
                zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "keyboard"));
                z = false;
            } else {
                z = true;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & 32) == 0) {
                zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "keyboardHidden"));
                z = false;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & 128) == 0) {
                zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", AdUnitActivity.EXTRA_ORIENTATION));
                z = false;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & 256) == 0) {
                zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "screenLayout"));
                z = false;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & AdRequest.MAX_CONTENT_URL_LENGTH) == 0) {
                zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "uiMode"));
                z = false;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & 1024) == 0) {
                zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "screenSize"));
                z = false;
            }
            if ((resolveInfoResolveActivity.activityInfo.configChanges & 2048) != 0) {
                return z;
            }
            zzakb.zzdk(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "smallestScreenSize"));
            return false;
        } catch (Exception e) {
            zzakb.zzc("Could not verify that com.google.android.gms.ads.AdActivity is declared in AndroidManifest.xml", e);
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "AdUtil.hasAdActivity");
            return false;
        }
    }

    protected static String zzam(Context context) {
        try {
            return new WebView(context).getSettings().getUserAgentString();
        } catch (Throwable th) {
            return zzrg();
        }
    }

    public static AlertDialog.Builder zzan(Context context) {
        return new AlertDialog.Builder(context);
    }

    public static zzmw zzao(Context context) {
        return new zzmw(context);
    }

    private static String zzap(Context context) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks != null && !runningTasks.isEmpty() && (runningTaskInfo = runningTasks.get(0)) != null && runningTaskInfo.topActivity != null) {
                return runningTaskInfo.topActivity.getClassName();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean zzaq(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (Process.myPid() == runningAppProcessInfo.pid) {
                    if (runningAppProcessInfo.importance != 100 || keyguardManager.inKeyguardRestrictedInputMode()) {
                        break;
                        break;
                    }
                    PowerManager powerManager = (PowerManager) context.getSystemService("power");
                    if (!(powerManager == null ? false : powerManager.isScreenOn())) {
                        break;
                    }
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x002c, code lost:
    
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap zzar(android.content.Context r3) {
        /*
            r1 = 0
            boolean r0 = r3 instanceof android.app.Activity
            if (r0 != 0) goto L6
        L5:
            return r1
        L6:
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r0 = com.google.android.gms.internal.ads.zznk.zzbbh     // Catch: java.lang.RuntimeException -> L3d
            com.google.android.gms.internal.ads.zzni r2 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch: java.lang.RuntimeException -> L3d
            java.lang.Object r0 = r2.zzd(r0)     // Catch: java.lang.RuntimeException -> L3d
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.RuntimeException -> L3d
            boolean r0 = r0.booleanValue()     // Catch: java.lang.RuntimeException -> L3d
            if (r0 == 0) goto L2e
            android.app.Activity r3 = (android.app.Activity) r3     // Catch: java.lang.RuntimeException -> L3d
            android.view.Window r0 = r3.getWindow()     // Catch: java.lang.RuntimeException -> L3d
            if (r0 == 0) goto L44
            android.view.View r0 = r0.getDecorView()     // Catch: java.lang.RuntimeException -> L3d
            android.view.View r0 = r0.getRootView()     // Catch: java.lang.RuntimeException -> L3d
            android.graphics.Bitmap r0 = zzv(r0)     // Catch: java.lang.RuntimeException -> L3d
        L2c:
            r1 = r0
            goto L5
        L2e:
            android.app.Activity r3 = (android.app.Activity) r3     // Catch: java.lang.RuntimeException -> L3d
            android.view.Window r0 = r3.getWindow()     // Catch: java.lang.RuntimeException -> L3d
            android.view.View r0 = r0.getDecorView()     // Catch: java.lang.RuntimeException -> L3d
            android.graphics.Bitmap r0 = zzu(r0)     // Catch: java.lang.RuntimeException -> L3d
            goto L2c
        L3d:
            r0 = move-exception
            java.lang.String r2 = "Fail to capture screen shot"
            com.google.android.gms.internal.ads.zzakb.zzb(r2, r0)
        L44:
            r0 = r1
            goto L2c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzakk.zzar(android.content.Context):android.graphics.Bitmap");
    }

    public static int zzas(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return 0;
        }
        return applicationInfo.targetSdkVersion;
    }

    private static KeyguardManager zzat(Context context) {
        Object systemService = context.getSystemService("keyguard");
        if (systemService == null || !(systemService instanceof KeyguardManager)) {
            return null;
        }
        return (KeyguardManager) systemService;
    }

    public static boolean zzau(Context context) {
        KeyguardManager keyguardManagerZzat;
        return context != null && s.abx() && (keyguardManagerZzat = zzat(context)) != null && keyguardManagerZzat.isKeyguardLocked();
    }

    public static boolean zzav(Context context) {
        try {
            context.getClassLoader().loadClass("com.google.android.gms.ads.internal.ClientApi");
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        } catch (Throwable th) {
            zzakb.zzb("Error loading class.", th);
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(th, "AdUtil.isLiteSdk");
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00b4 A[EDGE_INSN: B:29:0x00b4->B:17:0x0052 BREAK  A[LOOP:0: B:10:0x002a->B:28:0x00b0]] */
    public static WebResourceResponse zzb(HttpURLConnection httpURLConnection) {
        String strTrim;
        com.google.android.gms.ads.internal.zzbv.zzek();
        String contentType = httpURLConnection.getContentType();
        String strTrim2 = TextUtils.isEmpty(contentType) ? "" : contentType.split(";")[0].trim();
        com.google.android.gms.ads.internal.zzbv.zzek();
        String contentType2 = httpURLConnection.getContentType();
        if (TextUtils.isEmpty(contentType2)) {
            strTrim = "";
            break;
        }
        String[] strArrSplit = contentType2.split(";");
        if (strArrSplit.length == 1) {
            strTrim = "";
            break;
        }
        int i = 1;
        while (true) {
            if (i >= strArrSplit.length) {
                strTrim = "";
                break;
            }
            if (strArrSplit[i].trim().startsWith("charset")) {
                String[] strArrSplit2 = strArrSplit[i].trim().split("=");
                if (strArrSplit2.length > 1) {
                    strTrim = strArrSplit2[1].trim();
                    break;
                }
            }
            i++;
        }
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        HashMap map = new HashMap(headerFields.size());
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null && entry.getValue().size() > 0) {
                map.put(entry.getKey(), entry.getValue().get(0));
            }
        }
        return com.google.android.gms.ads.internal.zzbv.zzem().zza(strTrim2, strTrim, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage(), map, httpURLConnection.getInputStream());
    }

    public static void zzb(Context context, Intent intent) {
        if (intent != null && s.abz()) {
            Bundle extras = intent.getExtras() != null ? intent.getExtras() : new Bundle();
            extras.putBinder("android.support.customtabs.extra.SESSION", null);
            extras.putString("com.android.browser.application_id", context.getPackageName());
            intent.putExtras(extras);
        }
    }

    private static boolean zzb(int i, int i2, int i3) {
        return Math.abs(i - i2) <= i3;
    }

    public static String zzcu(String str) {
        return Uri.parse(str).buildUpon().query(null).build().toString();
    }

    public static int zzcv(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            String strValueOf = String.valueOf(e);
            zzakb.zzdk(new StringBuilder(String.valueOf(strValueOf).length() + 22).append("Could not parse value:").append(strValueOf).toString());
            return 0;
        }
    }

    public static boolean zzcw(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
    }

    public static void zzd(Context context, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        zza(context, str, arrayList);
    }

    public static void zzd(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            runnable.run();
        } else {
            zzaki.zzb(runnable);
        }
    }

    public static void zze(Context context, String str, String str2) {
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(str, 0);
            fileOutputStreamOpenFileOutput.write(str2.getBytes(AndroidWebView.UTF_8));
            fileOutputStreamOpenFileOutput.close();
        } catch (Exception e) {
            zzakb.zzb("Error writing to file in internal storage.", e);
        }
    }

    public static WebResourceResponse zzf(Context context, String str, String str2) {
        try {
            HashMap map = new HashMap();
            map.put("User-Agent", com.google.android.gms.ads.internal.zzbv.zzek().zzm(context, str));
            map.put("Cache-Control", "max-stale=3600");
            String str3 = new zzalt(context).zzc(str2, map).get(60L, TimeUnit.SECONDS);
            if (str3 != null) {
                return new WebResourceResponse("application/javascript", AndroidWebView.UTF_8, new ByteArrayInputStream(str3.getBytes(AndroidWebView.UTF_8)));
            }
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            zzakb.zzc("Could not fetch MRAID JS.", e);
        }
        return null;
    }

    private final JSONObject zzf(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            zza(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    public static int[] zzf(Activity activity) {
        View viewFindViewById;
        Window window = activity.getWindow();
        return (window == null || (viewFindViewById = window.findViewById(R.id.content)) == null) ? zzrj() : new int[]{viewFindViewById.getWidth(), viewFindViewById.getHeight()};
    }

    public static Map<String, String> zzg(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap map = new HashMap();
        for (String str : com.google.android.gms.ads.internal.zzbv.zzem().zzh(uri)) {
            map.put(str, uri.getQueryParameter(str));
        }
        return map;
    }

    public static boolean zzl(Context context, String str) {
        return c.aV(context).checkPermission(str, context.getPackageName()) == 0;
    }

    public static String zzn(Context context, String str) {
        try {
            return new String(o.a(context.openFileInput(str), true), AndroidWebView.UTF_8);
        } catch (IOException e) {
            zzakb.zzck("Error reading from internal storage.");
            return "";
        }
    }

    private static String zzrg() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("Mozilla/5.0 (Linux; U; Android");
        if (Build.VERSION.RELEASE != null) {
            sb.append(" ").append(Build.VERSION.RELEASE);
        }
        sb.append("; ").append(Locale.getDefault());
        if (Build.DEVICE != null) {
            sb.append("; ").append(Build.DEVICE);
            if (Build.DISPLAY != null) {
                sb.append(" Build/").append(Build.DISPLAY);
            }
        }
        sb.append(") AppleWebKit/533 Version/4.0 Safari/533");
        return sb.toString();
    }

    public static String zzrh() {
        return UUID.randomUUID().toString();
    }

    public static String zzri() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? str2 : new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(" ").append(str2).toString();
    }

    private static int[] zzrj() {
        return new int[]{0, 0};
    }

    public static Bundle zzrk() {
        Bundle bundle = new Bundle();
        try {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzavm)).booleanValue()) {
                Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                bundle.putParcelable("debug_memory_info", memoryInfo);
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzavn)).booleanValue()) {
                Runtime runtime = Runtime.getRuntime();
                bundle.putLong("runtime_free_memory", runtime.freeMemory());
                bundle.putLong("runtime_max_memory", runtime.maxMemory());
                bundle.putLong("runtime_total_memory", runtime.totalMemory());
            }
            bundle.putInt("web_view_count", com.google.android.gms.ads.internal.zzbv.zzeo().zzqg());
        } catch (Exception e) {
            zzakb.zzc("Unable to gather memory stats", e);
        }
        return bundle;
    }

    public static Bitmap zzs(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmapCreateBitmap;
    }

    public static Bitmap zzt(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmapZzv = zzv(view);
        return bitmapZzv == null ? zzu(view) : bitmapZzv;
    }

    private static Bitmap zzu(View view) {
        Bitmap bitmapCreateBitmap;
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width == 0 || height == 0) {
                zzakb.zzdk("Width or height of view is zero");
                bitmapCreateBitmap = null;
            } else {
                bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                view.layout(0, 0, width, height);
                view.draw(canvas);
            }
            return bitmapCreateBitmap;
        } catch (RuntimeException e) {
            zzakb.zzb("Fail to capture the webview", e);
            return null;
        }
    }

    private static Bitmap zzv(View view) {
        Bitmap bitmapCreateBitmap = null;
        try {
            boolean zIsDrawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            Bitmap drawingCache = view.getDrawingCache();
            bitmapCreateBitmap = drawingCache != null ? Bitmap.createBitmap(drawingCache) : null;
            view.setDrawingCacheEnabled(zIsDrawingCacheEnabled);
        } catch (RuntimeException e) {
            zzakb.zzb("Fail to capture the web view", e);
        }
        return bitmapCreateBitmap;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    public static boolean zzw(View view) {
        Activity activity;
        View rootView = view.getRootView();
        if (rootView != null) {
            Context context = rootView.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                activity = null;
            }
        } else {
            activity = null;
        }
        if (activity == null) {
            return false;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window == null ? null : window.getAttributes();
        return (attributes == null || (attributes.flags & 524288) == 0) ? false : true;
    }

    public static int zzx(View view) {
        if (view == null) {
            return -1;
        }
        ViewParent parent = view.getParent();
        while (parent != null && !(parent instanceof AdapterView)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            return -1;
        }
        return ((AdapterView) parent).getPositionForView(view);
    }

    public final JSONObject zza(Bundle bundle, JSONObject jSONObject) {
        if (bundle == null) {
            return null;
        }
        try {
            return zzf(bundle);
        } catch (JSONException e) {
            zzakb.zzb("Error converting Bundle to JSON", e);
            return null;
        }
    }

    public final void zza(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(zzm(context, str));
    }

    public final void zza(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (z) {
            com.google.android.gms.ads.internal.zzbv.zzek();
            bundle.putString("device", zzri());
            bundle.putString("eids", TextUtils.join(",", zznk.zzjb()));
        }
        zzkb.zzif();
        zzamu.zza(context, str, str2, bundle, z, new zzakn(this, context, str));
    }

    public final void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", zzm(context, str));
        httpURLConnection.setUseCaches(false);
    }

    public final void zza(Context context, List<String> list) {
        if ((context instanceof Activity) && !TextUtils.isEmpty(zzbfw.zzbn((Activity) context))) {
            if (list == null) {
                zzakb.v("Cannot ping urls: empty list.");
            } else {
                if (!zzoh.zzh(context)) {
                    zzakb.v("Cannot ping url because custom tabs is not supported");
                    return;
                }
                zzoh zzohVar = new zzoh();
                zzohVar.zza(new zzakl(this, list, zzohVar, context));
                zzohVar.zzd((Activity) context);
            }
        }
    }

    public final boolean zza(View view, Context context) {
        Context applicationContext = context.getApplicationContext();
        return zza(view, applicationContext != null ? (PowerManager) applicationContext.getSystemService("power") : null, zzat(context));
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0027  */
    public final boolean zza(View view, PowerManager powerManager, KeyguardManager keyguardManager) {
        boolean z;
        if (com.google.android.gms.ads.internal.zzbv.zzek().zzcrn) {
            z = true;
        } else {
            if (keyguardManager == null ? false : keyguardManager.inKeyguardRestrictedInputMode()) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzazu)).booleanValue() && zzw(view)) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = true;
            }
        }
        if (view.getVisibility() == 0 && view.isShown()) {
            if ((powerManager == null || powerManager.isScreenOn()) && z) {
                if (!((Boolean) zzkb.zzik().zzd(zznk.zzazs)).booleanValue() || view.getLocalVisibleRect(new Rect()) || view.getGlobalVisibleRect(new Rect())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean zzak(Context context) {
        if (this.zzcro) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver(new zzakp(this, null), intentFilter);
        this.zzcro = true;
        return true;
    }

    public final boolean zzal(Context context) {
        if (this.zzcrp) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.ads.intent.DEBUG_LOGGING_ENABLEMENT_CHANGED");
        context.getApplicationContext().registerReceiver(new zzako(this, null), intentFilter);
        this.zzcrp = true;
        return true;
    }

    public final void zzb(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (((Boolean) zzkb.zzik().zzd(zznk.zzazx)).booleanValue()) {
            zza(context, str, str2, bundle, z);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0026 A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:7:0x000a, B:9:0x000e, B:12:0x0038, B:13:0x0042, B:11:0x0026), top: B:20:0x000a, outer: #1 }] */
    public final boolean zzcx(String str) {
        boolean zMatches;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            synchronized (this) {
                try {
                    if (this.zzcrq != null) {
                        if (!((String) zzkb.zzik().zzd(zznk.zzaxo)).equals(this.zzcrq.pattern())) {
                            this.zzcrq = Pattern.compile((String) zzkb.zzik().zzd(zznk.zzaxo));
                        }
                    } else {
                        this.zzcrq = Pattern.compile((String) zzkb.zzik().zzd(zznk.zzaxo));
                    }
                    zMatches = this.zzcrq.matcher(str).matches();
                } catch (Throwable th) {
                    throw th;
                }
            }
            return zMatches;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0026 A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:7:0x000a, B:9:0x000e, B:12:0x0038, B:13:0x0042, B:11:0x0026), top: B:20:0x000a, outer: #1 }] */
    public final boolean zzcy(String str) {
        boolean zMatches;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            synchronized (this) {
                try {
                    if (this.zzcrr != null) {
                        if (!((String) zzkb.zzik().zzd(zznk.zzaxp)).equals(this.zzcrr.pattern())) {
                            this.zzcrr = Pattern.compile((String) zzkb.zzik().zzd(zznk.zzaxp));
                        }
                    } else {
                        this.zzcrr = Pattern.compile((String) zzkb.zzik().zzd(zznk.zzaxp));
                    }
                    zMatches = this.zzcrr.matcher(str).matches();
                } catch (Throwable th) {
                    throw th;
                }
            }
            return zMatches;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    public final int[] zzg(Activity activity) {
        int[] iArrZzf = zzf(activity);
        zzkb.zzif();
        zzkb.zzif();
        return new int[]{zzamu.zzb(activity, iArrZzf[0]), zzamu.zzb(activity, iArrZzf[1])};
    }

    public final int[] zzh(Activity activity) {
        View viewFindViewById;
        Window window = activity.getWindow();
        int[] iArrZzrj = (window == null || (viewFindViewById = window.findViewById(R.id.content)) == null) ? zzrj() : new int[]{viewFindViewById.getTop(), viewFindViewById.getBottom()};
        zzkb.zzif();
        zzkb.zzif();
        return new int[]{zzamu.zzb(activity, iArrZzrj[0]), zzamu.zzb(activity, iArrZzrj[1])};
    }

    public final String zzm(Context context, String str) {
        String strZzrg;
        synchronized (this.mLock) {
            try {
                if (this.zzcpq != null) {
                    strZzrg = this.zzcpq;
                } else if (str == null) {
                    strZzrg = zzrg();
                } else {
                    try {
                        this.zzcpq = com.google.android.gms.ads.internal.zzbv.zzem().getDefaultUserAgent(context);
                    } catch (Exception e) {
                    }
                    if (TextUtils.isEmpty(this.zzcpq)) {
                        zzkb.zzif();
                        if (zzamu.zzsh()) {
                            this.zzcpq = zzam(context);
                        } else {
                            this.zzcpq = null;
                            zzcrm.post(new zzakm(this, context));
                            while (this.zzcpq == null) {
                                try {
                                    this.mLock.wait();
                                } catch (InterruptedException e2) {
                                    this.zzcpq = zzrg();
                                    String strValueOf = String.valueOf(this.zzcpq);
                                    zzakb.zzdk(strValueOf.length() != 0 ? "Interrupted, use default user agent: ".concat(strValueOf) : new String("Interrupted, use default user agent: "));
                                }
                            }
                        }
                    }
                    String strValueOf2 = String.valueOf(this.zzcpq);
                    this.zzcpq = new StringBuilder(String.valueOf(strValueOf2).length() + 10 + String.valueOf(str).length()).append(strValueOf2).append(" (Mobile; ").append(str).toString();
                    try {
                        if (c.aV(context).abO()) {
                            this.zzcpq = String.valueOf(this.zzcpq).concat(";aia");
                        }
                    } catch (Exception e3) {
                        com.google.android.gms.ads.internal.zzbv.zzeo().zza(e3, "AdUtil.getUserAgent");
                    }
                    this.zzcpq = String.valueOf(this.zzcpq).concat(")");
                    strZzrg = this.zzcpq;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return strZzrg;
    }

    public final JSONObject zzn(Map<String, ?> map) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : map.keySet()) {
                zza(jSONObject, str, map.get(str));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            String strValueOf = String.valueOf(e.getMessage());
            throw new JSONException(strValueOf.length() != 0 ? "Could not convert map to JSON: ".concat(strValueOf) : new String("Could not convert map to JSON: "));
        }
    }
}
