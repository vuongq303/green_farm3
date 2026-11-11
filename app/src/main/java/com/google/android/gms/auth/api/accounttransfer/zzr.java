package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzr extends zzbs {
    public static final Parcelable.Creator<zzr> CREATOR = new c();
    private static final android.support.v4.e.a<String, FastJsonResponse.Field<?, ?>> bFv;
    private List<String> bFw;
    private List<String> bFx;
    private List<String> bFy;
    private List<String> zzbr;
    private List<String> zzbs;
    private final int zzy;

    static {
        android.support.v4.e.a<String, FastJsonResponse.Field<?, ?>> aVar = new android.support.v4.e.a<>();
        bFv = aVar;
        aVar.put("registered", FastJsonResponse.Field.O("registered", 2));
        bFv.put("in_progress", FastJsonResponse.Field.O("in_progress", 3));
        bFv.put("success", FastJsonResponse.Field.O("success", 4));
        bFv.put("failed", FastJsonResponse.Field.O("failed", 5));
        bFv.put("escrowed", FastJsonResponse.Field.O("escrowed", 6));
    }

    public zzr() {
        this.zzy = 1;
    }

    zzr(int i, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5) {
        this.zzy = i;
        this.bFw = list;
        this.zzbr = list2;
        this.zzbs = list3;
        this.bFx = list4;
        this.bFy = list5;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
        return bFv;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected Object getFieldValue(FastJsonResponse.Field field) {
        switch (field.aaO()) {
            case 1:
                return Integer.valueOf(this.zzy);
            case 2:
                return this.bFw;
            case 3:
                return this.zzbr;
            case 4:
                return this.zzbs;
            case 5:
                return this.bFx;
            case 6:
                return this.bFy;
            default:
                throw new IllegalStateException(new StringBuilder(37).append("Unknown SafeParcelable id=").append(field.aaO()).toString());
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected boolean isFieldSet(FastJsonResponse.Field field) {
        return true;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setStringsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<String> arrayList) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 2:
                this.bFw = arrayList;
                return;
            case 3:
                this.zzbr = arrayList;
                return;
            case 4:
                this.zzbs = arrayList;
                return;
            case 5:
                this.bFx = arrayList;
                return;
            case 6:
                this.bFy = arrayList;
                return;
            default:
                throw new IllegalArgumentException(String.format("Field with id=%d is not known to be a string list.", Integer.valueOf(iAaO)));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzy);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 2, this.bFw, false);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 3, this.zzbr, false);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 4, this.zzbs, false);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 5, this.bFx, false);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 6, this.bFy, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
