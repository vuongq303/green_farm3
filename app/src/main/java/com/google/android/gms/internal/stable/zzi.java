package com.google.android.gms.internal.stable;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class zzi {
    private static HashMap<String, String> zzagq;
    private static Object zzahd;
    private static boolean zzahe;
    private static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzagv = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static final Pattern zzagw = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static final Pattern zzagx = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzagy = new AtomicBoolean();
    private static final HashMap<String, Boolean> zzagz = new HashMap<>();
    private static final HashMap<String, Integer> zzaha = new HashMap<>();
    private static final HashMap<String, Long> zzahb = new HashMap<>();
    private static final HashMap<String, Float> zzahc = new HashMap<>();
    private static String[] zzahf = new String[0];

    public static int getInt(ContentResolver contentResolver, String str, int i) {
        Object objZzb = zzb(contentResolver);
        Integer numValueOf = (Integer) zza(zzaha, str, Integer.valueOf(i));
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        String strZza = zza(contentResolver, str, (String) null);
        if (strZza != null) {
            try {
                int i2 = Integer.parseInt(strZza);
                numValueOf = Integer.valueOf(i2);
                i = i2;
            } catch (NumberFormatException e) {
            }
        }
        zza(objZzb, zzaha, str, numValueOf);
        return i;
    }

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        Object objZzb = zzb(contentResolver);
        Long lValueOf = (Long) zza(zzahb, str, Long.valueOf(j));
        if (lValueOf != null) {
            return lValueOf.longValue();
        }
        String strZza = zza(contentResolver, str, (String) null);
        if (strZza != null) {
            try {
                long j2 = Long.parseLong(strZza);
                lValueOf = Long.valueOf(j2);
                j = j2;
            } catch (NumberFormatException e) {
            }
        }
        zza(objZzb, zzahb, str, lValueOf);
        return j;
    }

    private static <T> T zza(HashMap<String, T> map, String str, T t) {
        synchronized (zzi.class) {
            if (!map.containsKey(str)) {
                return null;
            }
            T t2 = map.get(str);
            if (t2 == null) {
                t2 = t;
            }
            return t2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0060 A[Catch: all -> 0x005d, DONT_GENERATE, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0006, B:6:0x0013, B:9:0x001e, B:11:0x0020, B:13:0x0026, B:15:0x002e, B:17:0x0032, B:29:0x0060, B:19:0x003a, B:21:0x0050, B:24:0x005b, B:31:0x0062, B:32:0x0065), top: B:55:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x007b A[Catch: all -> 0x009f, TRY_LEAVE, TryCatch #1 {all -> 0x009f, blocks: (B:35:0x0075, B:41:0x0086, B:43:0x008c, B:46:0x0093, B:37:0x007b), top: B:56:0x0075 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0081 A[DONT_GENERATE] */
    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzi.class) {
            zza(contentResolver);
            Object obj = zzahd;
            if (zzagq.containsKey(str)) {
                String str3 = zzagq.get(str);
                if (str3 != null) {
                    str2 = str3;
                }
            } else {
                for (String str4 : zzahf) {
                    if (str.startsWith(str4)) {
                        if (!zzahe || zzagq.isEmpty()) {
                            zzagq.putAll(zza(contentResolver, zzahf));
                            zzahe = true;
                            if (zzagq.containsKey(str)) {
                                String str5 = zzagq.get(str);
                                if (str5 != null) {
                                    str2 = str5;
                                }
                            }
                        }
                    }
                }
                Cursor cursorQuery = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            String string = cursorQuery.getString(1);
                            if (string != null && string.equals(str2)) {
                                string = str2;
                            }
                            zza(obj, str, string);
                            if (string != null) {
                                str2 = string;
                            }
                        } else {
                            zza(obj, str, (String) null);
                        }
                    } finally {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    }
                } else {
                    zza(obj, str, (String) null);
                }
            }
        }
        return str2;
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor cursorQuery = contentResolver.query(zzagv, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    treeMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
                } catch (Throwable th) {
                    cursorQuery.close();
                    throw th;
                }
            }
            cursorQuery.close();
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzagq == null) {
            zzagy.set(false);
            zzagq = new HashMap<>();
            zzahd = new Object();
            zzahe = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzj(null));
            return;
        }
        if (zzagy.getAndSet(false)) {
            zzagq.clear();
            zzagz.clear();
            zzaha.clear();
            zzahb.clear();
            zzahc.clear();
            zzahd = new Object();
            zzahe = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzi.class) {
            if (obj == zzahd) {
                zzagq.put(str, str2);
            }
        }
    }

    private static <T> void zza(Object obj, HashMap<String, T> map, String str, T t) {
        synchronized (zzi.class) {
            if (obj == zzahd) {
                map.put(str, t);
                zzagq.remove(str);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object objZzb = zzb(contentResolver);
        Boolean bool = (Boolean) zza(zzagz, str, Boolean.valueOf(z));
        if (bool != null) {
            return bool.booleanValue();
        }
        String strZza = zza(contentResolver, str, (String) null);
        if (strZza != null && !strZza.equals("")) {
            if (zzagw.matcher(strZza).matches()) {
                bool = true;
                z = true;
            } else if (zzagx.matcher(strZza).matches()) {
                bool = false;
                z = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + strZza + "\") as boolean");
            }
        }
        zza(objZzb, zzagz, str, bool);
        return z;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzi.class) {
            zza(contentResolver);
            obj = zzahd;
        }
        return obj;
    }
}
