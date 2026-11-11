package android.support.a;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;

/* JADX INFO: compiled from: CustomTabsIntent.java */
/* JADX INFO: loaded from: classes.dex */
public final class f {
    private ArrayList<Bundle> A;
    private boolean B;
    private final Intent v;
    private ArrayList<Bundle> w;
    private Bundle z;

    public f() {
        this(null);
    }

    public f(i iVar) {
        this.v = new Intent("android.intent.action.VIEW");
        this.w = null;
        this.z = null;
        this.A = null;
        this.B = true;
        if (iVar != null) {
            this.v.setPackage(iVar.getComponentName().getPackageName());
        }
        Bundle bundle = new Bundle();
        android.support.v4.app.l.a(bundle, "android.support.customtabs.extra.SESSION", iVar != null ? iVar.getBinder() : null);
        this.v.putExtras(bundle);
    }

    public d m() {
        if (this.w != null) {
            this.v.putParcelableArrayListExtra("android.support.customtabs.extra.MENU_ITEMS", this.w);
        }
        if (this.A != null) {
            this.v.putParcelableArrayListExtra("android.support.customtabs.extra.TOOLBAR_ITEMS", this.A);
        }
        this.v.putExtra("android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS", this.B);
        return new d(this.v, this.z);
    }
}
