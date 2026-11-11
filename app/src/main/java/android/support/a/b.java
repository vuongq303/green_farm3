package android.support.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.text.TextUtils;

/* JADX INFO: compiled from: CustomTabsClient.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private final m q;
    private final ComponentName r;

    b(m mVar, ComponentName componentName) {
        this.q = mVar;
        this.r = componentName;
    }

    public static boolean a(Context context, String str, g gVar) {
        Intent intent = new Intent("android.support.customtabs.action.CustomTabsService");
        if (!TextUtils.isEmpty(str)) {
            intent.setPackage(str);
        }
        return context.bindService(intent, gVar, 33);
    }

    public boolean b(long j) {
        try {
            return this.q.b(j);
        } catch (RemoteException e) {
            return false;
        }
    }

    public i a(a aVar) {
        c cVar = new c(this, aVar);
        try {
            if (this.q.a(cVar)) {
                return new i(this.q, cVar, this.r);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }
}
