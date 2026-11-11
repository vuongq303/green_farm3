package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class zzyy extends IOException {
    private zzzq zzbvf;

    public zzyy(String str) {
        super(str);
        this.zzbvf = null;
    }

    static zzyy zzue() {
        return new zzyy("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzyy zzuf() {
        return new zzyy("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }
}
