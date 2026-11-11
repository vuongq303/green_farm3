package android.support.a;

import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import java.util.List;

/* JADX INFO: compiled from: ICustomTabsService.java */
/* JADX INFO: loaded from: classes.dex */
public interface m extends IInterface {
    boolean a(j jVar);

    boolean a(j jVar, Uri uri, Bundle bundle, List<Bundle> list);

    boolean a(j jVar, Bundle bundle);

    Bundle b(String str, Bundle bundle);

    boolean b(long j);
}
