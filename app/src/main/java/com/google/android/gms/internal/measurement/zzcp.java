package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.bf;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class zzcp {
    private int zzaax;
    private ByteArrayOutputStream zzaay = new ByteArrayOutputStream();
    private final /* synthetic */ zzco zzaaz;

    public zzcp(zzco zzcoVar) {
        this.zzaaz = zzcoVar;
    }

    public final byte[] getPayload() {
        return this.zzaay.toByteArray();
    }

    public final boolean zze(zzch zzchVar) {
        bf.checkNotNull(zzchVar);
        if (this.zzaax + 1 > zzbu.zzdy()) {
            return false;
        }
        String strZza = this.zzaaz.zza(zzchVar, false);
        if (strZza == null) {
            this.zzaaz.zzbu().zza(zzchVar, "Error formatting hit");
            return true;
        }
        byte[] bytes = strZza.getBytes();
        int length = bytes.length;
        if (length > zzbu.zzdu()) {
            this.zzaaz.zzbu().zza(zzchVar, "Hit size exceeds the maximum size limit");
            return true;
        }
        if (this.zzaay.size() > 0) {
            length++;
        }
        if (this.zzaay.size() + length > zzcc.zzzf.get().intValue()) {
            return false;
        }
        try {
            if (this.zzaay.size() > 0) {
                this.zzaay.write(zzco.zzaaw);
            }
            this.zzaay.write(bytes);
            this.zzaax++;
            return true;
        } catch (IOException e) {
            this.zzaaz.zze("Failed to write payload when batching hits", e);
            return true;
        }
    }

    public final int zzez() {
        return this.zzaax;
    }
}
