package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class StringToIntConverter extends AbstractSafeParcelable implements com.google.android.gms.common.server.response.a<String, Integer> {
    public static final Parcelable.Creator<StringToIntConverter> CREATOR = new b();
    private final HashMap<String, Integer> bOS;
    private final SparseArray<String> bOT;
    private final ArrayList<Entry> bOU;
    private final int zzal;

    public final class Entry extends AbstractSafeParcelable {
        public static final Parcelable.Creator<Entry> CREATOR = new c();
        final String bOV;
        final int bOW;
        private final int versionCode;

        Entry(int i, String str, int i2) {
            this.versionCode = i;
            this.bOV = str;
            this.bOW = i2;
        }

        Entry(String str, int i) {
            this.versionCode = 1;
            this.bOV = str;
            this.bOW = i;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.versionCode);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bOV, false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.bOW);
            com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
        }
    }

    public StringToIntConverter() {
        this.zzal = 1;
        this.bOS = new HashMap<>();
        this.bOT = new SparseArray<>();
        this.bOU = null;
    }

    StringToIntConverter(int i, ArrayList<Entry> arrayList) {
        this.zzal = i;
        this.bOS = new HashMap<>();
        this.bOT = new SparseArray<>();
        this.bOU = null;
        ArrayList<Entry> arrayList2 = arrayList;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Entry entry = arrayList2.get(i2);
            i2++;
            Entry entry2 = entry;
            K(entry2.bOV, entry2.bOW);
        }
    }

    public final StringToIntConverter K(String str, int i) {
        this.bOS.put(str, Integer.valueOf(i));
        this.bOT.put(i, str);
        return this;
    }

    @Override // com.google.android.gms.common.server.response.a
    public final int aaJ() {
        return 7;
    }

    @Override // com.google.android.gms.common.server.response.a
    public final int aaK() {
        return 0;
    }

    @Override // com.google.android.gms.common.server.response.a
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final String convertBack(Integer num) {
        String str = this.bOT.get(num.intValue());
        return (str == null && this.bOS.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    @Override // com.google.android.gms.common.server.response.a
    /* JADX INFO: renamed from: ie, reason: merged with bridge method [inline-methods] */
    public final Integer convert(String str) {
        Integer num = this.bOS.get(str);
        return num == null ? this.bOS.get("gms_unknown") : num;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        ArrayList arrayList = new ArrayList();
        for (String str : this.bOS.keySet()) {
            arrayList.add(new Entry(str, this.bOS.get(str).intValue()));
        }
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, arrayList, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
