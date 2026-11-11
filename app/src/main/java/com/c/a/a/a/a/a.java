package com.c.a.a.a.a;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: AvidActivityStack.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private static a cjl = new a();
    private final ArrayList<com.c.a.a.a.i.a> cjm = new ArrayList<>();

    public static a aiT() {
        return cjl;
    }

    public void s(Activity activity) {
        if (t(activity) == null) {
            this.cjm.add(new com.c.a.a.a.i.a(activity));
        }
    }

    public List<View> aiU() {
        ArrayList arrayList = new ArrayList();
        View view = null;
        Iterator<com.c.a.a.a.i.a> it = this.cjm.iterator();
        while (it.hasNext()) {
            com.c.a.a.a.i.a next = it.next();
            if (a(next)) {
                it.remove();
            } else {
                View viewB = b(next);
                if (viewB == null) {
                    viewB = view;
                }
                view = viewB;
            }
        }
        if (view != null) {
            arrayList.add(view);
        }
        return arrayList;
    }

    public void bM() {
        this.cjm.clear();
    }

    com.c.a.a.a.i.a t(Activity activity) {
        for (com.c.a.a.a.i.a aVar : this.cjm) {
            if (aVar.contains(activity)) {
                return aVar;
            }
        }
        return null;
    }

    boolean a(com.c.a.a.a.i.a aVar) {
        Activity activity = aVar.get();
        if (activity == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            return activity.isDestroyed();
        }
        return activity.isFinishing();
    }

    private View b(com.c.a.a.a.i.a aVar) {
        Window window;
        Activity activity = aVar.get();
        if (activity == null || (window = activity.getWindow()) == null || !activity.hasWindowFocus()) {
            return null;
        }
        View decorView = window.getDecorView();
        if (decorView == null || !decorView.isShown()) {
            decorView = null;
        }
        return decorView;
    }
}
