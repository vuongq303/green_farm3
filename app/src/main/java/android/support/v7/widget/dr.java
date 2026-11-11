package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: VectorEnabledTintResources.java */
/* JADX INFO: loaded from: classes.dex */
public class dr extends Resources {
    private final WeakReference<Context> xQ;

    public static boolean fp() {
        return android.support.v7.app.w.bC() && Build.VERSION.SDK_INT <= 20;
    }

    public dr(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.xQ = new WeakReference<>(context);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) {
        Context context = this.xQ.get();
        return context != null ? ac.eg().a(context, this, i) : super.getDrawable(i);
    }

    final Drawable az(int i) {
        return super.getDrawable(i);
    }
}
