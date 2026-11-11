package com.google.android.gms.internal.ads;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;

/* JADX INFO: loaded from: classes.dex */
final class zzaaf implements DialogInterface.OnClickListener {
    private final /* synthetic */ String zzbwo;
    private final /* synthetic */ String zzbwp;
    private final /* synthetic */ zzaae zzbwq;

    zzaaf(zzaae zzaaeVar, String str, String str2) {
        this.zzbwq = zzaaeVar;
        this.zzbwo = str;
        this.zzbwp = str2;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        DownloadManager downloadManager = (DownloadManager) this.zzbwq.mContext.getSystemService("download");
        try {
            String str = this.zzbwo;
            String str2 = this.zzbwp;
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, str2);
            com.google.android.gms.ads.internal.zzbv.zzem().zza(request);
            downloadManager.enqueue(request);
        } catch (IllegalStateException e) {
            this.zzbwq.zzbw("Could not store picture.");
        }
    }
}
