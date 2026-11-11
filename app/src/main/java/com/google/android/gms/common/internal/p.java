package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.IInterface;
import com.facebook.R;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
abstract class p extends e<T>.h<Boolean> {
    public final Bundle bNC;
    private final /* synthetic */ e bNy;
    public final int statusCode;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected p(e eVar, int i, Bundle bundle) {
        super(true);
        this.bNy = eVar;
        this.statusCode = i;
        this.bNC = bundle;
    }

    protected void aaa() {
    }

    protected abstract boolean aac();

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void B(Boolean bool) {
        if (bool == null) {
            this.bNy.zza(1, (IInterface) null);
            return;
        }
        switch (this.statusCode) {
            case 0:
                if (aac()) {
                    return;
                }
                this.bNy.zza(1, (IInterface) null);
                k(new ConnectionResult(8, null));
                return;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                this.bNy.zza(1, (IInterface) null);
                throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
            default:
                this.bNy.zza(1, (IInterface) null);
                k(new ConnectionResult(this.statusCode, this.bNC != null ? (PendingIntent) this.bNC.getParcelable(e.KEY_PENDING_INTENT) : null));
                return;
        }
    }

    protected abstract void k(ConnectionResult connectionResult);
}
