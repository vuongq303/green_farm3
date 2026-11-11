package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: compiled from: TintContextWrapper.java */
/* JADX INFO: loaded from: classes.dex */
public class cx extends ContextWrapper {
    private static final Object xL = new Object();
    private static ArrayList<WeakReference<cx>> xM;
    private final Resources kL;
    private final Resources.Theme nH;

    public static Context t(Context context) {
        if (u(context)) {
            synchronized (xL) {
                if (xM == null) {
                    xM = new ArrayList<>();
                } else {
                    for (int size = xM.size() - 1; size >= 0; size--) {
                        WeakReference<cx> weakReference = xM.get(size);
                        if (weakReference == null || weakReference.get() == null) {
                            xM.remove(size);
                        }
                    }
                    for (int size2 = xM.size() - 1; size2 >= 0; size2--) {
                        WeakReference<cx> weakReference2 = xM.get(size2);
                        cx cxVar = weakReference2 != null ? weakReference2.get() : null;
                        if (cxVar != null && cxVar.getBaseContext() == context) {
                            return cxVar;
                        }
                    }
                }
                cx cxVar2 = new cx(context);
                xM.add(new WeakReference<>(cxVar2));
                return cxVar2;
            }
        }
        return context;
    }

    private static boolean u(Context context) {
        if ((context instanceof cx) || (context.getResources() instanceof cz) || (context.getResources() instanceof dr)) {
            return false;
        }
        return Build.VERSION.SDK_INT < 21 || dr.fp();
    }

    private cx(Context context) {
        super(context);
        if (dr.fp()) {
            this.kL = new dr(this, context.getResources());
            this.nH = this.kL.newTheme();
            this.nH.setTo(context.getTheme());
        } else {
            this.kL = new cz(this, context.getResources());
            this.nH = null;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        return this.nH == null ? super.getTheme() : this.nH;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        if (this.nH == null) {
            super.setTheme(i);
        } else {
            this.nH.applyStyle(i, true);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        return this.kL;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return this.kL.getAssets();
    }
}
