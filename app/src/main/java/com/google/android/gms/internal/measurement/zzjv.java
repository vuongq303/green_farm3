package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.d.f;
import com.google.android.gms.common.e.c;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.measurement.g;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;

/* JADX INFO: loaded from: classes.dex */
public final class zzjv extends zzhk {
    private static final String[] zzaqy = {"firebase_", "google_", "ga_"};
    private SecureRandom zzaqz;
    private final AtomicLong zzara;
    private int zzarb;
    private Integer zzarc;

    zzjv(zzgl zzglVar) {
        super(zzglVar);
        this.zzarc = null;
        this.zzara = new AtomicLong(0L);
    }

    static MessageDigest getMessageDigest(String str) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= 2) {
                return null;
            }
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str);
                if (messageDigest != null) {
                    return messageDigest;
                }
                i = i2 + 1;
            } catch (NoSuchAlgorithmException e) {
            }
        }
    }

    public static zzkj zza(zzki zzkiVar, String str) {
        for (zzkj zzkjVar : zzkiVar.zzasv) {
            if (zzkjVar.name.equals(str)) {
                return zzkjVar;
            }
        }
        return null;
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf(((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf(((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1L : 0L);
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            return zza(String.valueOf(obj), i, z);
        }
        return null;
    }

    public static String zza(String str, int i, boolean z) {
        if (str.codePointCount(0, str.length()) <= i) {
            return str;
        }
        if (z) {
            return String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...");
        }
        return null;
    }

    public static String zza(String str, String[] strArr, String[] strArr2) {
        bf.checkNotNull(strArr);
        bf.checkNotNull(strArr2);
        int iMin = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < iMin; i++) {
            if (zzs(str, strArr[i])) {
                return strArr2[i];
            }
        }
        return null;
    }

    private static void zza(Bundle bundle, Object obj) {
        bf.checkNotNull(bundle);
        if (obj != null) {
            if ((obj instanceof String) || (obj instanceof CharSequence)) {
                bundle.putLong("_el", String.valueOf(obj).length());
            }
        }
    }

    private static boolean zza(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", i);
        return true;
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        if (obj == null || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Boolean) || (obj instanceof Double)) {
            return true;
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            String strValueOf = String.valueOf(obj);
            if (strValueOf.codePointCount(0, strValueOf.length()) <= i) {
                return true;
            }
            zzgg().zzin().zzd("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(strValueOf.length()));
            return false;
        }
        if ((obj instanceof Bundle) && z) {
            return true;
        }
        if ((obj instanceof Parcelable[]) && z) {
            for (Parcelable parcelable : (Parcelable[]) obj) {
                if (!(parcelable instanceof Bundle)) {
                    zzgg().zzin().zze("All Parcelable[] elements must be of type Bundle. Value type, name", parcelable.getClass(), str2);
                    return false;
                }
            }
            return true;
        }
        if (!(obj instanceof ArrayList) || !z) {
            return false;
        }
        ArrayList arrayList = (ArrayList) obj;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            if (!(obj2 instanceof Bundle)) {
                zzgg().zzin().zze("All ArrayList elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                return false;
            }
        }
        return true;
    }

    public static boolean zza(long[] jArr, int i) {
        return i < (jArr.length << 6) && (jArr[i / 64] & (1 << (i % 64))) != 0;
    }

    static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(parcelObtain, 0);
            return parcelObtain.marshall();
        } finally {
            parcelObtain.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
            for (int i2 = 0; i2 < 64 && (i << 6) + i2 < bitSet.length(); i2++) {
                if (bitSet.get((i << 6) + i2)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
            }
        }
        return jArr;
    }

    static zzkj[] zza(zzkj[] zzkjVarArr, String str, Object obj) {
        for (zzkj zzkjVar : zzkjVarArr) {
            if (str.equals(zzkjVar.name)) {
                zzkjVar.zzasz = null;
                zzkjVar.zzajf = null;
                zzkjVar.zzaqx = null;
                if (obj instanceof Long) {
                    zzkjVar.zzasz = (Long) obj;
                    return zzkjVarArr;
                }
                if (obj instanceof String) {
                    zzkjVar.zzajf = (String) obj;
                    return zzkjVarArr;
                }
                if (!(obj instanceof Double)) {
                    return zzkjVarArr;
                }
                zzkjVar.zzaqx = (Double) obj;
                return zzkjVarArr;
            }
        }
        zzkj[] zzkjVarArr2 = new zzkj[zzkjVarArr.length + 1];
        System.arraycopy(zzkjVarArr, 0, zzkjVarArr2, 0, zzkjVarArr.length);
        zzkj zzkjVar2 = new zzkj();
        zzkjVar2.name = str;
        if (obj instanceof Long) {
            zzkjVar2.zzasz = (Long) obj;
        } else if (obj instanceof String) {
            zzkjVar2.zzajf = (String) obj;
        } else if (obj instanceof Double) {
            zzkjVar2.zzaqx = (Double) obj;
        }
        zzkjVarArr2[zzkjVarArr.length] = zzkjVar2;
        return zzkjVarArr2;
    }

    public static Object zzb(zzki zzkiVar, String str) {
        zzkj zzkjVarZza = zza(zzkiVar, str);
        if (zzkjVarZza != null) {
            if (zzkjVarZza.zzajf != null) {
                return zzkjVarZza.zzajf;
            }
            if (zzkjVarZza.zzasz != null) {
                return zzkjVarZza.zzasz;
            }
            if (zzkjVarZza.zzaqx != null) {
                return zzkjVarZza.zzaqx;
            }
        }
        return null;
    }

    static boolean zzbv(String str) {
        bf.id(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    static long zzc(byte[] bArr) {
        int i = 0;
        bf.checkNotNull(bArr);
        bf.bZ(bArr.length > 0);
        long j = 0;
        for (int length = bArr.length - 1; length >= 0 && length >= bArr.length - 8; length--) {
            j += (((long) bArr[length]) & 255) << i;
            i += 8;
        }
        return j;
    }

    public static boolean zzc(Context context, String str) {
        ServiceInfo serviceInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            return (packageManager == null || (serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 0)) == null || !serviceInfo.enabled) ? false : true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static int zzca(String str) {
        if ("_ldl".equals(str)) {
            return 2048;
        }
        return "_id".equals(str) ? 256 : 36;
    }

    public static boolean zzcb(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("_");
    }

    static boolean zzcd(String str) {
        return str != null && str.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    static boolean zzcg(String str) {
        bf.id(str);
        switch (str) {
            case "_in":
            case "_ui":
            case "_ug":
                return true;
            default:
                return false;
        }
    }

    public static boolean zzd(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    static boolean zzd(zzeu zzeuVar, zzec zzecVar) {
        bf.checkNotNull(zzeuVar);
        bf.checkNotNull(zzecVar);
        return !TextUtils.isEmpty(zzecVar.zzadh);
    }

    private final boolean zze(Context context, String str) {
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = c.aV(context).getPackageInfo(str, 64);
            if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
            }
        } catch (PackageManager.NameNotFoundException e) {
            zzgg().zzil().zzg("Package name not found", e);
        } catch (CertificateException e2) {
            zzgg().zzil().zzg("Error obtaining certificate", e2);
        }
        return true;
    }

    public static Bundle[] zze(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        }
        if (obj instanceof Parcelable[]) {
            return (Bundle[]) Arrays.copyOf((Parcelable[]) obj, ((Parcelable[]) obj).length, Bundle[].class);
        }
        if (!(obj instanceof ArrayList)) {
            return null;
        }
        ArrayList arrayList = (ArrayList) obj;
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    public static Object zzf(Object obj) throws Throwable {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;
        try {
            if (obj == null) {
                return null;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.flush();
                    objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                    try {
                        Object object = objectInputStream.readObject();
                        objectOutputStream.close();
                        objectInputStream.close();
                        return object;
                    } catch (Throwable th) {
                        th = th;
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    objectInputStream = null;
                }
            } catch (Throwable th3) {
                th = th3;
                objectInputStream = null;
                objectOutputStream = null;
            }
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e2) {
            return null;
        }
    }

    private final boolean zzr(String str, String str2) {
        if (str2 == null) {
            zzgg().zzil().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzgg().zzil().zzg("Name is required and can't be empty. Type", str);
            return false;
        }
        int iCodePointAt = str2.codePointAt(0);
        if (!Character.isLetter(iCodePointAt) && iCodePointAt != 95) {
            zzgg().zzil().zze("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int iCharCount = Character.charCount(iCodePointAt);
        while (iCharCount < length) {
            int iCodePointAt2 = str2.codePointAt(iCharCount);
            if (iCodePointAt2 != 95 && !Character.isLetterOrDigit(iCodePointAt2)) {
                zzgg().zzil().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return true;
    }

    public static boolean zzs(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null) {
            return false;
        }
        return str.equals(str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Bundle zza(Uri uri) {
        String queryParameter;
        String queryParameter2;
        String queryParameter3;
        String queryParameter4;
        Bundle bundle = null;
        if (uri != null) {
            try {
                if (uri.isHierarchical()) {
                    queryParameter4 = uri.getQueryParameter("utm_campaign");
                    queryParameter3 = uri.getQueryParameter("utm_source");
                    queryParameter2 = uri.getQueryParameter("utm_medium");
                    queryParameter = uri.getQueryParameter("gclid");
                } else {
                    queryParameter = null;
                    queryParameter2 = null;
                    queryParameter3 = null;
                    queryParameter4 = null;
                }
                if (!TextUtils.isEmpty(queryParameter4) || !TextUtils.isEmpty(queryParameter3) || !TextUtils.isEmpty(queryParameter2) || !TextUtils.isEmpty(queryParameter)) {
                    bundle = new Bundle();
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, queryParameter4);
                    }
                    if (!TextUtils.isEmpty(queryParameter3)) {
                        bundle.putString("source", queryParameter3);
                    }
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        bundle.putString(FirebaseAnalytics.Param.MEDIUM, queryParameter2);
                    }
                    if (!TextUtils.isEmpty(queryParameter)) {
                        bundle.putString("gclid", queryParameter);
                    }
                    String queryParameter5 = uri.getQueryParameter("utm_term");
                    if (!TextUtils.isEmpty(queryParameter5)) {
                        bundle.putString(FirebaseAnalytics.Param.TERM, queryParameter5);
                    }
                    String queryParameter6 = uri.getQueryParameter("utm_content");
                    if (!TextUtils.isEmpty(queryParameter6)) {
                        bundle.putString(FirebaseAnalytics.Param.CONTENT, queryParameter6);
                    }
                    String queryParameter7 = uri.getQueryParameter(FirebaseAnalytics.Param.ACLID);
                    if (!TextUtils.isEmpty(queryParameter7)) {
                        bundle.putString(FirebaseAnalytics.Param.ACLID, queryParameter7);
                    }
                    String queryParameter8 = uri.getQueryParameter(FirebaseAnalytics.Param.CP1);
                    if (!TextUtils.isEmpty(queryParameter8)) {
                        bundle.putString(FirebaseAnalytics.Param.CP1, queryParameter8);
                    }
                    String queryParameter9 = uri.getQueryParameter("anid");
                    if (!TextUtils.isEmpty(queryParameter9)) {
                        bundle.putString("anid", queryParameter9);
                    }
                }
            } catch (UnsupportedOperationException e) {
                zzgg().zzin().zzg("Install referrer url isn't a hierarchical URI", e);
            }
        }
        return bundle;
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:62:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:64:0x0113  */
    /* JADX WARN: Code duplicated, block: B:66:0x0119  */
    /* JADX WARN: Code duplicated, block: B:70:0x012c  */
    /* JADX WARN: Code duplicated, block: B:72:0x013a  */
    public final Bundle zza(String str, Bundle bundle, List<String> list, boolean z, boolean z2) {
        int i;
        int i2;
        boolean z3;
        int size;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        int i3 = 0;
        for (String str2 : bundle.keySet()) {
            int i4 = 0;
            if (list == null || !list.contains(str2)) {
                if (z) {
                    if (!zzq("event param", str2)) {
                        i4 = 3;
                    } else if (zza("event param", (String[]) null, str2)) {
                        i4 = !zza("event param", 40, str2) ? 3 : 0;
                    } else {
                        i4 = 14;
                    }
                }
                if (i4 == 0) {
                    if (!zzr("event param", str2)) {
                        i4 = 3;
                    } else if (zza("event param", (String[]) null, str2)) {
                        i4 = !zza("event param", 40, str2) ? 3 : 0;
                    } else {
                        i4 = 14;
                    }
                }
            }
            if (i4 != 0) {
                if (zza(bundle2, i4)) {
                    bundle2.putString("_ev", zza(str2, 40, true));
                    if (i4 == 3) {
                        zza(bundle2, str2);
                    }
                }
                bundle2.remove(str2);
            } else {
                Object obj = bundle.get(str2);
                zzab();
                if (z2) {
                    if (obj instanceof Parcelable[]) {
                        size = ((Parcelable[]) obj).length;
                    } else {
                        if (obj instanceof ArrayList) {
                            size = ((ArrayList) obj).size();
                        } else {
                            z3 = true;
                        }
                        if (!z3) {
                            i = 17;
                        } else if (((zzgi().zzd(zzfv().zzah(), zzew.zzahz) || !zzcb(str)) && !zzcb(str2)) ? zza("param", str2, 100, obj, z2) : zza("param", str2, 256, obj, z2)) {
                            i = 0;
                        } else {
                            i = 4;
                        }
                    }
                    if (size > 1000) {
                        zzgg().zzin().zzd("Parameter array is too long; discarded. Value kind, name, array length", "param", str2, Integer.valueOf(size));
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (!z3) {
                        i = 17;
                    } else if (((zzgi().zzd(zzfv().zzah(), zzew.zzahz) || !zzcb(str)) && !zzcb(str2)) ? zza("param", str2, 100, obj, z2) : zza("param", str2, 256, obj, z2)) {
                        i = 0;
                    } else {
                        i = 4;
                    }
                } else if (((zzgi().zzd(zzfv().zzah(), zzew.zzahz) || !zzcb(str)) && !zzcb(str2)) ? zza("param", str2, 100, obj, z2) : zza("param", str2, 256, obj, z2)) {
                    i = 0;
                } else {
                    i = 4;
                }
                if (i == 0 || "_ev".equals(str2)) {
                    if (zzbv(str2)) {
                        i2 = i3 + 1;
                        if (i2 > 25) {
                            zzgg().zzil().zze(new StringBuilder(48).append("Event can't contain more than 25 params").toString(), zzgb().zzbe(str), zzgb().zzb(bundle));
                            zza(bundle2, 5);
                            bundle2.remove(str2);
                            i3 = i2;
                        }
                    } else {
                        i2 = i3;
                    }
                    i3 = i2;
                } else {
                    if (zza(bundle2, i)) {
                        bundle2.putString("_ev", zza(str2, 40, true));
                        zza(bundle2, bundle.get(str2));
                    }
                    bundle2.remove(str2);
                }
            }
        }
        return bundle2;
    }

    final <T extends Parcelable> T zza(byte[] bArr, Parcelable.Creator<T> creator) {
        T tCreateFromParcel;
        if (bArr == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                parcelObtain.unmarshall(bArr, 0, bArr.length);
                parcelObtain.setDataPosition(0);
                tCreateFromParcel = creator.createFromParcel(parcelObtain);
                parcelObtain.recycle();
            } catch (b e) {
                zzgg().zzil().log("Failed to load parcelable from buffer");
                parcelObtain.recycle();
                tCreateFromParcel = null;
            }
            return tCreateFromParcel;
        } catch (Throwable th) {
            parcelObtain.recycle();
            throw th;
        }
    }

    final zzeu zza(String str, Bundle bundle, String str2, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (zzbw(str) != 0) {
            zzgg().zzil().zzg("Invalid conditional property event name", zzgb().zzbg(str));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str2);
        return new zzeu(str, new zzer(zzd(zza(str, bundle2, f.D("_o"), false, false))), str2, j);
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza((String) null, i, str, str2, i2);
    }

    public final void zza(Bundle bundle, String str, Object obj) {
        if (bundle == null) {
            return;
        }
        if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof String) {
            bundle.putString(str, String.valueOf(obj));
        } else if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (str != null) {
            zzgg().zzio().zze("Not putting event parameter. Invalid value type. name, type", zzgb().zzbf(str), obj != null ? obj.getClass().getSimpleName() : null);
        }
    }

    public final void zza(zzkj zzkjVar, Object obj) {
        bf.checkNotNull(obj);
        zzkjVar.zzajf = null;
        zzkjVar.zzasz = null;
        zzkjVar.zzaqx = null;
        if (obj instanceof String) {
            zzkjVar.zzajf = (String) obj;
            return;
        }
        if (obj instanceof Long) {
            zzkjVar.zzasz = (Long) obj;
        } else if (obj instanceof Double) {
            zzkjVar.zzaqx = (Double) obj;
        } else {
            zzgg().zzil().zzg("Ignoring invalid (type) event param value", obj);
        }
    }

    public final void zza(zzkn zzknVar, Object obj) {
        bf.checkNotNull(obj);
        zzknVar.zzajf = null;
        zzknVar.zzasz = null;
        zzknVar.zzaqx = null;
        if (obj instanceof String) {
            zzknVar.zzajf = (String) obj;
            return;
        }
        if (obj instanceof Long) {
            zzknVar.zzasz = (Long) obj;
        } else if (obj instanceof Double) {
            zzknVar.zzaqx = (Double) obj;
        } else {
            zzgg().zzil().zzg("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zza(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", i2);
        }
        this.zzacr.zzfu().zza("auto", "_err", bundle);
    }

    public final boolean zza(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzbt().currentTimeMillis() - j) > j2;
    }

    final boolean zza(String str, int i, String str2) {
        if (str2 == null) {
            zzgg().zzil().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        }
        zzgg().zzil().zzd("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
        return false;
    }

    final boolean zza(String str, String[] strArr, String str2) {
        boolean z;
        boolean z2;
        if (str2 == null) {
            zzgg().zzil().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        bf.checkNotNull(str2);
        int i = 0;
        while (true) {
            if (i >= zzaqy.length) {
                z = false;
                break;
            }
            if (str2.startsWith(zzaqy[i])) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            zzgg().zzil().zze("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            bf.checkNotNull(strArr);
            int i2 = 0;
            while (true) {
                if (i2 >= strArr.length) {
                    z2 = false;
                    break;
                }
                if (zzs(str2, strArr[i2])) {
                    z2 = true;
                    break;
                }
                i2++;
            }
            if (z2) {
                zzgg().zzil().zze("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    public final byte[] zza(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzgg().zzil().zzg("Failed to gzip content", e);
            throw e;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final byte[] zzb(zzkk zzkkVar) {
        try {
            byte[] bArr = new byte[zzkkVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzkkVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            return bArr;
        } catch (IOException e) {
            zzgg().zzil().zzg("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    public final byte[] zzb(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int i = gZIPInputStream.read(bArr2);
                if (i <= 0) {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr2, 0, i);
            }
        } catch (IOException e) {
            zzgg().zzil().zzg("Failed to ungzip content", e);
            throw e;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    public final int zzbw(String str) {
        if (!zzr("event", str)) {
            return 2;
        }
        if (zza("event", com.google.android.gms.measurement.c.bXE, str)) {
            return zza("event", 40, str) ? 0 : 2;
        }
        return 13;
    }

    public final int zzbx(String str) {
        if (!zzq("user property", str)) {
            return 6;
        }
        if (zza("user property", g.bXI, str)) {
            return zza("user property", 24, str) ? 0 : 6;
        }
        return 15;
    }

    public final int zzby(String str) {
        if (!zzr("user property", str)) {
            return 6;
        }
        if (zza("user property", g.bXI, str)) {
            return zza("user property", 24, str) ? 0 : 6;
        }
        return 15;
    }

    public final boolean zzbz(String str) {
        if (TextUtils.isEmpty(str)) {
            zzgg().zzil().log("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        bf.checkNotNull(str);
        if (str.matches("^1:\\d+:android:[a-f0-9]+$")) {
            return true;
        }
        zzgg().zzil().zzg("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", str);
        return false;
    }

    public final boolean zzcc(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return zzgi().zzhl().equals(str);
    }

    final boolean zzce(String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzgd().zzm(str, "measurement.upload.blacklist_internal"));
    }

    final boolean zzcf(String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzgd().zzm(str, "measurement.upload.blacklist_public"));
    }

    final long zzd(Context context, String str) {
        long jZzc = -1;
        zzab();
        bf.checkNotNull(context);
        bf.id(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest messageDigest = getMessageDigest("MD5");
        if (messageDigest == null) {
            zzgg().zzil().log("Could not get MD5 instance");
            return -1L;
        }
        if (packageManager != null) {
            try {
                if (!zze(context, str)) {
                    PackageInfo packageInfo = c.aV(context).getPackageInfo(getContext().getPackageName(), 64);
                    if (packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                        zzgg().zzin().log("Could not get signatures");
                    } else {
                        jZzc = zzc(messageDigest.digest(packageInfo.signatures[0].toByteArray()));
                    }
                    return jZzc;
                }
            } catch (PackageManager.NameNotFoundException e) {
                zzgg().zzil().zzg("Package name not found", e);
            }
        }
        return 0L;
    }

    final Bundle zzd(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object objZzh = zzh(str, bundle.get(str));
                if (objZzh == null) {
                    zzgg().zzin().zzg("Param value can't be null", zzgb().zzbf(str));
                } else {
                    zza(bundle2, str, objZzh);
                }
            }
        }
        return bundle2;
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

    public final Object zzh(String str, Object obj) {
        if ("_ev".equals(str)) {
            return zza(256, obj, true);
        }
        return zza(zzcb(str) ? 256 : 100, obj, false);
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return true;
    }

    public final int zzi(String str, Object obj) {
        return "_ldl".equals(str) ? zza("user property referrer", str, zzca(str), obj, false) : zza("user property", str, zzca(str), obj, false) ? 0 : 7;
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final void zzig() {
        zzab();
        SecureRandom secureRandom = new SecureRandom();
        long jNextLong = secureRandom.nextLong();
        if (jNextLong == 0) {
            jNextLong = secureRandom.nextLong();
            if (jNextLong == 0) {
                zzgg().zzin().log("Utils falling back to Random for random id");
            }
        }
        this.zzara.set(jNextLong);
    }

    public final Object zzj(String str, Object obj) {
        return "_ldl".equals(str) ? zza(zzca(str), obj, true) : zza(zzca(str), obj, false);
    }

    public final long zzkt() {
        long andIncrement;
        if (this.zzara.get() == 0) {
            synchronized (this.zzara) {
                long jNextLong = new Random(System.nanoTime() ^ zzbt().currentTimeMillis()).nextLong();
                int i = this.zzarb + 1;
                this.zzarb = i;
                andIncrement = jNextLong + ((long) i);
            }
        } else {
            synchronized (this.zzara) {
                this.zzara.compareAndSet(-1L, 1L);
                andIncrement = this.zzara.getAndIncrement();
            }
        }
        return andIncrement;
    }

    final SecureRandom zzku() {
        zzab();
        if (this.zzaqz == null) {
            this.zzaqz = new SecureRandom();
        }
        return this.zzaqz;
    }

    public final int zzkv() {
        if (this.zzarc == null) {
            this.zzarc = Integer.valueOf(com.google.android.gms.common.g.YD().getApkVersion(getContext()) / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
        }
        return this.zzarc.intValue();
    }

    final boolean zzq(String str, String str2) {
        if (str2 == null) {
            zzgg().zzil().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzgg().zzil().zzg("Name is required and can't be empty. Type", str);
            return false;
        }
        int iCodePointAt = str2.codePointAt(0);
        if (!Character.isLetter(iCodePointAt)) {
            zzgg().zzil().zze("Name must start with a letter. Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int iCharCount = Character.charCount(iCodePointAt);
        while (iCharCount < length) {
            int iCodePointAt2 = str2.codePointAt(iCharCount);
            if (iCodePointAt2 != 95 && !Character.isLetterOrDigit(iCodePointAt2)) {
                zzgg().zzil().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return true;
    }

    public final boolean zzx(String str) {
        zzab();
        if (c.aV(getContext()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzgg().zziq().zzg("Permission not granted", str);
        return false;
    }
}
