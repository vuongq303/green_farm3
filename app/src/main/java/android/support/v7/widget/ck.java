package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

/* JADX INFO: compiled from: MenuPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
public class ck extends bq {
    private ci wI;
    final int wJ;
    final int wK;
    private MenuItem wL;

    @Override // android.support.v7.widget.bq
    public /* bridge */ /* synthetic */ boolean a(MotionEvent motionEvent, int i) {
        return super.a(motionEvent, i);
    }

    @Override // android.support.v7.widget.bq, android.view.ViewGroup, android.view.View
    public /* bridge */ /* synthetic */ boolean hasFocus() {
        return super.hasFocus();
    }

    @Override // android.support.v7.widget.bq, android.view.View
    public /* bridge */ /* synthetic */ boolean hasWindowFocus() {
        return super.hasWindowFocus();
    }

    @Override // android.support.v7.widget.bq, android.view.View
    public /* bridge */ /* synthetic */ boolean isFocused() {
        return super.isFocused();
    }

    @Override // android.support.v7.widget.bq, android.view.View
    public /* bridge */ /* synthetic */ boolean isInTouchMode() {
        return super.isInTouchMode();
    }

    public ck(Context context, boolean z) {
        super(context, z);
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= 17 && 1 == configuration.getLayoutDirection()) {
            this.wJ = 21;
            this.wK = 22;
        } else {
            this.wJ = 22;
            this.wK = 21;
        }
    }

    public void a(ci ciVar) {
        this.wI = ciVar;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        android.support.v7.c.a.o oVar = (android.support.v7.c.a.o) getSelectedView();
        if (oVar != null && i == this.wJ) {
            if (oVar.isEnabled() && oVar.cE().hasSubMenu()) {
                performItemClick(oVar, getSelectedItemPosition(), getSelectedItemId());
            }
            return true;
        }
        if (oVar != null && i == this.wK) {
            setSelection(-1);
            ((android.support.v7.c.a.r) getAdapter()).cW().u(false);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int headersCount;
        android.support.v7.c.a.r rVar;
        android.support.v7.c.a.w item;
        int iPointToPosition;
        int i;
        if (this.wI != null) {
            ListAdapter adapter = getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                headersCount = headerViewListAdapter.getHeadersCount();
                rVar = (android.support.v7.c.a.r) headerViewListAdapter.getWrappedAdapter();
            } else {
                headersCount = 0;
                rVar = (android.support.v7.c.a.r) adapter;
            }
            if (motionEvent.getAction() == 10 || (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) == -1 || (i = iPointToPosition - headersCount) < 0 || i >= rVar.getCount()) {
                item = null;
            } else {
                item = rVar.getItem(i);
            }
            MenuItem menuItem = this.wL;
            if (menuItem != item) {
                android.support.v7.c.a.s sVarCW = rVar.cW();
                if (menuItem != null) {
                    this.wI.b(sVarCW, menuItem);
                }
                this.wL = item;
                if (item != null) {
                    this.wI.c(sVarCW, item);
                }
            }
        }
        return super.onHoverEvent(motionEvent);
    }
}
