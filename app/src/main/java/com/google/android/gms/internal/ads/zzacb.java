package com.google.android.gms.internal.ads;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.gms.common.d.s;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
final class zzacb implements zzalz<zzon> {
    private final /* synthetic */ String zzbwo;
    private final /* synthetic */ zzabv zzcal;
    private final /* synthetic */ boolean zzcav;
    private final /* synthetic */ double zzcaw;
    private final /* synthetic */ boolean zzcax;

    zzacb(zzabv zzabvVar, boolean z, double d, boolean z2, String str) {
        this.zzcal = zzabvVar;
        this.zzcav = z;
        this.zzcaw = d;
        this.zzcax = z2;
        this.zzbwo = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzalz
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzon zze(InputStream inputStream) {
        Bitmap bitmapDecodeStream;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = (int) (160.0d * this.zzcaw);
        if (!this.zzcax) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        try {
            bitmapDecodeStream = BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            zzakb.zzb("Error grabbing image.", e);
            bitmapDecodeStream = null;
        }
        if (bitmapDecodeStream == null) {
            this.zzcal.zzd(2, this.zzcav);
            return null;
        }
        long jUptimeMillis2 = SystemClock.uptimeMillis();
        if (s.abA() && zzakb.zzqp()) {
            int width = bitmapDecodeStream.getWidth();
            zzakb.v(new StringBuilder(108).append("Decoded image w: ").append(width).append(" h:").append(bitmapDecodeStream.getHeight()).append(" bytes: ").append(bitmapDecodeStream.getAllocationByteCount()).append(" time: ").append(jUptimeMillis2 - jUptimeMillis).append(" on ui thread: ").append(Looper.getMainLooper().getThread() == Thread.currentThread()).toString());
        }
        return new zzon(new BitmapDrawable(Resources.getSystem(), bitmapDecodeStream), Uri.parse(this.zzbwo), this.zzcaw);
    }

    @Override // com.google.android.gms.internal.ads.zzalz
    public final /* synthetic */ zzon zzny() {
        this.zzcal.zzd(2, this.zzcav);
        return null;
    }
}
