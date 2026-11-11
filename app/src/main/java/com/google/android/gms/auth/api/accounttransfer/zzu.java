package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbs;
import com.unity3d.ads.metadata.InAppPurchaseMetaData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class zzu extends zzbs {
    public static final Parcelable.Creator<zzu> CREATOR = new d();
    private static final HashMap<String, FastJsonResponse.Field<?, ?>> bFr;
    private final Set<Integer> bFs;
    private zzw bFz;
    private String bmF;
    private String zzbw;
    private final int zzy;

    static {
        HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
        bFr = map;
        map.put("authenticatorInfo", FastJsonResponse.Field.a("authenticatorInfo", 2, zzw.class));
        bFr.put(InAppPurchaseMetaData.KEY_SIGNATURE, FastJsonResponse.Field.N(InAppPurchaseMetaData.KEY_SIGNATURE, 3));
        bFr.put("package", FastJsonResponse.Field.N("package", 4));
    }

    public zzu() {
        this.bFs = new HashSet(3);
        this.zzy = 1;
    }

    zzu(Set<Integer> set, int i, zzw zzwVar, String str, String str2) {
        this.bFs = set;
        this.zzy = i;
        this.bFz = zzwVar;
        this.zzbw = str;
        this.bmF = str2;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 2:
                this.bFz = (zzw) t;
                this.bFs.add(Integer.valueOf(iAaO));
                return;
            default:
                throw new IllegalArgumentException(String.format("Field with id=%d is not a known custom type. Found %s", Integer.valueOf(iAaO), t.getClass().getCanonicalName()));
        }
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
                return this.bFz;
            case 3:
                return this.zzbw;
            case 4:
                return this.bmF;
            default:
                throw new IllegalStateException(new StringBuilder(37).append("Unknown SafeParcelable id=").append(field.aaO()).toString());
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected boolean isFieldSet(FastJsonResponse.Field field) {
        return this.bFs.contains(Integer.valueOf(field.aaO()));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 3:
                this.zzbw = str2;
                break;
            case 4:
                this.bmF = str2;
                break;
            default:
                throw new IllegalArgumentException(String.format("Field with id=%d is not known to be a string.", Integer.valueOf(iAaO)));
        }
        this.bFs.add(Integer.valueOf(iAaO));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        Set<Integer> set = this.bFs;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzy);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) this.bFz, i, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.zzbw, true);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bmF, true);
        }
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
