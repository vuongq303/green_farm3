package android.support.v7.widget;

import android.content.Context;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;

/* JADX INFO: compiled from: DecorToolbar.java */
/* JADX INFO: loaded from: classes.dex */
public interface bo {
    void L(boolean z);

    android.support.v4.view.ar a(int i, long j);

    void a(android.support.v7.c.a.aj ajVar, android.support.v7.c.a.t tVar);

    void a(Menu menu, android.support.v7.c.a.aj ajVar);

    void b(cq cqVar);

    void b(Window.Callback callback);

    void collapseActionView();

    boolean dR();

    boolean dS();

    void dT();

    void dismissPopupMenus();

    ViewGroup eE();

    void eF();

    void eG();

    Context getContext();

    int getDisplayOptions();

    Menu getMenu();

    int getNavigationMode();

    void h(CharSequence charSequence);

    boolean hasExpandedActionView();

    boolean hideOverflowMenu();

    boolean isOverflowMenuShowing();

    void setDisplayOptions(int i);

    void setHomeButtonEnabled(boolean z);

    void setVisibility(int i);

    boolean showOverflowMenu();
}
