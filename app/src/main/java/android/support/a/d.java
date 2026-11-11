package android.support.a;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/* JADX INFO: compiled from: CustomTabsIntent.java */
/* JADX INFO: loaded from: classes.dex */
public final class d {
    public final Intent intent;
    public final Bundle u;

    public void a(Context context, Uri uri) {
        this.intent.setData(uri);
        android.support.v4.content.c.startActivity(context, this.intent, this.u);
    }

    private d(Intent intent, Bundle bundle) {
        this.intent = intent;
        this.u = bundle;
    }
}
