package com.google.android.gms.internal.ads;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
interface zzbdl {
    int getTag();

    double readDouble();

    float readFloat();

    String readString();

    void readStringList(List<String> list);

    <T> T zza(zzbdm<T> zzbdmVar, zzbbb zzbbbVar);

    <T> void zza(List<T> list, zzbdm<T> zzbdmVar, zzbbb zzbbbVar);

    <K, V> void zza(Map<K, V> map, zzbcn<K, V> zzbcnVar, zzbbb zzbbbVar);

    void zzaa(List<Integer> list);

    void zzab(List<Integer> list);

    long zzabl();

    long zzabm();

    int zzabn();

    long zzabo();

    int zzabp();

    boolean zzabq();

    String zzabr();

    zzbah zzabs();

    int zzabt();

    int zzabu();

    int zzabv();

    long zzabw();

    int zzabx();

    long zzaby();

    void zzac(List<Long> list);

    int zzaci();

    boolean zzacj();

    void zzad(List<Integer> list);

    void zzae(List<Long> list);

    @Deprecated
    <T> T zzb(zzbdm<T> zzbdmVar, zzbbb zzbbbVar);

    @Deprecated
    <T> void zzb(List<T> list, zzbdm<T> zzbdmVar, zzbbb zzbbbVar);

    void zzp(List<Double> list);

    void zzq(List<Float> list);

    void zzr(List<Long> list);

    void zzs(List<Long> list);

    void zzt(List<Integer> list);

    void zzu(List<Long> list);

    void zzv(List<Integer> list);

    void zzw(List<Boolean> list);

    void zzx(List<String> list);

    void zzy(List<zzbah> list);

    void zzz(List<Integer> list);
}
