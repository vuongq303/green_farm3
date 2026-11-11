package android.support.v7.widget;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

/* JADX INFO: compiled from: AppCompatDrawableManager.java */
/* JADX INFO: loaded from: classes.dex */
class ae extends android.support.v4.e.i<Integer, PorterDuffColorFilter> {
    public ae(int i) {
        super(i);
    }

    PorterDuffColorFilter b(int i, PorterDuff.Mode mode) {
        return get(Integer.valueOf(c(i, mode)));
    }

    PorterDuffColorFilter a(int i, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
        return put(Integer.valueOf(c(i, mode)), porterDuffColorFilter);
    }

    private static int c(int i, PorterDuff.Mode mode) {
        return ((i + 31) * 31) + mode.hashCode();
    }
}
