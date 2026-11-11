package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class ConnectionResult extends AbstractSafeParcelable {
    private final String bFl;
    private final int bHY;
    private final PendingIntent bHZ;
    private final int zzal;
    public static final ConnectionResult bHX = new ConnectionResult(0);
    public static final Parcelable.Creator<ConnectionResult> CREATOR = new b();

    public ConnectionResult(int i) {
        this(i, null, null);
    }

    ConnectionResult(int i, int i2, PendingIntent pendingIntent, String str) {
        this.zzal = i;
        this.bHY = i2;
        this.bHZ = pendingIntent;
        this.bFl = str;
    }

    public ConnectionResult(int i, PendingIntent pendingIntent) {
        this(i, pendingIntent, null);
    }

    public ConnectionResult(int i, PendingIntent pendingIntent, String str) {
        this(1, i, pendingIntent, str);
    }

    static String np(int i) {
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "SUCCESS";
            case 1:
                return "SERVICE_MISSING";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return "NETWORK_ERROR";
            case 8:
                return "INTERNAL_ERROR";
            case 9:
                return "SERVICE_INVALID";
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                return "DEVELOPER_ERROR";
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                return "LICENSE_CHECK_FAILED";
            case 13:
                return "CANCELED";
            case 14:
                return "TIMEOUT";
            case 15:
                return "INTERRUPTED";
            case 16:
                return "API_UNAVAILABLE";
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                return "SIGN_IN_FAILED";
            case 18:
                return "SERVICE_UPDATING";
            case 19:
                return "SERVICE_MISSING_PERMISSION";
            case 20:
                return "RESTRICTED_PROFILE";
            case 21:
                return "API_VERSION_UPDATE_REQUIRED";
            case 99:
                return "UNFINISHED";
            case 1500:
                return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
            default:
                return new StringBuilder(31).append("UNKNOWN_ERROR_CODE(").append(i).append(")").toString();
        }
    }

    public final boolean Sp() {
        return this.bHY == 0;
    }

    public final boolean Yy() {
        return (this.bHY == 0 || this.bHZ == null) ? false : true;
    }

    public final PendingIntent Yz() {
        return this.bHZ;
    }

    public final void b(Activity activity, int i) throws IntentSender.SendIntentException {
        if (Yy()) {
            activity.startIntentSenderForResult(this.bHZ.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConnectionResult)) {
            return false;
        }
        ConnectionResult connectionResult = (ConnectionResult) obj;
        return this.bHY == connectionResult.bHY && bd.b(this.bHZ, connectionResult.bHZ) && bd.b(this.bFl, connectionResult.bFl);
    }

    public final int getErrorCode() {
        return this.bHY;
    }

    public final String getErrorMessage() {
        return this.bFl;
    }

    public final int hashCode() {
        return bd.hashCode(Integer.valueOf(this.bHY), this.bHZ, this.bFl);
    }

    public final String toString() {
        return bd.C(this).c("statusCode", np(this.bHY)).c("resolution", this.bHZ).c(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, this.bFl).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, getErrorCode());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) Yz(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, getErrorMessage(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
