package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.measurement.c;
import com.google.android.gms.measurement.f;
import com.google.android.gms.measurement.g;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class zzfe extends zzhk {
    private static final AtomicReference<String[]> zzaij = new AtomicReference<>();
    private static final AtomicReference<String[]> zzaik = new AtomicReference<>();
    private static final AtomicReference<String[]> zzail = new AtomicReference<>();

    zzfe(zzgl zzglVar) {
        super(zzglVar);
    }

    private static String zza(String str, String[] strArr, String[] strArr2, AtomicReference<String[]> atomicReference) {
        bf.checkNotNull(strArr);
        bf.checkNotNull(strArr2);
        bf.checkNotNull(atomicReference);
        bf.ca(strArr.length == strArr2.length);
        for (int i = 0; i < strArr.length; i++) {
            if (zzjv.zzs(str, strArr[i])) {
                synchronized (atomicReference) {
                    String[] strArr3 = atomicReference.get();
                    if (strArr3 == null) {
                        strArr3 = new String[strArr2.length];
                        atomicReference.set(strArr3);
                    }
                    if (strArr3[i] == null) {
                        strArr3[i] = strArr2[i] + "(" + strArr[i] + ")";
                    }
                    str = strArr3[i];
                    break;
                }
            }
        }
        return str;
    }

    private static void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
    }

    private final void zza(StringBuilder sb, int i, zzka zzkaVar) {
        if (zzkaVar == null) {
            return;
        }
        zza(sb, i);
        sb.append("filter {\n");
        zza(sb, i, "complement", zzkaVar.zzars);
        zza(sb, i, "param_name", zzbf(zzkaVar.zzart));
        int i2 = i + 1;
        zzkd zzkdVar = zzkaVar.zzarq;
        if (zzkdVar != null) {
            zza(sb, i2);
            sb.append("string_filter");
            sb.append(" {\n");
            if (zzkdVar.zzasc != null) {
                String str = "UNKNOWN_MATCH_TYPE";
                switch (zzkdVar.zzasc.intValue()) {
                    case 1:
                        str = "REGEXP";
                        break;
                    case 2:
                        str = "BEGINS_WITH";
                        break;
                    case 3:
                        str = "ENDS_WITH";
                        break;
                    case 4:
                        str = "PARTIAL";
                        break;
                    case 5:
                        str = "EXACT";
                        break;
                    case 6:
                        str = "IN_LIST";
                        break;
                }
                zza(sb, i2, "match_type", str);
            }
            zza(sb, i2, "expression", zzkdVar.zzasd);
            zza(sb, i2, "case_sensitive", zzkdVar.zzase);
            if (zzkdVar.zzasf.length > 0) {
                zza(sb, i2 + 1);
                sb.append("expression_list {\n");
                for (String str2 : zzkdVar.zzasf) {
                    zza(sb, i2 + 2);
                    sb.append(str2);
                    sb.append("\n");
                }
                sb.append("}\n");
            }
            zza(sb, i2);
            sb.append("}\n");
        }
        zza(sb, i + 1, "number_filter", zzkaVar.zzarr);
        zza(sb, i);
        sb.append("}\n");
    }

    private final void zza(StringBuilder sb, int i, String str, zzkb zzkbVar) {
        if (zzkbVar == null) {
            return;
        }
        zza(sb, i);
        sb.append(str);
        sb.append(" {\n");
        if (zzkbVar.zzaru != null) {
            String str2 = "UNKNOWN_COMPARISON_TYPE";
            switch (zzkbVar.zzaru.intValue()) {
                case 1:
                    str2 = "LESS_THAN";
                    break;
                case 2:
                    str2 = "GREATER_THAN";
                    break;
                case 3:
                    str2 = "EQUAL";
                    break;
                case 4:
                    str2 = "BETWEEN";
                    break;
            }
            zza(sb, i, "comparison_type", str2);
        }
        zza(sb, i, "match_as_float", zzkbVar.zzarv);
        zza(sb, i, "comparison_value", zzkbVar.zzarw);
        zza(sb, i, "min_comparison_value", zzkbVar.zzarx);
        zza(sb, i, "max_comparison_value", zzkbVar.zzary);
        zza(sb, i);
        sb.append("}\n");
    }

    private static void zza(StringBuilder sb, int i, String str, zzkm zzkmVar) {
        if (zzkmVar == null) {
            return;
        }
        zza(sb, 3);
        sb.append(str);
        sb.append(" {\n");
        if (zzkmVar.zzaug != null) {
            zza(sb, 4);
            sb.append("results: ");
            long[] jArr = zzkmVar.zzaug;
            int length = jArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                Long lValueOf = Long.valueOf(jArr[i2]);
                int i4 = i3 + 1;
                if (i3 != 0) {
                    sb.append(", ");
                }
                sb.append(lValueOf);
                i2++;
                i3 = i4;
            }
            sb.append('\n');
        }
        if (zzkmVar.zzauf != null) {
            zza(sb, 4);
            sb.append("status: ");
            long[] jArr2 = zzkmVar.zzauf;
            int length2 = jArr2.length;
            int i5 = 0;
            int i6 = 0;
            while (i5 < length2) {
                Long lValueOf2 = Long.valueOf(jArr2[i5]);
                int i7 = i6 + 1;
                if (i6 != 0) {
                    sb.append(", ");
                }
                sb.append(lValueOf2);
                i5++;
                i6 = i7;
            }
            sb.append('\n');
        }
        zza(sb, 3);
        sb.append("}\n");
    }

    private static void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj == null) {
            return;
        }
        zza(sb, i + 1);
        sb.append(str);
        sb.append(": ");
        sb.append(obj);
        sb.append('\n');
    }

    private final String zzb(zzer zzerVar) {
        if (zzerVar == null) {
            return null;
        }
        return !zzik() ? zzerVar.toString() : zzb(zzerVar.zzif());
    }

    private final boolean zzik() {
        return this.zzacr.zzgg().isLoggable(3);
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    protected final String zza(zzep zzepVar) {
        if (zzepVar == null) {
            return null;
        }
        if (!zzik()) {
            return zzepVar.toString();
        }
        return "Event{appId='" + zzepVar.zztd + "', name='" + zzbe(zzepVar.name) + "', params=" + zzb(zzepVar.zzafo) + "}";
    }

    protected final String zza(zzjz zzjzVar) {
        if (zzjzVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        zza(sb, 0, "filter_id", zzjzVar.zzark);
        zza(sb, 0, "event_name", zzbe(zzjzVar.zzarl));
        zza(sb, 1, "event_count_filter", zzjzVar.zzaro);
        sb.append("  filters {\n");
        for (zzka zzkaVar : zzjzVar.zzarm) {
            zza(sb, 2, zzkaVar);
        }
        zza(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    protected final String zza(zzkc zzkcVar) {
        if (zzkcVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        zza(sb, 0, "filter_id", zzkcVar.zzark);
        zza(sb, 0, "property_name", zzbg(zzkcVar.zzasa));
        zza(sb, 1, zzkcVar.zzasb);
        sb.append("}\n");
        return sb.toString();
    }

    protected final String zza(zzkk zzkkVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        if (zzkkVar.zzata != null) {
            for (zzkl zzklVar : zzkkVar.zzata) {
                if (zzklVar != null && zzklVar != null) {
                    zza(sb, 1);
                    sb.append("bundle {\n");
                    zza(sb, 1, "protocol_version", zzklVar.zzatc);
                    zza(sb, 1, "platform", zzklVar.zzatk);
                    zza(sb, 1, "gmp_version", zzklVar.zzato);
                    zza(sb, 1, "uploading_gmp_version", zzklVar.zzatp);
                    zza(sb, 1, "config_version", zzklVar.zzaua);
                    zza(sb, 1, "gmp_app_id", zzklVar.zzadh);
                    zza(sb, 1, "app_id", zzklVar.zztd);
                    zza(sb, 1, "app_version", zzklVar.zztc);
                    zza(sb, 1, "app_version_major", zzklVar.zzatw);
                    zza(sb, 1, "firebase_instance_id", zzklVar.zzadj);
                    zza(sb, 1, "dev_cert_hash", zzklVar.zzats);
                    zza(sb, 1, "app_store", zzklVar.zzado);
                    zza(sb, 1, "upload_timestamp_millis", zzklVar.zzatf);
                    zza(sb, 1, "start_timestamp_millis", zzklVar.zzatg);
                    zza(sb, 1, "end_timestamp_millis", zzklVar.zzath);
                    zza(sb, 1, "previous_bundle_start_timestamp_millis", zzklVar.zzati);
                    zza(sb, 1, "previous_bundle_end_timestamp_millis", zzklVar.zzatj);
                    zza(sb, 1, "app_instance_id", zzklVar.zzadg);
                    zza(sb, 1, "resettable_device_id", zzklVar.zzatq);
                    zza(sb, 1, "device_id", zzklVar.zzatz);
                    zza(sb, 1, "limited_ad_tracking", zzklVar.zzatr);
                    zza(sb, 1, "os_version", zzklVar.zzatl);
                    zza(sb, 1, "device_model", zzklVar.zzatm);
                    zza(sb, 1, "user_default_language", zzklVar.zzafl);
                    zza(sb, 1, "time_zone_offset_minutes", zzklVar.zzatn);
                    zza(sb, 1, "bundle_sequential_index", zzklVar.zzatt);
                    zza(sb, 1, "service_upload", zzklVar.zzatu);
                    zza(sb, 1, "health_monitor", zzklVar.zzaef);
                    if (zzklVar.zzaub != null && zzklVar.zzaub.longValue() != 0) {
                        zza(sb, 1, "android_id", zzklVar.zzaub);
                    }
                    if (zzklVar.zzaue != null) {
                        zza(sb, 1, "retry_counter", zzklVar.zzaue);
                    }
                    zzkn[] zzknVarArr = zzklVar.zzate;
                    if (zzknVarArr != null) {
                        for (zzkn zzknVar : zzknVarArr) {
                            if (zzknVar != null) {
                                zza(sb, 2);
                                sb.append("user_property {\n");
                                zza(sb, 2, "set_timestamp_millis", zzknVar.zzaui);
                                zza(sb, 2, "name", zzbg(zzknVar.name));
                                zza(sb, 2, "string_value", zzknVar.zzajf);
                                zza(sb, 2, "int_value", zzknVar.zzasz);
                                zza(sb, 2, "double_value", zzknVar.zzaqx);
                                zza(sb, 2);
                                sb.append("}\n");
                            }
                        }
                    }
                    zzkh[] zzkhVarArr = zzklVar.zzatv;
                    if (zzkhVarArr != null) {
                        for (zzkh zzkhVar : zzkhVarArr) {
                            if (zzkhVar != null) {
                                zza(sb, 2);
                                sb.append("audience_membership {\n");
                                zza(sb, 2, "audience_id", zzkhVar.zzarg);
                                zza(sb, 2, "new_audience", zzkhVar.zzast);
                                zza(sb, 2, "current_data", zzkhVar.zzasr);
                                zza(sb, 2, "previous_data", zzkhVar.zzass);
                                zza(sb, 2);
                                sb.append("}\n");
                            }
                        }
                    }
                    zzki[] zzkiVarArr = zzklVar.zzatd;
                    if (zzkiVarArr != null) {
                        for (zzki zzkiVar : zzkiVarArr) {
                            if (zzkiVar != null) {
                                zza(sb, 2);
                                sb.append("event {\n");
                                zza(sb, 2, "name", zzbe(zzkiVar.name));
                                zza(sb, 2, "timestamp_millis", zzkiVar.zzasw);
                                zza(sb, 2, "previous_timestamp_millis", zzkiVar.zzasx);
                                zza(sb, 2, "count", zzkiVar.count);
                                zzkj[] zzkjVarArr = zzkiVar.zzasv;
                                if (zzkjVarArr != null) {
                                    for (zzkj zzkjVar : zzkjVarArr) {
                                        if (zzkjVar != null) {
                                            zza(sb, 3);
                                            sb.append("param {\n");
                                            zza(sb, 3, "name", zzbf(zzkjVar.name));
                                            zza(sb, 3, "string_value", zzkjVar.zzajf);
                                            zza(sb, 3, "int_value", zzkjVar.zzasz);
                                            zza(sb, 3, "double_value", zzkjVar.zzaqx);
                                            zza(sb, 3);
                                            sb.append("}\n");
                                        }
                                    }
                                }
                                zza(sb, 2);
                                sb.append("}\n");
                            }
                        }
                    }
                    zza(sb, 1);
                    sb.append("}\n");
                }
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    protected final String zzb(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!zzik()) {
            return bundle.toString();
        }
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            if (sb.length() != 0) {
                sb.append(", ");
            } else {
                sb.append("Bundle[{");
            }
            sb.append(zzbf(str));
            sb.append("=");
            sb.append(bundle.get(str));
        }
        sb.append("}]");
        return sb.toString();
    }

    protected final String zzb(zzeu zzeuVar) {
        if (zzeuVar == null) {
            return null;
        }
        if (!zzik()) {
            return zzeuVar.toString();
        }
        return "origin=" + zzeuVar.zzaek + ",name=" + zzbe(zzeuVar.name) + ",params=" + zzb(zzeuVar.zzafo);
    }

    protected final String zzbe(String str) {
        if (str == null) {
            return null;
        }
        return zzik() ? zza(str, c.bXF, c.bXE, zzaij) : str;
    }

    protected final String zzbf(String str) {
        if (str == null) {
            return null;
        }
        return zzik() ? zza(str, f.bXH, f.bXG, zzaik) : str;
    }

    protected final String zzbg(String str) {
        if (str == null) {
            return null;
        }
        if (!zzik()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, g.bXJ, g.bXI, zzail);
        }
        return "experiment_id(" + str + ")";
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfq() {
        super.zzfq();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzdx zzfs() {
        return super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzee zzft() {
        return super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzhm zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfb zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeo zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzil zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzih zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfc zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzei zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfe zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjv zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgf zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjk zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgg zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfg zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfr zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeh zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }
}
