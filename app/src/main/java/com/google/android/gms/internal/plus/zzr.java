package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.safeparcel.c;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import com.google.android.gms.plus.a.a.a;
import com.google.android.gms.plus.a.a.b;
import com.google.android.gms.plus.a.a.d;
import com.google.android.gms.plus.a.a.e;
import com.google.android.gms.plus.a.a.f;
import com.google.android.gms.plus.a.a.g;
import com.google.android.gms.plus.a.a.h;
import com.google.android.gms.plus.a.a.i;
import com.google.android.gms.plus.a.a.j;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class zzr extends FastSafeParcelableJsonResponse implements a {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
    private final Set<Integer> zzap;
    private String zzaq;
    private zza zzar;
    private String zzas;
    private String zzat;
    private int zzau;
    private zzb zzav;
    private String zzaw;
    private String zzax;
    private int zzay;
    private String zzaz;
    private zzc zzba;
    private boolean zzbb;
    private String zzbc;
    private zzd zzbd;
    private String zzbe;
    private int zzbf;
    private List<zze> zzbg;
    private List<zzf> zzbh;
    private int zzbi;
    private int zzbj;
    private String zzbk;
    private List<zzg> zzbl;
    private boolean zzbm;
    private String zzk;
    private final int zzw;

    public final class zza extends FastSafeParcelableJsonResponse implements b {
        public static final Parcelable.Creator<zza> CREATOR = new zzt();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private final Set<Integer> zzap;
        private int zzbn;
        private int zzbo;
        private final int zzw;

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("max", FastJsonResponse.Field.L("max", 2));
            zzao.put("min", FastJsonResponse.Field.L("min", 3));
        }

        public zza() {
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        zza(Set<Integer> set, int i, int i2, int i3) {
            this.zzap = set;
            this.zzw = i;
            this.zzbn = i2;
            this.zzbo = i3;
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zza zzaVar = (zza) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzaVar.isFieldSet(field) && getFieldValue(field).equals(zzaVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzaVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ b freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 2:
                    return Integer.valueOf(this.zzbn);
                case 3:
                    return Integer.valueOf(this.zzbo);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final int getMax() {
            return this.zzbn;
        }

        public final int getMin() {
            return this.zzbo;
        }

        public final boolean hasMax() {
            return this.zzap.contains(2);
        }

        public final boolean hasMin() {
            return this.zzap.contains(3);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 2:
                    this.zzbn = i;
                    break;
                case 3:
                    this.zzbo = i;
                    break;
                default:
                    throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
            }
            this.zzap.add(Integer.valueOf(iAaO));
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(2)) {
                c.c(parcel, 2, this.zzbn);
            }
            if (set.contains(3)) {
                c.c(parcel, 3, this.zzbo);
            }
            c.G(parcel, iX);
        }
    }

    public final class zzb extends FastSafeParcelableJsonResponse implements com.google.android.gms.plus.a.a.c {
        public static final Parcelable.Creator<zzb> CREATOR = new zzu();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private final Set<Integer> zzap;
        private zza zzbp;
        private C0004zzb zzbq;
        private int zzbr;
        private final int zzw;

        public final class zza extends FastSafeParcelableJsonResponse implements d {
            public static final Parcelable.Creator<zza> CREATOR = new zzv();
            private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
            private final Set<Integer> zzap;
            private int zzbs;
            private int zzbt;
            private final int zzw;

            static {
                HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
                zzao = map;
                map.put("leftImageOffset", FastJsonResponse.Field.L("leftImageOffset", 2));
                zzao.put("topImageOffset", FastJsonResponse.Field.L("topImageOffset", 3));
            }

            public zza() {
                this.zzw = 1;
                this.zzap = new HashSet();
            }

            zza(Set<Integer> set, int i, int i2, int i3) {
                this.zzap = set;
                this.zzw = i;
                this.zzbs = i2;
                this.zzbt = i3;
            }

            @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
            public final boolean equals(Object obj) {
                if (!(obj instanceof zza)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                zza zzaVar = (zza) obj;
                for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                    if (isFieldSet(field)) {
                        if (zzaVar.isFieldSet(field) && getFieldValue(field).equals(zzaVar.getFieldValue(field))) {
                        }
                        return false;
                    }
                    if (zzaVar.isFieldSet(field)) {
                        return false;
                    }
                }
                return true;
            }

            @Override // com.google.android.gms.common.data.k
            public final /* bridge */ /* synthetic */ d freeze() {
                if (this == null) {
                    throw null;
                }
                return this;
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            public final /* synthetic */ Map getFieldMappings() {
                return zzao;
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final Object getFieldValue(FastJsonResponse.Field field) {
                switch (field.aaO()) {
                    case 2:
                        return Integer.valueOf(this.zzbs);
                    case 3:
                        return Integer.valueOf(this.zzbt);
                    default:
                        throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
                }
            }

            public final int getLeftImageOffset() {
                return this.zzbs;
            }

            public final int getTopImageOffset() {
                return this.zzbt;
            }

            public final boolean hasLeftImageOffset() {
                return this.zzap.contains(2);
            }

            public final boolean hasTopImageOffset() {
                return this.zzap.contains(3);
            }

            @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
            public final int hashCode() {
                int iHashCode = 0;
                Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
                while (true) {
                    int i = iHashCode;
                    if (!it.hasNext()) {
                        return i;
                    }
                    FastJsonResponse.Field<?, ?> next = it.next();
                    if (isFieldSet(next)) {
                        iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                    } else {
                        iHashCode = i;
                    }
                }
            }

            public final boolean isDataValid() {
                return true;
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final boolean isFieldSet(FastJsonResponse.Field field) {
                return this.zzap.contains(Integer.valueOf(field.aaO()));
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
                int iAaO = field.aaO();
                switch (iAaO) {
                    case 2:
                        this.zzbs = i;
                        break;
                    case 3:
                        this.zzbt = i;
                        break;
                    default:
                        throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
                }
                this.zzap.add(Integer.valueOf(iAaO));
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                int iX = c.x(parcel);
                Set<Integer> set = this.zzap;
                if (set.contains(1)) {
                    c.c(parcel, 1, this.zzw);
                }
                if (set.contains(2)) {
                    c.c(parcel, 2, this.zzbs);
                }
                if (set.contains(3)) {
                    c.c(parcel, 3, this.zzbt);
                }
                c.G(parcel, iX);
            }
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.plus.zzr$zzb$zzb, reason: collision with other inner class name */
        public final class C0004zzb extends FastSafeParcelableJsonResponse implements e {
            public static final Parcelable.Creator<C0004zzb> CREATOR = new zzw();
            private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
            private final Set<Integer> zzap;
            private int zzbu;
            private int zzbv;
            private String zzk;
            private final int zzw;

            static {
                HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
                zzao = map;
                map.put("height", FastJsonResponse.Field.L("height", 2));
                zzao.put("url", FastJsonResponse.Field.N("url", 3));
                zzao.put("width", FastJsonResponse.Field.L("width", 4));
            }

            public C0004zzb() {
                this.zzw = 1;
                this.zzap = new HashSet();
            }

            C0004zzb(Set<Integer> set, int i, int i2, String str, int i3) {
                this.zzap = set;
                this.zzw = i;
                this.zzbu = i2;
                this.zzk = str;
                this.zzbv = i3;
            }

            @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
            public final boolean equals(Object obj) {
                if (!(obj instanceof C0004zzb)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C0004zzb c0004zzb = (C0004zzb) obj;
                for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                    if (isFieldSet(field)) {
                        if (c0004zzb.isFieldSet(field) && getFieldValue(field).equals(c0004zzb.getFieldValue(field))) {
                        }
                        return false;
                    }
                    if (c0004zzb.isFieldSet(field)) {
                        return false;
                    }
                }
                return true;
            }

            @Override // com.google.android.gms.common.data.k
            public final /* bridge */ /* synthetic */ e freeze() {
                if (this == null) {
                    throw null;
                }
                return this;
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            public final /* synthetic */ Map getFieldMappings() {
                return zzao;
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final Object getFieldValue(FastJsonResponse.Field field) {
                switch (field.aaO()) {
                    case 2:
                        return Integer.valueOf(this.zzbu);
                    case 3:
                        return this.zzk;
                    case 4:
                        return Integer.valueOf(this.zzbv);
                    default:
                        throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
                }
            }

            public final int getHeight() {
                return this.zzbu;
            }

            public final String getUrl() {
                return this.zzk;
            }

            public final int getWidth() {
                return this.zzbv;
            }

            public final boolean hasHeight() {
                return this.zzap.contains(2);
            }

            public final boolean hasUrl() {
                return this.zzap.contains(3);
            }

            public final boolean hasWidth() {
                return this.zzap.contains(4);
            }

            @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
            public final int hashCode() {
                int iHashCode = 0;
                Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
                while (true) {
                    int i = iHashCode;
                    if (!it.hasNext()) {
                        return i;
                    }
                    FastJsonResponse.Field<?, ?> next = it.next();
                    if (isFieldSet(next)) {
                        iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                    } else {
                        iHashCode = i;
                    }
                }
            }

            public final boolean isDataValid() {
                return true;
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final boolean isFieldSet(FastJsonResponse.Field field) {
                return this.zzap.contains(Integer.valueOf(field.aaO()));
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
                int iAaO = field.aaO();
                switch (iAaO) {
                    case 2:
                        this.zzbu = i;
                        break;
                    case 3:
                    default:
                        throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
                    case 4:
                        this.zzbv = i;
                        break;
                }
                this.zzap.add(Integer.valueOf(iAaO));
            }

            @Override // com.google.android.gms.common.server.response.FastJsonResponse
            protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
                int iAaO = field.aaO();
                switch (iAaO) {
                    case 3:
                        this.zzk = str2;
                        this.zzap.add(Integer.valueOf(iAaO));
                        return;
                    default:
                        throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
                }
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                int iX = c.x(parcel);
                Set<Integer> set = this.zzap;
                if (set.contains(1)) {
                    c.c(parcel, 1, this.zzw);
                }
                if (set.contains(2)) {
                    c.c(parcel, 2, this.zzbu);
                }
                if (set.contains(3)) {
                    c.a(parcel, 3, this.zzk, true);
                }
                if (set.contains(4)) {
                    c.c(parcel, 4, this.zzbv);
                }
                c.G(parcel, iX);
            }
        }

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("coverInfo", FastJsonResponse.Field.a("coverInfo", 2, zza.class));
            zzao.put("coverPhoto", FastJsonResponse.Field.a("coverPhoto", 3, C0004zzb.class));
            zzao.put("layout", FastJsonResponse.Field.a("layout", 4, new StringToIntConverter().K("banner", 0), false));
        }

        public zzb() {
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        zzb(Set<Integer> set, int i, zza zzaVar, C0004zzb c0004zzb, int i2) {
            this.zzap = set;
            this.zzw = i;
            this.zzbp = zzaVar;
            this.zzbq = c0004zzb;
            this.zzbr = i2;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 2:
                    this.zzbp = (zza) t;
                    break;
                case 3:
                    this.zzbq = (C0004zzb) t;
                    break;
                default:
                    String canonicalName = t.getClass().getCanonicalName();
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(canonicalName).length() + 62).append("Field with id=").append(iAaO).append(" is not a known custom type.  Found ").append(canonicalName).append(".").toString());
            }
            this.zzap.add(Integer.valueOf(iAaO));
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzb zzbVar = (zzb) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzbVar.isFieldSet(field) && getFieldValue(field).equals(zzbVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzbVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ com.google.android.gms.plus.a.a.c freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        public final d getCoverInfo() {
            return this.zzbp;
        }

        public final e getCoverPhoto() {
            return this.zzbq;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 2:
                    return this.zzbp;
                case 3:
                    return this.zzbq;
                case 4:
                    return Integer.valueOf(this.zzbr);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final int getLayout() {
            return this.zzbr;
        }

        public final boolean hasCoverInfo() {
            return this.zzap.contains(2);
        }

        public final boolean hasCoverPhoto() {
            return this.zzap.contains(3);
        }

        public final boolean hasLayout() {
            return this.zzap.contains(4);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 4:
                    this.zzbr = i;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
            }
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(2)) {
                c.a(parcel, 2, (Parcelable) this.zzbp, i, true);
            }
            if (set.contains(3)) {
                c.a(parcel, 3, (Parcelable) this.zzbq, i, true);
            }
            if (set.contains(4)) {
                c.c(parcel, 4, this.zzbr);
            }
            c.G(parcel, iX);
        }
    }

    public final class zzc extends FastSafeParcelableJsonResponse implements f {
        public static final Parcelable.Creator<zzc> CREATOR = new zzx();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private final Set<Integer> zzap;
        private String zzk;
        private final int zzw;

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("url", FastJsonResponse.Field.N("url", 2));
        }

        public zzc() {
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        public zzc(String str) {
            this.zzap = new HashSet();
            this.zzw = 1;
            this.zzk = str;
            this.zzap.add(2);
        }

        zzc(Set<Integer> set, int i, String str) {
            this.zzap = set;
            this.zzw = i;
            this.zzk = str;
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zzc)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzc zzcVar = (zzc) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzcVar.isFieldSet(field) && getFieldValue(field).equals(zzcVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzcVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ f freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 2:
                    return this.zzk;
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final String getUrl() {
            return this.zzk;
        }

        public final boolean hasUrl() {
            return this.zzap.contains(2);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 2:
                    this.zzk = str2;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
            }
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(2)) {
                c.a(parcel, 2, this.zzk, true);
            }
            c.G(parcel, iX);
        }
    }

    public final class zzd extends FastSafeParcelableJsonResponse implements g {
        public static final Parcelable.Creator<zzd> CREATOR = new zzy();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private final Set<Integer> zzap;
        private String zzbw;
        private String zzbx;
        private String zzby;
        private String zzbz;
        private String zzca;
        private String zzcb;
        private final int zzw;

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("familyName", FastJsonResponse.Field.N("familyName", 2));
            zzao.put("formatted", FastJsonResponse.Field.N("formatted", 3));
            zzao.put("givenName", FastJsonResponse.Field.N("givenName", 4));
            zzao.put("honorificPrefix", FastJsonResponse.Field.N("honorificPrefix", 5));
            zzao.put("honorificSuffix", FastJsonResponse.Field.N("honorificSuffix", 6));
            zzao.put("middleName", FastJsonResponse.Field.N("middleName", 7));
        }

        public zzd() {
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        zzd(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
            this.zzap = set;
            this.zzw = i;
            this.zzbw = str;
            this.zzbx = str2;
            this.zzby = str3;
            this.zzbz = str4;
            this.zzca = str5;
            this.zzcb = str6;
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zzd)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzd zzdVar = (zzd) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzdVar.isFieldSet(field) && getFieldValue(field).equals(zzdVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzdVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ g freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        public final String getFamilyName() {
            return this.zzbw;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 2:
                    return this.zzbw;
                case 3:
                    return this.zzbx;
                case 4:
                    return this.zzby;
                case 5:
                    return this.zzbz;
                case 6:
                    return this.zzca;
                case 7:
                    return this.zzcb;
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final String getFormatted() {
            return this.zzbx;
        }

        public final String getGivenName() {
            return this.zzby;
        }

        public final String getHonorificPrefix() {
            return this.zzbz;
        }

        public final String getHonorificSuffix() {
            return this.zzca;
        }

        public final String getMiddleName() {
            return this.zzcb;
        }

        public final boolean hasFamilyName() {
            return this.zzap.contains(2);
        }

        public final boolean hasFormatted() {
            return this.zzap.contains(3);
        }

        public final boolean hasGivenName() {
            return this.zzap.contains(4);
        }

        public final boolean hasHonorificPrefix() {
            return this.zzap.contains(5);
        }

        public final boolean hasHonorificSuffix() {
            return this.zzap.contains(6);
        }

        public final boolean hasMiddleName() {
            return this.zzap.contains(7);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 2:
                    this.zzbw = str2;
                    break;
                case 3:
                    this.zzbx = str2;
                    break;
                case 4:
                    this.zzby = str2;
                    break;
                case 5:
                    this.zzbz = str2;
                    break;
                case 6:
                    this.zzca = str2;
                    break;
                case 7:
                    this.zzcb = str2;
                    break;
                default:
                    throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
            }
            this.zzap.add(Integer.valueOf(iAaO));
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(2)) {
                c.a(parcel, 2, this.zzbw, true);
            }
            if (set.contains(3)) {
                c.a(parcel, 3, this.zzbx, true);
            }
            if (set.contains(4)) {
                c.a(parcel, 4, this.zzby, true);
            }
            if (set.contains(5)) {
                c.a(parcel, 5, this.zzbz, true);
            }
            if (set.contains(6)) {
                c.a(parcel, 6, this.zzca, true);
            }
            if (set.contains(7)) {
                c.a(parcel, 7, this.zzcb, true);
            }
            c.G(parcel, iX);
        }
    }

    public final class zze extends FastSafeParcelableJsonResponse implements h {
        public static final Parcelable.Creator<zze> CREATOR = new zzz();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private String mName;
        private final Set<Integer> zzap;
        private String zzcc;
        private String zzcd;
        private String zzce;
        private String zzcf;
        private boolean zzcg;
        private String zzch;
        private String zzci;
        private int zzcj;
        private final int zzw;

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("department", FastJsonResponse.Field.N("department", 2));
            zzao.put("description", FastJsonResponse.Field.N("description", 3));
            zzao.put("endDate", FastJsonResponse.Field.N("endDate", 4));
            zzao.put(FirebaseAnalytics.Param.LOCATION, FastJsonResponse.Field.N(FirebaseAnalytics.Param.LOCATION, 5));
            zzao.put("name", FastJsonResponse.Field.N("name", 6));
            zzao.put("primary", FastJsonResponse.Field.M("primary", 7));
            zzao.put("startDate", FastJsonResponse.Field.N("startDate", 8));
            zzao.put("title", FastJsonResponse.Field.N("title", 9));
            zzao.put("type", FastJsonResponse.Field.a("type", 10, new StringToIntConverter().K("work", 0).K("school", 1), false));
        }

        public zze() {
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        zze(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
            this.zzap = set;
            this.zzw = i;
            this.zzcc = str;
            this.zzcd = str2;
            this.zzce = str3;
            this.zzcf = str4;
            this.mName = str5;
            this.zzcg = z;
            this.zzch = str6;
            this.zzci = str7;
            this.zzcj = i2;
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zze)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zze zzeVar = (zze) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzeVar.isFieldSet(field) && getFieldValue(field).equals(zzeVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzeVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ Object freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        public final String getDepartment() {
            return this.zzcc;
        }

        public final String getDescription() {
            return this.zzcd;
        }

        public final String getEndDate() {
            return this.zzce;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 2:
                    return this.zzcc;
                case 3:
                    return this.zzcd;
                case 4:
                    return this.zzce;
                case 5:
                    return this.zzcf;
                case 6:
                    return this.mName;
                case 7:
                    return Boolean.valueOf(this.zzcg);
                case 8:
                    return this.zzch;
                case 9:
                    return this.zzci;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    return Integer.valueOf(this.zzcj);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final String getLocation() {
            return this.zzcf;
        }

        public final String getName() {
            return this.mName;
        }

        public final String getStartDate() {
            return this.zzch;
        }

        public final String getTitle() {
            return this.zzci;
        }

        public final int getType() {
            return this.zzcj;
        }

        public final boolean hasDepartment() {
            return this.zzap.contains(2);
        }

        public final boolean hasDescription() {
            return this.zzap.contains(3);
        }

        public final boolean hasEndDate() {
            return this.zzap.contains(4);
        }

        public final boolean hasLocation() {
            return this.zzap.contains(5);
        }

        public final boolean hasName() {
            return this.zzap.contains(6);
        }

        public final boolean hasPrimary() {
            return this.zzap.contains(7);
        }

        public final boolean hasStartDate() {
            return this.zzap.contains(8);
        }

        public final boolean hasTitle() {
            return this.zzap.contains(9);
        }

        public final boolean hasType() {
            return this.zzap.contains(10);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        public final boolean isPrimary() {
            return this.zzcg;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 7:
                    this.zzcg = z;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(55).append("Field with id=").append(iAaO).append(" is not known to be a boolean.").toString());
            }
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzcj = i;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
            }
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 2:
                    this.zzcc = str2;
                    break;
                case 3:
                    this.zzcd = str2;
                    break;
                case 4:
                    this.zzce = str2;
                    break;
                case 5:
                    this.zzcf = str2;
                    break;
                case 6:
                    this.mName = str2;
                    break;
                case 7:
                default:
                    throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
                case 8:
                    this.zzch = str2;
                    break;
                case 9:
                    this.zzci = str2;
                    break;
            }
            this.zzap.add(Integer.valueOf(iAaO));
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(2)) {
                c.a(parcel, 2, this.zzcc, true);
            }
            if (set.contains(3)) {
                c.a(parcel, 3, this.zzcd, true);
            }
            if (set.contains(4)) {
                c.a(parcel, 4, this.zzce, true);
            }
            if (set.contains(5)) {
                c.a(parcel, 5, this.zzcf, true);
            }
            if (set.contains(6)) {
                c.a(parcel, 6, this.mName, true);
            }
            if (set.contains(7)) {
                c.a(parcel, 7, this.zzcg);
            }
            if (set.contains(8)) {
                c.a(parcel, 8, this.zzch, true);
            }
            if (set.contains(9)) {
                c.a(parcel, 9, this.zzci, true);
            }
            if (set.contains(10)) {
                c.c(parcel, 10, this.zzcj);
            }
            c.G(parcel, iX);
        }
    }

    public final class zzf extends FastSafeParcelableJsonResponse implements i {
        public static final Parcelable.Creator<zzf> CREATOR = new zzaa();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private String mValue;
        private final Set<Integer> zzap;
        private boolean zzcg;
        private final int zzw;

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("primary", FastJsonResponse.Field.M("primary", 2));
            zzao.put(FirebaseAnalytics.Param.VALUE, FastJsonResponse.Field.N(FirebaseAnalytics.Param.VALUE, 3));
        }

        public zzf() {
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        zzf(Set<Integer> set, int i, boolean z, String str) {
            this.zzap = set;
            this.zzw = i;
            this.zzcg = z;
            this.mValue = str;
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zzf)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzf zzfVar = (zzf) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzfVar.isFieldSet(field) && getFieldValue(field).equals(zzfVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzfVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ Object freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 2:
                    return Boolean.valueOf(this.zzcg);
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final String getValue() {
            return this.mValue;
        }

        public final boolean hasPrimary() {
            return this.zzap.contains(2);
        }

        public final boolean hasValue() {
            return this.zzap.contains(3);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        public final boolean isPrimary() {
            return this.zzcg;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 2:
                    this.zzcg = z;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(55).append("Field with id=").append(iAaO).append(" is not known to be a boolean.").toString());
            }
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 3:
                    this.mValue = str2;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
            }
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(2)) {
                c.a(parcel, 2, this.zzcg);
            }
            if (set.contains(3)) {
                c.a(parcel, 3, this.mValue, true);
            }
            c.G(parcel, iX);
        }
    }

    public final class zzg extends FastSafeParcelableJsonResponse implements j {
        public static final Parcelable.Creator<zzg> CREATOR = new zzab();
        private static final HashMap<String, FastJsonResponse.Field<?, ?>> zzao;
        private String mValue;
        private final Set<Integer> zzap;
        private int zzcj;
        private String zzck;
        private final int zzcl;
        private final int zzw;

        static {
            HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
            zzao = map;
            map.put("label", FastJsonResponse.Field.N("label", 5));
            zzao.put("type", FastJsonResponse.Field.a("type", 6, new StringToIntConverter().K("home", 0).K("work", 1).K("blog", 2).K("profile", 3).K(FacebookRequestErrorClassification.KEY_OTHER, 4).K("otherProfile", 5).K("contributor", 6).K("website", 7), false));
            zzao.put(FirebaseAnalytics.Param.VALUE, FastJsonResponse.Field.N(FirebaseAnalytics.Param.VALUE, 4));
        }

        public zzg() {
            this.zzcl = 4;
            this.zzw = 1;
            this.zzap = new HashSet();
        }

        zzg(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
            this.zzcl = 4;
            this.zzap = set;
            this.zzw = i;
            this.zzck = str;
            this.zzcj = i2;
            this.mValue = str2;
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final boolean equals(Object obj) {
            if (!(obj instanceof zzg)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzg zzgVar = (zzg) obj;
            for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
                if (isFieldSet(field)) {
                    if (zzgVar.isFieldSet(field) && getFieldValue(field).equals(zzgVar.getFieldValue(field))) {
                    }
                    return false;
                }
                if (zzgVar.isFieldSet(field)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.common.data.k
        public final /* bridge */ /* synthetic */ Object freeze() {
            if (this == null) {
                throw null;
            }
            return this;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        public final /* synthetic */ Map getFieldMappings() {
            return zzao;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final Object getFieldValue(FastJsonResponse.Field field) {
            switch (field.aaO()) {
                case 4:
                    return this.mValue;
                case 5:
                    return this.zzck;
                case 6:
                    return Integer.valueOf(this.zzcj);
                default:
                    throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            }
        }

        public final String getLabel() {
            return this.zzck;
        }

        public final int getType() {
            return this.zzcj;
        }

        public final String getValue() {
            return this.mValue;
        }

        public final boolean hasLabel() {
            return this.zzap.contains(5);
        }

        public final boolean hasType() {
            return this.zzap.contains(6);
        }

        public final boolean hasValue() {
            return this.zzap.contains(4);
        }

        @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
        public final int hashCode() {
            int iHashCode = 0;
            Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                FastJsonResponse.Field<?, ?> next = it.next();
                if (isFieldSet(next)) {
                    iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
                } else {
                    iHashCode = i;
                }
            }
        }

        public final boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final boolean isFieldSet(FastJsonResponse.Field field) {
            return this.zzap.contains(Integer.valueOf(field.aaO()));
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 6:
                    this.zzcj = i;
                    this.zzap.add(Integer.valueOf(iAaO));
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
            }
        }

        @Override // com.google.android.gms.common.server.response.FastJsonResponse
        protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
            int iAaO = field.aaO();
            switch (iAaO) {
                case 4:
                    this.mValue = str2;
                    break;
                case 5:
                    this.zzck = str2;
                    break;
                default:
                    throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
            }
            this.zzap.add(Integer.valueOf(iAaO));
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iX = c.x(parcel);
            Set<Integer> set = this.zzap;
            if (set.contains(1)) {
                c.c(parcel, 1, this.zzw);
            }
            if (set.contains(3)) {
                c.c(parcel, 3, 4);
            }
            if (set.contains(4)) {
                c.a(parcel, 4, this.mValue, true);
            }
            if (set.contains(5)) {
                c.a(parcel, 5, this.zzck, true);
            }
            if (set.contains(6)) {
                c.c(parcel, 6, this.zzcj);
            }
            c.G(parcel, iX);
        }
    }

    static {
        HashMap<String, FastJsonResponse.Field<?, ?>> map = new HashMap<>();
        zzao = map;
        map.put("aboutMe", FastJsonResponse.Field.N("aboutMe", 2));
        zzao.put("ageRange", FastJsonResponse.Field.a("ageRange", 3, zza.class));
        zzao.put("birthday", FastJsonResponse.Field.N("birthday", 4));
        zzao.put("braggingRights", FastJsonResponse.Field.N("braggingRights", 5));
        zzao.put("circledByCount", FastJsonResponse.Field.L("circledByCount", 6));
        zzao.put("cover", FastJsonResponse.Field.a("cover", 7, zzb.class));
        zzao.put("currentLocation", FastJsonResponse.Field.N("currentLocation", 8));
        zzao.put("displayName", FastJsonResponse.Field.N("displayName", 9));
        zzao.put("gender", FastJsonResponse.Field.a("gender", 12, new StringToIntConverter().K("male", 0).K("female", 1).K(FacebookRequestErrorClassification.KEY_OTHER, 2), false));
        zzao.put(ShareConstants.WEB_DIALOG_PARAM_ID, FastJsonResponse.Field.N(ShareConstants.WEB_DIALOG_PARAM_ID, 14));
        zzao.put(MessengerShareContentUtility.MEDIA_IMAGE, FastJsonResponse.Field.a(MessengerShareContentUtility.MEDIA_IMAGE, 15, zzc.class));
        zzao.put("isPlusUser", FastJsonResponse.Field.M("isPlusUser", 16));
        zzao.put("language", FastJsonResponse.Field.N("language", 18));
        zzao.put("name", FastJsonResponse.Field.a("name", 19, zzd.class));
        zzao.put("nickname", FastJsonResponse.Field.N("nickname", 20));
        zzao.put("objectType", FastJsonResponse.Field.a("objectType", 21, new StringToIntConverter().K("person", 0).K("page", 1), false));
        zzao.put("organizations", FastJsonResponse.Field.b("organizations", 22, zze.class));
        zzao.put("placesLived", FastJsonResponse.Field.b("placesLived", 23, zzf.class));
        zzao.put("plusOneCount", FastJsonResponse.Field.L("plusOneCount", 24));
        zzao.put("relationshipStatus", FastJsonResponse.Field.a("relationshipStatus", 25, new StringToIntConverter().K("single", 0).K("in_a_relationship", 1).K("engaged", 2).K("married", 3).K("its_complicated", 4).K("open_relationship", 5).K("widowed", 6).K("in_domestic_partnership", 7).K("in_civil_union", 8), false));
        zzao.put("tagline", FastJsonResponse.Field.N("tagline", 26));
        zzao.put("url", FastJsonResponse.Field.N("url", 27));
        zzao.put("urls", FastJsonResponse.Field.b("urls", 28, zzg.class));
        zzao.put("verified", FastJsonResponse.Field.M("verified", 29));
    }

    public zzr() {
        this.zzw = 1;
        this.zzap = new HashSet();
    }

    public zzr(String str, String str2, zzc zzcVar, int i, String str3) {
        this.zzw = 1;
        this.zzap = new HashSet();
        this.zzax = str;
        this.zzap.add(9);
        this.zzaz = str2;
        this.zzap.add(14);
        this.zzba = zzcVar;
        this.zzap.add(15);
        this.zzbf = i;
        this.zzap.add(21);
        this.zzk = str3;
        this.zzap.add(27);
    }

    zzr(Set<Integer> set, int i, String str, zza zzaVar, String str2, String str3, int i2, zzb zzbVar, String str4, String str5, int i3, String str6, zzc zzcVar, boolean z, String str7, zzd zzdVar, String str8, int i4, List<zze> list, List<zzf> list2, int i5, int i6, String str9, String str10, List<zzg> list3, boolean z2) {
        this.zzap = set;
        this.zzw = i;
        this.zzaq = str;
        this.zzar = zzaVar;
        this.zzas = str2;
        this.zzat = str3;
        this.zzau = i2;
        this.zzav = zzbVar;
        this.zzaw = str4;
        this.zzax = str5;
        this.zzay = i3;
        this.zzaz = str6;
        this.zzba = zzcVar;
        this.zzbb = z;
        this.zzbc = str7;
        this.zzbd = zzdVar;
        this.zzbe = str8;
        this.zzbf = i4;
        this.zzbg = list;
        this.zzbh = list2;
        this.zzbi = i5;
        this.zzbj = i6;
        this.zzbk = str9;
        this.zzk = str10;
        this.zzbl = list3;
        this.zzbm = z2;
    }

    public static zzr zza(byte[] bArr) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        zzr zzrVarCreateFromParcel = CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return zzrVarCreateFromParcel;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<T> arrayList) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 22:
                this.zzbg = arrayList;
                break;
            case 23:
                this.zzbh = arrayList;
                break;
            case 28:
                this.zzbl = arrayList;
                break;
            default:
                String canonicalName = arrayList.getClass().getCanonicalName();
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(canonicalName).length() + 71).append("Field with id=").append(iAaO).append(" is not a known array of custom type.  Found ").append(canonicalName).append(".").toString());
        }
        this.zzap.add(Integer.valueOf(iAaO));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 3:
                this.zzar = (zza) t;
                break;
            case 7:
                this.zzav = (zzb) t;
                break;
            case 15:
                this.zzba = (zzc) t;
                break;
            case 19:
                this.zzbd = (zzd) t;
                break;
            default:
                String canonicalName = t.getClass().getCanonicalName();
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(canonicalName).length() + 62).append("Field with id=").append(iAaO).append(" is not a known custom type.  Found ").append(canonicalName).append(".").toString());
        }
        this.zzap.add(Integer.valueOf(iAaO));
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
    public final boolean equals(Object obj) {
        if (!(obj instanceof zzr)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzr zzrVar = (zzr) obj;
        for (FastJsonResponse.Field<?, ?> field : zzao.values()) {
            if (isFieldSet(field)) {
                if (zzrVar.isFieldSet(field) && getFieldValue(field).equals(zzrVar.getFieldValue(field))) {
                }
                return false;
            }
            if (zzrVar.isFieldSet(field)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.common.data.k
    public final /* bridge */ /* synthetic */ a freeze() {
        if (this == null) {
            throw null;
        }
        return this;
    }

    public final String getAboutMe() {
        return this.zzaq;
    }

    public final b getAgeRange() {
        return this.zzar;
    }

    public final String getBirthday() {
        return this.zzas;
    }

    public final String getBraggingRights() {
        return this.zzat;
    }

    public final int getCircledByCount() {
        return this.zzau;
    }

    public final com.google.android.gms.plus.a.a.c getCover() {
        return this.zzav;
    }

    public final String getCurrentLocation() {
        return this.zzaw;
    }

    public final String getDisplayName() {
        return this.zzax;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final /* synthetic */ Map getFieldMappings() {
        return zzao;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final Object getFieldValue(FastJsonResponse.Field field) {
        switch (field.aaO()) {
            case 2:
                return this.zzaq;
            case 3:
                return this.zzar;
            case 4:
                return this.zzas;
            case 5:
                return this.zzat;
            case 6:
                return Integer.valueOf(this.zzau);
            case 7:
                return this.zzav;
            case 8:
                return this.zzaw;
            case 9:
                return this.zzax;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
            case R.styleable.CardView_contentPaddingTop /* 11 */:
            case 13:
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
            default:
                throw new IllegalStateException(new StringBuilder(38).append("Unknown safe parcelable id=").append(field.aaO()).toString());
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                return Integer.valueOf(this.zzay);
            case 14:
                return this.zzaz;
            case 15:
                return this.zzba;
            case 16:
                return Boolean.valueOf(this.zzbb);
            case 18:
                return this.zzbc;
            case 19:
                return this.zzbd;
            case 20:
                return this.zzbe;
            case 21:
                return Integer.valueOf(this.zzbf);
            case 22:
                return this.zzbg;
            case 23:
                return this.zzbh;
            case 24:
                return Integer.valueOf(this.zzbi);
            case Constants.MAX_TREE_DEPTH /* 25 */:
                return Integer.valueOf(this.zzbj);
            case 26:
                return this.zzbk;
            case 27:
                return this.zzk;
            case 28:
                return this.zzbl;
            case 29:
                return Boolean.valueOf(this.zzbm);
        }
    }

    public final int getGender() {
        return this.zzay;
    }

    public final String getId() {
        return this.zzaz;
    }

    public final f getImage() {
        return this.zzba;
    }

    public final String getLanguage() {
        return this.zzbc;
    }

    public final g getName() {
        return this.zzbd;
    }

    public final String getNickname() {
        return this.zzbe;
    }

    public final int getObjectType() {
        return this.zzbf;
    }

    public final List<h> getOrganizations() {
        return (ArrayList) this.zzbg;
    }

    public final List<i> getPlacesLived() {
        return (ArrayList) this.zzbh;
    }

    public final int getPlusOneCount() {
        return this.zzbi;
    }

    public final int getRelationshipStatus() {
        return this.zzbj;
    }

    public final String getTagline() {
        return this.zzbk;
    }

    public final String getUrl() {
        return this.zzk;
    }

    public final List<j> getUrls() {
        return (ArrayList) this.zzbl;
    }

    public final boolean hasAboutMe() {
        return this.zzap.contains(2);
    }

    public final boolean hasAgeRange() {
        return this.zzap.contains(3);
    }

    public final boolean hasBirthday() {
        return this.zzap.contains(4);
    }

    public final boolean hasBraggingRights() {
        return this.zzap.contains(5);
    }

    public final boolean hasCircledByCount() {
        return this.zzap.contains(6);
    }

    public final boolean hasCover() {
        return this.zzap.contains(7);
    }

    public final boolean hasCurrentLocation() {
        return this.zzap.contains(8);
    }

    public final boolean hasDisplayName() {
        return this.zzap.contains(9);
    }

    public final boolean hasGender() {
        return this.zzap.contains(12);
    }

    public final boolean hasId() {
        return this.zzap.contains(14);
    }

    public final boolean hasImage() {
        return this.zzap.contains(15);
    }

    public final boolean hasIsPlusUser() {
        return this.zzap.contains(16);
    }

    public final boolean hasLanguage() {
        return this.zzap.contains(18);
    }

    public final boolean hasName() {
        return this.zzap.contains(19);
    }

    public final boolean hasNickname() {
        return this.zzap.contains(20);
    }

    public final boolean hasObjectType() {
        return this.zzap.contains(21);
    }

    public final boolean hasOrganizations() {
        return this.zzap.contains(22);
    }

    public final boolean hasPlacesLived() {
        return this.zzap.contains(23);
    }

    public final boolean hasPlusOneCount() {
        return this.zzap.contains(24);
    }

    public final boolean hasRelationshipStatus() {
        return this.zzap.contains(25);
    }

    public final boolean hasTagline() {
        return this.zzap.contains(26);
    }

    public final boolean hasUrl() {
        return this.zzap.contains(27);
    }

    public final boolean hasUrls() {
        return this.zzap.contains(28);
    }

    public final boolean hasVerified() {
        return this.zzap.contains(29);
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
    public final int hashCode() {
        int iHashCode = 0;
        Iterator<FastJsonResponse.Field<?, ?>> it = zzao.values().iterator();
        while (true) {
            int i = iHashCode;
            if (!it.hasNext()) {
                return i;
            }
            FastJsonResponse.Field<?, ?> next = it.next();
            if (isFieldSet(next)) {
                iHashCode = getFieldValue(next).hashCode() + i + next.aaO();
            } else {
                iHashCode = i;
            }
        }
    }

    public final boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final boolean isFieldSet(FastJsonResponse.Field field) {
        return this.zzap.contains(Integer.valueOf(field.aaO()));
    }

    public final boolean isPlusUser() {
        return this.zzbb;
    }

    public final boolean isVerified() {
        return this.zzbm;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 16:
                this.zzbb = z;
                break;
            case 29:
                this.zzbm = z;
                break;
            default:
                throw new IllegalArgumentException(new StringBuilder(55).append("Field with id=").append(iAaO).append(" is not known to be a boolean.").toString());
        }
        this.zzap.add(Integer.valueOf(iAaO));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 6:
                this.zzau = i;
                break;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                this.zzay = i;
                break;
            case 21:
                this.zzbf = i;
                break;
            case 24:
                this.zzbi = i;
                break;
            case Constants.MAX_TREE_DEPTH /* 25 */:
                this.zzbj = i;
                break;
            default:
                throw new IllegalArgumentException(new StringBuilder(52).append("Field with id=").append(iAaO).append(" is not known to be an int.").toString());
        }
        this.zzap.add(Integer.valueOf(iAaO));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        int iAaO = field.aaO();
        switch (iAaO) {
            case 2:
                this.zzaq = str2;
                break;
            case 4:
                this.zzas = str2;
                break;
            case 5:
                this.zzat = str2;
                break;
            case 8:
                this.zzaw = str2;
                break;
            case 9:
                this.zzax = str2;
                break;
            case 14:
                this.zzaz = str2;
                break;
            case 18:
                this.zzbc = str2;
                break;
            case 20:
                this.zzbe = str2;
                break;
            case 26:
                this.zzbk = str2;
                break;
            case 27:
                this.zzk = str2;
                break;
            default:
                throw new IllegalArgumentException(new StringBuilder(54).append("Field with id=").append(iAaO).append(" is not known to be a String.").toString());
        }
        this.zzap.add(Integer.valueOf(iAaO));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        Set<Integer> set = this.zzap;
        if (set.contains(1)) {
            c.c(parcel, 1, this.zzw);
        }
        if (set.contains(2)) {
            c.a(parcel, 2, this.zzaq, true);
        }
        if (set.contains(3)) {
            c.a(parcel, 3, (Parcelable) this.zzar, i, true);
        }
        if (set.contains(4)) {
            c.a(parcel, 4, this.zzas, true);
        }
        if (set.contains(5)) {
            c.a(parcel, 5, this.zzat, true);
        }
        if (set.contains(6)) {
            c.c(parcel, 6, this.zzau);
        }
        if (set.contains(7)) {
            c.a(parcel, 7, (Parcelable) this.zzav, i, true);
        }
        if (set.contains(8)) {
            c.a(parcel, 8, this.zzaw, true);
        }
        if (set.contains(9)) {
            c.a(parcel, 9, this.zzax, true);
        }
        if (set.contains(12)) {
            c.c(parcel, 12, this.zzay);
        }
        if (set.contains(14)) {
            c.a(parcel, 14, this.zzaz, true);
        }
        if (set.contains(15)) {
            c.a(parcel, 15, (Parcelable) this.zzba, i, true);
        }
        if (set.contains(16)) {
            c.a(parcel, 16, this.zzbb);
        }
        if (set.contains(18)) {
            c.a(parcel, 18, this.zzbc, true);
        }
        if (set.contains(19)) {
            c.a(parcel, 19, (Parcelable) this.zzbd, i, true);
        }
        if (set.contains(20)) {
            c.a(parcel, 20, this.zzbe, true);
        }
        if (set.contains(21)) {
            c.c(parcel, 21, this.zzbf);
        }
        if (set.contains(22)) {
            c.c(parcel, 22, this.zzbg, true);
        }
        if (set.contains(23)) {
            c.c(parcel, 23, this.zzbh, true);
        }
        if (set.contains(24)) {
            c.c(parcel, 24, this.zzbi);
        }
        if (set.contains(25)) {
            c.c(parcel, 25, this.zzbj);
        }
        if (set.contains(26)) {
            c.a(parcel, 26, this.zzbk, true);
        }
        if (set.contains(27)) {
            c.a(parcel, 27, this.zzk, true);
        }
        if (set.contains(28)) {
            c.c(parcel, 28, this.zzbl, true);
        }
        if (set.contains(29)) {
            c.a(parcel, 29, this.zzbm);
        }
        c.G(parcel, iX);
    }
}
