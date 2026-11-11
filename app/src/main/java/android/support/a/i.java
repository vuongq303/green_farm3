package android.support.a;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: compiled from: CustomTabsSession.java */
/* JADX INFO: loaded from: classes.dex */
public final class i {
    private final j D;
    private final ComponentName E;
    private final m q;

    i(m mVar, j jVar, ComponentName componentName) {
        this.q = mVar;
        this.D = jVar;
        this.E = componentName;
    }

    public boolean mayLaunchUrl(Uri uri, Bundle bundle, List<Bundle> list) {
        try {
            return this.q.a(this.D, uri, bundle, list);
        } catch (RemoteException e) {
            return false;
        }
    }

    IBinder getBinder() {
        return this.D.asBinder();
    }

    ComponentName getComponentName() {
        return this.E;
    }
}
