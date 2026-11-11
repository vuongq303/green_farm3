package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.an;
import com.google.android.gms.common.internal.ao;
import com.google.android.gms.common.internal.bf;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
abstract class i extends ao {
    private int bIk;

    protected i(byte[] bArr) {
        bf.ca(bArr.length == 25);
        this.bIk = Arrays.hashCode(bArr);
    }

    protected static byte[] hS(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.google.android.gms.common.internal.an
    public com.google.android.gms.c.a YE() {
        return com.google.android.gms.c.d.F(getBytes());
    }

    @Override // com.google.android.gms.common.internal.an
    public int YF() {
        return hashCode();
    }

    public boolean equals(Object obj) {
        com.google.android.gms.c.a aVarYE;
        if (obj == null || !(obj instanceof an)) {
            return false;
        }
        try {
            an anVar = (an) obj;
            if (anVar.YF() == hashCode() && (aVarYE = anVar.YE()) != null) {
                return Arrays.equals(getBytes(), (byte[]) com.google.android.gms.c.d.b(aVarYE));
            }
            return false;
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    abstract byte[] getBytes();

    public int hashCode() {
        return this.bIk;
    }
}
