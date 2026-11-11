package com.google.firebase.messaging;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.measurement.zzabi;
import com.google.android.gms.internal.measurement.zzabj;
import com.google.android.gms.internal.measurement.zzabn;
import com.google.android.gms.internal.measurement.zzabo;
import com.google.android.gms.measurement.a;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzc {
    private static Object zza(zzabo zzaboVar, String str, zzb zzbVar) {
        Object objNewInstance;
        try {
            Class<?> cls = Class.forName("com.google.android.gms.measurement.b");
            Bundle bundleZzw = zzw(zzaboVar.zzcag, zzaboVar.zzcah);
            objNewInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
            try {
                cls.getField("mOrigin").set(objNewInstance, str);
                cls.getField("mCreationTimestamp").set(objNewInstance, Long.valueOf(zzaboVar.zzcai));
                cls.getField("mName").set(objNewInstance, zzaboVar.zzcag);
                cls.getField("mValue").set(objNewInstance, zzaboVar.zzcah);
                cls.getField("mTriggerEventName").set(objNewInstance, TextUtils.isEmpty(zzaboVar.zzcaj) ? null : zzaboVar.zzcaj);
                cls.getField("mTimedOutEventName").set(objNewInstance, !TextUtils.isEmpty(zzaboVar.zzbsn) ? zzaboVar.zzbsn : zzbVar.zztk());
                cls.getField("mTimedOutEventParams").set(objNewInstance, bundleZzw);
                cls.getField("mTriggerTimeout").set(objNewInstance, Long.valueOf(zzaboVar.zzcak));
                cls.getField("mTriggeredEventName").set(objNewInstance, !TextUtils.isEmpty(zzaboVar.zzbsm) ? zzaboVar.zzbsm : zzbVar.zztj());
                cls.getField("mTriggeredEventParams").set(objNewInstance, bundleZzw);
                cls.getField("mTimeToLive").set(objNewInstance, Long.valueOf(zzaboVar.zzrp));
                cls.getField("mExpiredEventName").set(objNewInstance, !TextUtils.isEmpty(zzaboVar.zzbso) ? zzaboVar.zzbso : zzbVar.zztl());
                cls.getField("mExpiredEventParams").set(objNewInstance, bundleZzw);
            } catch (Exception e) {
                e = e;
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            }
        } catch (Exception e2) {
            e = e2;
            objNewInstance = null;
        }
        return objNewInstance;
    }

    private static String zza(zzabo zzaboVar, zzb zzbVar) {
        return (zzaboVar == null || TextUtils.isEmpty(zzaboVar.zzbsp)) ? zzbVar.zztm() : zzaboVar.zzbsp;
    }

    private static List<Object> zza(a aVar, String str) {
        List<Object> list;
        ArrayList arrayList = new ArrayList();
        try {
            Method declaredMethod = a.class.getDeclaredMethod("getConditionalUserProperties", String.class, String.class);
            declaredMethod.setAccessible(true);
            list = (List) declaredMethod.invoke(aVar, str, "");
        } catch (Exception e) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            list = arrayList;
        }
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str).length() + 55).append("Number of currently set _Es for origin: ").append(str).append(" is ").append(list.size()).toString());
        }
        return list;
    }

    private static void zza(Context context, String str, String str2, String str3, String str4) {
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String strValueOf = String.valueOf(str);
            Log.v("FirebaseAbtUtil", strValueOf.length() != 0 ? "_CE(experimentId) called by ".concat(strValueOf) : new String("_CE(experimentId) called by "));
        }
        if (zzy(context)) {
            a aVarZzx = zzx(context);
            try {
                Method declaredMethod = a.class.getDeclaredMethod("clearConditionalUserProperty", String.class, String.class, Bundle.class);
                declaredMethod.setAccessible(true);
                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str2).length() + 17 + String.valueOf(str3).length()).append("Clearing _E: [").append(str2).append(", ").append(str3).append("]").toString());
                }
                declaredMethod.invoke(aVarZzx, str2, str4, zzw(str2, str3));
            } catch (Exception e) {
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            }
        }
    }

    public static void zza(Context context, String str, byte[] bArr, zzb zzbVar, int i) {
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String strValueOf = String.valueOf(str);
            Log.v("FirebaseAbtUtil", strValueOf.length() != 0 ? "_SE called by ".concat(strValueOf) : new String("_SE called by "));
        }
        if (zzy(context)) {
            a aVarZzx = zzx(context);
            zzabo zzaboVarZzi = zzi(bArr);
            if (zzaboVarZzi == null) {
                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    Log.v("FirebaseAbtUtil", "_SE failed; either _P was not set, or we couldn't deserialize the _P.");
                    return;
                }
                return;
            }
            try {
                Class.forName("com.google.android.gms.measurement.b");
                boolean z = false;
                for (Object obj : zza(aVarZzx, str)) {
                    String strZzs = zzs(obj);
                    String strZzt = zzt(obj);
                    long jLongValue = ((Long) Class.forName("com.google.android.gms.measurement.b").getField("mCreationTimestamp").get(obj)).longValue();
                    if (zzaboVarZzi.zzcag.equals(strZzs) && zzaboVarZzi.zzcah.equals(strZzt)) {
                        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                            Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(strZzs).length() + 23 + String.valueOf(strZzt).length()).append("_E is already set. [").append(strZzs).append(", ").append(strZzt).append("]").toString());
                        }
                        z = true;
                    } else {
                        boolean z2 = false;
                        for (zzabn zzabnVar : zzaboVarZzi.zzcam) {
                            if (zzabnVar.zzcag.equals(strZzs)) {
                                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                    Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(strZzs).length() + 33 + String.valueOf(strZzt).length()).append("_E is found in the _OE list. [").append(strZzs).append(", ").append(strZzt).append("]").toString());
                                }
                                z2 = true;
                                break;
                            }
                        }
                        if (!z2) {
                            if (zzaboVarZzi.zzcai > jLongValue) {
                                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                    Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(strZzs).length() + 115 + String.valueOf(strZzt).length()).append("Clearing _E as it was not in the _OE list, andits start time is older than the start time of the _E to be set. [").append(strZzs).append(", ").append(strZzt).append("]").toString());
                                }
                                zza(context, str, strZzs, strZzt, zza(zzaboVarZzi, zzbVar));
                            } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(strZzs).length() + 109 + String.valueOf(strZzt).length()).append("_E was not found in the _OE list, but not clearing it as it has a new start time than the _E to be set.  [").append(strZzs).append(", ").append(strZzt).append("]").toString());
                            }
                        }
                    }
                }
                if (z) {
                    if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                        String str2 = zzaboVarZzi.zzcag;
                        String str3 = zzaboVarZzi.zzcah;
                        Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str2).length() + 44 + String.valueOf(str3).length()).append("_E is already set. Not setting it again [").append(str2).append(", ").append(str3).append("]").toString());
                        return;
                    }
                    return;
                }
                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    String str4 = zzaboVarZzi.zzcag;
                    String str5 = zzaboVarZzi.zzcah;
                    Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str4).length() + 7 + String.valueOf(str5).length()).append("_SEI: ").append(str4).append(" ").append(str5).toString());
                }
                try {
                    Class.forName("com.google.android.gms.measurement.b");
                    List<Object> listZza = zza(aVarZzx, str);
                    if (zza(aVarZzx, str).size() >= zzb(aVarZzx, str)) {
                        if ((zzaboVarZzi.zzcal != 0 ? zzaboVarZzi.zzcal : 1) != 1) {
                            if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                                String str6 = zzaboVarZzi.zzcag;
                                String str7 = zzaboVarZzi.zzcah;
                                Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str6).length() + 44 + String.valueOf(str7).length()).append("_E won't be set due to overflow policy. [").append(str6).append(", ").append(str7).append("]").toString());
                                return;
                            }
                            return;
                        }
                        Object obj2 = listZza.get(0);
                        String strZzs2 = zzs(obj2);
                        String strZzt2 = zzt(obj2);
                        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                            Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(strZzs2).length() + 38).append("Clearing _E due to overflow policy: [").append(strZzs2).append("]").toString());
                        }
                        zza(context, str, strZzs2, strZzt2, zza(zzaboVarZzi, zzbVar));
                    }
                    for (Object obj3 : listZza) {
                        String strZzs3 = zzs(obj3);
                        String strZzt3 = zzt(obj3);
                        if (strZzs3.equals(zzaboVarZzi.zzcag) && !strZzt3.equals(zzaboVarZzi.zzcah) && Log.isLoggable("FirebaseAbtUtil", 2)) {
                            Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(strZzs3).length() + 77 + String.valueOf(strZzt3).length()).append("Clearing _E, as only one _V of the same _E can be set atany given time: [").append(strZzs3).append(", ").append(strZzt3).append("].").toString());
                            zza(context, str, strZzs3, strZzt3, zza(zzaboVarZzi, zzbVar));
                        }
                    }
                    Object objZza = zza(zzaboVarZzi, str, zzbVar);
                    if (objZza == null) {
                        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                            String str8 = zzaboVarZzi.zzcag;
                            String str9 = zzaboVarZzi.zzcah;
                            Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str8).length() + 42 + String.valueOf(str9).length()).append("Could not create _CUP for: [").append(str8).append(", ").append(str9).append("]. Skipping.").toString());
                            return;
                        }
                        return;
                    }
                    try {
                        Method declaredMethod = a.class.getDeclaredMethod("setConditionalUserProperty", Class.forName("com.google.android.gms.measurement.b"));
                        declaredMethod.setAccessible(true);
                        declaredMethod.invoke(aVarZzx, objZza);
                    } catch (Exception e) {
                        Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
                    }
                } catch (Exception e2) {
                    Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e2);
                }
            } catch (Exception e3) {
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e3);
            }
        }
    }

    private static int zzb(a aVar, String str) {
        try {
            Method declaredMethod = a.class.getDeclaredMethod("iy", String.class);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(aVar, str)).intValue();
        } catch (Exception e) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return 20;
        }
    }

    private static zzabo zzi(byte[] bArr) {
        try {
            return (zzabo) zzabj.zza(new zzabo(), bArr);
        } catch (zzabi e) {
            return null;
        }
    }

    private static String zzs(Object obj) {
        return (String) Class.forName("com.google.android.gms.measurement.b").getField("mName").get(obj);
    }

    private static String zzt(Object obj) {
        return (String) Class.forName("com.google.android.gms.measurement.b").getField("mValue").get(obj);
    }

    private static Bundle zzw(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        return bundle;
    }

    private static a zzx(Context context) {
        try {
            return a.aX(context);
        } catch (NoClassDefFoundError e) {
            return null;
        }
    }

    private static boolean zzy(Context context) {
        if (zzx(context) == null) {
            if (!Log.isLoggable("FirebaseAbtUtil", 2)) {
                return false;
            }
            Log.v("FirebaseAbtUtil", "Firebase Analytics not available");
            return false;
        }
        try {
            Class.forName("com.google.android.gms.measurement.b");
            return true;
        } catch (ClassNotFoundException e) {
            if (!Log.isLoggable("FirebaseAbtUtil", 2)) {
                return false;
            }
            Log.v("FirebaseAbtUtil", "Firebase Analytics library is missing support for abt. Please update to a more recent version.");
            return false;
        }
    }
}
