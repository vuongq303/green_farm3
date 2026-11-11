package com.google.android.gms.auth.api.accounttransfer;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbs;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class zzw extends zzbs {
    public static final Parcelable.Creator<zzw> CREATOR = new e();
    private static final HashMap<String, FastJsonResponse.Field<?, ?>> bFr;
    private int bFA;
    private PendingIntent bFB;
    private DeviceMetaData bFC;
    private final Set<Integer> bFs;
    private String zzbx;
    private byte[] zzbz;
    private final int zzy;

    static {
        HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
        bFr = map;
        map.put("accountType", FastJsonResponse.Field.N("accountType", 2));
        bFr.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, FastJsonResponse.Field.L(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, 3));
        bFr.put("transferBytes", (FastJsonResponse.Field<?, ?>) FastJsonResponse.Field.P("transferBytes", 4));
    }

    public zzw() {
        this.bFs = new android.support.v4.e.c(3);
        this.zzy = 1;
    }

    zzw(Set<Integer> set, int i, String str, int i2, byte[] bArr, PendingIntent pendingIntent, DeviceMetaData deviceMetaData) {
        this.bFs = set;
        this.zzy = i;
        this.zzbx = str;
        this.bFA = i2;
        this.zzbz = bArr;
        this.bFB = pendingIntent;
        this.bFC = deviceMetaData;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public /* synthetic */ Map getFieldMappings() {
        return bFr;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected Object getFieldValue(FastJsonResponse.Field field) {
        switch (field.aaO()) {
            case 1:
                return Integer.valueOf(this.zzy);
            case 2:
                return this.zzbx;
            case 3:
                return Integer.valueOf(this.bFA);
            case 4:
                return this.zzbz;
            default:
                throw new IllegalStateException(new StringBuilder(37).append("Unknown SafeParcelable id=").append(field.aaO()).toString());
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected boolean isFieldSet(FastJsonResponse.Field field) {
        return this.bFs.contains(Integer.valueOf(field.aaO()));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> field, String str, byte[] bArr) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 4:
                this.zzbz = bArr;
                this.bFs.add(Integer.valueOf(iAaO));
                return;
            default:
                throw new IllegalArgumentException(new StringBuilder(59).append("Field with id=").append(iAaO).append(" is not known to be an byte array.").toString());
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 3:
                this.bFA = i;
                this.bFs.add(Integer.valueOf(iAaO));
                return;
            default:
                throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 2:
                this.zzbx = str2;
                this.bFs.add(Integer.valueOf(iAaO));
                return;
            default:
                throw new IllegalArgumentException(String.format("Field with id=%d is not known to be a string.", Integer.valueOf(iAaO)));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        Set<Integer> set = this.bFs;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzy);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.zzbx, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.bFA);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.zzbz, true);
        }
        if (set.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, (Parcelable) this.bFB, i, true);
        }
        if (set.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, (Parcelable) this.bFC, i, true);
        }
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
