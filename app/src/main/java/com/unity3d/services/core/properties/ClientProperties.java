package com.unity3d.services.core.properties;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.unity3d.services.banners.view.BannerPosition;
import com.unity3d.services.core.log.DeviceLog;
import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

/* JADX INFO: loaded from: classes.dex */
public class ClientProperties {
    private static final X500Principal DEBUG_CERT = new X500Principal("CN=Android Debug,O=Android,C=US");
    private static WeakReference<Activity> _activity;
    private static Application _application;
    private static Context _applicationContext;
    private static BannerPosition _bannerDefaultPosition;
    private static String _gameId;

    public static Activity getActivity() {
        return _activity.get();
    }

    public static void setActivity(Activity activity) {
        _activity = new WeakReference<>(activity);
    }

    public static Context getApplicationContext() {
        return _applicationContext;
    }

    public static void setApplicationContext(Context context) {
        _applicationContext = context;
    }

    public static Application getApplication() {
        return _application;
    }

    public static void setApplication(Application application) {
        _application = application;
    }

    public static String getGameId() {
        return _gameId;
    }

    public static void setGameId(String str) {
        _gameId = str;
    }

    public static String getAppName() {
        return _applicationContext.getPackageName();
    }

    public static String getAppVersion() {
        try {
            return getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            DeviceLog.exception("Error getting package info", e);
            return null;
        }
    }

    public static BannerPosition getbannerDefaultPosition() {
        return _bannerDefaultPosition;
    }

    public static void setBannerDefaultPosition(BannerPosition bannerPosition) {
        _bannerDefaultPosition = bannerPosition;
    }

    public static boolean isAppDebuggable() {
        boolean z;
        boolean z2;
        int i = 0;
        if (getApplicationContext() == null) {
            return false;
        }
        PackageManager packageManager = getApplicationContext().getPackageManager();
        String packageName = getApplicationContext().getPackageName();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            int i2 = applicationInfo.flags & 2;
            applicationInfo.flags = i2;
            boolean z3 = i2 != 0;
            z = false;
            z2 = z3;
        } catch (PackageManager.NameNotFoundException e) {
            DeviceLog.exception("Could not find name", e);
            z = true;
            z2 = false;
        }
        if (z) {
            try {
                Signature[] signatureArr = packageManager.getPackageInfo(packageName, 64).signatures;
                int length = signatureArr.length;
                while (i < length) {
                    boolean zEquals = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[i].toByteArray()))).getSubjectX500Principal().equals(DEBUG_CERT);
                    if (!zEquals) {
                        i++;
                        z2 = zEquals;
                    } else {
                        return zEquals;
                    }
                }
            } catch (PackageManager.NameNotFoundException e2) {
                boolean z4 = z2;
                DeviceLog.exception("Could not find name", e2);
                return z4;
            } catch (CertificateException e3) {
                boolean z5 = z2;
                DeviceLog.exception("Certificate exception", e3);
                return z5;
            }
        }
        return z2;
    }
}
