package android.support.v7.c.a;

import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

/* JADX INFO: compiled from: MenuWrapperFactory.java */
/* JADX INFO: loaded from: classes.dex */
public final class am {
    public static Menu a(Context context, android.support.v4.b.a.a aVar) {
        return new an(context, aVar);
    }

    public static MenuItem a(Context context, android.support.v4.b.a.b bVar) {
        return Build.VERSION.SDK_INT >= 16 ? new ad(context, bVar) : new y(context, bVar);
    }

    public static SubMenu a(Context context, android.support.v4.b.a.c cVar) {
        return new at(context, cVar);
    }
}
