package com.google.android.gms.auth.api.signin.internal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public class SignInHubActivity extends FragmentActivity {
    private static boolean bHc = false;
    private SignInConfiguration bHd;
    private int bHe;
    private Intent bHf;
    private boolean zzfh = false;
    private boolean zzfj;

    private final void Xu() {
        getSupportLoaderManager().a(0, null, new c(this));
        bHc = false;
    }

    private final void no(int i) {
        Status status = new Status(i);
        Intent intent = new Intent();
        intent.putExtra("googleSignInStatus", status);
        setResult(0, intent);
        finish();
        bHc = false;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return true;
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (this.zzfh) {
        }
        setResult(0);
        switch (i) {
            case 40962:
                if (intent != null) {
                    SignInAccount signInAccount = (SignInAccount) intent.getParcelableExtra("signInAccount");
                    if (signInAccount != null && signInAccount.Yq() != null) {
                        GoogleSignInAccount googleSignInAccountYq = signInAccount.Yq();
                        i.az(this).a(this.bHd.Ys(), googleSignInAccountYq);
                        intent.removeExtra("signInAccount");
                        intent.putExtra("googleSignInAccount", googleSignInAccountYq);
                        this.zzfj = true;
                        this.bHe = i2;
                        this.bHf = intent;
                        Xu();
                    } else if (intent.hasExtra("errorCode")) {
                        no(intent.getIntExtra("errorCode", 8));
                    }
                }
                no(8);
                break;
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        String action = intent.getAction();
        if ("com.google.android.gms.auth.NO_IMPL".equals(action)) {
            no(12500);
            return;
        }
        if (!action.equals("com.google.android.gms.auth.GOOGLE_SIGN_IN") && !action.equals("com.google.android.gms.auth.APPAUTH_SIGN_IN")) {
            String strValueOf = String.valueOf(intent.getAction());
            Log.e("AuthSignInClient", strValueOf.length() != 0 ? "Unknown action: ".concat(strValueOf) : new String("Unknown action: "));
            finish();
            return;
        }
        this.bHd = (SignInConfiguration) intent.getBundleExtra("config").getParcelable("config");
        if (this.bHd == null) {
            Log.e("AuthSignInClient", "Activity started with invalid configuration.");
            setResult(0);
            finish();
            return;
        }
        if (!(bundle == null)) {
            this.zzfj = bundle.getBoolean("signingInGoogleApiClients");
            if (this.zzfj) {
                this.bHe = bundle.getInt("signInResultCode");
                this.bHf = (Intent) bundle.getParcelable("signInResultData");
                Xu();
                return;
            }
            return;
        }
        if (bHc) {
            setResult(0);
            no(12502);
            return;
        }
        bHc = true;
        Intent intent2 = new Intent(action);
        if (action.equals("com.google.android.gms.auth.GOOGLE_SIGN_IN")) {
            intent2.setPackage("com.google.android.gms");
        } else {
            intent2.setPackage(getPackageName());
        }
        intent2.putExtra("config", this.bHd);
        try {
            startActivityForResult(intent2, 40962);
        } catch (ActivityNotFoundException e) {
            this.zzfh = true;
            Log.w("AuthSignInClient", "Could not launch sign in Intent. Google Play Service is probably being updated...");
            no(17);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("signingInGoogleApiClients", this.zzfj);
        if (this.zzfj) {
            bundle.putInt("signInResultCode", this.bHe);
            bundle.putParcelable("signInResultData", this.bHf);
        }
    }
}
