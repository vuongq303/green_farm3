package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private boolean bGO;
    private boolean bGP;
    private boolean bGQ;
    private Set<Scope> mScopes;
    private Map<Integer, GoogleSignInOptionsExtensionParcelable> zzab;
    private Account zzs;
    private String zzw;
    private String zzx;

    public c() {
        this.mScopes = new HashSet();
        this.zzab = new HashMap();
    }

    public c(GoogleSignInOptions googleSignInOptions) {
        this.mScopes = new HashSet();
        this.zzab = new HashMap();
        bf.checkNotNull(googleSignInOptions);
        this.mScopes = new HashSet(googleSignInOptions.bGN);
        this.bGP = googleSignInOptions.bGP;
        this.bGQ = googleSignInOptions.bGQ;
        this.bGO = googleSignInOptions.bGO;
        this.zzw = googleSignInOptions.zzw;
        this.zzs = googleSignInOptions.zzs;
        this.zzx = googleSignInOptions.zzx;
        this.zzab = GoogleSignInOptions.f((List<GoogleSignInOptionsExtensionParcelable>) googleSignInOptions.bGR);
    }

    public final c Yn() {
        this.mScopes.add(GoogleSignInOptions.bGI);
        return this;
    }

    public final c Yo() {
        this.mScopes.add(GoogleSignInOptions.bGG);
        return this;
    }

    public final GoogleSignInOptions Yp() {
        if (this.mScopes.contains(GoogleSignInOptions.bGK) && this.mScopes.contains(GoogleSignInOptions.bGJ)) {
            this.mScopes.remove(GoogleSignInOptions.bGJ);
        }
        if (this.bGO && (this.zzs == null || !this.mScopes.isEmpty())) {
            Yn();
        }
        return new GoogleSignInOptions(3, new ArrayList(this.mScopes), this.zzs, this.bGO, this.bGP, this.bGQ, this.zzw, this.zzx, this.zzab, null);
    }

    public final c a(Scope scope, Scope... scopeArr) {
        this.mScopes.add(scope);
        this.mScopes.addAll(Arrays.asList(scopeArr));
        return this;
    }
}
