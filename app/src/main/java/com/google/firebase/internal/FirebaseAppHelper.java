package com.google.firebase.internal;

import com.google.android.gms.f.f;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.GetTokenResult;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseAppHelper {
    public static void addIdTokenListener(FirebaseApp firebaseApp, FirebaseApp.IdTokenListener idTokenListener) {
        firebaseApp.addIdTokenListener(idTokenListener);
    }

    public static f<GetTokenResult> getToken(FirebaseApp firebaseApp, boolean z) {
        return firebaseApp.getToken(z);
    }

    public static String getUid(FirebaseApp firebaseApp) {
        return firebaseApp.getUid();
    }

    public static void removeIdTokenListener(FirebaseApp firebaseApp, FirebaseApp.IdTokenListener idTokenListener) {
        firebaseApp.zza(idTokenListener);
    }
}
