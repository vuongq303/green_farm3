package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.ArrayList;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class bv {
    public CharSequence cS;
    public CharSequence cT;
    PendingIntent cU;
    PendingIntent cV;
    RemoteViews cW;
    public Bitmap cX;
    public CharSequence cY;
    public int cZ;
    private int dA;
    public Notification dB;
    public ArrayList<String> dC;
    int da;
    boolean db;
    public boolean dc;
    public cj dd;
    public CharSequence de;
    public CharSequence[] df;
    int dg;
    int dh;
    boolean di;
    String dj;
    boolean dk;
    String dl;
    public ArrayList<br> dm;
    boolean dn;

    /* JADX INFO: renamed from: do, reason: not valid java name */
    boolean f0do;
    boolean dp;
    int dq;
    int dr;
    Notification ds;
    RemoteViews dt;
    RemoteViews du;
    RemoteViews dv;
    String dw;
    int dx;
    String dy;
    long dz;
    String mCategory;
    public Context mContext;
    Bundle mExtras;

    public bv(Context context, String str) {
        this.db = true;
        this.dm = new ArrayList<>();
        this.dn = false;
        this.dq = 0;
        this.dr = 0;
        this.dx = 0;
        this.dA = 0;
        this.dB = new Notification();
        this.mContext = context;
        this.dw = str;
        this.dB.when = System.currentTimeMillis();
        this.dB.audioStreamType = -1;
        this.da = 0;
        this.dC = new ArrayList<>();
    }

    @Deprecated
    public bv(Context context) {
        this(context, null);
    }

    public bv c(long j) {
        this.dB.when = j;
        return this;
    }

    public bv l(int i) {
        this.dB.icon = i;
        return this;
    }

    public bv b(CharSequence charSequence) {
        this.cS = e(charSequence);
        return this;
    }

    public bv c(CharSequence charSequence) {
        this.cT = e(charSequence);
        return this;
    }

    public bv m(int i) {
        this.cZ = i;
        return this;
    }

    public bv a(PendingIntent pendingIntent) {
        this.cU = pendingIntent;
        return this;
    }

    public bv b(PendingIntent pendingIntent) {
        this.dB.deleteIntent = pendingIntent;
        return this;
    }

    public bv d(CharSequence charSequence) {
        this.dB.tickerText = e(charSequence);
        return this;
    }

    public bv a(Bitmap bitmap) {
        this.cX = bitmap;
        return this;
    }

    public bv a(Uri uri) {
        this.dB.sound = uri;
        this.dB.audioStreamType = -1;
        return this;
    }

    public bv d(boolean z) {
        c(16, z);
        return this;
    }

    public bv e(boolean z) {
        this.dn = z;
        return this;
    }

    public bv n(int i) {
        this.dB.defaults = i;
        if ((i & 4) != 0) {
            this.dB.flags |= 1;
        }
        return this;
    }

    private void c(int i, boolean z) {
        if (z) {
            this.dB.flags |= i;
        } else {
            this.dB.flags &= i ^ (-1);
        }
    }

    public bv a(cj cjVar) {
        if (this.dd != cjVar) {
            this.dd = cjVar;
            if (this.dd != null) {
                this.dd.b(this);
            }
        }
        return this;
    }

    public bv o(int i) {
        this.dq = i;
        return this;
    }

    public bv i(String str) {
        this.dw = str;
        return this;
    }

    public Notification build() {
        return bq.cJ.a(this, ae());
    }

    protected bw ae() {
        return new bw();
    }

    protected static CharSequence e(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() > 5120) {
            return charSequence.subSequence(0, 5120);
        }
        return charSequence;
    }

    protected CharSequence af() {
        return this.cT;
    }

    protected CharSequence ag() {
        return this.cS;
    }
}
