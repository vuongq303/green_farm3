package com.a.a.a.a.a;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public final class d extends ContentObserver {
    private final Context cdD;
    private final AudioManager cdE;
    private final a cdF;
    private final c cdG;
    private float cdH;

    public d(Handler handler, Context context, a aVar, c cVar) {
        super(handler);
        this.cdD = context;
        this.cdE = (AudioManager) context.getSystemService("audio");
        this.cdF = aVar;
        this.cdG = cVar;
    }

    private float ahC() {
        return this.cdF.cH(this.cdE.getStreamVolume(3), this.cdE.getStreamMaxVolume(3));
    }

    private void d() {
        this.cdG.k(this.cdH);
    }

    private boolean l(float f) {
        return f != this.cdH;
    }

    public void a() {
        this.cdH = ahC();
        d();
        this.cdD.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this);
    }

    public void b() {
        this.cdD.getContentResolver().unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        float fAhC = ahC();
        if (l(fAhC)) {
            this.cdH = fAhC;
            d();
        }
    }
}
