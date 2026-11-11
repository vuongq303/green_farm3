package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FieldMappingDictionary extends AbstractSafeParcelable {
    public static final Parcelable.Creator<FieldMappingDictionary> CREATOR = new g();
    private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> bPB;
    private final ArrayList<Entry> bPC = null;
    private final String bPD;
    private final int zzal;

    public class Entry extends AbstractSafeParcelable {
        public static final Parcelable.Creator<Entry> CREATOR = new h();
        final ArrayList<FieldMapPair> bPE;
        final String className;
        private final int versionCode;

        Entry(int i, String str, ArrayList<FieldMapPair> arrayList) {
            this.versionCode = i;
            this.className = str;
            this.bPE = arrayList;
        }

        Entry(String str, Map<String, FastJsonResponse.Field<?, ?>> map) {
            ArrayList<FieldMapPair> arrayList;
            this.versionCode = 1;
            this.className = str;
            if (map == null) {
                arrayList = null;
            } else {
                ArrayList<FieldMapPair> arrayList2 = new ArrayList<>();
                for (String str2 : map.keySet()) {
                    arrayList2.add(new FieldMapPair(str2, map.get(str2)));
                }
                arrayList = arrayList2;
            }
            this.bPE = arrayList;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.versionCode);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.className, false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.bPE, false);
            com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
        }
    }

    public class FieldMapPair extends AbstractSafeParcelable {
        public static final Parcelable.Creator<FieldMapPair> CREATOR = new f();
        final String bPF;
        final FastJsonResponse.Field<?, ?> bPG;
        private final int versionCode;

        FieldMapPair(int i, String str, FastJsonResponse.Field<?, ?> field) {
            this.versionCode = i;
            this.bPF = str;
            this.bPG = field;
        }

        FieldMapPair(String str, FastJsonResponse.Field<?, ?> field) {
            this.versionCode = 1;
            this.bPF = str;
            this.bPG = field;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.versionCode);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bPF, false);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) this.bPG, i, false);
            com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
        }
    }

    FieldMappingDictionary(int i, ArrayList<Entry> arrayList, String str) {
        this.zzal = i;
        HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> map = new HashMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Entry entry = arrayList.get(i2);
            String str2 = entry.className;
            HashMap map2 = new HashMap();
            int size2 = entry.bPE.size();
            for (int i3 = 0; i3 < size2; i3++) {
                FieldMapPair fieldMapPair = entry.bPE.get(i3);
                map2.put(fieldMapPair.bPF, fieldMapPair.bPG);
            }
            map.put(str2, map2);
        }
        this.bPB = map;
        this.bPD = (String) bf.checkNotNull(str);
        aaV();
    }

    public void aaV() {
        Iterator<String> it = this.bPB.keySet().iterator();
        while (it.hasNext()) {
            Map<String, FastJsonResponse.Field<?, ?>> map = this.bPB.get(it.next());
            Iterator<String> it2 = map.keySet().iterator();
            while (it2.hasNext()) {
                map.get(it2.next()).a(this);
            }
        }
    }

    public String aaW() {
        return this.bPD;
    }

    /* JADX INFO: renamed from: if, reason: not valid java name */
    public Map<String, FastJsonResponse.Field<?, ?>> m6if(String str) {
        return this.bPB.get(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.bPB.keySet()) {
            sb.append(str).append(":\n");
            Map<String, FastJsonResponse.Field<?, ?>> map = this.bPB.get(str);
            for (String str2 : map.keySet()) {
                sb.append("  ").append(str2).append(": ");
                sb.append(map.get(str2));
            }
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        ArrayList arrayList = new ArrayList();
        for (String str : this.bPB.keySet()) {
            arrayList.add(new Entry(str, this.bPB.get(str)));
        }
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, arrayList, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, aaW(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
