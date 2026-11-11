package android.support.v7.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import com.google.android.gms.ads.AdRequest;

/* JADX INFO: compiled from: AppCompatDelegateImplV14.java */
/* JADX INFO: loaded from: classes.dex */
class ad extends ac {
    private int lg;
    private boolean lh;
    private boolean li;
    private af lj;

    ad(Context context, Window window, v vVar) {
        super(context, window, vVar);
        this.lg = -100;
        this.li = true;
    }

    @Override // android.support.v7.app.aj, android.support.v7.app.w
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && this.lg == -100) {
            this.lg = bundle.getInt("appcompat:local_night_mode", -100);
        }
    }

    @Override // android.support.v7.app.x
    Window.Callback a(Window.Callback callback) {
        return new ae(this, callback);
    }

    @Override // android.support.v7.app.x
    public boolean bG() {
        return this.li;
    }

    @Override // android.support.v7.app.x, android.support.v7.app.w
    public boolean bA() {
        boolean zR = false;
        int nightMode = getNightMode();
        int iQ = Q(nightMode);
        if (iQ != -1) {
            zR = R(iQ);
        }
        if (nightMode == 0) {
            bI();
            this.lj.setup();
        }
        this.lh = true;
        return zR;
    }

    @Override // android.support.v7.app.x, android.support.v7.app.w
    public void onStart() {
        super.onStart();
        bA();
    }

    @Override // android.support.v7.app.aj, android.support.v7.app.x, android.support.v7.app.w
    public void onStop() {
        super.onStop();
        if (this.lj != null) {
            this.lj.bM();
        }
    }

    int Q(int i) {
        switch (i) {
            case -100:
                return -1;
            case 0:
                bI();
                return this.lj.bK();
            default:
                return i;
        }
    }

    private int getNightMode() {
        return this.lg != -100 ? this.lg : bB();
    }

    @Override // android.support.v7.app.x, android.support.v7.app.w
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.lg != -100) {
            bundle.putInt("appcompat:local_night_mode", this.lg);
        }
    }

    @Override // android.support.v7.app.aj, android.support.v7.app.x, android.support.v7.app.w
    public void onDestroy() {
        super.onDestroy();
        if (this.lj != null) {
            this.lj.bM();
        }
    }

    private boolean R(int i) {
        Resources resources = this.mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        int i2 = configuration.uiMode & 48;
        int i3 = i == 2 ? 32 : 16;
        if (i2 != i3) {
            if (bJ()) {
                ((Activity) this.mContext).recreate();
            } else {
                Configuration configuration2 = new Configuration(configuration);
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration2.uiMode = i3 | (configuration2.uiMode & (-49));
                resources.updateConfiguration(configuration2, displayMetrics);
                if (Build.VERSION.SDK_INT < 26) {
                    ba.b(resources);
                }
            }
            return true;
        }
        return false;
    }

    private void bI() {
        if (this.lj == null) {
            this.lj = new af(this, bf.l(this.mContext));
        }
    }

    private boolean bJ() {
        if (!this.lh || !(this.mContext instanceof Activity)) {
            return false;
        }
        try {
            return (this.mContext.getPackageManager().getActivityInfo(new ComponentName(this.mContext, this.mContext.getClass()), 0).configChanges & AdRequest.MAX_CONTENT_URL_LENGTH) == 0;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
            return true;
        }
    }
}
