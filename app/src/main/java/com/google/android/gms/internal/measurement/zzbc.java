package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.a.n;
import com.google.android.gms.common.internal.bf;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzbc extends zzar implements Closeable {
    private static final String zzwp = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzwq = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zzbd zzwr;
    private final zzcz zzws;
    private final zzcz zzwt;

    zzbc(zzat zzatVar) {
        super(zzatVar);
        this.zzws = new zzcz(zzbt());
        this.zzwt = new zzcz(zzbt());
        this.zzwr = new zzbd(this, zzatVar.getContext(), "google_analytics_v4.db");
    }

    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery(str, null);
                if (!cursorRawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzd("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long zza(String str, String[] strArr, long j) throws Throwable {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                try {
                    if (!cursorRawQuery.moveToFirst()) {
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        return 0L;
                    }
                    long j2 = cursorRawQuery.getLong(0);
                    if (cursorRawQuery == null) {
                        return j2;
                    }
                    cursorRawQuery.close();
                    return j2;
                } catch (SQLiteException e) {
                    e = e;
                    zzd("Database error", str, e);
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorRawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0073  */
    private final List<Long> zzc(long j) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        n.zzab();
        zzch();
        if (j <= 0) {
            return Collections.emptyList();
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList arrayList = new ArrayList();
        try {
            cursorQuery = writableDatabase.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
            try {
                try {
                    if (cursorQuery.moveToFirst()) {
                        do {
                            arrayList.add(Long.valueOf(cursorQuery.getLong(0)));
                        } while (cursorQuery.moveToNext());
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e) {
                e = e;
                zzd("Error selecting hit ids", e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    private final long zzcr() {
        n.zzab();
        zzch();
        return zza("SELECT COUNT(*) FROM hits2", (String[]) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String zzcz() {
        return "google_analytics_v4.db";
    }

    private final Map<String, String> zzv(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                String strValueOf = String.valueOf(str);
                str = strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?");
            }
            return com.google.android.gms.common.d.n.a(new URI(str), AndroidWebView.UTF_8);
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    private final Map<String, String> zzw(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            String strValueOf = String.valueOf(str);
            return com.google.android.gms.common.d.n.a(new URI(strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?")), AndroidWebView.UTF_8);
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    public final void beginTransaction() {
        zzch();
        getWritableDatabase().beginTransaction();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        try {
            this.zzwr.close();
        } catch (SQLiteException e) {
            zze("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zze("Error closing database", e2);
        }
    }

    public final void endTransaction() {
        zzch();
        getWritableDatabase().endTransaction();
    }

    final SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzwr.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }

    final boolean isEmpty() {
        return zzcr() == 0;
    }

    public final void setTransactionSuccessful() {
        zzch();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(long j, String str, String str2) {
        bf.id(str);
        bf.id(str2);
        zzch();
        n.zzab();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0L);
    }

    public final void zza(List<Long> list) {
        bf.checkNotNull(list);
        n.zzab();
        zzch();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("hit_id");
        sb.append(" in (");
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                sb.append(")");
                String string = sb.toString();
                try {
                    SQLiteDatabase writableDatabase = getWritableDatabase();
                    zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                    int iDelete = writableDatabase.delete("hits2", string, null);
                    if (iDelete != list.size()) {
                        zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(iDelete), string);
                        return;
                    }
                    return;
                } catch (SQLiteException e) {
                    zze("Error deleting hits", e);
                    throw e;
                }
            }
            Long l = list.get(i2);
            if (l == null || l.longValue() == 0) {
                throw new SQLiteException("Invalid hit id");
            }
            if (i2 > 0) {
                sb.append(",");
            }
            sb.append(l);
            i = i2 + 1;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
    }

    public final void zzc(zzch zzchVar) throws Throwable {
        bf.checkNotNull(zzchVar);
        n.zzab();
        zzch();
        bf.checkNotNull(zzchVar);
        Uri.Builder builder = new Uri.Builder();
        for (Map.Entry<String, String> entry : zzchVar.zzcs().entrySet()) {
            String key = entry.getKey();
            if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key)) {
                builder.appendQueryParameter(key, entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        String str = encodedQuery == null ? "" : encodedQuery;
        if (str.length() > 8192) {
            zzbu().zza(zzchVar, "Hit length exceeds the maximum allowed size");
            return;
        }
        int iIntValue = zzcc.zzyk.get().intValue();
        long jZzcr = zzcr();
        if (jZzcr > iIntValue - 1) {
            List<Long> listZzc = zzc((jZzcr - ((long) iIntValue)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(listZzc.size()));
            zza(listZzc);
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", str);
        contentValues.put("hit_time", Long.valueOf(zzchVar.zzen()));
        contentValues.put("hit_app_id", Integer.valueOf(zzchVar.zzel()));
        contentValues.put("hit_url", zzchVar.zzeo() ? zzbu.zzdz() : zzbu.zzea());
        try {
            long jInsert = writableDatabase.insert("hits2", null, contentValues);
            if (jInsert == -1) {
                zzu("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(jInsert), zzchVar);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    public final int zzcx() {
        n.zzab();
        zzch();
        if (!this.zzws.zzj(86400000L)) {
            return 0;
        }
        this.zzws.start();
        zzq("Deleting stale hits (if any)");
        int iDelete = getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzbt().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(iDelete));
        return iDelete;
    }

    public final long zzcy() {
        n.zzab();
        zzch();
        return zza(zzwq, (String[]) null, 0L);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x00a7  */
    public final List<zzch> zzd(long j) throws Throwable {
        Cursor cursor;
        Cursor cursor2 = null;
        bf.ca(j >= 0);
        n.zzab();
        zzch();
        try {
            try {
                Cursor cursorQuery = getWritableDatabase().query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
                try {
                    ArrayList arrayList = new ArrayList();
                    if (cursorQuery.moveToFirst()) {
                        do {
                            arrayList.add(new zzch(this, zzv(cursorQuery.getString(2)), cursorQuery.getLong(1), zzdd.zzah(cursorQuery.getString(3)), cursorQuery.getLong(0), cursorQuery.getInt(4)));
                        } while (cursorQuery.moveToNext());
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (SQLiteException e) {
                    e = e;
                    cursor = cursorQuery;
                    try {
                        zze("Error loading hits from the database", e);
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        }
    }

    public final void zze(long j) {
        n.zzab();
        zzch();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zza(arrayList);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00c2  */
    public final List<zzaw> zzf(long j) throws Throwable {
        Cursor cursor;
        zzch();
        n.zzab();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor2 = null;
        try {
            try {
                String[] strArr = {"cid", "tid", "adid", "hits_count", NativeProtocol.WEB_DIALOG_PARAMS};
                int iIntValue = zzcc.zzym.get().intValue();
                Cursor cursorQuery = writableDatabase.query("properties", strArr, "app_uid=?", new String[]{AppEventsConstants.EVENT_PARAM_VALUE_NO}, null, null, null, String.valueOf(iIntValue));
                try {
                    ArrayList arrayList = new ArrayList();
                    if (cursorQuery.moveToFirst()) {
                        do {
                            String string = cursorQuery.getString(0);
                            String string2 = cursorQuery.getString(1);
                            boolean z = cursorQuery.getInt(2) != 0;
                            long j2 = cursorQuery.getInt(3);
                            Map<String, String> mapZzw = zzw(cursorQuery.getString(4));
                            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                                zzc("Read property with empty client id or tracker id", string, string2);
                            } else {
                                arrayList.add(new zzaw(0L, string, string2, z, j2, mapZzw));
                            }
                        } while (cursorQuery.moveToNext());
                    }
                    if (arrayList.size() >= iIntValue) {
                        zzt("Sending hits to too many properties. Campaign report might be incorrect");
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (SQLiteException e) {
                    e = e;
                    cursor = cursorQuery;
                    try {
                        zze("Error loading hits from the database", e);
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        }
    }
}
