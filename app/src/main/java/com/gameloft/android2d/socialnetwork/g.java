package com.gameloft.android2d.socialnetwork;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.gameloft.android.ANMP.GloftGF2F.R;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: GameHelperUtils.java */
/* JADX INFO: loaded from: classes.dex */
class g {
    private static final String[] bpJ = {"*Unknown error.", "*Failed to sign in. Please check your network connection and try again.", "*The application is incorrectly configured. Check that the package name and signing certificate match the client ID created in Developer Console. Also, if the application is not yet published, check that the account you are trying to sign in with is listed as a tester account. See logs for more information.", "*License check failed."};
    private static final int[] bpK = {R.string.gamehelper_unknown_error, R.string.gamehelper_sign_in_failed, R.string.gamehelper_app_misconfigured, R.string.gamehelper_license_failed};

    static String mQ(int i) {
        switch (i) {
            case -1:
                return "RESULT_OK";
            case 0:
                return "RESULT_CANCELED";
            case 10001:
                return "RESULT_RECONNECT_REQUIRED";
            case 10002:
                return "SIGN_IN_FAILED";
            case 10003:
                return "RESULT_LICENSE_FAILED";
            case 10004:
                return "RESULT_APP_MISCONFIGURED";
            case 10005:
                return "RESULT_LEFT_ROOM";
            default:
                return String.valueOf(i);
        }
    }

    static String mR(int i) {
        switch (i) {
            case 0:
                return "SUCCESS(" + i + ")";
            case 1:
                return "SERVICE_MISSING(" + i + ")";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED(" + i + ")";
            case 3:
                return "SERVICE_DISABLED(" + i + ")";
            case 4:
                return "SIGN_IN_REQUIRED(" + i + ")";
            case 5:
                return "INVALID_ACCOUNT(" + i + ")";
            case 6:
                return "RESOLUTION_REQUIRED(" + i + ")";
            case 7:
                return "NETWORK_ERROR(" + i + ")";
            case 8:
                return "INTERNAL_ERROR(" + i + ")";
            case 9:
                return "SERVICE_INVALID(" + i + ")";
            case com.facebook.R.styleable.CardView_contentPaddingRight /* 10 */:
                return "DEVELOPER_ERROR(" + i + ")";
            case com.facebook.R.styleable.CardView_contentPaddingTop /* 11 */:
                return "LICENSE_CHECK_FAILED(" + i + ")";
            case com.facebook.R.styleable.CardView_contentPaddingBottom /* 12 */:
            case 13:
            case 14:
            case 15:
            case 16:
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case Constants.MAX_TREE_DEPTH /* 25 */:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
                return "Unknown error code " + i;
            case 30:
                return "ACCESS TOKEN MISSING";
        }
    }

    static void as(Context context) {
        if (context == null) {
            return;
        }
        String str = "**** Package name         : " + context.getPackageName();
        String str2 = "**** Cert SHA1 fingerprint: " + au(context);
        String str3 = "**** App ID from          : " + at(context);
    }

    static String at(Context context) {
        try {
            Resources resources = context.getResources();
            return resources.getString(resources.getIdentifier("app_id", "string", context.getPackageName()));
        } catch (Exception e) {
            e.printStackTrace();
            return "??? (failed to retrieve APP ID)";
        }
    }

    static String au(Context context) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            if (signatureArr.length == 0) {
                return "ERROR: NO SIGNATURE.";
            }
            if (signatureArr.length > 1) {
                return "ERROR: MULTIPLE SIGNATURES";
            }
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(signatureArr[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bArrDigest.length; i++) {
                if (i > 0) {
                    sb.append(":");
                }
                a(sb, bArrDigest[i]);
            }
            return sb.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "(ERROR: package not found)";
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "(ERROR: SHA1 algorithm not found)";
        }
    }

    static void a(StringBuilder sb, byte b) {
        int i = b;
        if (b < 0) {
            i = b + 256;
        }
        int i2 = i / 16;
        int i3 = i % 16;
        sb.append("0123456789ABCDEF".substring(i2, i2 + 1));
        sb.append("0123456789ABCDEF".substring(i3, i3 + 1));
    }

    static String x(Context context, int i) {
        if (i < 0 || i >= bpK.length) {
            i = 0;
        }
        int i2 = bpK[i];
        try {
            return context.getString(i2);
        } catch (Exception e) {
            e.printStackTrace();
            String str = "*** GameHelper could not found resource id #" + i2 + ". This probably happened because you included it as a stand-alone JAR. BaseGameUtils should be compiled as a LIBRARY PROJECT, so that it can access its resources. Using a fallback string.";
            return bpJ[i];
        }
    }
}
