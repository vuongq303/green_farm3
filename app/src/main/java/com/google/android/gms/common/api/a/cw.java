package com.google.android.gms.common.api.a;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class cw extends cz {
    private final SparseArray<cx> bMb;

    private cw(u uVar) {
        super(uVar);
        this.bMb = new SparseArray<>();
        this.bJO.a("AutoManageHelper", this);
    }

    public static cw b(s sVar) {
        u uVarA = a(sVar);
        cw cwVar = (cw) uVarA.a("AutoManageHelper", cw.class);
        return cwVar != null ? cwVar : new cw(uVarA);
    }

    private final cx nC(int i) {
        if (this.bMb.size() <= i) {
            return null;
        }
        return this.bMb.get(this.bMb.keyAt(i));
    }

    @Override // com.google.android.gms.common.api.a.cz
    protected final void YX() {
        for (int i = 0; i < this.bMb.size(); i++) {
            cx cxVarNC = nC(i);
            if (cxVarNC != null) {
                cxVarNC.bMd.connect();
            }
        }
    }

    public final void a(int i, com.google.android.gms.common.api.w wVar, com.google.android.gms.common.api.z zVar) {
        com.google.android.gms.common.internal.bf.d(wVar, "GoogleApiClient instance cannot be null");
        com.google.android.gms.common.internal.bf.a(this.bMb.indexOfKey(i) < 0, new StringBuilder(54).append("Already managing a GoogleApiClient with id ").append(i).toString());
        da daVar = this.bMk.get();
        boolean z = this.mStarted;
        String strValueOf = String.valueOf(daVar);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(strValueOf).length() + 49).append("starting AutoManage for client ").append(i).append(" ").append(z).append(" ").append(strValueOf).toString());
        this.bMb.put(i, new cx(this, i, wVar, zVar));
        if (this.mStarted && daVar == null) {
            String strValueOf2 = String.valueOf(wVar);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(strValueOf2).length() + 11).append("connecting ").append(strValueOf2).toString());
            wVar.connect();
        }
    }

    @Override // com.google.android.gms.common.api.a.cz
    protected final void b(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        cx cxVar = this.bMb.get(i);
        if (cxVar != null) {
            zzc(i);
            com.google.android.gms.common.api.z zVar = cxVar.bMe;
            if (zVar != null) {
                zVar.onConnectionFailed(connectionResult);
            }
        }
    }

    @Override // com.google.android.gms.common.api.a.t
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.bMb.size(); i++) {
            cx cxVarNC = nC(i);
            if (cxVarNC != null) {
                printWriter.append((CharSequence) str).append("GoogleApiClient #").print(cxVarNC.bMc);
                printWriter.println(":");
                cxVarNC.bMd.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    @Override // com.google.android.gms.common.api.a.cz, com.google.android.gms.common.api.a.t
    public final void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String strValueOf = String.valueOf(this.bMb);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(strValueOf).length() + 14).append("onStart ").append(z).append(" ").append(strValueOf).toString());
        if (this.bMk.get() == null) {
            for (int i = 0; i < this.bMb.size(); i++) {
                cx cxVarNC = nC(i);
                if (cxVarNC != null) {
                    cxVarNC.bMd.connect();
                }
            }
        }
    }

    @Override // com.google.android.gms.common.api.a.cz, com.google.android.gms.common.api.a.t
    public void onStop() {
        super.onStop();
        for (int i = 0; i < this.bMb.size(); i++) {
            cx cxVarNC = nC(i);
            if (cxVarNC != null) {
                cxVarNC.bMd.disconnect();
            }
        }
    }

    public final void zzc(int i) {
        cx cxVar = this.bMb.get(i);
        this.bMb.remove(i);
        if (cxVar != null) {
            cxVar.bMd.b(cxVar);
            cxVar.bMd.disconnect();
        }
    }
}
