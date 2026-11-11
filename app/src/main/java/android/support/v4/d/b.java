package android.support.v4.d;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.v4.e.r;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: FontsContractCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final android.support.v4.e.i<String, Typeface> ft = new android.support.v4.e.i<>(16);
    private static final i fR = new i("fonts", 10, 10000);
    private static final Object sLock = new Object();
    private static final r<String, ArrayList<n<Typeface>>> fS = new r<>();
    private static final Comparator<byte[]> fT = new c();

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface a(Context context, a aVar) {
        try {
            g gVarA = a(context, (CancellationSignal) null, aVar);
            if (gVarA.getStatusCode() == 0) {
                return a(context, (CancellationSignal) null, gVarA.aE());
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Typeface a(Context context, a aVar, TextView textView, int i, int i2, int i3) {
        String identifier = aVar.getIdentifier();
        Typeface typeface = ft.get(identifier);
        if (typeface == null) {
            boolean z = i == 0;
            if (z && i2 == -1) {
                return a(context, aVar);
            }
            d dVar = new d(context, aVar, identifier);
            if (z) {
                try {
                    return (Typeface) fR.a(dVar, i2);
                } catch (InterruptedException e) {
                    return null;
                }
            }
            e eVar = new e(textView, i3);
            synchronized (sLock) {
                if (fS.containsKey(identifier)) {
                    fS.get(identifier).add(eVar);
                } else {
                    ArrayList<n<Typeface>> arrayList = new ArrayList<>();
                    arrayList.add(eVar);
                    fS.put(identifier, arrayList);
                    fR.a(dVar, new f(identifier));
                }
            }
            return 0;
        }
        return typeface;
    }

    private static Map<Uri, ByteBuffer> a(Context context, h[] hVarArr, CancellationSignal cancellationSignal) {
        MappedByteBuffer map;
        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor;
        HashMap map2 = new HashMap();
        ContentResolver contentResolver = context.getContentResolver();
        for (h hVar : hVarArr) {
            if (hVar.getResultCode() == 0) {
                Uri uri = hVar.getUri();
                if (!map2.containsKey(uri)) {
                    try {
                        if (Build.VERSION.SDK_INT > 19) {
                            parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(uri, "r", cancellationSignal);
                        } else {
                            parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
                        }
                        FileChannel channel = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).getChannel();
                        map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                    } catch (IOException e) {
                        map = null;
                    }
                    map2.put(uri, map);
                }
            }
        }
        return Collections.unmodifiableMap(map2);
    }

    public static Typeface a(Context context, CancellationSignal cancellationSignal, h[] hVarArr) {
        return android.support.v4.a.b.a(context, hVarArr, a(context, hVarArr, cancellationSignal));
    }

    public static g a(Context context, CancellationSignal cancellationSignal, a aVar) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfoA = a(context.getPackageManager(), aVar, context.getResources());
        if (providerInfoA == null) {
            return new g(1, null);
        }
        return new g(0, a(context, aVar, providerInfoA.authority, cancellationSignal));
    }

    public static ProviderInfo a(PackageManager packageManager, a aVar, Resources resources) throws PackageManager.NameNotFoundException {
        int i = 0;
        String providerAuthority = aVar.getProviderAuthority();
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!providerInfoResolveContentProvider.packageName.equals(aVar.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + aVar.getProviderPackage());
        }
        List<byte[]> listA = a(packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures);
        Collections.sort(listA, fT);
        List<List<byte[]>> listA2 = a(aVar, resources);
        while (true) {
            int i2 = i;
            if (i2 < listA2.size()) {
                ArrayList arrayList = new ArrayList(listA2.get(i2));
                Collections.sort(arrayList, fT);
                if (a(listA, arrayList)) {
                    return providerInfoResolveContentProvider;
                }
                i = i2 + 1;
            } else {
                return null;
            }
        }
    }

    private static List<List<byte[]>> a(a aVar, Resources resources) {
        if (aVar.getCertificates() != null) {
            return aVar.getCertificates();
        }
        return android.support.v4.content.a.a.a(resources, aVar.az());
    }

    private static boolean a(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> a(Signature[] signatureArr) {
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x016f  */
    static h[] a(Context context, a aVar, String str, CancellationSignal cancellationSignal) throws Throwable {
        Cursor cursor;
        Cursor cursorQuery;
        ArrayList arrayList;
        Uri uriWithAppendedId;
        ArrayList arrayList2 = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme(FirebaseAnalytics.Param.CONTENT).authority(str).build();
        Uri uriBuild2 = new Uri.Builder().scheme(FirebaseAnalytics.Param.CONTENT).authority(str).appendPath("file").build();
        try {
            if (Build.VERSION.SDK_INT > 16) {
                cursorQuery = context.getContentResolver().query(uriBuild, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{aVar.getQuery()}, null, cancellationSignal);
            } else {
                cursorQuery = context.getContentResolver().query(uriBuild, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{aVar.getQuery()}, null);
            }
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0) {
                        int columnIndex = cursorQuery.getColumnIndex("result_code");
                        arrayList = new ArrayList();
                        int columnIndex2 = cursorQuery.getColumnIndex("_id");
                        int columnIndex3 = cursorQuery.getColumnIndex("file_id");
                        int columnIndex4 = cursorQuery.getColumnIndex("font_ttc_index");
                        int columnIndex5 = cursorQuery.getColumnIndex("font_weight");
                        int columnIndex6 = cursorQuery.getColumnIndex("font_italic");
                        while (cursorQuery.moveToNext()) {
                            int i = columnIndex != -1 ? cursorQuery.getInt(columnIndex) : 0;
                            int i2 = columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : 0;
                            if (columnIndex3 == -1) {
                                uriWithAppendedId = ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(columnIndex2));
                            } else {
                                uriWithAppendedId = ContentUris.withAppendedId(uriBuild2, cursorQuery.getLong(columnIndex3));
                            }
                            arrayList.add(new h(uriWithAppendedId, i2, columnIndex5 != -1 ? cursorQuery.getInt(columnIndex5) : 400, columnIndex6 != -1 && cursorQuery.getInt(columnIndex6) == 1, i));
                        }
                    } else {
                        arrayList = arrayList2;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } else {
                arrayList = arrayList2;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return (h[]) arrayList.toArray(new h[0]);
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }
}
