package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.ac;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseExceptionMapper implements ac {
    @Override // com.google.android.gms.common.api.a.ac
    public Exception getException(Status status) {
        return status.getStatusCode() == 8 ? new FirebaseException(status.YS()) : new FirebaseApiNotAvailableException(status.YS());
    }
}
