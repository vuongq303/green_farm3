package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public final class zzauo {
    private static final Logger logger = Logger.getLogger(zzauo.class.getName());
    private static final ConcurrentMap<String, zzaug> zzdhq = new ConcurrentHashMap();
    private static final ConcurrentMap<String, Boolean> zzdhr = new ConcurrentHashMap();
    private static final ConcurrentMap<String, zzaua> zzdhs = new ConcurrentHashMap();

    /* JADX WARN: Multi-variable type inference failed */
    public static <P> zzaum<P> zza(zzauh zzauhVar, zzaug<P> zzaugVar) throws GeneralSecurityException {
        zzaup.zzc(zzauhVar.zzwg());
        zzaum<P> zzaumVar = (zzaum<P>) new zzaum();
        for (zzaxr.zzb zzbVar : zzauhVar.zzwg().zzzl()) {
            if (zzbVar.zzzq() == zzaxl.ENABLED) {
                zzaun zzaunVarZza = zzaumVar.zza(zza(zzbVar.zzzp().zzyw(), zzbVar.zzzp().zzyx()), zzbVar);
                if (zzbVar.zzzr() == zzauhVar.zzwg().zzzk()) {
                    zzaumVar.zza(zzaunVarZza);
                }
            }
        }
        return zzaumVar;
    }

    public static <P> zzaxi zza(zzaxn zzaxnVar) throws GeneralSecurityException {
        zzaug zzaugVarZzdz = zzdz(zzaxnVar.zzyw());
        if (zzdhr.get(zzaxnVar.zzyw()).booleanValue()) {
            return zzaugVarZzdz.zzc(zzaxnVar.zzyx());
        }
        String strValueOf = String.valueOf(zzaxnVar.zzyw());
        throw new GeneralSecurityException(strValueOf.length() != 0 ? "newKey-operation not permitted for key type ".concat(strValueOf) : new String("newKey-operation not permitted for key type "));
    }

    public static <P> zzbcu zza(String str, zzbcu zzbcuVar) throws GeneralSecurityException {
        zzaug zzaugVarZzdz = zzdz(str);
        if (zzdhr.get(str).booleanValue()) {
            return zzaugVarZzdz.zzb(zzbcuVar);
        }
        String strValueOf = String.valueOf(str);
        throw new GeneralSecurityException(strValueOf.length() != 0 ? "newKey-operation not permitted for key type ".concat(strValueOf) : new String("newKey-operation not permitted for key type "));
    }

    private static <P> P zza(String str, zzbah zzbahVar) {
        return (P) zzdz(str).zza(zzbahVar);
    }

    public static <P> P zza(String str, byte[] bArr) {
        return (P) zza(str, zzbah.zzo(bArr));
    }

    public static synchronized <P> void zza(String str, zzaua<P> zzauaVar) {
        if (zzdhs.containsKey(str.toLowerCase())) {
            if (!zzauaVar.getClass().equals(zzdhs.get(str.toLowerCase()).getClass())) {
                Logger logger2 = logger;
                Level level = Level.WARNING;
                String strValueOf = String.valueOf(str);
                logger2.logp(level, "com.google.crypto.tink.Registry", "addCatalogue", strValueOf.length() != 0 ? "Attempted overwrite of a catalogueName catalogue for name ".concat(strValueOf) : new String("Attempted overwrite of a catalogueName catalogue for name "));
                throw new GeneralSecurityException(new StringBuilder(String.valueOf(str).length() + 47).append("catalogue for name ").append(str).append(" has been already registered").toString());
            }
        }
        zzdhs.put(str.toLowerCase(), zzauaVar);
    }

    public static <P> void zza(String str, zzaug<P> zzaugVar) {
        zza(str, zzaugVar, true);
    }

    public static synchronized <P> void zza(String str, zzaug<P> zzaugVar, boolean z) {
        try {
            if (zzaugVar == null) {
                throw new IllegalArgumentException("key manager must be non-null.");
            }
            if (zzdhq.containsKey(str)) {
                zzaug zzaugVarZzdz = zzdz(str);
                boolean zBooleanValue = zzdhr.get(str).booleanValue();
                if (!zzaugVar.getClass().equals(zzaugVarZzdz.getClass()) || (!zBooleanValue && z)) {
                    Logger logger2 = logger;
                    Level level = Level.WARNING;
                    String strValueOf = String.valueOf(str);
                    logger2.logp(level, "com.google.crypto.tink.Registry", "registerKeyManager", strValueOf.length() != 0 ? "Attempted overwrite of a registered key manager for key type ".concat(strValueOf) : new String("Attempted overwrite of a registered key manager for key type "));
                    throw new GeneralSecurityException(String.format("typeUrl (%s) is already registered with %s, cannot be re-registered with %s", str, zzaugVarZzdz.getClass().getName(), zzaugVar.getClass().getName()));
                }
            }
            zzdhq.put(str, zzaugVar);
            zzdhr.put(str, Boolean.valueOf(z));
        } catch (Throwable th) {
            throw th;
        }
    }

    public static <P> zzbcu zzb(zzaxn zzaxnVar) throws GeneralSecurityException {
        zzaug zzaugVarZzdz = zzdz(zzaxnVar.zzyw());
        if (zzdhr.get(zzaxnVar.zzyw()).booleanValue()) {
            return zzaugVarZzdz.zzb(zzaxnVar.zzyx());
        }
        String strValueOf = String.valueOf(zzaxnVar.zzyw());
        throw new GeneralSecurityException(strValueOf.length() != 0 ? "newKey-operation not permitted for key type ".concat(strValueOf) : new String("newKey-operation not permitted for key type "));
    }

    public static <P> P zzb(String str, zzbcu zzbcuVar) {
        return (P) zzdz(str).zza(zzbcuVar);
    }

    public static <P> zzaua<P> zzdy(String str) throws GeneralSecurityException {
        if (str == null) {
            throw new IllegalArgumentException("catalogueName must be non-null.");
        }
        zzaua<P> zzauaVar = zzdhs.get(str.toLowerCase());
        if (zzauaVar != null) {
            return zzauaVar;
        }
        String strConcat = String.format("no catalogue found for %s. ", str);
        if (str.toLowerCase().startsWith("tinkaead")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call AeadConfig.init().");
        }
        if (str.toLowerCase().startsWith("tinkdeterministicaead")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call DeterministicAeadConfig.init().");
        } else if (str.toLowerCase().startsWith("tinkstreamingaead")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call StreamingAeadConfig.init().");
        } else if (str.toLowerCase().startsWith("tinkhybriddecrypt") || str.toLowerCase().startsWith("tinkhybridencrypt")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call HybridConfig.init().");
        } else if (str.toLowerCase().startsWith("tinkmac")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call MacConfig.init().");
        } else if (str.toLowerCase().startsWith("tinkpublickeysign") || str.toLowerCase().startsWith("tinkpublickeyverify")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call SignatureConfig.init().");
        } else if (str.toLowerCase().startsWith("tink")) {
            strConcat = String.valueOf(strConcat).concat("Maybe call TinkConfig.init().");
        }
        throw new GeneralSecurityException(strConcat);
    }

    private static <P> zzaug<P> zzdz(String str) throws GeneralSecurityException {
        zzaug<P> zzaugVar = zzdhq.get(str);
        if (zzaugVar == null) {
            throw new GeneralSecurityException(new StringBuilder(String.valueOf(str).length() + 78).append("No key manager found for key type: ").append(str).append(".  Check the configuration of the registry.").toString());
        }
        return zzaugVar;
    }
}
