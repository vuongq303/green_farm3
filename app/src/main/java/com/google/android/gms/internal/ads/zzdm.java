package com.google.android.gms.internal.ads;

import android.provider.Settings;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdm extends zzei {
    public zzdm(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2) {
        super(zzczVar, str, str2, zzbaVar, i, 49);
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() throws InvocationTargetException {
        this.zztq.zzem = 2;
        try {
            this.zztq.zzem = Integer.valueOf(((Boolean) this.zztz.invoke(null, this.zzps.getContext())).booleanValue() ? 1 : 0);
        } catch (InvocationTargetException e) {
            if (!(e.getTargetException() instanceof Settings.SettingNotFoundException)) {
                throw e;
            }
        }
    }
}
