package android.support.v4.view;

import android.os.Build;
import android.view.WindowInsets;

/* JADX INFO: compiled from: WindowInsetsCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class ay {
    private final Object hJ;

    private ay(Object obj) {
        this.hJ = obj;
    }

    public int getSystemWindowInsetLeft() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.hJ).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int getSystemWindowInsetTop() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.hJ).getSystemWindowInsetTop();
        }
        return 0;
    }

    public int getSystemWindowInsetRight() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.hJ).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int getSystemWindowInsetBottom() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.hJ).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public ay a(int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 20) {
            return new ay(((WindowInsets) this.hJ).replaceSystemWindowInsets(i, i2, i3, i4));
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ay ayVar = (ay) obj;
        if (this.hJ == null) {
            return ayVar.hJ == null;
        }
        return this.hJ.equals(ayVar.hJ);
    }

    public int hashCode() {
        if (this.hJ == null) {
            return 0;
        }
        return this.hJ.hashCode();
    }

    static ay l(Object obj) {
        if (obj == null) {
            return null;
        }
        return new ay(obj);
    }

    static Object a(ay ayVar) {
        if (ayVar == null) {
            return null;
        }
        return ayVar.hJ;
    }
}
