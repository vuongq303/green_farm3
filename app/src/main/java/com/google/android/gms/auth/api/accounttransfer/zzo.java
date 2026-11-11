package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class zzo extends zzbs {
    public static final Parcelable.Creator<zzo> CREATOR = new a();
    private static final HashMap<String, FastJsonResponse.Field<?, ?>> bFr;
    private final Set<Integer> bFs;
    private ArrayList<zzu> bFt;
    private zzr bFu;
    private int zzbn;
    private final int zzy;

    static {
        HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
        bFr = map;
        map.put("authenticatorData", FastJsonResponse.Field.b("authenticatorData", 2, zzu.class));
        bFr.put("progress", FastJsonResponse.Field.a("progress", 4, zzr.class));
    }

    public zzo() {
        this.bFs = new HashSet(1);
        this.zzy = 1;
    }

    zzo(Set<Integer> set, int i, ArrayList<zzu> arrayList, int i2, zzr zzrVar) {
        this.bFs = set;
        this.zzy = i;
        this.bFt = arrayList;
        this.zzbn = i2;
        this.bFu = zzrVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<T> arrayList) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 2:
                this.bFt = arrayList;
                this.bFs.add(Integer.valueOf(iAaO));
                return;
            default:
                throw new IllegalArgumentException(String.format("Field with id=%d is not a known ConcreteTypeArray type. Found %s", Integer.valueOf(iAaO), arrayList.getClass().getCanonicalName()));
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 4:
                this.bFu = (zzr) t;
                this.bFs.add(Integer.valueOf(iAaO));
                return;
            default:
                throw new IllegalArgumentException(String.format("Field with id=%d is not a known custom type. Found %s", Integer.valueOf(iAaO), t.getClass().getCanonicalName()));
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final /* synthetic */ Map getFieldMappings() {
        return bFr;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final Object getFieldValue(FastJsonResponse.Field field) {
        switch (field.aaO()) {
            case 1:
                return Integer.valueOf(this.zzy);
            case 2:
                return this.bFt;
            case 3:
            default:
                throw new IllegalStateException(new StringBuilder(37).append("Unknown SafeParcelable id=").append(field.aaO()).toString());
            case 4:
                return this.bFu;
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final boolean isFieldSet(FastJsonResponse.Field field) {
        return this.bFs.contains(Integer.valueOf(field.aaO()));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        Set<Integer> set = this.bFs;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzy);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, this.bFt, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.zzbn);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) this.bFu, i, true);
        }
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
