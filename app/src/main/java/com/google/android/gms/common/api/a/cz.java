package com.google.android.gms.common.api.a;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class cz extends t implements DialogInterface.OnCancelListener {
    protected final com.google.android.gms.common.e bII;
    protected final AtomicReference<da> bMk;
    private final Handler bMl;
    protected volatile boolean mStarted;

    protected cz(u uVar) {
        this(uVar, com.google.android.gms.common.e.YB());
    }

    private cz(u uVar, com.google.android.gms.common.e eVar) {
        super(uVar);
        this.bMk = new AtomicReference<>(null);
        this.bMl = new Handler(Looper.getMainLooper());
        this.bII = eVar;
    }

    private static int a(da daVar) {
        if (daVar == null) {
            return -1;
        }
        return daVar.ZL();
    }

    protected abstract void YX();

    protected abstract void b(ConnectionResult connectionResult, int i);

    public final void c(ConnectionResult connectionResult, int i) {
        da daVar = new da(connectionResult, i);
        if (this.bMk.compareAndSet(null, daVar)) {
            this.bMl.post(new db(this, daVar));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:23:0x005b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x005d  */
    /* JADX WARN: Code duplicated, block: B:4:0x0011  */
    /* JADX WARN: Code duplicated, block: B:6:0x0014  */
    @Override // com.google.android.gms.common.api.a.t
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z;
        da daVar = this.bMk.get();
        switch (i) {
            case 1:
                if (i2 == -1) {
                    z = true;
                } else if (i2 == 0) {
                    da daVar2 = new da(new ConnectionResult(intent != null ? intent.getIntExtra("<<ResolutionFailureErrorDetail>>", 13) : 13, null), a(daVar));
                    this.bMk.set(daVar2);
                    daVar = daVar2;
                    z = false;
                } else {
                    z = false;
                }
                if (z) {
                    zzt();
                } else if (daVar != null) {
                    b(daVar.ZM(), daVar.ZL());
                }
                break;
            case 2:
                int iIsGooglePlayServicesAvailable = this.bII.isGooglePlayServicesAvailable(getActivity());
                z = iIsGooglePlayServicesAvailable == 0;
                if (daVar != null) {
                    if (daVar.ZM().getErrorCode() == 18 && iIsGooglePlayServicesAvailable == 18) {
                    }
                    if (z) {
                        zzt();
                    } else if (daVar != null) {
                        b(daVar.ZM(), daVar.ZL());
                    }
                }
                break;
            default:
                z = false;
                if (z) {
                    zzt();
                } else if (daVar != null) {
                    b(daVar.ZM(), daVar.ZL());
                }
                break;
        }
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        b(new ConnectionResult(13, null), a(this.bMk.get()));
        zzt();
    }

    @Override // com.google.android.gms.common.api.a.t
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.bMk.set(bundle.getBoolean("resolving_error", false) ? new da(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    @Override // com.google.android.gms.common.api.a.t
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        da daVar = this.bMk.get();
        if (daVar != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", daVar.ZL());
            bundle.putInt("failed_status", daVar.ZM().getErrorCode());
            bundle.putParcelable("failed_resolution", daVar.ZM().Yz());
        }
    }

    @Override // com.google.android.gms.common.api.a.t
    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    @Override // com.google.android.gms.common.api.a.t
    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected final void zzt() {
        this.bMk.set(null);
        YX();
    }
}
