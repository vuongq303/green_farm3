package android.support.v7.c;

import android.view.InflateException;
import android.view.MenuItem;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: SupportMenuInflater.java */
/* JADX INFO: loaded from: classes.dex */
class j implements MenuItem.OnMenuItemClickListener {
    private static final Class<?>[] nU = {MenuItem.class};
    private Object nT;
    private Method nV;

    public j(Object obj, String str) {
        this.nT = obj;
        Class<?> cls = obj.getClass();
        try {
            this.nV = cls.getMethod(str, nU);
        } catch (Exception e) {
            InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
            inflateException.initCause(e);
            throw inflateException;
        }
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem) {
        try {
            if (this.nV.getReturnType() == Boolean.TYPE) {
                return ((Boolean) this.nV.invoke(this.nT, menuItem)).booleanValue();
            }
            this.nV.invoke(this.nT, menuItem);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
