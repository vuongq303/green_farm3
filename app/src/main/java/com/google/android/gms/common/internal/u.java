package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.GooglePlayServicesUtil;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class u {

    @GuardedBy("sCache")
    private static final android.support.v4.e.r<String, String> bNJ = new android.support.v4.e.r<>();

    public static String A(Context context, int i) {
        String strX = i == 6 ? x(context, "common_google_play_services_resolution_required_title") : z(context, i);
        return strX == null ? context.getResources().getString(com.google.android.gms.b.c.bHI) : strX;
    }

    public static String B(Context context, int i) {
        Resources resources = context.getResources();
        String strAF = aF(context);
        switch (i) {
            case 1:
                return resources.getString(com.google.android.gms.b.c.bHF, strAF);
            case 2:
                return com.google.android.gms.common.d.i.aN(context) ? resources.getString(com.google.android.gms.b.c.bHO) : resources.getString(com.google.android.gms.b.c.bHL, strAF);
            case 3:
                return resources.getString(com.google.android.gms.b.c.bHC, strAF);
            case 4:
            case 6:
            case 8:
            case R.styleable.CardView_contentPaddingRight /* 10 */:
            case R.styleable.CardView_contentPaddingTop /* 11 */:
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
            case 13:
            case 14:
            case 15:
            case 19:
            default:
                return resources.getString(com.google.android.gms.common.p.common_google_play_services_unknown_issue, strAF);
            case 5:
                return f(context, "common_google_play_services_invalid_account_text", strAF);
            case 7:
                return f(context, "common_google_play_services_network_error_text", strAF);
            case 9:
                return resources.getString(com.google.android.gms.b.c.bHJ, strAF);
            case 16:
                return f(context, "common_google_play_services_api_unavailable_text", strAF);
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                return f(context, "common_google_play_services_sign_in_failed_text", strAF);
            case 18:
                return resources.getString(com.google.android.gms.b.c.bHN, strAF);
            case 20:
                return f(context, "common_google_play_services_restricted_profile_text", strAF);
        }
    }

    public static String C(Context context, int i) {
        return i == 6 ? f(context, "common_google_play_services_resolution_required_text", aF(context)) : B(context, i);
    }

    public static String D(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(com.google.android.gms.b.c.bHE);
            case 2:
                return resources.getString(com.google.android.gms.b.c.bHK);
            case 3:
                return resources.getString(com.google.android.gms.b.c.bHB);
            default:
                return resources.getString(android.R.string.ok);
        }
    }

    public static String aF(Context context) {
        String packageName = context.getPackageName();
        try {
            return com.google.android.gms.common.e.c.aV(context).in(packageName).toString();
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            String str = context.getApplicationInfo().name;
            return !TextUtils.isEmpty(str) ? str : packageName;
        }
    }

    public static String aG(Context context) {
        return context.getResources().getString(com.google.android.gms.b.c.bHH);
    }

    private static String f(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String strX = x(context, str);
        if (strX == null) {
            strX = resources.getString(com.google.android.gms.common.p.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, strX, str2);
    }

    private static String x(Context context, String str) {
        synchronized (bNJ) {
            String str2 = bNJ.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", "com.google.android.gms");
            if (identifier == 0) {
                String strValueOf = String.valueOf(str);
                Log.w("GoogleApiAvailability", strValueOf.length() != 0 ? "Missing resource: ".concat(strValueOf) : new String("Missing resource: "));
                return null;
            }
            String string = remoteResource.getString(identifier);
            if (!TextUtils.isEmpty(string)) {
                bNJ.put(str, string);
                return string;
            }
            String strValueOf2 = String.valueOf(str);
            Log.w("GoogleApiAvailability", strValueOf2.length() != 0 ? "Got empty resource: ".concat(strValueOf2) : new String("Got empty resource: "));
            return null;
        }
    }

    public static String z(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(com.google.android.gms.b.c.bHG);
            case 2:
                return resources.getString(com.google.android.gms.b.c.bHM);
            case 3:
                return resources.getString(com.google.android.gms.b.c.bHD);
            case 4:
            case 6:
            case 18:
                return null;
            case 5:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return x(context, "common_google_play_services_invalid_account_title");
            case 7:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return x(context, "common_google_play_services_network_error_title");
            case 8:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case 9:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return null;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
            case 13:
            case 14:
            case 15:
            case 19:
            default:
                Log.e("GoogleApiAvailability", new StringBuilder(33).append("Unexpected error code ").append(i).toString());
                return null;
            case 16:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return x(context, "common_google_play_services_sign_in_failed_title");
            case 20:
                Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
                return x(context, "common_google_play_services_restricted_profile_title");
        }
    }
}
