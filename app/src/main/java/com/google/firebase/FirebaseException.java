package com.google.firebase;

import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseException extends Exception {
    @Deprecated
    protected FirebaseException() {
    }

    public FirebaseException(String str) {
        super(bf.d(str, (Object) "Detail message must not be empty"));
    }

    public FirebaseException(String str, Throwable th) {
        super(bf.d(str, (Object) "Detail message must not be empty"), th);
    }
}
