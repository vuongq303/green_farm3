package com.google.android.gms.c;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.m;

/* JADX INFO: loaded from: classes.dex */
public abstract class e<T> {
    private final String zzabo;
    private T zzabp;

    protected e(String str) {
        this.zzabo = str;
    }

    protected abstract T getRemoteCreator(IBinder iBinder);

    protected final T getRemoteCreatorInstance(Context context) throws f {
        if (this.zzabp == null) {
            bf.checkNotNull(context);
            Context remoteContext = m.getRemoteContext(context);
            if (remoteContext == null) {
                throw new f("Could not get remote context.");
            }
            try {
                this.zzabp = getRemoteCreator((IBinder) remoteContext.getClassLoader().loadClass(this.zzabo).newInstance());
            } catch (ClassNotFoundException e) {
                throw new f("Could not load creator class.", e);
            } catch (IllegalAccessException e2) {
                throw new f("Could not access creator.", e2);
            } catch (InstantiationException e3) {
                throw new f("Could not instantiate creator.", e3);
            }
        }
        return this.zzabp;
    }
}
