package com.gameloft.android.ANMP.GloftGF2F;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public class KeyProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.gameloft.android.ANMP.GloftGF2F.KeyProvider/key");
    private static final UriMatcher EI = new UriMatcher(-1);
    private bc EH;

    static {
        EI.addURI("com.gameloft.android.ANMP.GloftGF2F.KeyProvider", "key", 1);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.EH = new bc(getContext());
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables("glshare");
        if (1 != EI.match(uri)) {
            throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursorQuery = sQLiteQueryBuilder.query(this.EH.getReadableDatabase(), strArr, str, strArr2, null, null, str2);
        cursorQuery.setNotificationUri(getContext().getContentResolver(), uri);
        return cursorQuery;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        int iMatch = EI.match(uri);
        SQLiteDatabase writableDatabase = this.EH.getWritableDatabase();
        if (1 == iMatch) {
            int iDelete = writableDatabase.delete("glshare", str, strArr);
            getContext().getContentResolver().notifyChange(uri, null);
            return iDelete;
        }
        throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        switch (EI.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/mt-key";
            default:
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (EI.match(uri) != 1) {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        long jInsert = this.EH.getWritableDatabase().insert("glshare", null, contentValues);
        if (jInsert > 0) {
            Uri uriWithAppendedId = ContentUris.withAppendedId(uri, jInsert);
            getContext().getContentResolver().notifyChange(uri, null);
            return uriWithAppendedId;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int iMatch = EI.match(uri);
        SQLiteDatabase writableDatabase = this.EH.getWritableDatabase();
        if (1 == iMatch) {
            int iUpdate = writableDatabase.update("glshare", contentValues, str, strArr);
            getContext().getContentResolver().notifyChange(uri, null);
            return iUpdate;
        }
        throw new IllegalArgumentException("Unknown URI");
    }
}
