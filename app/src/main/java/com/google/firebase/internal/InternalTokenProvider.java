package com.google.firebase.internal;

import com.google.android.gms.f.f;
import com.google.firebase.auth.GetTokenResult;

/* JADX INFO: loaded from: classes.dex */
public interface InternalTokenProvider {
    f<GetTokenResult> getAccessToken(boolean z);

    String getUid();
}
