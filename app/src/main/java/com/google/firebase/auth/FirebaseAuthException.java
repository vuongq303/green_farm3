package com.google.firebase.auth;

import com.google.android.gms.common.internal.bf;
import com.google.firebase.FirebaseException;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseAuthException extends FirebaseException {
    private final String zzy;

    public FirebaseAuthException(String str, String str2) {
        super(str2);
        this.zzy = bf.id(str);
    }

    public String getErrorCode() {
        return this.zzy;
    }
}
