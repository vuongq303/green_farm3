package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.e.a;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.common.internal.bf;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class zzei extends zzhk {
    private static final String[] zzaeu = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzaev = {FirebaseAnalytics.Param.ORIGIN, "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzaew = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;"};
    private static final String[] zzaex = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzaey = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzaez = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzel zzafa;
    private final zzjp zzafb;

    zzei(zzgl zzglVar) {
        super(zzglVar);
        this.zzafb = new zzjp(zzbt());
        this.zzafa = new zzel(this, getContext(), "google_app_measurement.db");
    }

    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (!cursorRawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long zza(String str, String[] strArr, long j) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    j = cursorRawQuery.getLong(0);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } else if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzgg().zzil().log("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzgg().zzil().log("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzgg().zzil().zzg("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    private static void zza(ContentValues contentValues, String str, Object obj) {
        bf.id(str);
        bf.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else {
            if (!(obj instanceof Double)) {
                throw new IllegalArgumentException("Invalid value type");
            }
            contentValues.put(str, (Double) obj);
        }
    }

    static void zza(zzfg zzfgVar, SQLiteDatabase sQLiteDatabase) {
        if (zzfgVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzfgVar.zzin().log("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzfgVar.zzin().log("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzfgVar.zzin().log("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzfgVar.zzin().log("Failed to turn on database write permission for owner");
    }

    static void zza(zzfg zzfgVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) {
        if (zzfgVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzfgVar, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            if (zzfgVar == null) {
                throw new IllegalArgumentException("Monitor must not be null");
            }
            Set<String> setZzb = zzb(sQLiteDatabase, str);
            for (String str4 : str3.split(",")) {
                if (!setZzb.remove(str4)) {
                    throw new SQLiteException(new StringBuilder(String.valueOf(str).length() + 35 + String.valueOf(str4).length()).append("Table ").append(str).append(" is missing required column: ").append(str4).toString());
                }
            }
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i += 2) {
                    if (!setZzb.remove(strArr[i])) {
                        sQLiteDatabase.execSQL(strArr[i + 1]);
                    }
                }
            }
            if (setZzb.isEmpty()) {
                return;
            }
            zzfgVar.zzin().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", setZzb));
        } catch (SQLiteException e) {
            zzfgVar.zzil().zzg("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004a  */
    private static boolean zza(zzfg zzfgVar, SQLiteDatabase sQLiteDatabase, String str) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        if (zzfgVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        try {
            cursorQuery = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
            try {
                try {
                    boolean zMoveToFirst = cursorQuery.moveToFirst();
                    if (cursorQuery == null) {
                        return zMoveToFirst;
                    }
                    cursorQuery.close();
                    return zMoveToFirst;
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
                zzfgVar.zzin().zze("Error querying for table", str, e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return false;
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
    }

    private final boolean zza(String str, int i, zzjz zzjzVar) {
        zzch();
        zzab();
        bf.id(str);
        bf.checkNotNull(zzjzVar);
        if (TextUtils.isEmpty(zzjzVar.zzarl)) {
            zzgg().zzin().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzfg.zzbh(str), Integer.valueOf(i), String.valueOf(zzjzVar.zzark));
            return false;
        }
        try {
            byte[] bArr = new byte[zzjzVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzjzVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzjzVar.zzark);
            contentValues.put("event_name", zzjzVar.zzarl);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                    zzgg().zzil().zzg("Failed to insert event filter (got -1). appId", zzfg.zzbh(str));
                }
                return true;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Error storing event filter. appId", zzfg.zzbh(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgg().zzil().zze("Configuration loss. Failed to serialize event filter. appId", zzfg.zzbh(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, int i, zzkc zzkcVar) {
        zzch();
        zzab();
        bf.id(str);
        bf.checkNotNull(zzkcVar);
        if (TextUtils.isEmpty(zzkcVar.zzasa)) {
            zzgg().zzin().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzfg.zzbh(str), Integer.valueOf(i), String.valueOf(zzkcVar.zzark));
            return false;
        }
        try {
            byte[] bArr = new byte[zzkcVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzkcVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzkcVar.zzark);
            contentValues.put("property_name", zzkcVar.zzasa);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgg().zzil().zzg("Failed to insert property filter (got -1). appId", zzfg.zzbh(str));
                return false;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Error storing property filter. appId", zzfg.zzbh(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgg().zzil().zze("Configuration loss. Failed to serialize property filter. appId", zzfg.zzbh(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, List<Integer> list) {
        bf.id(str);
        zzch();
        zzab();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            long jZza = zza("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int iMax = Math.max(0, Math.min(2000, zzgi().zzb(str, zzew.zzahq)));
            if (jZza <= iMax) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String strJoin = TextUtils.join(",", arrayList);
            String string = new StringBuilder(String.valueOf(strJoin).length() + 2).append("(").append(strJoin).append(")").toString();
            return writableDatabase.delete("audience_filter_values", new StringBuilder(String.valueOf(string).length() + 140).append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ").append(string).append(" order by rowid desc limit -1 offset ?)").toString(), new String[]{str, Integer.toString(iMax)}) > 0;
        } catch (SQLiteException e) {
            zzgg().zzil().zze("Database error querying filters. appId", zzfg.zzbh(str), e);
            return false;
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(str).length() + 22).append("SELECT * FROM ").append(str).append(" LIMIT 0").toString(), null);
        try {
            Collections.addAll(hashSet, cursorRawQuery.getColumnNames());
            return hashSet;
        } finally {
            cursorRawQuery.close();
        }
    }

    private final boolean zzhv() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    public final void beginTransaction() {
        zzch();
        getWritableDatabase().beginTransaction();
    }

    public final void endTransaction() {
        zzch();
        getWritableDatabase().endTransaction();
    }

    final SQLiteDatabase getWritableDatabase() {
        zzab();
        try {
            return this.zzafa.getWritableDatabase();
        } catch (SQLiteException e) {
            zzgg().zzin().zzg("Error opening database", e);
            throw e;
        }
    }

    public final void setTransactionSuccessful() {
        zzch();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(zzkl zzklVar) throws IOException {
        long jZzc;
        zzab();
        zzch();
        bf.checkNotNull(zzklVar);
        bf.id(zzklVar.zztd);
        try {
            byte[] bArr = new byte[zzklVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzklVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            zzjv zzjvVarZzgc = zzgc();
            bf.checkNotNull(bArr);
            zzjvVarZzgc.zzab();
            MessageDigest messageDigest = zzjv.getMessageDigest("MD5");
            if (messageDigest == null) {
                zzjvVarZzgc.zzgg().zzil().log("Failed to get MD5");
                jZzc = 0;
            } else {
                jZzc = zzjv.zzc(messageDigest.digest(bArr));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzklVar.zztd);
            contentValues.put("metadata_fingerprint", Long.valueOf(jZzc));
            contentValues.put("metadata", bArr);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return jZzc;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Error storing raw event metadata. appId", zzfg.zzbh(zzklVar.zztd), e);
                throw e;
            }
        } catch (IOException e2) {
            zzgg().zzil().zze("Data loss. Failed to serialize event metadata. appId", zzfg.zzbh(zzklVar.zztd), e2);
            throw e2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0092  */
    public final Pair<zzki, Long> zza(String str, Long l) {
        Cursor cursorRawQuery;
        Pair<zzki, Long> pairCreate = null;
        zzab();
        zzch();
        try {
            cursorRawQuery = getWritableDatabase().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
            try {
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        byte[] blob = cursorRawQuery.getBlob(0);
                        Long lValueOf = Long.valueOf(cursorRawQuery.getLong(1));
                        zzaba zzabaVarZza = zzaba.zza(blob, 0, blob.length);
                        zzki zzkiVar = new zzki();
                        try {
                            zzkiVar.zzb(zzabaVarZza);
                            pairCreate = Pair.create(zzkiVar, lValueOf);
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                        } catch (IOException e) {
                            zzgg().zzil().zzd("Failed to merge main event. appId, eventId", zzfg.zzbh(str), l, e);
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                        }
                    } else {
                        zzgg().zzir().log("Main event not found");
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                e = e2;
                zzgg().zzil().zzg("Error selecting main event", e);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursorRawQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursorRawQuery = null;
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
        return pairCreate;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0147  */
    public final zzej zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) throws Throwable {
        Cursor cursorQuery;
        bf.id(str);
        zzab();
        zzch();
        String[] strArr = {str};
        zzej zzejVar = new zzej();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                cursorQuery = writableDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        zzgg().zzin().zzg("Not updating daily counts, app is not known. appId", zzfg.zzbh(str));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return zzejVar;
                    }
                    if (cursorQuery.getLong(0) == j) {
                        zzejVar.zzafd = cursorQuery.getLong(1);
                        zzejVar.zzafc = cursorQuery.getLong(2);
                        zzejVar.zzafe = cursorQuery.getLong(3);
                        zzejVar.zzaff = cursorQuery.getLong(4);
                        zzejVar.zzafg = cursorQuery.getLong(5);
                    }
                    if (z) {
                        zzejVar.zzafd++;
                    }
                    if (z2) {
                        zzejVar.zzafc++;
                    }
                    if (z3) {
                        zzejVar.zzafe++;
                    }
                    if (z4) {
                        zzejVar.zzaff++;
                    }
                    if (z5) {
                        zzejVar.zzafg++;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("day", Long.valueOf(j));
                    contentValues.put("daily_public_events_count", Long.valueOf(zzejVar.zzafc));
                    contentValues.put("daily_events_count", Long.valueOf(zzejVar.zzafd));
                    contentValues.put("daily_conversions_count", Long.valueOf(zzejVar.zzafe));
                    contentValues.put("daily_error_events_count", Long.valueOf(zzejVar.zzaff));
                    contentValues.put("daily_realtime_events_count", Long.valueOf(zzejVar.zzafg));
                    writableDatabase.update("apps", contentValues, "app_id=?", strArr);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return zzejVar;
                } catch (SQLiteException e) {
                    e = e;
                    zzgg().zzil().zze("Error updating daily counts. appId", zzfg.zzbh(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return zzejVar;
                }
            } catch (SQLiteException e2) {
                e = e2;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void zza(zzeb zzebVar) {
        bf.checkNotNull(zzebVar);
        zzab();
        zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzebVar.zzah());
        contentValues.put("app_instance_id", zzebVar.getAppInstanceId());
        contentValues.put("gmp_app_id", zzebVar.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzebVar.zzgk());
        contentValues.put("last_bundle_index", Long.valueOf(zzebVar.zzgs()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzebVar.zzgm()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzebVar.zzgn()));
        contentValues.put("app_version", zzebVar.zzag());
        contentValues.put("app_store", zzebVar.zzgp());
        contentValues.put("gmp_version", Long.valueOf(zzebVar.zzgq()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzebVar.zzgr()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzebVar.isMeasurementEnabled()));
        contentValues.put("day", Long.valueOf(zzebVar.zzgw()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzebVar.zzgx()));
        contentValues.put("daily_events_count", Long.valueOf(zzebVar.zzgy()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzebVar.zzgz()));
        contentValues.put("config_fetched_time", Long.valueOf(zzebVar.zzgt()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzebVar.zzgu()));
        contentValues.put("app_version_int", Long.valueOf(zzebVar.zzgo()));
        contentValues.put("firebase_instance_id", zzebVar.zzgl());
        contentValues.put("daily_error_events_count", Long.valueOf(zzebVar.zzhb()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzebVar.zzha()));
        contentValues.put("health_monitor_sample", zzebVar.zzhc());
        contentValues.put("android_id", Long.valueOf(zzebVar.zzhe()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzebVar.zzhf()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(zzebVar.zzhg()));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzebVar.zzah()}) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzgg().zzil().zzg("Failed to insert/update app (got -1). appId", zzfg.zzbh(zzebVar.zzah()));
            }
        } catch (SQLiteException e) {
            zzgg().zzil().zze("Error storing app. appId", zzfg.zzbh(zzebVar.zzah()), e);
        }
    }

    public final void zza(zzeq zzeqVar) {
        Long l = null;
        bf.checkNotNull(zzeqVar);
        zzab();
        zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzeqVar.zztd);
        contentValues.put("name", zzeqVar.name);
        contentValues.put("lifetime_count", Long.valueOf(zzeqVar.zzafp));
        contentValues.put("current_bundle_count", Long.valueOf(zzeqVar.zzafq));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzeqVar.zzafr));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzeqVar.zzafs));
        contentValues.put("last_sampled_complex_event_id", zzeqVar.zzaft);
        contentValues.put("last_sampling_rate", zzeqVar.zzafu);
        if (zzeqVar.zzafv != null && zzeqVar.zzafv.booleanValue()) {
            l = 1L;
        }
        contentValues.put("last_exempt_from_sampling", l);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzgg().zzil().zzg("Failed to insert/update event aggregates (got -1). appId", zzfg.zzbh(zzeqVar.zztd));
            }
        } catch (SQLiteException e) {
            zzgg().zzil().zze("Error storing event aggregates. appId", zzfg.zzbh(zzeqVar.zztd), e);
        }
    }

    final void zza(String str, zzjy[] zzjyVarArr) {
        boolean z;
        zzch();
        zzab();
        bf.id(str);
        bf.checkNotNull(zzjyVarArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zzch();
            zzab();
            bf.id(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete("property_filters", "app_id=?", new String[]{str});
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzjy zzjyVar : zzjyVarArr) {
                zzch();
                zzab();
                bf.id(str);
                bf.checkNotNull(zzjyVar);
                bf.checkNotNull(zzjyVar.zzari);
                bf.checkNotNull(zzjyVar.zzarh);
                if (zzjyVar.zzarg != null) {
                    int iIntValue = zzjyVar.zzarg.intValue();
                    zzjz[] zzjzVarArr = zzjyVar.zzari;
                    int length = zzjzVarArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            zzkc[] zzkcVarArr = zzjyVar.zzarh;
                            int length2 = zzkcVarArr.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length2) {
                                    zzjz[] zzjzVarArr2 = zzjyVar.zzari;
                                    int length3 = zzjzVarArr2.length;
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= length3) {
                                            z = true;
                                            break;
                                        } else {
                                            if (!zza(str, iIntValue, zzjzVarArr2[i3])) {
                                                z = false;
                                                break;
                                            }
                                            i3++;
                                        }
                                    }
                                    if (z) {
                                        zzkc[] zzkcVarArr2 = zzjyVar.zzarh;
                                        for (zzkc zzkcVar : zzkcVarArr2) {
                                            if (!zza(str, iIntValue, zzkcVar)) {
                                                z = false;
                                                break;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        zzch();
                                        zzab();
                                        bf.id(str);
                                        SQLiteDatabase writableDatabase3 = getWritableDatabase();
                                        writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(iIntValue)});
                                        writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(iIntValue)});
                                        break;
                                    }
                                    break;
                                }
                                if (zzkcVarArr[i2].zzark == null) {
                                    zzgg().zzin().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", zzfg.zzbh(str), zzjyVar.zzarg);
                                    break;
                                }
                                i2++;
                            }
                        } else {
                            if (zzjzVarArr[i].zzark == null) {
                                zzgg().zzin().zze("Event filter with no ID. Audience definition ignored. appId, audienceId", zzfg.zzbh(str), zzjyVar.zzarg);
                                break;
                            }
                            i++;
                        }
                    }
                } else {
                    zzgg().zzin().zzg("Audience with no ID. appId", zzfg.zzbh(str));
                }
            }
            ArrayList arrayList = new ArrayList();
            for (zzjy zzjyVar2 : zzjyVarArr) {
                arrayList.add(zzjyVar2.zzarg);
            }
            zza(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final boolean zza(zzef zzefVar) {
        bf.checkNotNull(zzefVar);
        zzab();
        zzch();
        if (zzg(zzefVar.packageName, zzefVar.zzael.name) == null && zza("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzefVar.packageName}) >= 1000) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzefVar.packageName);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzefVar.zzaek);
        contentValues.put("name", zzefVar.zzael.name);
        zza(contentValues, FirebaseAnalytics.Param.VALUE, zzefVar.zzael.getValue());
        contentValues.put("active", Boolean.valueOf(zzefVar.zzaen));
        contentValues.put("trigger_event_name", zzefVar.zzaeo);
        contentValues.put("trigger_timeout", Long.valueOf(zzefVar.zzaeq));
        zzgc();
        contentValues.put("timed_out_event", zzjv.zza(zzefVar.zzaep));
        contentValues.put("creation_timestamp", Long.valueOf(zzefVar.zzaem));
        zzgc();
        contentValues.put("triggered_event", zzjv.zza(zzefVar.zzaer));
        contentValues.put("triggered_timestamp", Long.valueOf(zzefVar.zzael.zzaqu));
        contentValues.put("time_to_live", Long.valueOf(zzefVar.zzaes));
        zzgc();
        contentValues.put("expired_event", zzjv.zza(zzefVar.zzaet));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzgg().zzil().zzg("Failed to insert/update conditional user property (got -1)", zzfg.zzbh(zzefVar.packageName));
            }
        } catch (SQLiteException e) {
            zzgg().zzil().zze("Error storing conditional user property", zzfg.zzbh(zzefVar.packageName), e);
        }
        return true;
    }

    public final boolean zza(zzep zzepVar, long j, boolean z) {
        zzab();
        zzch();
        bf.checkNotNull(zzepVar);
        bf.id(zzepVar.zztd);
        zzki zzkiVar = new zzki();
        zzkiVar.zzasx = Long.valueOf(zzepVar.zzafn);
        zzkiVar.zzasv = new zzkj[zzepVar.zzafo.size()];
        int i = 0;
        for (String str : zzepVar.zzafo) {
            zzkj zzkjVar = new zzkj();
            zzkiVar.zzasv[i] = zzkjVar;
            zzkjVar.name = str;
            zzgc().zza(zzkjVar, zzepVar.zzafo.get(str));
            i++;
        }
        try {
            byte[] bArr = new byte[zzkiVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzkiVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            zzgg().zzir().zze("Saving event, name, data size", zzgb().zzbe(zzepVar.name), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzepVar.zztd);
            contentValues.put("name", zzepVar.name);
            contentValues.put("timestamp", Long.valueOf(zzepVar.timestamp));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                    return true;
                }
                zzgg().zzil().zzg("Failed to insert raw event (got -1). appId", zzfg.zzbh(zzepVar.zztd));
                return false;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Error storing raw event. appId", zzfg.zzbh(zzepVar.zztd), e);
                return false;
            }
        } catch (IOException e2) {
            zzgg().zzil().zze("Data loss. Failed to serialize event params/data. appId", zzfg.zzbh(zzepVar.zztd), e2);
            return false;
        }
    }

    public final boolean zza(zzju zzjuVar) {
        bf.checkNotNull(zzjuVar);
        zzab();
        zzch();
        if (zzg(zzjuVar.zztd, zzjuVar.name) == null) {
            if (zzjv.zzbv(zzjuVar.name)) {
                if (zza("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzjuVar.zztd}) >= 25) {
                    return false;
                }
            } else if (zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzjuVar.zztd, zzjuVar.zzaek}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzjuVar.zztd);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzjuVar.zzaek);
        contentValues.put("name", zzjuVar.name);
        contentValues.put("set_timestamp", Long.valueOf(zzjuVar.zzaqu));
        zza(contentValues, FirebaseAnalytics.Param.VALUE, zzjuVar.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzgg().zzil().zzg("Failed to insert/update user property (got -1). appId", zzfg.zzbh(zzjuVar.zztd));
            }
        } catch (SQLiteException e) {
            zzgg().zzil().zze("Error storing user property. appId", zzfg.zzbh(zzjuVar.zztd), e);
        }
        return true;
    }

    public final boolean zza(zzkl zzklVar, boolean z) {
        zzab();
        zzch();
        bf.checkNotNull(zzklVar);
        bf.id(zzklVar.zztd);
        bf.checkNotNull(zzklVar.zzath);
        zzhp();
        long jCurrentTimeMillis = zzbt().currentTimeMillis();
        if (zzklVar.zzath.longValue() < jCurrentTimeMillis - zzeh.zzhj() || zzklVar.zzath.longValue() > zzeh.zzhj() + jCurrentTimeMillis) {
            zzgg().zzin().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzfg.zzbh(zzklVar.zztd), Long.valueOf(jCurrentTimeMillis), zzklVar.zzath);
        }
        try {
            byte[] bArr = new byte[zzklVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzklVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            byte[] bArrZza = zzgc().zza(bArr);
            zzgg().zzir().zzg("Saving bundle, size", Integer.valueOf(bArrZza.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzklVar.zztd);
            contentValues.put("bundle_end_timestamp", zzklVar.zzath);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArrZza);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            if (zzklVar.zzaue != null) {
                contentValues.put("retry_count", zzklVar.zzaue);
            }
            try {
                if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzgg().zzil().zzg("Failed to insert bundle (got -1). appId", zzfg.zzbh(zzklVar.zztd));
                return false;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Error storing bundle. appId", zzfg.zzbh(zzklVar.zztd), e);
                return false;
            }
        } catch (IOException e2) {
            zzgg().zzil().zze("Data loss. Failed to serialize bundle. appId", zzfg.zzbh(zzklVar.zztd), e2);
            return false;
        }
    }

    public final boolean zza(String str, Long l, long j, zzki zzkiVar) {
        zzab();
        zzch();
        bf.checkNotNull(zzkiVar);
        bf.id(str);
        bf.checkNotNull(l);
        try {
            byte[] bArr = new byte[zzkiVar.zzwg()];
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, bArr.length);
            zzkiVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
            zzgg().zzir().zze("Saving complex main event, appId, data size", zzgb().zzbe(str), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("event_id", l);
            contentValues.put("children_to_process", Long.valueOf(j));
            contentValues.put("main_event", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgg().zzil().zzg("Failed to insert complex main event (got -1). appId", zzfg.zzbh(str));
                return false;
            } catch (SQLiteException e) {
                zzgg().zzil().zze("Error storing complex main event. appId", zzfg.zzbh(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgg().zzil().zzd("Data loss. Failed to serialize event params/data. appId, eventId", zzfg.zzbh(str), l, e2);
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x005b  */
    public final String zzab(long j) throws Throwable {
        Cursor cursorRawQuery;
        String string = null;
        zzab();
        zzch();
        try {
            cursorRawQuery = getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j)});
            try {
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        string = cursorRawQuery.getString(0);
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                    } else {
                        zzgg().zzir().log("No expired configs for apps with pending events");
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e) {
                e = e;
                zzgg().zzil().zzg("Error selecting expired configs", e);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorRawQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursorRawQuery = null;
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
        return string;
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00b5  */
    public final List<zzju> zzaw(String str) throws Throwable {
        Cursor cursor;
        Cursor cursor2 = null;
        bf.id(str);
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList();
        try {
            Cursor cursorQuery = getWritableDatabase().query("user_attributes", new String[]{"name", FirebaseAnalytics.Param.ORIGIN, "set_timestamp", FirebaseAnalytics.Param.VALUE}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                }
                do {
                    String string = cursorQuery.getString(0);
                    String string2 = cursorQuery.getString(1);
                    if (string2 == null) {
                        string2 = "";
                    }
                    long j = cursorQuery.getLong(2);
                    Object objZza = zza(cursorQuery, 3);
                    if (objZza == null) {
                        zzgg().zzil().zzg("Read invalid user property value, ignoring it. appId", zzfg.zzbh(str));
                    } else {
                        arrayList.add(new zzju(str, string2, string, j, objZza));
                    }
                } while (cursorQuery.moveToNext());
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (SQLiteException e) {
                e = e;
                cursor = cursorQuery;
                try {
                    zzgg().zzil().zze("Error querying user properties. appId", zzfg.zzbh(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursorQuery;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:52:0x023e  */
    public final zzeb zzax(String str) {
        Cursor cursorQuery;
        Cursor cursor = null;
        bf.id(str);
        zzab();
        zzch();
        try {
            try {
                cursorQuery = getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled", "ssaid_reporting_enabled"}, "app_id=?", new String[]{str}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    zzeb zzebVar = new zzeb(this.zzacr, str);
                    zzebVar.zzal(cursorQuery.getString(0));
                    zzebVar.zzam(cursorQuery.getString(1));
                    zzebVar.zzan(cursorQuery.getString(2));
                    zzebVar.zzr(cursorQuery.getLong(3));
                    zzebVar.zzm(cursorQuery.getLong(4));
                    zzebVar.zzn(cursorQuery.getLong(5));
                    zzebVar.setAppVersion(cursorQuery.getString(6));
                    zzebVar.zzap(cursorQuery.getString(7));
                    zzebVar.zzp(cursorQuery.getLong(8));
                    zzebVar.zzq(cursorQuery.getLong(9));
                    zzebVar.setMeasurementEnabled(cursorQuery.isNull(10) || cursorQuery.getInt(10) != 0);
                    zzebVar.zzu(cursorQuery.getLong(11));
                    zzebVar.zzv(cursorQuery.getLong(12));
                    zzebVar.zzw(cursorQuery.getLong(13));
                    zzebVar.zzx(cursorQuery.getLong(14));
                    zzebVar.zzs(cursorQuery.getLong(15));
                    zzebVar.zzt(cursorQuery.getLong(16));
                    zzebVar.zzo(cursorQuery.isNull(17) ? -2147483648L : cursorQuery.getInt(17));
                    zzebVar.zzao(cursorQuery.getString(18));
                    zzebVar.zzz(cursorQuery.getLong(19));
                    zzebVar.zzy(cursorQuery.getLong(20));
                    zzebVar.zzaq(cursorQuery.getString(21));
                    zzebVar.zzaa(cursorQuery.isNull(22) ? 0L : cursorQuery.getLong(22));
                    zzebVar.zzd(cursorQuery.isNull(23) || cursorQuery.getInt(23) != 0);
                    zzebVar.zze(cursorQuery.isNull(24) || cursorQuery.getInt(24) != 0);
                    zzebVar.zzgj();
                    if (cursorQuery.moveToNext()) {
                        zzgg().zzil().zzg("Got multiple records for app, expected one. appId", zzfg.zzbh(str));
                    }
                    if (cursorQuery == null) {
                        return zzebVar;
                    }
                    cursorQuery.close();
                    return zzebVar;
                } catch (SQLiteException e) {
                    e = e;
                    zzgg().zzil().zze("Error querying app. appId", zzfg.zzbh(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (SQLiteException e2) {
                e = e2;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final long zzay(String str) {
        bf.id(str);
        zzab();
        zzch();
        try {
            return getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, zzgi().zzb(str, zzew.zzaha))))});
        } catch (SQLiteException e) {
            zzgg().zzil().zze("Error deleting over the limit events. appId", zzfg.zzbh(str), e);
            return 0L;
        }
    }

    public final byte[] zzaz(String str) throws Throwable {
        Cursor cursorQuery;
        bf.id(str);
        zzab();
        zzch();
        try {
            cursorQuery = getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    byte[] blob = cursorQuery.getBlob(0);
                    if (cursorQuery.moveToNext()) {
                        zzgg().zzil().zzg("Got multiple records for app config, expected one. appId", zzfg.zzbh(str));
                    }
                    if (cursorQuery == null) {
                        return blob;
                    }
                    cursorQuery.close();
                    return blob;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (SQLiteException e) {
                e = e;
                zzgg().zzil().zze("Error querying remote config. appId", zzfg.zzbh(str), e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
        th = th;
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        throw th;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x0107  */
    public final List<Pair<zzkl, Long>> zzb(String str, int i, int i2) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor;
        List<Pair<zzkl, Long>> listEmptyList;
        int length;
        zzab();
        zzch();
        bf.ca(i > 0);
        bf.ca(i2 > 0);
        bf.id(str);
        try {
            cursorQuery = getWritableDatabase().query("queue", new String[]{"rowid", ShareConstants.WEB_DIALOG_PARAM_DATA, "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
            try {
                if (cursorQuery.moveToFirst()) {
                    listEmptyList = new ArrayList<>();
                    int i3 = 0;
                    while (true) {
                        long j = cursorQuery.getLong(0);
                        try {
                            byte[] bArrZzb = zzgc().zzb(cursorQuery.getBlob(1));
                            if (!listEmptyList.isEmpty() && bArrZzb.length + i3 > i2) {
                                break;
                            }
                            zzaba zzabaVarZza = zzaba.zza(bArrZzb, 0, bArrZzb.length);
                            zzkl zzklVar = new zzkl();
                            try {
                                zzklVar.zzb(zzabaVarZza);
                                if (!cursorQuery.isNull(2)) {
                                    zzklVar.zzaue = Integer.valueOf(cursorQuery.getInt(2));
                                }
                                length = bArrZzb.length + i3;
                                listEmptyList.add(Pair.create(zzklVar, Long.valueOf(j)));
                            } catch (IOException e) {
                                zzgg().zzil().zze("Failed to merge queued bundle. appId", zzfg.zzbh(str), e);
                                length = i3;
                            }
                            if (!cursorQuery.moveToNext() || length > i2) {
                                break;
                            }
                            i3 = length;
                        } catch (IOException e2) {
                            zzgg().zzil().zze("Failed to unzip queued bundle. appId", zzfg.zzbh(str), e2);
                            length = i3;
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } else {
                    listEmptyList = Collections.emptyList();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                cursor = cursorQuery;
                try {
                    zzgg().zzil().zze("Error querying bundles. appId", zzfg.zzbh(str), e);
                    listEmptyList = Collections.emptyList();
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    cursorQuery = cursor;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
        return listEmptyList;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0110  */
    public final List<zzju> zzb(String str, String str2, String str3) throws Throwable {
        Cursor cursor;
        Cursor cursor2 = null;
        bf.id(str);
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList arrayList2 = new ArrayList(3);
            arrayList2.add(str);
            StringBuilder sb = new StringBuilder("app_id=?");
            if (!TextUtils.isEmpty(str2)) {
                arrayList2.add(str2);
                sb.append(" and origin=?");
            }
            if (!TextUtils.isEmpty(str3)) {
                arrayList2.add(String.valueOf(str3).concat("*"));
                sb.append(" and name glob ?");
            }
            Cursor cursorQuery = getWritableDatabase().query("user_attributes", new String[]{"name", "set_timestamp", FirebaseAnalytics.Param.VALUE, FirebaseAnalytics.Param.ORIGIN}, sb.toString(), (String[]) arrayList2.toArray(new String[arrayList2.size()]), null, null, "rowid", NativeContentAd.ASSET_HEADLINE);
            try {
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return arrayList;
                    }
                    while (true) {
                        if (arrayList.size() >= 1000) {
                            zzgg().zzil().zzg("Read more than the max allowed user properties, ignoring excess", Integer.valueOf(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS));
                            break;
                        }
                        String string = cursorQuery.getString(0);
                        long j = cursorQuery.getLong(1);
                        Object objZza = zza(cursorQuery, 2);
                        String string2 = cursorQuery.getString(3);
                        if (objZza == null) {
                            try {
                                zzgg().zzil().zzd("(2)Read invalid user property value, ignoring it", zzfg.zzbh(str), string2, str3);
                            } catch (SQLiteException e) {
                                e = e;
                                cursor = cursorQuery;
                                str2 = string2;
                                try {
                                    zzgg().zzil().zzd("(2)Error querying user properties", zzfg.zzbh(str), str2, e);
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    return null;
                                } catch (Throwable th) {
                                    th = th;
                                    cursor2 = cursor;
                                    if (cursor2 != null) {
                                        cursor2.close();
                                    }
                                    throw th;
                                }
                            }
                        } else {
                            arrayList.add(new zzju(str, string2, string, j, objZza));
                        }
                        if (!cursorQuery.moveToNext()) {
                            break;
                        }
                        str2 = string2;
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (SQLiteException e2) {
                    e = e2;
                    cursor = cursorQuery;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursorQuery;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0177  */
    public final List<zzef> zzb(String str, String[] strArr) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor;
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList();
        try {
            cursorQuery = getWritableDatabase().query("conditional_properties", new String[]{"app_id", FirebaseAnalytics.Param.ORIGIN, "name", FirebaseAnalytics.Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", NativeContentAd.ASSET_HEADLINE);
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                }
                do {
                    if (arrayList.size() >= 1000) {
                        zzgg().zzil().zzg("Read more than the max allowed conditional properties, ignoring extra", Integer.valueOf(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS));
                        break;
                    }
                    String string = cursorQuery.getString(0);
                    String string2 = cursorQuery.getString(1);
                    String string3 = cursorQuery.getString(2);
                    Object objZza = zza(cursorQuery, 3);
                    boolean z = cursorQuery.getInt(4) != 0;
                    arrayList.add(new zzef(string, string2, new zzjs(string3, cursorQuery.getLong(10), objZza, string2), cursorQuery.getLong(8), z, cursorQuery.getString(5), (zzeu) zzgc().zza(cursorQuery.getBlob(7), zzeu.CREATOR), cursorQuery.getLong(6), (zzeu) zzgc().zza(cursorQuery.getBlob(9), zzeu.CREATOR), cursorQuery.getLong(11), (zzeu) zzgc().zza(cursorQuery.getBlob(12), zzeu.CREATOR)));
                } while (cursorQuery.moveToNext());
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (SQLiteException e) {
                e = e;
                cursor = cursorQuery;
                try {
                    zzgg().zzil().zzg("Error querying conditional user property value", e);
                    List<zzef> listEmptyList = Collections.emptyList();
                    if (cursor == null) {
                        return listEmptyList;
                    }
                    cursor.close();
                    return listEmptyList;
                } catch (Throwable th) {
                    th = th;
                    cursorQuery = cursor;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00a3  */
    final Map<Integer, zzkm> zzba(String str) {
        Cursor cursorQuery;
        Cursor cursor = null;
        zzch();
        zzab();
        bf.id(str);
        try {
            try {
                cursorQuery = getWritableDatabase().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                    a aVar = new a();
                    do {
                        int i = cursorQuery.getInt(0);
                        byte[] blob = cursorQuery.getBlob(1);
                        zzaba zzabaVarZza = zzaba.zza(blob, 0, blob.length);
                        zzkm zzkmVar = new zzkm();
                        try {
                            zzkmVar.zzb(zzabaVarZza);
                            aVar.put(Integer.valueOf(i), zzkmVar);
                        } catch (IOException e) {
                            zzgg().zzil().zzd("Failed to merge filter results. appId, audienceId, error", zzfg.zzbh(str), Integer.valueOf(i), e);
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery == null) {
                        return aVar;
                    }
                    cursorQuery.close();
                    return aVar;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgg().zzil().zze("Database error querying filter results. appId", zzfg.zzbh(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (SQLiteException e3) {
                e = e3;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final long zzbb(String str) {
        bf.id(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    public final List<zzef> zzc(String str, String str2, String str3) {
        bf.id(str);
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzb(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    final void zzc(List<Long> list) {
        zzab();
        zzch();
        bf.checkNotNull(list);
        bf.nP(list.size());
        if (zzhv()) {
            String strJoin = TextUtils.join(",", list);
            String string = new StringBuilder(String.valueOf(strJoin).length() + 2).append("(").append(strJoin).append(")").toString();
            if (zza(new StringBuilder(String.valueOf(string).length() + 80).append("SELECT COUNT(1) FROM queue WHERE rowid IN ").append(string).append(" AND retry_count =  2147483647 LIMIT 1").toString(), (String[]) null) > 0) {
                zzgg().zzin().log("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                getWritableDatabase().execSQL(new StringBuilder(String.valueOf(string).length() + 127).append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ").append(string).append(" AND (retry_count IS NULL OR retry_count < 2147483647)").toString());
            } catch (SQLiteException e) {
                zzgg().zzil().zzg("Error incrementing retry count. error", e);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0126  */
    public final zzeq zze(String str, String str2) {
        Cursor cursorQuery;
        Cursor cursor;
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        try {
            cursorQuery = getWritableDatabase().query("events", new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                long j = cursorQuery.getLong(0);
                long j2 = cursorQuery.getLong(1);
                long j3 = cursorQuery.getLong(2);
                long j4 = cursorQuery.isNull(3) ? 0L : cursorQuery.getLong(3);
                Long lValueOf = cursorQuery.isNull(4) ? null : Long.valueOf(cursorQuery.getLong(4));
                Long lValueOf2 = cursorQuery.isNull(5) ? null : Long.valueOf(cursorQuery.getLong(5));
                Boolean boolValueOf = null;
                if (!cursorQuery.isNull(6)) {
                    boolValueOf = Boolean.valueOf(cursorQuery.getLong(6) == 1);
                }
                zzeq zzeqVar = new zzeq(str, str2, j, j2, j3, j4, lValueOf, lValueOf2, boolValueOf);
                if (cursorQuery.moveToNext()) {
                    zzgg().zzil().zzg("Got multiple records for event aggregates, expected one. appId", zzfg.zzbh(str));
                }
                if (cursorQuery == null) {
                    return zzeqVar;
                }
                cursorQuery.close();
                return zzeqVar;
            } catch (SQLiteException e) {
                e = e;
                cursor = cursorQuery;
                try {
                    zzgg().zzil().zzd("Error querying events. appId", zzfg.zzbh(str), zzgb().zzbe(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    cursorQuery = cursor;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
    }

    public final void zzf(String str, String str2) {
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        try {
            zzgg().zzir().zzg("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzgg().zzil().zzd("Error deleting user attribute. appId", zzfg.zzbh(str), zzgb().zzbg(str2), e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00a3  */
    public final zzju zzg(String str, String str2) {
        Cursor cursor;
        Cursor cursor2 = null;
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        try {
            Cursor cursorQuery = getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", FirebaseAnalytics.Param.VALUE, FirebaseAnalytics.Param.ORIGIN}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                zzju zzjuVar = new zzju(str, cursorQuery.getString(2), str2, cursorQuery.getLong(0), zza(cursorQuery, 1));
                if (cursorQuery.moveToNext()) {
                    zzgg().zzil().zzg("Got multiple records for user property, expected one. appId", zzfg.zzbh(str));
                }
                if (cursorQuery == null) {
                    return zzjuVar;
                }
                cursorQuery.close();
                return zzjuVar;
            } catch (SQLiteException e) {
                e = e;
                cursor = cursorQuery;
                try {
                    zzgg().zzil().zzd("Error querying user property. appId", zzfg.zzbh(str), zzgb().zzbg(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursorQuery;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x015c  */
    public final zzef zzh(String str, String str2) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor;
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        try {
            cursorQuery = getWritableDatabase().query("conditional_properties", new String[]{FirebaseAnalytics.Param.ORIGIN, FirebaseAnalytics.Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                String string = cursorQuery.getString(0);
                Object objZza = zza(cursorQuery, 1);
                boolean z = cursorQuery.getInt(2) != 0;
                zzef zzefVar = new zzef(str, string, new zzjs(str2, cursorQuery.getLong(8), objZza, string), cursorQuery.getLong(6), z, cursorQuery.getString(3), (zzeu) zzgc().zza(cursorQuery.getBlob(5), zzeu.CREATOR), cursorQuery.getLong(4), (zzeu) zzgc().zza(cursorQuery.getBlob(7), zzeu.CREATOR), cursorQuery.getLong(9), (zzeu) zzgc().zza(cursorQuery.getBlob(10), zzeu.CREATOR));
                if (cursorQuery.moveToNext()) {
                    zzgg().zzil().zze("Got multiple records for conditional property, expected one", zzfg.zzbh(str), zzgb().zzbg(str2));
                }
                if (cursorQuery == null) {
                    return zzefVar;
                }
                cursorQuery.close();
                return zzefVar;
            } catch (SQLiteException e) {
                e = e;
                cursor = cursorQuery;
                try {
                    zzgg().zzil().zzd("Error querying conditional property", zzfg.zzbh(str), zzgb().zzbg(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    cursorQuery = cursor;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x003e  */
    public final String zzhn() throws Throwable {
        Cursor cursorRawQuery;
        String string = null;
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        string = cursorRawQuery.getString(0);
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                    } else if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (SQLiteException e) {
                    e = e;
                    zzgg().zzil().zzg("Database error getting next bundle app id", e);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                }
            } catch (SQLiteException e2) {
                e = e2;
                cursorRawQuery = null;
            } catch (Throwable th) {
                th = th;
                cursorRawQuery = null;
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                throw th;
            }
            return string;
        } catch (Throwable th2) {
            th = th2;
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    public final boolean zzho() {
        return zza("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    final void zzhp() {
        int iDelete;
        zzab();
        zzch();
        if (zzhv()) {
            long j = zzgh().zzajw.get();
            long jElapsedRealtime = zzbt().elapsedRealtime();
            if (Math.abs(jElapsedRealtime - j) > zzew.zzahj.get().longValue()) {
                zzgh().zzajw.set(jElapsedRealtime);
                zzab();
                zzch();
                if (!zzhv() || (iDelete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzbt().currentTimeMillis()), String.valueOf(zzeh.zzhj())})) <= 0) {
                    return;
                }
                zzgg().zzir().zzg("Deleted stale rows. rowsDeleted", Integer.valueOf(iDelete));
            }
        }
    }

    public final long zzhq() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0L);
    }

    public final long zzhr() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0L);
    }

    public final boolean zzhs() {
        return zza("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzht() {
        return zza("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    public final long zzhu() {
        long j = -1;
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            if (cursorRawQuery.moveToFirst()) {
                j = cursorRawQuery.getLong(0);
            }
        } catch (SQLiteException e) {
            zzgg().zzil().zzg("Error querying raw events", e);
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
        return j;
    }

    public final int zzi(String str, String str2) {
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzgg().zzil().zzd("Error deleting conditional property", zzfg.zzbh(str), zzgb().zzbg(str2), e);
            return 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00bd  */
    final Map<Integer, List<zzjz>> zzj(String str, String str2) {
        Cursor cursorQuery;
        Cursor cursor = null;
        zzch();
        zzab();
        bf.id(str);
        bf.id(str2);
        a aVar = new a();
        try {
            try {
                cursorQuery = getWritableDatabase().query("event_filters", new String[]{"audience_id", ShareConstants.WEB_DIALOG_PARAM_DATA}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        Map<Integer, List<zzjz>> mapEmptyMap = Collections.emptyMap();
                        if (cursorQuery == null) {
                            return mapEmptyMap;
                        }
                        cursorQuery.close();
                        return mapEmptyMap;
                    }
                    do {
                        byte[] blob = cursorQuery.getBlob(1);
                        zzaba zzabaVarZza = zzaba.zza(blob, 0, blob.length);
                        zzjz zzjzVar = new zzjz();
                        try {
                            zzjzVar.zzb(zzabaVarZza);
                            int i = cursorQuery.getInt(0);
                            List arrayList = (List) aVar.get(Integer.valueOf(i));
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                aVar.put(Integer.valueOf(i), arrayList);
                            }
                            arrayList.add(zzjzVar);
                        } catch (IOException e) {
                            zzgg().zzil().zze("Failed to merge filter. appId", zzfg.zzbh(str), e);
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return aVar;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgg().zzil().zze("Database error querying filters. appId", zzfg.zzbh(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (SQLiteException e3) {
                e = e3;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00bd  */
    final Map<Integer, List<zzkc>> zzk(String str, String str2) {
        Cursor cursorQuery;
        Cursor cursor = null;
        zzch();
        zzab();
        bf.id(str);
        bf.id(str2);
        a aVar = new a();
        try {
            try {
                cursorQuery = getWritableDatabase().query("property_filters", new String[]{"audience_id", ShareConstants.WEB_DIALOG_PARAM_DATA}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursorQuery.moveToFirst()) {
                        Map<Integer, List<zzkc>> mapEmptyMap = Collections.emptyMap();
                        if (cursorQuery == null) {
                            return mapEmptyMap;
                        }
                        cursorQuery.close();
                        return mapEmptyMap;
                    }
                    do {
                        byte[] blob = cursorQuery.getBlob(1);
                        zzaba zzabaVarZza = zzaba.zza(blob, 0, blob.length);
                        zzkc zzkcVar = new zzkc();
                        try {
                            zzkcVar.zzb(zzabaVarZza);
                            int i = cursorQuery.getInt(0);
                            List arrayList = (List) aVar.get(Integer.valueOf(i));
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                aVar.put(Integer.valueOf(i), arrayList);
                            }
                            arrayList.add(zzkcVar);
                        } catch (IOException e) {
                            zzgg().zzil().zze("Failed to merge filter", zzfg.zzbh(str), e);
                        }
                    } while (cursorQuery.moveToNext());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return aVar;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgg().zzil().zze("Database error querying filters. appId", zzfg.zzbh(str), e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            } catch (SQLiteException e3) {
                e = e3;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x00bc A[Catch: all -> 0x00f2, SQLiteException -> 0x00f7, TRY_LEAVE, TryCatch #0 {SQLiteException -> 0x00f7, blocks: (B:11:0x0092, B:13:0x00bc, B:15:0x00d3), top: B:27:0x0092 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x00d3 A[Catch: all -> 0x00f2, SQLiteException -> 0x00f7, TRY_ENTER, TRY_LEAVE, TryCatch #0 {SQLiteException -> 0x00f7, blocks: (B:11:0x0092, B:13:0x00bc, B:15:0x00d3), top: B:27:0x0092 }] */
    protected final long zzl(String str, String str2) {
        long jZza;
        ContentValues contentValues;
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            try {
                jZza = zza(new StringBuilder(String.valueOf(str2).length() + 32).append("select ").append(str2).append(" from app2 where app_id=?").toString(), new String[]{str}, -1L);
                if (jZza == -1) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("app_id", str);
                    contentValues2.put("first_open_count", (Integer) 0);
                    contentValues2.put("previous_install_count", (Integer) 0);
                    if (writableDatabase.insertWithOnConflict("app2", null, contentValues2, 5) == -1) {
                        zzgg().zzil().zze("Failed to insert column (got -1). appId", zzfg.zzbh(str), str2);
                        writableDatabase.endTransaction();
                        jZza = -1;
                    } else {
                        jZza = 0;
                        try {
                            contentValues = new ContentValues();
                            contentValues.put("app_id", str);
                            contentValues.put(str2, Long.valueOf(1 + jZza));
                            if (writableDatabase.update("app2", contentValues, "app_id = ?", new String[]{str}) == 0) {
                                zzgg().zzil().zze("Failed to update column (got 0). appId", zzfg.zzbh(str), str2);
                                writableDatabase.endTransaction();
                                jZza = -1;
                            } else {
                                writableDatabase.setTransactionSuccessful();
                                writableDatabase.endTransaction();
                            }
                        } catch (SQLiteException e) {
                            e = e;
                            zzgg().zzil().zzd("Error inserting column. appId", zzfg.zzbh(str), str2, e);
                            writableDatabase.endTransaction();
                        }
                    }
                } else {
                    contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put(str2, Long.valueOf(1 + jZza));
                    if (writableDatabase.update("app2", contentValues, "app_id = ?", new String[]{str}) == 0) {
                        zzgg().zzil().zze("Failed to update column (got 0). appId", zzfg.zzbh(str), str2);
                        writableDatabase.endTransaction();
                        jZza = -1;
                    } else {
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                    }
                }
            } catch (SQLiteException e2) {
                e = e2;
                jZza = 0;
            }
            return jZza;
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }
}
