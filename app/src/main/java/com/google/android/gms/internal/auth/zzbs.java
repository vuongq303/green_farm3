package com.google.android.gms.internal.auth;

import android.util.Log;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzbs extends FastSafeParcelableJsonResponse {
    private static String zzhq = "AUTH";

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
    public byte[] toByteArray() {
        try {
            return toString().getBytes(AndroidWebView.UTF_8);
        } catch (UnsupportedEncodingException e) {
            Log.e(zzhq, "Error serializing object.", e);
            return null;
        }
    }
}
