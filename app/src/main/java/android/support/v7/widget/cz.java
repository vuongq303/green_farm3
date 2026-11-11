package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: TintResources.java */
/* JADX INFO: loaded from: classes.dex */
class cz extends cl {
    private final WeakReference<Context> xQ;

    public cz(Context context, Resources resources) {
        super(resources);
        this.xQ = new WeakReference<>(context);
    }

    @Override // android.support.v7.widget.cl, android.content.res.Resources
    public Drawable getDrawable(int i) {
        Drawable drawable = super.getDrawable(i);
        Context context = this.xQ.get();
        if (drawable != null && context != null) {
            ac.eg();
            ac.a(context, i, drawable);
        }
        return drawable;
    }
}
