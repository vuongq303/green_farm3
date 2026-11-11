package com.google.android.gms.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class f extends Handler {
    private final Context bIe;
    private final /* synthetic */ e bIf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public f(e eVar, Context context) {
        super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
        this.bIf = eVar;
        this.bIe = context.getApplicationContext();
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                int iIsGooglePlayServicesAvailable = this.bIf.isGooglePlayServicesAvailable(this.bIe);
                if (this.bIf.ns(iIsGooglePlayServicesAvailable)) {
                    this.bIf.y(this.bIe, iIsGooglePlayServicesAvailable);
                }
                break;
            default:
                Log.w("GoogleApiAvailability", new StringBuilder(50).append("Don't know how to handle this message: ").append(message.what).toString());
                break;
        }
    }
}
