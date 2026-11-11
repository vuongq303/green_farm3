package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzfc extends zzhk {
    private final zzfd zzaig;
    private boolean zzaih;

    zzfc(zzgl zzglVar) {
        super(zzglVar);
        this.zzaig = new zzfd(this, getContext(), "google_app_measurement_local.db");
    }

    private final SQLiteDatabase getWritableDatabase() {
        if (this.zzaih) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzaig.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzaih = true;
        return null;
    }

    private final boolean zza(int i, byte[] bArr) {
        zzab();
        if (this.zzaih) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", Integer.valueOf(i));
        contentValues.put("entry", bArr);
        int i2 = 5;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= 5) {
                zzgg().zzin().log("Failed to write entry to local database");
                return false;
            }
            SQLiteDatabase sQLiteDatabase = null;
            Cursor cursor = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = getWritableDatabase();
                    if (writableDatabase == null) {
                        this.zzaih = true;
                        if (writableDatabase != null) {
                            writableDatabase.close();
                        }
                        return false;
                    }
                    writableDatabase.beginTransaction();
                    long j = 0;
                    Cursor cursorRawQuery = writableDatabase.rawQuery("select count(1) from messages", null);
                    if (cursorRawQuery != null && cursorRawQuery.moveToFirst()) {
                        j = cursorRawQuery.getLong(0);
                    }
                    if (j >= 100000) {
                        zzgg().zzil().log("Data loss, local db full");
                        long j2 = (100000 - j) + 1;
                        long jDelete = writableDatabase.delete("messages", "rowid in (select rowid from messages order by rowid asc limit ?)", new String[]{Long.toString(j2)});
                        if (jDelete != j2) {
                            zzgg().zzil().zzd("Different delete count than expected in local db. expected, received, difference", Long.valueOf(j2), Long.valueOf(jDelete), Long.valueOf(j2 - jDelete));
                        }
                    }
                    writableDatabase.insertOrThrow("messages", null, contentValues);
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    return true;
                } catch (SQLiteDatabaseLockedException e) {
                    SystemClock.sleep(i2);
                    i2 += 20;
                    if (0 != 0) {
                        cursor.close();
                    }
                    if (0 != 0) {
                        sQLiteDatabase.close();
                    }
                } catch (SQLiteFullException e2) {
                    try {
                        zzgg().zzil().zzg("Error writing entry to local database", e2);
                        this.zzaih = true;
                        if (0 != 0) {
                            cursor.close();
                        }
                        if (0 != 0) {
                            sQLiteDatabase.close();
                        }
                        i3 = i4 + 1;
                    } catch (Throwable th) {
                        if (0 != 0) {
                            cursor.close();
                        }
                        if (0 != 0) {
                            sQLiteDatabase.close();
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                if (0 != 0 && sQLiteDatabase.inTransaction()) {
                    sQLiteDatabase.endTransaction();
                }
                zzgg().zzil().zzg("Error writing entry to local database", e3);
                this.zzaih = true;
                if (0 != 0) {
                    cursor.close();
                }
                if (0 != 0) {
                    sQLiteDatabase.close();
                }
            }
            i3 = i4 + 1;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void resetAnalyticsData() {
        zzab();
        try {
            int iDelete = getWritableDatabase().delete("messages", null, null) + 0;
            if (iDelete > 0) {
                zzgg().zzir().zzg("Reset local analytics data. records", Integer.valueOf(iDelete));
            }
        } catch (SQLiteException e) {
            zzgg().zzil().zzg("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zza(zzeu zzeuVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzeuVar.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zza(0, bArrMarshall);
        }
        zzgg().zzin().log("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzjs zzjsVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzjsVar.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zza(1, bArrMarshall);
        }
        zzgg().zzin().log("User property too long for local database. Sending directly to service");
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    public final boolean zzc(zzef zzefVar) {
        zzgc();
        byte[] bArrZza = zzjv.zza(zzefVar);
        if (bArrZza.length <= 131072) {
            return zza(2, bArrZza);
        }
        zzgg().zzin().log("Conditional user property too long for local database. Sending directly to service");
        return false;
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

    /* JADX INFO: Removed unreachable split cross block B:143:0x0008 */
    /* JADX WARN: Code duplicated, block: B:134:0x0221  */
    /* JADX WARN: Code duplicated, block: B:80:0x013c  */
    /* JADX WARN: Code duplicated, block: B:82:0x0141  */
    public final List<AbstractSafeParcelable> zzp(int i) throws Throwable {
        Throwable th;
        Cursor cursorQuery;
        SQLiteException e;
        SQLiteFullException e2;
        int i2;
        zzjs zzjsVarCreateFromParcel;
        zzef zzefVarCreateFromParcel;
        zzab();
        if (this.zzaih) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!getContext().getDatabasePath("google_app_measurement_local.db").exists()) {
            return arrayList;
        }
        int i3 = 5;
        int i4 = 0;
        while (i4 < 5) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                try {
                    if (writableDatabase == null) {
                        this.zzaih = true;
                        if (writableDatabase != null) {
                            writableDatabase.close();
                        }
                        return null;
                    }
                    writableDatabase.beginTransaction();
                    cursorQuery = writableDatabase.query("messages", new String[]{"rowid", "type", "entry"}, null, null, null, null, "rowid asc", Integer.toString(100));
                    long j = -1;
                    while (cursorQuery.moveToNext()) {
                        try {
                            j = cursorQuery.getLong(0);
                            int i5 = cursorQuery.getInt(1);
                            byte[] blob = cursorQuery.getBlob(2);
                            if (i5 == 0) {
                                Parcel parcelObtain = Parcel.obtain();
                                try {
                                    try {
                                        parcelObtain.unmarshall(blob, 0, blob.length);
                                        parcelObtain.setDataPosition(0);
                                        zzeu zzeuVarCreateFromParcel = zzeu.CREATOR.createFromParcel(parcelObtain);
                                        parcelObtain.recycle();
                                        if (zzeuVarCreateFromParcel != null) {
                                            arrayList.add(zzeuVarCreateFromParcel);
                                        }
                                    } catch (Throwable th2) {
                                        parcelObtain.recycle();
                                        throw th2;
                                    }
                                } catch (b e3) {
                                    zzgg().zzil().log("Failed to load event from local database");
                                    parcelObtain.recycle();
                                }
                            } else if (i5 == 1) {
                                Parcel parcelObtain2 = Parcel.obtain();
                                try {
                                    try {
                                        parcelObtain2.unmarshall(blob, 0, blob.length);
                                        parcelObtain2.setDataPosition(0);
                                        zzjsVarCreateFromParcel = zzjs.CREATOR.createFromParcel(parcelObtain2);
                                        parcelObtain2.recycle();
                                    } catch (Throwable th3) {
                                        parcelObtain2.recycle();
                                        throw th3;
                                    }
                                } catch (b e4) {
                                    zzgg().zzil().log("Failed to load user property from local database");
                                    parcelObtain2.recycle();
                                    zzjsVarCreateFromParcel = null;
                                }
                                if (zzjsVarCreateFromParcel != null) {
                                    arrayList.add(zzjsVarCreateFromParcel);
                                }
                            } else if (i5 == 2) {
                                Parcel parcelObtain3 = Parcel.obtain();
                                try {
                                    try {
                                        parcelObtain3.unmarshall(blob, 0, blob.length);
                                        parcelObtain3.setDataPosition(0);
                                        zzefVarCreateFromParcel = zzef.CREATOR.createFromParcel(parcelObtain3);
                                        parcelObtain3.recycle();
                                    } catch (Throwable th4) {
                                        parcelObtain3.recycle();
                                        throw th4;
                                    }
                                } catch (b e5) {
                                    zzgg().zzil().log("Failed to load user property from local database");
                                    parcelObtain3.recycle();
                                    zzefVarCreateFromParcel = null;
                                }
                                if (zzefVarCreateFromParcel != null) {
                                    arrayList.add(zzefVarCreateFromParcel);
                                }
                            } else {
                                zzgg().zzil().log("Unknown record type in local database");
                            }
                        } catch (SQLiteDatabaseLockedException e6) {
                            sQLiteDatabase = writableDatabase;
                            SystemClock.sleep(i3);
                            i2 = i3 + 20;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            i4++;
                            i3 = i2;
                        } catch (SQLiteFullException e7) {
                            e2 = e7;
                            sQLiteDatabase = writableDatabase;
                            zzgg().zzil().zzg("Error reading entries from local database", e2);
                            this.zzaih = true;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                                i2 = i3;
                            } else {
                                i2 = i3;
                            }
                            i4++;
                            i3 = i2;
                        } catch (SQLiteException e8) {
                            e = e8;
                            sQLiteDatabase = writableDatabase;
                            if (sQLiteDatabase != null) {
                                try {
                                    if (sQLiteDatabase.inTransaction()) {
                                        sQLiteDatabase.endTransaction();
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    if (sQLiteDatabase != null) {
                                        sQLiteDatabase.close();
                                    }
                                    throw th;
                                }
                            }
                            zzgg().zzil().zzg("Error reading entries from local database", e);
                            this.zzaih = true;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                                i2 = i3;
                            } else {
                                i2 = i3;
                            }
                            i4++;
                            i3 = i2;
                        } catch (Throwable th6) {
                            th = th6;
                            sQLiteDatabase = writableDatabase;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            throw th;
                        }
                    }
                    if (writableDatabase.delete("messages", "rowid <= ?", new String[]{Long.toString(j)}) < arrayList.size()) {
                        zzgg().zzil().log("Fewer entries removed from local database than expected");
                    }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    return arrayList;
                } catch (SQLiteDatabaseLockedException e9) {
                    cursorQuery = null;
                    sQLiteDatabase = writableDatabase;
                } catch (SQLiteFullException e10) {
                    e2 = e10;
                    cursorQuery = null;
                    sQLiteDatabase = writableDatabase;
                } catch (SQLiteException e11) {
                    e = e11;
                    cursorQuery = null;
                    sQLiteDatabase = writableDatabase;
                } catch (Throwable th7) {
                    th = th7;
                    cursorQuery = null;
                    sQLiteDatabase = writableDatabase;
                }
            } catch (SQLiteDatabaseLockedException e12) {
                cursorQuery = null;
            } catch (SQLiteFullException e13) {
                e2 = e13;
                cursorQuery = null;
            } catch (SQLiteException e14) {
                e = e14;
                cursorQuery = null;
            } catch (Throwable th8) {
                th = th8;
                cursorQuery = null;
            }
        }
        zzgg().zzin().log("Failed to read events from database in reasonable time");
        return null;
    }
}
