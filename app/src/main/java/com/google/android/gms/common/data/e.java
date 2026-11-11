package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public class e {
    protected final DataHolder mDataHolder;
    protected int mDataRow;
    private int zznj;

    public e(DataHolder dataHolder, int i) {
        this.mDataHolder = (DataHolder) bf.checkNotNull(dataHolder);
        setDataRow(i);
    }

    protected void copyToBuffer(String str, CharArrayBuffer charArrayBuffer) {
        this.mDataHolder.a(str, this.mDataRow, this.zznj, charArrayBuffer);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return bd.b(Integer.valueOf(eVar.mDataRow), Integer.valueOf(this.mDataRow)) && bd.b(Integer.valueOf(eVar.zznj), Integer.valueOf(this.zznj)) && eVar.mDataHolder == this.mDataHolder;
    }

    protected boolean getBoolean(String str) {
        return this.mDataHolder.s(str, this.mDataRow, this.zznj);
    }

    protected byte[] getByteArray(String str) {
        return this.mDataHolder.v(str, this.mDataRow, this.zznj);
    }

    protected int getDataRow() {
        return this.mDataRow;
    }

    protected double getDouble(String str) {
        return this.mDataHolder.u(str, this.mDataRow, this.zznj);
    }

    protected float getFloat(String str) {
        return this.mDataHolder.t(str, this.mDataRow, this.zznj);
    }

    protected int getInteger(String str) {
        return this.mDataHolder.q(str, this.mDataRow, this.zznj);
    }

    protected long getLong(String str) {
        return this.mDataHolder.p(str, this.mDataRow, this.zznj);
    }

    protected String getString(String str) {
        return this.mDataHolder.r(str, this.mDataRow, this.zznj);
    }

    public boolean hasColumn(String str) {
        return this.mDataHolder.hasColumn(str);
    }

    protected boolean hasNull(String str) {
        return this.mDataHolder.x(str, this.mDataRow, this.zznj);
    }

    public int hashCode() {
        return bd.hashCode(Integer.valueOf(this.mDataRow), Integer.valueOf(this.zznj), this.mDataHolder);
    }

    public boolean isDataValid() {
        return !this.mDataHolder.isClosed();
    }

    protected Uri parseUri(String str) {
        return this.mDataHolder.w(str, this.mDataRow, this.zznj);
    }

    protected void setDataRow(int i) {
        bf.bZ(i >= 0 && i < this.mDataHolder.getCount());
        this.mDataRow = i;
        this.zznj = this.mDataHolder.nF(this.mDataRow);
    }
}
