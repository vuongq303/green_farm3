package android.support.v7.c;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.view.LayoutInflater;

/* JADX INFO: compiled from: ContextThemeWrapper.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends ContextWrapper {
    private Resources kL;
    private LayoutInflater kc;
    private int nG;
    private Resources.Theme nH;
    private Configuration nI;

    public e() {
        super(null);
    }

    public e(Context context, int i) {
        super(context);
        this.nG = i;
    }

    public e(Context context, Resources.Theme theme) {
        super(context);
        this.nH = theme;
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        return cs();
    }

    private Resources cs() {
        if (this.kL == null) {
            if (this.nI == null) {
                this.kL = super.getResources();
            } else if (Build.VERSION.SDK_INT >= 17) {
                this.kL = createConfigurationContext(this.nI).getResources();
            }
        }
        return this.kL;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        if (this.nG != i) {
            this.nG = i;
            cu();
        }
    }

    public int ct() {
        return this.nG;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        if (this.nH != null) {
            return this.nH;
        }
        if (this.nG == 0) {
            this.nG = R.style.Theme_AppCompat_Light;
        }
        cu();
        return this.nH;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.kc == null) {
            this.kc = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.kc;
    }

    protected void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, true);
    }

    private void cu() {
        boolean z = this.nH == null;
        if (z) {
            this.nH = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.nH.setTo(theme);
            }
        }
        onApplyThemeResource(this.nH, this.nG, z);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return getResources().getAssets();
    }
}
