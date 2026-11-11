package android.support.v4.d;

import android.net.Uri;
import android.support.v4.e.q;

/* JADX INFO: compiled from: FontsContractCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class h {
    private final int fZ;
    private final int fl;
    private final boolean fm;
    private final int ga;
    private final Uri mUri;

    public h(Uri uri, int i, int i2, boolean z, int i3) {
        this.mUri = (Uri) q.checkNotNull(uri);
        this.fZ = i;
        this.fl = i2;
        this.fm = z;
        this.ga = i3;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public int getTtcIndex() {
        return this.fZ;
    }

    public int getWeight() {
        return this.fl;
    }

    public boolean isItalic() {
        return this.fm;
    }

    public int getResultCode() {
        return this.ga;
    }
}
