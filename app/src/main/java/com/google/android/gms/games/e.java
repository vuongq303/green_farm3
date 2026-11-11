package com.google.android.gms.games;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class e {
    private boolean bSk;
    private boolean bSl;
    private int bSm;
    private boolean bSn;
    private int bSo;
    private ArrayList<String> bSp;
    private boolean bSq;
    private boolean bSr;
    private boolean bSs;
    GoogleSignInAccount bSt;
    private String zzaw;

    private e() {
        this.bSk = false;
        this.bSl = true;
        this.bSm = 17;
        this.bSn = false;
        this.bSo = 4368;
        this.zzaw = null;
        this.bSp = new ArrayList<>();
        this.bSq = false;
        this.bSr = false;
        this.bSs = false;
        this.bSt = null;
    }

    /* synthetic */ e(x xVar) {
        this();
    }

    public final d aco() {
        return new d(this.bSk, this.bSl, this.bSm, this.bSn, this.bSo, this.zzaw, this.bSp, this.bSq, this.bSr, this.bSs, this.bSt, null);
    }
}
