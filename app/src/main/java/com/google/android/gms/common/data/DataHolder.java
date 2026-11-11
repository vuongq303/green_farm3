package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new i();
    private static final g bNk = new l(new String[0], null);
    private final int bHY;
    private final String[] bNe;
    private Bundle bNf;
    private final CursorWindow[] bNg;
    private final Bundle bNh;
    private int[] bNi;
    private boolean bNj;
    private boolean mClosed;
    private final int zzal;
    private int zznr;

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.bNj = true;
        this.zzal = i;
        this.bNe = strArr;
        this.bNg = cursorWindowArr;
        this.bHY = i2;
        this.bNh = bundle;
    }

    private DataHolder(g gVar, int i, Bundle bundle) {
        this(gVar.bNe, a(gVar, -1), i, bundle);
    }

    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.bNj = true;
        this.zzal = 1;
        this.bNe = (String[]) bf.checkNotNull(strArr);
        this.bNg = (CursorWindow[]) bf.checkNotNull(cursorWindowArr);
        this.bHY = i;
        this.bNh = bundle;
        ZV();
    }

    private static CursorWindow[] a(g gVar, int i) {
        int i2;
        boolean z;
        if (gVar.bNe.length == 0) {
            return new CursorWindow[0];
        }
        List listSubList = (i < 0 || i >= gVar.bNl.size()) ? gVar.bNl : gVar.bNl.subList(0, i);
        int size = listSubList.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(gVar.bNe.length);
        int i3 = 0;
        boolean z2 = false;
        while (i3 < size) {
            try {
                if (!cursorWindow.allocRow()) {
                    Log.d("DataHolder", new StringBuilder(72).append("Allocating additional cursor window for large data set (row ").append(i3).append(")").toString());
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(i3);
                    cursorWindow.setNumColumns(gVar.bNe.length);
                    arrayList.add(cursorWindow);
                    if (!cursorWindow.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) listSubList.get(i3);
                boolean zPutDouble = true;
                for (int i4 = 0; i4 < gVar.bNe.length && zPutDouble; i4++) {
                    String str = gVar.bNe[i4];
                    Object obj = map.get(str);
                    if (obj == null) {
                        zPutDouble = cursorWindow.putNull(i3, i4);
                    } else if (obj instanceof String) {
                        zPutDouble = cursorWindow.putString((String) obj, i3, i4);
                    } else if (obj instanceof Long) {
                        zPutDouble = cursorWindow.putLong(((Long) obj).longValue(), i3, i4);
                    } else if (obj instanceof Integer) {
                        zPutDouble = cursorWindow.putLong(((Integer) obj).intValue(), i3, i4);
                    } else if (obj instanceof Boolean) {
                        zPutDouble = cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1L : 0L, i3, i4);
                    } else if (obj instanceof byte[]) {
                        zPutDouble = cursorWindow.putBlob((byte[]) obj, i3, i4);
                    } else if (obj instanceof Double) {
                        zPutDouble = cursorWindow.putDouble(((Double) obj).doubleValue(), i3, i4);
                    } else {
                        if (!(obj instanceof Float)) {
                            String strValueOf = String.valueOf(obj);
                            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(strValueOf).length()).append("Unsupported object for column ").append(str).append(": ").append(strValueOf).toString());
                        }
                        zPutDouble = cursorWindow.putDouble(((Float) obj).floatValue(), i3, i4);
                    }
                }
                if (zPutDouble) {
                    i2 = i3;
                    z = false;
                } else {
                    if (z2) {
                        throw new h("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                    }
                    Log.d("DataHolder", new StringBuilder(74).append("Couldn't populate window data for row ").append(i3).append(" - allocating new window.").toString());
                    cursorWindow.freeLastRow();
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(i3);
                    cursorWindow.setNumColumns(gVar.bNe.length);
                    arrayList.add(cursorWindow);
                    i2 = i3 - 1;
                    z = true;
                }
                i3 = i2 + 1;
                z2 = z;
            } catch (RuntimeException e) {
                int size2 = arrayList.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    ((CursorWindow) arrayList.get(i5)).close();
                }
                throw e;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder c(int i, Bundle bundle) {
        return new DataHolder(bNk, i, bundle);
    }

    public static DataHolder nG(int i) {
        return c(i, null);
    }

    private final void zza(String str, int i) {
        if (this.bNf == null || !this.bNf.containsKey(str)) {
            String strValueOf = String.valueOf(str);
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "No such column: ".concat(strValueOf) : new String("No such column: "));
        }
        if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        }
        if (i < 0 || i >= this.zznr) {
            throw new CursorIndexOutOfBoundsException(i, this.zznr);
        }
    }

    public final void ZV() {
        this.bNf = new Bundle();
        for (int i = 0; i < this.bNe.length; i++) {
            this.bNf.putInt(this.bNe[i], i);
        }
        this.bNi = new int[this.bNg.length];
        int numRows = 0;
        for (int i2 = 0; i2 < this.bNg.length; i2++) {
            this.bNi[i2] = numRows;
            numRows += this.bNg[i2].getNumRows() - (numRows - this.bNg[i2].getStartPosition());
        }
        this.zznr = numRows;
    }

    public final Bundle ZW() {
        return this.bNh;
    }

    public final void a(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zza(str, i);
        this.bNg[i2].copyStringToBuffer(i, this.bNf.getInt(str), charArrayBuffer);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i = 0; i < this.bNg.length; i++) {
                    this.bNg[i].close();
                }
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.bNj && this.bNg.length > 0 && !isClosed()) {
                close();
                String string = toString();
                Log.e("DataBuffer", new StringBuilder(String.valueOf(string).length() + 178).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ").append(string).append(")").toString());
            }
        } finally {
            super.finalize();
        }
    }

    public final int getCount() {
        return this.zznr;
    }

    public final int getStatusCode() {
        return this.bHY;
    }

    public final boolean hasColumn(String str) {
        return this.bNf.containsKey(str);
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public final int nF(int i) {
        int i2 = 0;
        bf.bZ(i >= 0 && i < this.zznr);
        while (i2 < this.bNi.length) {
            if (i < this.bNi[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.bNi.length ? i2 - 1 : i2;
    }

    public final long p(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].getLong(i, this.bNf.getInt(str));
    }

    public final int q(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].getInt(i, this.bNf.getInt(str));
    }

    public final String r(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].getString(i, this.bNf.getInt(str));
    }

    public final boolean s(String str, int i, int i2) {
        zza(str, i);
        return Long.valueOf(this.bNg[i2].getLong(i, this.bNf.getInt(str))).longValue() == 1;
    }

    public final float t(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].getFloat(i, this.bNf.getInt(str));
    }

    public final double u(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].getDouble(i, this.bNf.getInt(str));
    }

    public final byte[] v(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].getBlob(i, this.bNf.getInt(str));
    }

    public final Uri w(String str, int i, int i2) {
        String strR = r(str, i, i2);
        if (strR == null) {
            return null;
        }
        return Uri.parse(strR);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.bNe, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable[]) this.bNg, i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, getStatusCode());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, ZW(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
        if ((i & 1) != 0) {
            close();
        }
    }

    public final boolean x(String str, int i, int i2) {
        zza(str, i);
        return this.bNg[i2].isNull(i, this.bNf.getInt(str));
    }
}
