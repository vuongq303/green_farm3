package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.v4.e.a;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.bf;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: loaded from: classes.dex */
final class zzee extends zzhk {
    zzee(zzgl zzglVar) {
        super(zzglVar);
    }

    private final Boolean zza(double d, zzkb zzkbVar) {
        try {
            return zza(new BigDecimal(d), zzkbVar, Math.ulp(d));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(long j, zzkb zzkbVar) {
        try {
            return zza(new BigDecimal(j), zzkbVar, 0.0d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        if (str == null) {
            return null;
        }
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && i != 1) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException e) {
                    zzgg().zzin().zzg("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                return Boolean.valueOf(str.startsWith(str2));
            case 3:
                return Boolean.valueOf(str.endsWith(str2));
            case 4:
                return Boolean.valueOf(str.contains(str2));
            case 5:
                return Boolean.valueOf(str.equals(str2));
            case 6:
                return Boolean.valueOf(list.contains(str));
            default:
                return null;
        }
    }

    private final Boolean zza(String str, zzkb zzkbVar) {
        if (!zzjv.zzcd(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzkbVar, 0.0d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(String str, zzkd zzkdVar) {
        List<String> arrayList;
        bf.checkNotNull(zzkdVar);
        if (str == null || zzkdVar.zzasc == null || zzkdVar.zzasc.intValue() == 0) {
            return null;
        }
        if (zzkdVar.zzasc.intValue() == 6) {
            if (zzkdVar.zzasf == null || zzkdVar.zzasf.length == 0) {
                return null;
            }
        } else if (zzkdVar.zzasd == null) {
            return null;
        }
        int iIntValue = zzkdVar.zzasc.intValue();
        boolean z = zzkdVar.zzase != null && zzkdVar.zzase.booleanValue();
        String upperCase = (z || iIntValue == 1 || iIntValue == 6) ? zzkdVar.zzasd : zzkdVar.zzasd.toUpperCase(Locale.ENGLISH);
        if (zzkdVar.zzasf == null) {
            arrayList = null;
        } else {
            String[] strArr = zzkdVar.zzasf;
            if (z) {
                arrayList = Arrays.asList(strArr);
            } else {
                arrayList = new ArrayList<>();
                for (String str2 : strArr) {
                    arrayList.add(str2.toUpperCase(Locale.ENGLISH));
                }
            }
        }
        return zza(str, iIntValue, z, upperCase, arrayList, iIntValue == 1 ? upperCase : null);
    }

    private static Boolean zza(BigDecimal bigDecimal, zzkb zzkbVar, double d) {
        BigDecimal bigDecimal2;
        BigDecimal bigDecimal3;
        BigDecimal bigDecimal4;
        bf.checkNotNull(zzkbVar);
        if (zzkbVar.zzaru == null || zzkbVar.zzaru.intValue() == 0) {
            return null;
        }
        if (zzkbVar.zzaru.intValue() == 4) {
            if (zzkbVar.zzarx == null || zzkbVar.zzary == null) {
                return null;
            }
        } else if (zzkbVar.zzarw == null) {
            return null;
        }
        int iIntValue = zzkbVar.zzaru.intValue();
        if (zzkbVar.zzaru.intValue() == 4) {
            if (!zzjv.zzcd(zzkbVar.zzarx) || !zzjv.zzcd(zzkbVar.zzary)) {
                return null;
            }
            try {
                bigDecimal4 = new BigDecimal(zzkbVar.zzarx);
                bigDecimal3 = new BigDecimal(zzkbVar.zzary);
                bigDecimal2 = null;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            if (!zzjv.zzcd(zzkbVar.zzarw)) {
                return null;
            }
            try {
                bigDecimal2 = new BigDecimal(zzkbVar.zzarw);
                bigDecimal3 = null;
                bigDecimal4 = null;
            } catch (NumberFormatException e2) {
                return null;
            }
        }
        if (iIntValue != 4) {
            if (bigDecimal2 != null) {
            }
            return null;
        }
        if (bigDecimal4 == null) {
            return null;
        }
        switch (iIntValue) {
            case 1:
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == -1);
            case 2:
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 1);
            case 3:
                if (d != 0.0d) {
                    return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d).multiply(new BigDecimal(2)))) == 1 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d).multiply(new BigDecimal(2)))) == -1);
                }
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 0);
            case 4:
                return Boolean.valueOf((bigDecimal.compareTo(bigDecimal4) == -1 || bigDecimal.compareTo(bigDecimal3) == 1) ? false : true);
            default:
                return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x037f  */
    /* JADX WARN: Code duplicated, block: B:104:0x03c6  */
    /* JADX WARN: Code duplicated, block: B:106:0x03d7  */
    /* JADX WARN: Code duplicated, block: B:129:0x0499  */
    /* JADX WARN: Code duplicated, block: B:132:0x04a8  */
    /* JADX WARN: Code duplicated, block: B:135:0x04d0 A[LOOP:7: B:130:0x04a4->B:135:0x04d0, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:138:0x04e9  */
    /* JADX WARN: Code duplicated, block: B:140:0x04f9  */
    /* JADX WARN: Code duplicated, block: B:142:0x0501  */
    /* JADX WARN: Code duplicated, block: B:144:0x0519  */
    /* JADX WARN: Code duplicated, block: B:146:0x0521  */
    /* JADX WARN: Code duplicated, block: B:147:0x0537  */
    /* JADX WARN: Code duplicated, block: B:149:0x053f  */
    /* JADX WARN: Code duplicated, block: B:154:0x058f  */
    /* JADX WARN: Code duplicated, block: B:157:0x05c2  */
    /* JADX WARN: Code duplicated, block: B:159:0x05ce  */
    /* JADX WARN: Code duplicated, block: B:162:0x05fd  */
    /* JADX WARN: Code duplicated, block: B:165:0x0614  */
    /* JADX WARN: Code duplicated, block: B:167:0x061a  */
    /* JADX WARN: Code duplicated, block: B:171:0x0626  */
    /* JADX WARN: Code duplicated, block: B:172:0x0628  */
    /* JADX WARN: Code duplicated, block: B:174:0x062c  */
    /* JADX WARN: Code duplicated, block: B:177:0x065b  */
    /* JADX WARN: Code duplicated, block: B:180:0x0672  */
    /* JADX WARN: Code duplicated, block: B:182:0x0678  */
    /* JADX WARN: Code duplicated, block: B:186:0x0684  */
    /* JADX WARN: Code duplicated, block: B:187:0x0686  */
    /* JADX WARN: Code duplicated, block: B:189:0x068a  */
    /* JADX WARN: Code duplicated, block: B:191:0x0690  */
    /* JADX WARN: Code duplicated, block: B:194:0x06a1  */
    /* JADX WARN: Code duplicated, block: B:196:0x06a7  */
    /* JADX WARN: Code duplicated, block: B:198:0x06b0  */
    /* JADX WARN: Code duplicated, block: B:201:0x070f  */
    /* JADX WARN: Code duplicated, block: B:203:0x0715  */
    /* JADX WARN: Code duplicated, block: B:207:0x0721  */
    /* JADX WARN: Code duplicated, block: B:209:0x0725 A[EDGE_INSN: B:209:0x0725->B:120:0x046b BREAK  A[LOOP:7: B:130:0x04a4->B:135:0x04d0]] */
    /* JADX WARN: Code duplicated, block: B:210:0x0752 A[EDGE_INSN: B:210:0x0752->B:120:0x046b BREAK  A[LOOP:7: B:130:0x04a4->B:135:0x04d0]] */
    /* JADX WARN: Code duplicated, block: B:312:0x0bd8  */
    /* JADX WARN: Code duplicated, block: B:314:0x0be7  */
    /* JADX WARN: Code duplicated, block: B:316:0x0bf3  */
    /* JADX WARN: Code duplicated, block: B:338:0x0359 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:339:0x02cc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:357:0x04de A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:358:0x04b6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:359:0x0580 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:360:0x0555 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:362:0x0516 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:365:0x069e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:366:0x06e6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:367:0x06bd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:368:0x071a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:369:0x0780 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:370:0x05a7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:371:0x05d4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:372:0x0611 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:373:0x061f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:374:0x0632 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:375:0x066f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:376:0x067d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:377:0x0723 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:378:0x077b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:379:0x077b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:380:0x077b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x0241  */
    /* JADX WARN: Code duplicated, block: B:77:0x028d  */
    /* JADX WARN: Code duplicated, block: B:79:0x029b  */
    /* JADX WARN: Code duplicated, block: B:84:0x02b6  */
    /* JADX WARN: Code duplicated, block: B:97:0x0353  */
    /* JADX WARN: Multi-variable type inference failed */
    final zzkh[] zza(String str, zzki[] zzkiVarArr, zzkn[] zzknVarArr) {
        Map<Integer, List<zzkc>> map;
        Boolean boolZza;
        zzkj[] zzkjVarArr;
        String str2;
        Long l;
        long j;
        zzki zzkiVar;
        zzeq zzeqVarZze;
        zzeq zzeqVarZzie;
        Map<Integer, List<zzjz>> map2;
        Map<Integer, List<zzjz>> map3;
        Iterator<Integer> it;
        int iIntValue;
        zzkh zzkhVar;
        BitSet bitSet;
        BitSet bitSet2;
        BitSet bitSet3;
        BitSet bitSet4;
        HashSet hashSet;
        zzka[] zzkaVarArr;
        int length;
        int i;
        a aVar;
        int length2;
        int i2;
        zzka[] zzkaVarArr2;
        int length3;
        int i3;
        int i4;
        Boolean bool;
        zzka zzkaVar;
        boolean zEquals;
        String str3;
        V v;
        Boolean boolZza2;
        boolean z;
        Boolean boolZza3;
        boolean z2;
        Boolean boolZza4;
        boolean z3;
        zzkj zzkjVar;
        zzka zzkaVar2;
        Map<Integer, List<zzjz>> mapZzj;
        Long l2;
        int i5;
        bf.id(str);
        HashSet hashSet2 = new HashSet();
        a aVar2 = new a();
        a aVar3 = new a();
        a aVar4 = new a();
        Map<Integer, zzkm> mapZzba = zzga().zzba(str);
        if (mapZzba != null) {
            Iterator<Integer> it2 = mapZzba.keySet().iterator();
            while (it2.hasNext()) {
                int iIntValue2 = it2.next().intValue();
                zzkm zzkmVar = mapZzba.get(Integer.valueOf(iIntValue2));
                BitSet bitSet5 = (BitSet) aVar3.get(Integer.valueOf(iIntValue2));
                BitSet bitSet6 = (BitSet) aVar4.get(Integer.valueOf(iIntValue2));
                if (bitSet5 == null) {
                    bitSet5 = new BitSet();
                    aVar3.put(Integer.valueOf(iIntValue2), bitSet5);
                    bitSet6 = new BitSet();
                    aVar4.put(Integer.valueOf(iIntValue2), bitSet6);
                }
                for (int i6 = 0; i6 < (zzkmVar.zzauf.length << 6); i6++) {
                    if (zzjv.zza(zzkmVar.zzauf, i6)) {
                        zzgg().zzir().zze("Filter already evaluated. audience ID, filter ID", Integer.valueOf(iIntValue2), Integer.valueOf(i6));
                        bitSet6.set(i6);
                        if (zzjv.zza(zzkmVar.zzaug, i6)) {
                            bitSet5.set(i6);
                        }
                    }
                }
                zzkh zzkhVar2 = new zzkh();
                aVar2.put(Integer.valueOf(iIntValue2), zzkhVar2);
                zzkhVar2.zzast = false;
                zzkhVar2.zzass = zzkmVar;
                zzkhVar2.zzasr = new zzkm();
                zzkhVar2.zzasr.zzaug = zzjv.zza(bitSet5);
                zzkhVar2.zzasr.zzauf = zzjv.zza(bitSet6);
            }
        }
        if (zzkiVarArr != null) {
            zzki zzkiVar2 = null;
            long jLongValue = 0;
            Long l3 = null;
            a aVar5 = new a();
            int length4 = zzkiVarArr.length;
            int i7 = 0;
            while (true) {
                int i8 = i7;
                if (i8 >= length4) {
                    break;
                }
                zzki zzkiVar3 = zzkiVarArr[i8];
                String str4 = zzkiVar3.name;
                zzkj[] zzkjVarArr2 = zzkiVar3.zzasv;
                if (zzgi().zzd(str, zzew.zzahy)) {
                    zzgc();
                    Long l4 = (Long) zzjv.zzb(zzkiVar3, "_eid");
                    boolean z4 = l4 != null;
                    if (z4 && str4.equals("_ep")) {
                        zzgc();
                        String str5 = (String) zzjv.zzb(zzkiVar3, "_en");
                        if (TextUtils.isEmpty(str5)) {
                            zzgg().zzil().zzg("Extra parameter without an event name. eventId", l4);
                        } else {
                            if (zzkiVar2 == null || l3 == null || l4.longValue() != l3.longValue()) {
                                Pair<zzki, Long> pairZza = zzga().zza(str, l4);
                                if (pairZza == null || pairZza.first == null) {
                                    zzgg().zzil().zze("Extra parameter without existing main event. eventName, eventId", str5, l4);
                                } else {
                                    zzki zzkiVar4 = (zzki) pairZza.first;
                                    jLongValue = ((Long) pairZza.second).longValue();
                                    zzgc();
                                    l2 = (Long) zzjv.zzb(zzkiVar4, "_eid");
                                    zzkiVar2 = zzkiVar4;
                                }
                            } else {
                                l2 = l3;
                            }
                            long j2 = jLongValue - 1;
                            if (j2 <= 0) {
                                zzei zzeiVarZzga = zzga();
                                zzeiVarZzga.zzab();
                                zzeiVarZzga.zzgg().zzir().zzg("Clearing complex main event info. appId", str);
                                try {
                                    zzeiVarZzga.getWritableDatabase().execSQL("delete from main_event_params where app_id=?", new String[]{str});
                                } catch (SQLiteException e) {
                                    zzeiVarZzga.zzgg().zzil().zzg("Error clearing complex main event", e);
                                }
                            } else {
                                zzga().zza(str, l4, j2, zzkiVar2);
                            }
                            zzkj[] zzkjVarArr3 = new zzkj[zzkiVar2.zzasv.length + zzkjVarArr2.length];
                            int i9 = 0;
                            zzkj[] zzkjVarArr4 = zzkiVar2.zzasv;
                            int length5 = zzkjVarArr4.length;
                            int i10 = 0;
                            while (i10 < length5) {
                                zzkj zzkjVar2 = zzkjVarArr4[i10];
                                zzgc();
                                if (zzjv.zza(zzkiVar3, zzkjVar2.name) == null) {
                                    i5 = i9 + 1;
                                    zzkjVarArr3[i9] = zzkjVar2;
                                } else {
                                    i5 = i9;
                                }
                                i10++;
                                i9 = i5;
                            }
                            if (i9 > 0) {
                                int length6 = zzkjVarArr2.length;
                                int i11 = 0;
                                while (i11 < length6) {
                                    zzkjVarArr3[i9] = zzkjVarArr2[i11];
                                    i11++;
                                    i9++;
                                }
                                zzkjVarArr = i9 == zzkjVarArr3.length ? zzkjVarArr3 : (zzkj[]) Arrays.copyOf(zzkjVarArr3, i9);
                                str2 = str5;
                                l = l2;
                                j = j2;
                                zzkiVar = zzkiVar2;
                            } else {
                                zzgg().zzin().zzg("No unique parameters in main event. eventName", str5);
                                zzkjVarArr = zzkjVarArr2;
                                str2 = str5;
                                l = l2;
                                j = j2;
                                zzkiVar = zzkiVar2;
                            }
                        }
                    } else if (z4) {
                        zzgc();
                        Object objZzb = zzjv.zzb(zzkiVar3, "_epc");
                        long jLongValue2 = ((Long) (objZzb != null ? objZzb : 0L)).longValue();
                        if (jLongValue2 <= 0) {
                            zzgg().zzin().zzg("Complex event with zero extra param count. eventName", str4);
                            zzkjVarArr = zzkjVarArr2;
                            str2 = str4;
                            l = l4;
                            j = jLongValue2;
                            zzkiVar = zzkiVar3;
                        } else {
                            zzga().zza(str, l4, jLongValue2, zzkiVar3);
                            zzkjVarArr = zzkjVarArr2;
                            str2 = str4;
                            l = l4;
                            j = jLongValue2;
                            zzkiVar = zzkiVar3;
                        }
                    } else {
                        zzkjVarArr = zzkjVarArr2;
                        str2 = str4;
                        l = l3;
                        j = jLongValue;
                        zzkiVar = zzkiVar2;
                    }
                    zzeqVarZze = zzga().zze(str, zzkiVar3.name);
                    if (zzeqVarZze == null) {
                        zzgg().zzin().zze("Event aggregate wasn't created during raw event logging. appId, event", zzfg.zzbh(str), zzgb().zzbe(str2));
                        zzeqVarZzie = new zzeq(str, zzkiVar3.name, 1L, 1L, zzkiVar3.zzasw.longValue(), 0L, null, null, null);
                    } else {
                        zzeqVarZzie = zzeqVarZze.zzie();
                    }
                    zzga().zza(zzeqVarZzie);
                    long j3 = zzeqVarZzie.zzafp;
                    map2 = (Map) aVar5.get(str2);
                    if (map2 == null) {
                        mapZzj = zzga().zzj(str, str2);
                        if (mapZzj == null) {
                            mapZzj = new a<>();
                        }
                        aVar5.put(str2, mapZzj);
                        map3 = mapZzj;
                    } else {
                        map3 = map2;
                    }
                    it = map3.keySet().iterator();
                    while (it.hasNext()) {
                        iIntValue = it.next().intValue();
                        if (hashSet2.contains(Integer.valueOf(iIntValue))) {
                            zzgg().zzir().zzg("Skipping failed audience ID", Integer.valueOf(iIntValue));
                        } else {
                            zzkhVar = (zzkh) aVar2.get(Integer.valueOf(iIntValue));
                            bitSet = (BitSet) aVar3.get(Integer.valueOf(iIntValue));
                            bitSet2 = (BitSet) aVar4.get(Integer.valueOf(iIntValue));
                            if (zzkhVar == null) {
                                zzkh zzkhVar3 = new zzkh();
                                aVar2.put(Integer.valueOf(iIntValue), zzkhVar3);
                                zzkhVar3.zzast = true;
                                BitSet bitSet7 = new BitSet();
                                aVar3.put(Integer.valueOf(iIntValue), bitSet7);
                                BitSet bitSet8 = new BitSet();
                                aVar4.put(Integer.valueOf(iIntValue), bitSet8);
                                bitSet3 = bitSet8;
                                bitSet4 = bitSet7;
                            } else {
                                bitSet3 = bitSet2;
                                bitSet4 = bitSet;
                            }
                            for (zzjz zzjzVar : map3.get(Integer.valueOf(iIntValue))) {
                                if (zzgg().isLoggable(2)) {
                                    zzgg().zzir().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(iIntValue), zzjzVar.zzark, zzgb().zzbe(zzjzVar.zzarl));
                                    zzgg().zzir().zzg("Filter definition", zzgb().zza(zzjzVar));
                                }
                                if (zzjzVar.zzark != null || zzjzVar.zzark.intValue() > 256) {
                                    zzgg().zzin().zze("Invalid event filter ID. appId, id", zzfg.zzbh(str), String.valueOf(zzjzVar.zzark));
                                } else if (bitSet4.get(zzjzVar.zzark.intValue())) {
                                    zzgg().zzir().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(iIntValue), zzjzVar.zzark);
                                } else {
                                    if (zzjzVar.zzaro != null) {
                                        Boolean boolZza5 = zza(j3, zzjzVar.zzaro);
                                        if (boolZza5 == null) {
                                            bool = null;
                                        } else if (boolZza5.booleanValue()) {
                                            hashSet = new HashSet();
                                            zzkaVarArr = zzjzVar.zzarm;
                                            length = zzkaVarArr.length;
                                            i = 0;
                                            while (true) {
                                                if (i < length) {
                                                    aVar = new a();
                                                    length2 = zzkjVarArr.length;
                                                    i2 = 0;
                                                    while (true) {
                                                        if (i2 < length2) {
                                                            zzkaVarArr2 = zzjzVar.zzarm;
                                                            length3 = zzkaVarArr2.length;
                                                            i3 = 0;
                                                            while (true) {
                                                                i4 = i3;
                                                                if (i4 < length3) {
                                                                    bool = true;
                                                                    break;
                                                                }
                                                                zzkaVar = zzkaVarArr2[i4];
                                                                zEquals = Boolean.TRUE.equals(zzkaVar.zzars);
                                                                str3 = zzkaVar.zzart;
                                                                if (TextUtils.isEmpty(str3)) {
                                                                    zzgg().zzin().zzg("Event has empty param name. event", zzgb().zzbe(str2));
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                v = aVar.get(str3);
                                                                if (v instanceof Long) {
                                                                    if (zzkaVar.zzarr == null) {
                                                                        zzgg().zzin().zze("No number filter for long param. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    boolZza4 = zza(((Long) v).longValue(), zzkaVar.zzarr);
                                                                    if (boolZza4 == null) {
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    if (boolZza4.booleanValue()) {
                                                                        z3 = false;
                                                                    } else {
                                                                        z3 = true;
                                                                    }
                                                                    if (z3 ^ zEquals) {
                                                                        bool = false;
                                                                        break;
                                                                    }
                                                                    i3 = i4 + 1;
                                                                } else if (v instanceof Double) {
                                                                    if (v instanceof String) {
                                                                        if (v == 0) {
                                                                            zzgg().zzin().zze("Unknown param type. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                            bool = null;
                                                                            break;
                                                                        }
                                                                        zzgg().zzir().zze("Missing param for filter. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                        bool = false;
                                                                        break;
                                                                    }
                                                                    if (zzkaVar.zzarq != null) {
                                                                        if (zzkaVar.zzarr != null) {
                                                                            zzgg().zzin().zze("No filter for String param. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                            bool = null;
                                                                            break;
                                                                        }
                                                                        if (zzjv.zzcd((String) v)) {
                                                                            zzgg().zzin().zze("Invalid param value for number filter. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                            bool = null;
                                                                            break;
                                                                        }
                                                                        boolZza2 = zza((String) v, zzkaVar.zzarr);
                                                                    } else {
                                                                        boolZza2 = zza((String) v, zzkaVar.zzarq);
                                                                    }
                                                                    if (boolZza2 == null) {
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    if (boolZza2.booleanValue()) {
                                                                        z = false;
                                                                    } else {
                                                                        z = true;
                                                                    }
                                                                    if (z ^ zEquals) {
                                                                        bool = false;
                                                                        break;
                                                                    }
                                                                    i3 = i4 + 1;
                                                                } else {
                                                                    if (zzkaVar.zzarr == null) {
                                                                        zzgg().zzin().zze("No number filter for double param. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    boolZza3 = zza(((Double) v).doubleValue(), zzkaVar.zzarr);
                                                                    if (boolZza3 == null) {
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    if (boolZza3.booleanValue()) {
                                                                        z2 = false;
                                                                    } else {
                                                                        z2 = true;
                                                                    }
                                                                    if (z2 ^ zEquals) {
                                                                        bool = false;
                                                                        break;
                                                                    }
                                                                    i3 = i4 + 1;
                                                                }
                                                            }
                                                        } else {
                                                            zzkjVar = zzkjVarArr[i2];
                                                            if (!hashSet.contains(zzkjVar.name)) {
                                                                if (zzkjVar.zzasz != null) {
                                                                    aVar.put(zzkjVar.name, zzkjVar.zzasz);
                                                                } else if (zzkjVar.zzaqx != null) {
                                                                    if (zzkjVar.zzajf != null) {
                                                                        zzgg().zzin().zze("Unknown value for param. event, param", zzgb().zzbe(str2), zzgb().zzbf(zzkjVar.name));
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    aVar.put(zzkjVar.name, zzkjVar.zzajf);
                                                                } else {
                                                                    aVar.put(zzkjVar.name, zzkjVar.zzaqx);
                                                                }
                                                            }
                                                            i2++;
                                                        }
                                                    }
                                                } else {
                                                    zzkaVar2 = zzkaVarArr[i];
                                                    if (TextUtils.isEmpty(zzkaVar2.zzart)) {
                                                        zzgg().zzin().zzg("null or empty param name in filter. event", zzgb().zzbe(str2));
                                                        bool = null;
                                                        break;
                                                    }
                                                    hashSet.add(zzkaVar2.zzart);
                                                    i++;
                                                }
                                            }
                                        } else {
                                            bool = false;
                                        }
                                    } else {
                                        hashSet = new HashSet();
                                        zzkaVarArr = zzjzVar.zzarm;
                                        length = zzkaVarArr.length;
                                        i = 0;
                                        while (true) {
                                            if (i < length) {
                                                aVar = new a();
                                                length2 = zzkjVarArr.length;
                                                i2 = 0;
                                                while (true) {
                                                    if (i2 < length2) {
                                                        zzkaVarArr2 = zzjzVar.zzarm;
                                                        length3 = zzkaVarArr2.length;
                                                        i3 = 0;
                                                        while (true) {
                                                            i4 = i3;
                                                            if (i4 < length3) {
                                                                bool = true;
                                                                break;
                                                            }
                                                            zzkaVar = zzkaVarArr2[i4];
                                                            zEquals = Boolean.TRUE.equals(zzkaVar.zzars);
                                                            str3 = zzkaVar.zzart;
                                                            if (TextUtils.isEmpty(str3)) {
                                                                zzgg().zzin().zzg("Event has empty param name. event", zzgb().zzbe(str2));
                                                                bool = null;
                                                                break;
                                                            }
                                                            v = aVar.get(str3);
                                                            if (v instanceof Long) {
                                                                if (zzkaVar.zzarr == null) {
                                                                    zzgg().zzin().zze("No number filter for long param. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                boolZza4 = zza(((Long) v).longValue(), zzkaVar.zzarr);
                                                                if (boolZza4 == null) {
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                if (boolZza4.booleanValue()) {
                                                                    z3 = true;
                                                                } else {
                                                                    z3 = false;
                                                                }
                                                                if (z3 ^ zEquals) {
                                                                    bool = false;
                                                                    break;
                                                                }
                                                                i3 = i4 + 1;
                                                            } else if (v instanceof Double) {
                                                                if (v instanceof String) {
                                                                    if (v == 0) {
                                                                        zzgg().zzin().zze("Unknown param type. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    zzgg().zzir().zze("Missing param for filter. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                    bool = false;
                                                                    break;
                                                                }
                                                                if (zzkaVar.zzarq != null) {
                                                                    if (zzkaVar.zzarr != null) {
                                                                        zzgg().zzin().zze("No filter for String param. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    if (zzjv.zzcd((String) v)) {
                                                                        zzgg().zzin().zze("Invalid param value for number filter. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                        bool = null;
                                                                        break;
                                                                    }
                                                                    boolZza2 = zza((String) v, zzkaVar.zzarr);
                                                                } else {
                                                                    boolZza2 = zza((String) v, zzkaVar.zzarq);
                                                                }
                                                                if (boolZza2 == null) {
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                if (boolZza2.booleanValue()) {
                                                                    z = true;
                                                                } else {
                                                                    z = false;
                                                                }
                                                                if (z ^ zEquals) {
                                                                    bool = false;
                                                                    break;
                                                                }
                                                                i3 = i4 + 1;
                                                            } else {
                                                                if (zzkaVar.zzarr == null) {
                                                                    zzgg().zzin().zze("No number filter for double param. event, param", zzgb().zzbe(str2), zzgb().zzbf(str3));
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                boolZza3 = zza(((Double) v).doubleValue(), zzkaVar.zzarr);
                                                                if (boolZza3 == null) {
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                if (boolZza3.booleanValue()) {
                                                                    z2 = true;
                                                                } else {
                                                                    z2 = false;
                                                                }
                                                                if (z2 ^ zEquals) {
                                                                    bool = false;
                                                                    break;
                                                                }
                                                                i3 = i4 + 1;
                                                            }
                                                        }
                                                    } else {
                                                        zzkjVar = zzkjVarArr[i2];
                                                        if (!hashSet.contains(zzkjVar.name)) {
                                                            if (zzkjVar.zzasz != null) {
                                                                aVar.put(zzkjVar.name, zzkjVar.zzasz);
                                                            } else if (zzkjVar.zzaqx != null) {
                                                                if (zzkjVar.zzajf != null) {
                                                                    zzgg().zzin().zze("Unknown value for param. event, param", zzgb().zzbe(str2), zzgb().zzbf(zzkjVar.name));
                                                                    bool = null;
                                                                    break;
                                                                }
                                                                aVar.put(zzkjVar.name, zzkjVar.zzajf);
                                                            } else {
                                                                aVar.put(zzkjVar.name, zzkjVar.zzaqx);
                                                            }
                                                        }
                                                        i2++;
                                                    }
                                                }
                                            } else {
                                                zzkaVar2 = zzkaVarArr[i];
                                                if (TextUtils.isEmpty(zzkaVar2.zzart)) {
                                                    zzgg().zzin().zzg("null or empty param name in filter. event", zzgb().zzbe(str2));
                                                    bool = null;
                                                    break;
                                                }
                                                hashSet.add(zzkaVar2.zzart);
                                                i++;
                                            }
                                        }
                                    }
                                    zzgg().zzir().zzg("Event filter result", bool == null ? "null" : bool);
                                    if (bool == null) {
                                        hashSet2.add(Integer.valueOf(iIntValue));
                                    } else {
                                        bitSet3.set(zzjzVar.zzark.intValue());
                                        if (bool.booleanValue()) {
                                            bitSet4.set(zzjzVar.zzark.intValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    l3 = l;
                    jLongValue = j;
                    zzkiVar2 = zzkiVar;
                } else {
                    zzkjVarArr = zzkjVarArr2;
                    str2 = str4;
                    l = l3;
                    j = jLongValue;
                    zzkiVar = zzkiVar2;
                    zzeqVarZze = zzga().zze(str, zzkiVar3.name);
                    if (zzeqVarZze == null) {
                        zzgg().zzin().zze("Event aggregate wasn't created during raw event logging. appId, event", zzfg.zzbh(str), zzgb().zzbe(str2));
                        zzeqVarZzie = new zzeq(str, zzkiVar3.name, 1L, 1L, zzkiVar3.zzasw.longValue(), 0L, null, null, null);
                    } else {
                        zzeqVarZzie = zzeqVarZze.zzie();
                    }
                    zzga().zza(zzeqVarZzie);
                    long j4 = zzeqVarZzie.zzafp;
                    map2 = (Map) aVar5.get(str2);
                    if (map2 == null) {
                        mapZzj = zzga().zzj(str, str2);
                        if (mapZzj == null) {
                            mapZzj = new a<>();
                        }
                        aVar5.put(str2, mapZzj);
                        map3 = mapZzj;
                    } else {
                        map3 = map2;
                    }
                    it = map3.keySet().iterator();
                    while (it.hasNext()) {
                        iIntValue = it.next().intValue();
                        if (hashSet2.contains(Integer.valueOf(iIntValue))) {
                            zzgg().zzir().zzg("Skipping failed audience ID", Integer.valueOf(iIntValue));
                        } else {
                            zzkhVar = (zzkh) aVar2.get(Integer.valueOf(iIntValue));
                            bitSet = (BitSet) aVar3.get(Integer.valueOf(iIntValue));
                            bitSet2 = (BitSet) aVar4.get(Integer.valueOf(iIntValue));
                            if (zzkhVar == null) {
                                zzkh zzkhVar4 = new zzkh();
                                aVar2.put(Integer.valueOf(iIntValue), zzkhVar4);
                                zzkhVar4.zzast = true;
                                BitSet bitSet9 = new BitSet();
                                aVar3.put(Integer.valueOf(iIntValue), bitSet9);
                                BitSet bitSet10 = new BitSet();
                                aVar4.put(Integer.valueOf(iIntValue), bitSet10);
                                bitSet3 = bitSet10;
                                bitSet4 = bitSet9;
                            } else {
                                bitSet3 = bitSet2;
                                bitSet4 = bitSet;
                            }
                            while (r15.hasNext()) {
                                if (zzgg().isLoggable(2)) {
                                    zzgg().zzir().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(iIntValue), zzjzVar.zzark, zzgb().zzbe(zzjzVar.zzarl));
                                    zzgg().zzir().zzg("Filter definition", zzgb().zza(zzjzVar));
                                }
                                if (zzjzVar.zzark != null) {
                                }
                                zzgg().zzin().zze("Invalid event filter ID. appId, id", zzfg.zzbh(str), String.valueOf(zzjzVar.zzark));
                            }
                        }
                    }
                    l3 = l;
                    jLongValue = j;
                    zzkiVar2 = zzkiVar;
                }
                i7 = i8 + 1;
            }
        }
        if (zzknVarArr != null) {
            a aVar6 = new a();
            for (zzkn zzknVar : zzknVarArr) {
                Map<Integer, List<zzkc>> map4 = (Map) aVar6.get(zzknVar.name);
                if (map4 == null) {
                    Map<Integer, List<zzkc>> mapZzk = zzga().zzk(str, zzknVar.name);
                    if (mapZzk == null) {
                        mapZzk = new a<>();
                    }
                    aVar6.put(zzknVar.name, mapZzk);
                    map = mapZzk;
                } else {
                    map = map4;
                }
                Iterator<Integer> it3 = map.keySet().iterator();
                while (it3.hasNext()) {
                    int iIntValue3 = it3.next().intValue();
                    if (hashSet2.contains(Integer.valueOf(iIntValue3))) {
                        zzgg().zzir().zzg("Skipping failed audience ID", Integer.valueOf(iIntValue3));
                    } else {
                        zzkh zzkhVar5 = (zzkh) aVar2.get(Integer.valueOf(iIntValue3));
                        BitSet bitSet11 = (BitSet) aVar3.get(Integer.valueOf(iIntValue3));
                        BitSet bitSet12 = (BitSet) aVar4.get(Integer.valueOf(iIntValue3));
                        if (zzkhVar5 == null) {
                            zzkh zzkhVar6 = new zzkh();
                            aVar2.put(Integer.valueOf(iIntValue3), zzkhVar6);
                            zzkhVar6.zzast = true;
                            bitSet11 = new BitSet();
                            aVar3.put(Integer.valueOf(iIntValue3), bitSet11);
                            bitSet12 = new BitSet();
                            aVar4.put(Integer.valueOf(iIntValue3), bitSet12);
                        }
                        for (zzkc zzkcVar : map.get(Integer.valueOf(iIntValue3))) {
                            if (zzgg().isLoggable(2)) {
                                zzgg().zzir().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(iIntValue3), zzkcVar.zzark, zzgb().zzbg(zzkcVar.zzasa));
                                zzgg().zzir().zzg("Filter definition", zzgb().zza(zzkcVar));
                            }
                            if (zzkcVar.zzark == null || zzkcVar.zzark.intValue() > 256) {
                                zzgg().zzin().zze("Invalid property filter ID. appId, id", zzfg.zzbh(str), String.valueOf(zzkcVar.zzark));
                                hashSet2.add(Integer.valueOf(iIntValue3));
                                break;
                            }
                            if (bitSet11.get(zzkcVar.zzark.intValue())) {
                                zzgg().zzir().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(iIntValue3), zzkcVar.zzark);
                            } else {
                                zzka zzkaVar3 = zzkcVar.zzasb;
                                if (zzkaVar3 == null) {
                                    zzgg().zzin().zzg("Missing property filter. property", zzgb().zzbg(zzknVar.name));
                                    boolZza = null;
                                } else {
                                    boolean zEquals2 = Boolean.TRUE.equals(zzkaVar3.zzars);
                                    if (zzknVar.zzasz != null) {
                                        if (zzkaVar3.zzarr == null) {
                                            zzgg().zzin().zzg("No number filter for long property. property", zzgb().zzbg(zzknVar.name));
                                            boolZza = null;
                                        } else {
                                            boolZza = zza(zza(zzknVar.zzasz.longValue(), zzkaVar3.zzarr), zEquals2);
                                        }
                                    } else if (zzknVar.zzaqx != null) {
                                        if (zzkaVar3.zzarr == null) {
                                            zzgg().zzin().zzg("No number filter for double property. property", zzgb().zzbg(zzknVar.name));
                                            boolZza = null;
                                        } else {
                                            boolZza = zza(zza(zzknVar.zzaqx.doubleValue(), zzkaVar3.zzarr), zEquals2);
                                        }
                                    } else if (zzknVar.zzajf == null) {
                                        zzgg().zzin().zzg("User property has no value, property", zzgb().zzbg(zzknVar.name));
                                        boolZza = null;
                                    } else if (zzkaVar3.zzarq == null) {
                                        if (zzkaVar3.zzarr == null) {
                                            zzgg().zzin().zzg("No string or number filter defined. property", zzgb().zzbg(zzknVar.name));
                                        } else if (zzjv.zzcd(zzknVar.zzajf)) {
                                            boolZza = zza(zza(zzknVar.zzajf, zzkaVar3.zzarr), zEquals2);
                                        } else {
                                            zzgg().zzin().zze("Invalid user property value for Numeric number filter. property, value", zzgb().zzbg(zzknVar.name), zzknVar.zzajf);
                                        }
                                        boolZza = null;
                                    } else {
                                        boolZza = zza(zza(zzknVar.zzajf, zzkaVar3.zzarq), zEquals2);
                                    }
                                }
                                zzgg().zzir().zzg("Property filter result", boolZza == null ? "null" : boolZza);
                                if (boolZza == null) {
                                    hashSet2.add(Integer.valueOf(iIntValue3));
                                } else {
                                    bitSet12.set(zzkcVar.zzark.intValue());
                                    if (boolZza.booleanValue()) {
                                        bitSet11.set(zzkcVar.zzark.intValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        zzkh[] zzkhVarArr = new zzkh[aVar3.size()];
        Iterator it4 = aVar3.keySet().iterator();
        int i12 = 0;
        while (it4.hasNext()) {
            int iIntValue4 = ((Integer) it4.next()).intValue();
            if (!hashSet2.contains(Integer.valueOf(iIntValue4))) {
                zzkh zzkhVar7 = (zzkh) aVar2.get(Integer.valueOf(iIntValue4));
                zzkh zzkhVar8 = zzkhVar7 == null ? new zzkh() : zzkhVar7;
                int i13 = i12 + 1;
                zzkhVarArr[i12] = zzkhVar8;
                zzkhVar8.zzarg = Integer.valueOf(iIntValue4);
                zzkhVar8.zzasr = new zzkm();
                zzkhVar8.zzasr.zzaug = zzjv.zza((BitSet) aVar3.get(Integer.valueOf(iIntValue4)));
                zzkhVar8.zzasr.zzauf = zzjv.zza((BitSet) aVar4.get(Integer.valueOf(iIntValue4)));
                zzei zzeiVarZzga2 = zzga();
                zzkm zzkmVar2 = zzkhVar8.zzasr;
                zzeiVarZzga2.zzch();
                zzeiVarZzga2.zzab();
                bf.id(str);
                bf.checkNotNull(zzkmVar2);
                try {
                    byte[] bArr = new byte[zzkmVar2.zzwg()];
                    zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
                    zzkmVar2.zza(zzabbVarZzb);
                    zzabbVarZzb.zzvy();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("audience_id", Integer.valueOf(iIntValue4));
                    contentValues.put("current_results", bArr);
                    try {
                        if (zzeiVarZzga2.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, contentValues, 5) == -1) {
                            zzeiVarZzga2.zzgg().zzil().zzg("Failed to insert filter results (got -1). appId", zzfg.zzbh(str));
                        }
                        i12 = i13;
                    } catch (SQLiteException e2) {
                        zzeiVarZzga2.zzgg().zzil().zze("Error storing filter results. appId", zzfg.zzbh(str), e2);
                        i12 = i13;
                    }
                } catch (IOException e3) {
                    zzeiVarZzga2.zzgg().zzil().zze("Configuration loss. Failed to serialize filter results. appId", zzfg.zzbh(str), e3);
                    i12 = i13;
                }
            }
        }
        return (zzkh[]) Arrays.copyOf(zzkhVarArr, i12);
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }
}
