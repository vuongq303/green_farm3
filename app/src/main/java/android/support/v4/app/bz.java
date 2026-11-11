package android.support.v4.app;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.share.internal.ShareConstants;
import java.util.List;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class bz {
    private final CharSequence dH;
    private final long dI;
    private final CharSequence dJ;
    private String dK;
    private Uri dL;
    private Bundle mExtras;

    public CharSequence getText() {
        return this.dH;
    }

    public long getTimestamp() {
        return this.dI;
    }

    public CharSequence getSender() {
        return this.dJ;
    }

    public String getDataMimeType() {
        return this.dK;
    }

    public Uri getDataUri() {
        return this.dL;
    }

    private Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (this.dH != null) {
            bundle.putCharSequence("text", this.dH);
        }
        bundle.putLong("time", this.dI);
        if (this.dJ != null) {
            bundle.putCharSequence("sender", this.dJ);
        }
        if (this.dK != null) {
            bundle.putString("type", this.dK);
        }
        if (this.dL != null) {
            bundle.putParcelable(ShareConstants.MEDIA_URI, this.dL);
        }
        if (this.mExtras != null) {
            bundle.putBundle("extras", this.mExtras);
        }
        return bundle;
    }

    static Bundle[] b(List<bz> list) {
        Bundle[] bundleArr = new Bundle[list.size()];
        int size = list.size();
        for (int i = 0; i < size; i++) {
            bundleArr[i] = list.get(i).toBundle();
        }
        return bundleArr;
    }
}
