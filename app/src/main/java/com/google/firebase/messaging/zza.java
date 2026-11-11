package com.google.firebase.messaging;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.bu;
import android.support.v4.app.bv;
import android.support.v4.content.c;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.d.s;
import com.google.firebase.iid.zzz;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
final class zza {
    private static zza zzbsg;
    private Bundle zzbsh;
    private Method zzbsi;
    private Method zzbsj;
    private final AtomicInteger zzbsk = new AtomicInteger((int) SystemClock.elapsedRealtime());
    private final Context zzqs;

    private zza(Context context) {
        this.zzqs = context.getApplicationContext();
    }

    private final Notification zza(CharSequence charSequence, String str, int i, Integer num, Uri uri, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str2) {
        Notification.Builder smallIcon = new Notification.Builder(this.zzqs).setAutoCancel(true).setSmallIcon(i);
        if (!TextUtils.isEmpty(charSequence)) {
            smallIcon.setContentTitle(charSequence);
        }
        if (!TextUtils.isEmpty(str)) {
            smallIcon.setContentText(str);
            smallIcon.setStyle(new Notification.BigTextStyle().bigText(str));
        }
        if (num != null) {
            smallIcon.setColor(num.intValue());
        }
        if (uri != null) {
            smallIcon.setSound(uri);
        }
        if (pendingIntent != null) {
            smallIcon.setContentIntent(pendingIntent);
        }
        if (pendingIntent2 != null) {
            smallIcon.setDeleteIntent(pendingIntent2);
        }
        if (str2 != null) {
            if (this.zzbsi == null) {
                this.zzbsi = zzfh("setChannelId");
            }
            if (this.zzbsi == null) {
                this.zzbsi = zzfh("setChannel");
            }
            if (this.zzbsi == null) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel");
            } else {
                try {
                    this.zzbsi.invoke(smallIcon, str2);
                } catch (IllegalAccessException e) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
                } catch (IllegalArgumentException e2) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e2);
                } catch (SecurityException e3) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e3);
                } catch (InvocationTargetException e4) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e4);
                }
            }
        }
        return smallIcon.build();
    }

    static String zza(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    private final boolean zzaf(int i) {
        if (Build.VERSION.SDK_INT != 26) {
            return true;
        }
        try {
            if (!(this.zzqs.getResources().getDrawable(i, null) instanceof AdaptiveIconDrawable)) {
                return true;
            }
            Log.e("FirebaseMessaging", new StringBuilder(77).append("Adaptive icons cannot be used in notifications. Ignoring icon id: ").append(i).toString());
            return false;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }

    static String zzb(Bundle bundle, String str) {
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf("_loc_key");
        return zza(bundle, strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
    }

    /* JADX WARN: Multi-variable type inference failed */
    static Object[] zzc(Bundle bundle, String str) {
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf("_loc_args");
        String strZza = zza(bundle, strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        if (TextUtils.isEmpty(strZza)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(strZza);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = jSONArray.opt(i);
            }
            return strArr;
        } catch (JSONException e) {
            String strValueOf3 = String.valueOf(str);
            String strValueOf4 = String.valueOf("_loc_args");
            String strSubstring = (strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3)).substring(6);
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strSubstring).length() + 41 + String.valueOf(strZza).length()).append("Malformed ").append(strSubstring).append(": ").append(strZza).append("  Default value will be used.").toString());
            return null;
        }
    }

    private final String zzd(Bundle bundle, String str) {
        String strZza = zza(bundle, str);
        if (!TextUtils.isEmpty(strZza)) {
            return strZza;
        }
        String strZzb = zzb(bundle, str);
        if (TextUtils.isEmpty(strZzb)) {
            return null;
        }
        Resources resources = this.zzqs.getResources();
        int identifier = resources.getIdentifier(strZzb, "string", this.zzqs.getPackageName());
        if (identifier == 0) {
            String strValueOf = String.valueOf(str);
            String strValueOf2 = String.valueOf("_loc_key");
            String strSubstring = (strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf)).substring(6);
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strSubstring).length() + 49 + String.valueOf(strZzb).length()).append(strSubstring).append(" resource not found: ").append(strZzb).append(" Default value will be used.").toString());
            return null;
        }
        Object[] objArrZzc = zzc(bundle, str);
        if (objArrZzc == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, objArrZzc);
        } catch (MissingFormatArgumentException e) {
            String string = Arrays.toString(objArrZzc);
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strZzb).length() + 58 + String.valueOf(string).length()).append("Missing format argument for ").append(strZzb).append(": ").append(string).append(" Default value will be used.").toString(), e);
            return null;
        }
    }

    private static Method zzfh(String str) {
        try {
            return Notification.Builder.class.getMethod(str, String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    private final Integer zzfi(String str) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.valueOf(Color.parseColor(str));
            } catch (IllegalArgumentException e) {
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + 54).append("Color ").append(str).append(" not valid. Notification will use default color.").toString());
            }
        }
        int i = zzti().getInt("com.google.firebase.messaging.default_notification_color", 0);
        if (i == 0) {
            return null;
        }
        try {
            return Integer.valueOf(c.c(this.zzqs, i));
        } catch (Resources.NotFoundException e2) {
            Log.w("FirebaseMessaging", "Cannot find the color resource referenced in AndroidManifest.");
            return null;
        }
    }

    private final String zzfj(String str) {
        if (!s.ax()) {
            return null;
        }
        NotificationManager notificationManager = (NotificationManager) this.zzqs.getSystemService(NotificationManager.class);
        try {
            if (this.zzbsj == null) {
                this.zzbsj = notificationManager.getClass().getMethod("getNotificationChannel", String.class);
            }
            if (!TextUtils.isEmpty(str)) {
                if (this.zzbsj.invoke(notificationManager, str) != null) {
                    return str;
                }
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + 122).append("Notification Channel requested (").append(str).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
            }
            String string = zzti().getString("com.google.firebase.messaging.default_notification_channel_id");
            if (TextUtils.isEmpty(string)) {
                Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
            } else {
                if (this.zzbsj.invoke(notificationManager, string) != null) {
                    return string;
                }
                Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
            }
            if (this.zzbsj.invoke(notificationManager, "fcm_fallback_notification_channel") == null) {
                Class<?> cls = Class.forName("android.app.NotificationChannel");
                notificationManager.getClass().getMethod("createNotificationChannel", cls).invoke(notificationManager, cls.getConstructor(String.class, CharSequence.class, Integer.TYPE).newInstance("fcm_fallback_notification_channel", this.zzqs.getString(R.string.fcm_fallback_notification_channel_label), 3));
            }
            return "fcm_fallback_notification_channel";
        } catch (ClassNotFoundException e) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
            return null;
        } catch (IllegalAccessException e2) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e2);
            return null;
        } catch (IllegalArgumentException e3) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e3);
            return null;
        } catch (InstantiationException e4) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e4);
            return null;
        } catch (LinkageError e5) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e5);
            return null;
        } catch (NoSuchMethodException e6) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e6);
            return null;
        } catch (SecurityException e7) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e7);
            return null;
        } catch (InvocationTargetException e8) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e8);
            return null;
        }
    }

    static boolean zzl(Bundle bundle) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zza(bundle, "gcm.n.e")) || zza(bundle, "gcm.n.icon") != null;
    }

    static Uri zzm(Bundle bundle) {
        String strZza = zza(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(strZza)) {
            strZza = zza(bundle, "gcm.n.link");
        }
        if (TextUtils.isEmpty(strZza)) {
            return null;
        }
        return Uri.parse(strZza);
    }

    static String zzo(Bundle bundle) {
        String strZza = zza(bundle, "gcm.n.sound2");
        return TextUtils.isEmpty(strZza) ? zza(bundle, "gcm.n.sound") : strZza;
    }

    private final Bundle zzti() {
        if (this.zzbsh != null) {
            return this.zzbsh;
        }
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.zzqs.getPackageManager().getApplicationInfo(this.zzqs.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return Bundle.EMPTY;
        }
        this.zzbsh = applicationInfo.metaData;
        return this.zzbsh;
    }

    static synchronized zza zzw(Context context) {
        if (zzbsg == null) {
            zzbsg = new zza(context);
        }
        return zzbsg;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0069 A[EDGE_INSN: B:24:0x0069->B:21:0x0063 BREAK  A[LOOP:0: B:14:0x004c->B:112:?]] */
    /* JADX WARN: Code duplicated, block: B:66:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:70:0x01fc  */
    final boolean zzn(Bundle bundle) {
        boolean z;
        int i;
        int identifier;
        Uri defaultUri;
        Intent intent;
        PendingIntent activity;
        PendingIntent pendingIntentZza;
        Notification notificationBuild;
        PendingIntent pendingIntentZza2 = null;
        if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zza(bundle, "gcm.n.noui"))) {
            return true;
        }
        if (((KeyguardManager) this.zzqs.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            z = false;
            break;
        }
        if (!s.abC()) {
            SystemClock.sleep(10L);
        }
        int iMyPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.zzqs.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            z = false;
            break;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (next.pid == iMyPid) {
                if (next.importance != 100) {
                    z = false;
                    break;
                }
                z = true;
                break;
            }
        }
        if (z) {
            return false;
        }
        CharSequence charSequenceZzd = zzd(bundle, "gcm.n.title");
        if (TextUtils.isEmpty(charSequenceZzd)) {
            charSequenceZzd = this.zzqs.getApplicationInfo().loadLabel(this.zzqs.getPackageManager());
        }
        String strZzd = zzd(bundle, "gcm.n.body");
        String strZza = zza(bundle, "gcm.n.icon");
        if (TextUtils.isEmpty(strZza)) {
            i = zzti().getInt("com.google.firebase.messaging.default_notification_icon", 0);
            if (i != 0 || !zzaf(i)) {
                i = this.zzqs.getApplicationInfo().icon;
            }
            if (i != 0 || !zzaf(i)) {
                i = android.R.drawable.sym_def_app_icon;
            }
            identifier = i;
        } else {
            Resources resources = this.zzqs.getResources();
            identifier = resources.getIdentifier(strZza, "drawable", this.zzqs.getPackageName());
            if ((identifier == 0 || !zzaf(identifier)) && ((identifier = resources.getIdentifier(strZza, "mipmap", this.zzqs.getPackageName())) == 0 || !zzaf(identifier))) {
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strZza).length() + 61).append("Icon resource ").append(strZza).append(" not found. Notification will use default icon.").toString());
                i = zzti().getInt("com.google.firebase.messaging.default_notification_icon", 0);
                if (i != 0) {
                    i = this.zzqs.getApplicationInfo().icon;
                } else {
                    i = this.zzqs.getApplicationInfo().icon;
                }
                if (i != 0) {
                    i = android.R.drawable.sym_def_app_icon;
                } else {
                    i = android.R.drawable.sym_def_app_icon;
                }
                identifier = i;
            }
        }
        Integer numZzfi = zzfi(zza(bundle, "gcm.n.color"));
        String strZzo = zzo(bundle);
        if (TextUtils.isEmpty(strZzo)) {
            defaultUri = null;
        } else if ("default".equals(strZzo) || this.zzqs.getResources().getIdentifier(strZzo, "raw", this.zzqs.getPackageName()) == 0) {
            defaultUri = RingtoneManager.getDefaultUri(2);
        } else {
            String packageName = this.zzqs.getPackageName();
            defaultUri = Uri.parse(new StringBuilder(String.valueOf(packageName).length() + 24 + String.valueOf(strZzo).length()).append("android.resource://").append(packageName).append("/raw/").append(strZzo).toString());
        }
        String strZza2 = zza(bundle, "gcm.n.click_action");
        if (TextUtils.isEmpty(strZza2)) {
            Uri uriZzm = zzm(bundle);
            if (uriZzm != null) {
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setPackage(this.zzqs.getPackageName());
                intent2.setData(uriZzm);
                intent = intent2;
            } else {
                Intent launchIntentForPackage = this.zzqs.getPackageManager().getLaunchIntentForPackage(this.zzqs.getPackageName());
                if (launchIntentForPackage == null) {
                    Log.w("FirebaseMessaging", "No activity found to launch app");
                }
                intent = launchIntentForPackage;
            }
        } else {
            Intent intent3 = new Intent(strZza2);
            intent3.setPackage(this.zzqs.getPackageName());
            intent3.setFlags(268435456);
            intent = intent3;
        }
        if (intent == null) {
            activity = null;
        } else {
            intent.addFlags(67108864);
            Bundle bundle2 = new Bundle(bundle);
            FirebaseMessagingService.zzp(bundle2);
            intent.putExtras(bundle2);
            for (String str : bundle2.keySet()) {
                if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                    intent.removeExtra(str);
                }
            }
            activity = PendingIntent.getActivity(this.zzqs, this.zzbsk.incrementAndGet(), intent, 1073741824);
        }
        if (FirebaseMessagingService.zzq(bundle)) {
            Intent intent4 = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
            zza(intent4, bundle);
            intent4.putExtra("pending_intent", activity);
            pendingIntentZza = zzz.zza(this.zzqs, this.zzbsk.incrementAndGet(), intent4, 1073741824);
            Intent intent5 = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
            zza(intent5, bundle);
            pendingIntentZza2 = zzz.zza(this.zzqs, this.zzbsk.incrementAndGet(), intent5, 1073741824);
        } else {
            pendingIntentZza = activity;
        }
        if (!s.ax() || this.zzqs.getApplicationInfo().targetSdkVersion <= 25) {
            bv bvVarL = new bv(this.zzqs).d(true).l(identifier);
            if (!TextUtils.isEmpty(charSequenceZzd)) {
                bvVarL.b(charSequenceZzd);
            }
            if (!TextUtils.isEmpty(strZzd)) {
                bvVarL.c(strZzd);
                bvVarL.a(new bu().a(strZzd));
            }
            if (numZzfi != null) {
                bvVarL.o(numZzfi.intValue());
            }
            if (defaultUri != null) {
                bvVarL.a(defaultUri);
            }
            if (pendingIntentZza != null) {
                bvVarL.a(pendingIntentZza);
            }
            if (pendingIntentZza2 != null) {
                bvVarL.b(pendingIntentZza2);
            }
            notificationBuild = bvVarL.build();
        } else {
            notificationBuild = zza(charSequenceZzd, strZzd, identifier, numZzfi, defaultUri, pendingIntentZza, pendingIntentZza2, zzfj(zza(bundle, "gcm.n.android_channel_id")));
        }
        String strZza3 = zza(bundle, "gcm.n.tag");
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            Log.d("FirebaseMessaging", "Showing notification");
        }
        NotificationManager notificationManager = (NotificationManager) this.zzqs.getSystemService("notification");
        if (TextUtils.isEmpty(strZza3)) {
            strZza3 = new StringBuilder(37).append("FCM-Notification:").append(SystemClock.uptimeMillis()).toString();
        }
        notificationManager.notify(strZza3, 0, notificationBuild);
        return true;
    }
}
