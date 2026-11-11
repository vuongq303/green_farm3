package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: AppCompatViewInflater.java */
/* JADX INFO: loaded from: classes.dex */
class az implements View.OnClickListener {
    private final View mo;
    private final String mp;
    private Method mq;
    private Context mr;

    public az(View view, String str) {
        this.mo = view;
        this.mp = str;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mq == null) {
            e(this.mo.getContext(), this.mp);
        }
        try {
            this.mq.invoke(this.mr, view);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
        } catch (InvocationTargetException e2) {
            throw new IllegalStateException("Could not execute method for android:onClick", e2);
        }
    }

    private void e(Context context, String str) {
        Method method;
        Context baseContext = context;
        while (baseContext != null) {
            try {
                if (!baseContext.isRestricted() && (method = baseContext.getClass().getMethod(this.mp, View.class)) != null) {
                    this.mq = method;
                    this.mr = baseContext;
                    return;
                }
            } catch (NoSuchMethodException e) {
            }
            if (baseContext instanceof ContextWrapper) {
                baseContext = ((ContextWrapper) baseContext).getBaseContext();
            } else {
                baseContext = null;
            }
        }
        int id = this.mo.getId();
        throw new IllegalStateException("Could not find method " + this.mp + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mo.getClass() + (id == -1 ? "" : " with id '" + this.mo.getContext().getResources().getResourceEntryName(id) + "'"));
    }
}
