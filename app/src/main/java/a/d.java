package a;

import android.net.Uri;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: AppLink.java */
/* JADX INFO: loaded from: classes.dex */
public class d {
    private Uri zp;
    private List<e> zq;
    private Uri zr;

    public d(Uri uri, List<e> list, Uri uri2) {
        this.zp = uri;
        this.zq = list == null ? Collections.emptyList() : list;
        this.zr = uri2;
    }
}
