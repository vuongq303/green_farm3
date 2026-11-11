package com.google.android.gms.games;

import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class d implements com.google.android.gms.common.api.g, com.google.android.gms.common.api.j {
    public final boolean bSk;
    public final boolean bSl;
    public final int bSm;
    public final boolean bSn;
    public final int bSo;
    public final ArrayList<String> bSp;
    public final boolean bSq;
    public final boolean bSr;
    public final boolean bSs;
    public final GoogleSignInAccount bSt;
    public final String zzaw;

    private d(boolean z, boolean z2, int i, boolean z3, int i2, String str, ArrayList<String> arrayList, boolean z4, boolean z5, boolean z6, GoogleSignInAccount googleSignInAccount) {
        this.bSk = z;
        this.bSl = z2;
        this.bSm = i;
        this.bSn = z3;
        this.bSo = i2;
        this.zzaw = str;
        this.bSp = arrayList;
        this.bSq = z4;
        this.bSr = z5;
        this.bSs = z6;
        this.bSt = googleSignInAccount;
    }

    /* synthetic */ d(boolean z, boolean z2, int i, boolean z3, int i2, String str, ArrayList arrayList, boolean z4, boolean z5, boolean z6, GoogleSignInAccount googleSignInAccount, x xVar) {
        this(z, z2, i, z3, i2, str, arrayList, z4, z5, z6, googleSignInAccount);
    }

    public static e acn() {
        return new e(null);
    }

    @Override // com.google.android.gms.common.api.g
    public final GoogleSignInAccount Yq() {
        return this.bSt;
    }

    public final Bundle acm() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.bSk);
        bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.bSl);
        bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.bSm);
        bundle.putBoolean("com.google.android.gms.games.key.retryingSignIn", this.bSn);
        bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.bSo);
        bundle.putString("com.google.android.gms.games.key.forceResolveAccountKey", this.zzaw);
        bundle.putStringArrayList("com.google.android.gms.games.key.proxyApis", this.bSp);
        bundle.putBoolean("com.google.android.gms.games.key.requireGooglePlus", this.bSq);
        bundle.putBoolean("com.google.android.gms.games.key.unauthenticated", this.bSr);
        bundle.putBoolean("com.google.android.gms.games.key.skipWelcomePopup", this.bSs);
        return bundle;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        if (this.bSk == dVar.bSk && this.bSl == dVar.bSl && this.bSm == dVar.bSm && this.bSn == dVar.bSn && this.bSo == dVar.bSo && (this.zzaw != null ? this.zzaw.equals(dVar.zzaw) : dVar.zzaw == null) && this.bSp.equals(dVar.bSp) && this.bSq == dVar.bSq && this.bSr == dVar.bSr && this.bSs == dVar.bSs) {
            if (this.bSt == null) {
                if (dVar.bSt == null) {
                    return true;
                }
            } else if (this.bSt.equals(dVar.bSt)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (((((this.bSr ? 1 : 0) + (((this.bSq ? 1 : 0) + (((((this.zzaw == null ? 0 : this.zzaw.hashCode()) + (((((this.bSn ? 1 : 0) + (((((this.bSl ? 1 : 0) + (((this.bSk ? 1 : 0) + 527) * 31)) * 31) + this.bSm) * 31)) * 31) + this.bSo) * 31)) * 31) + this.bSp.hashCode()) * 31)) * 31)) * 31) + (this.bSs ? 1 : 0)) * 31) + (this.bSt != null ? this.bSt.hashCode() : 0);
    }
}
