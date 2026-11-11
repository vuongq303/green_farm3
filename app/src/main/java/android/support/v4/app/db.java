package android.support.v4.app;

import android.os.Build;
import android.os.Bundle;
import java.util.Set;

/* JADX INFO: compiled from: RemoteInput.java */
/* JADX INFO: loaded from: classes.dex */
public final class db extends dj {
    private static final dd eh;
    public static final dk ei;
    private final String ec;
    private final CharSequence ed;
    private final CharSequence[] ee;
    private final boolean ef;
    private final Set<String> eg;
    private final Bundle mExtras;

    @Override // android.support.v4.app.dj
    public String getResultKey() {
        return this.ec;
    }

    @Override // android.support.v4.app.dj
    public CharSequence getLabel() {
        return this.ed;
    }

    @Override // android.support.v4.app.dj
    public CharSequence[] getChoices() {
        return this.ee;
    }

    @Override // android.support.v4.app.dj
    public Set<String> getAllowedDataTypes() {
        return this.eg;
    }

    @Override // android.support.v4.app.dj
    public boolean getAllowFreeFormInput() {
        return this.ef;
    }

    @Override // android.support.v4.app.dj
    public Bundle getExtras() {
        return this.mExtras;
    }

    static {
        if (Build.VERSION.SDK_INT >= 20) {
            eh = new de();
        } else if (Build.VERSION.SDK_INT >= 16) {
            eh = new dg();
        } else {
            eh = new df();
        }
        ei = new dc();
    }
}
