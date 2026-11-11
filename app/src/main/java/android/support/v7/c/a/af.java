package android.support.v7.c.a;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: MenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class af implements ai, ao, AdapterView.OnItemClickListener {
    private Rect rl;

    public abstract void e(s sVar);

    public abstract void q(boolean z);

    public abstract void setAnchorView(View view);

    public abstract void setForceShowIcon(boolean z);

    public abstract void setGravity(int i);

    public abstract void setHorizontalOffset(int i);

    public abstract void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener);

    public abstract void setVerticalOffset(int i);

    af() {
    }

    public void setEpicenterBounds(Rect rect) {
        this.rl = rect;
    }

    public Rect getEpicenterBounds() {
        return this.rl;
    }

    @Override // android.support.v7.c.a.ai
    public void a(Context context, s sVar) {
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(s sVar, w wVar) {
        return false;
    }

    @Override // android.support.v7.c.a.ai
    public boolean b(s sVar, w wVar) {
        return false;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter();
        a(listAdapter).qq.a((MenuItem) listAdapter.getItem(i), this, cQ() ? 0 : 4);
    }

    protected static int a(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int i) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int i2 = 0;
        int i3 = 0;
        View view = null;
        int i4 = 0;
        ViewGroup frameLayout = viewGroup;
        while (i2 < count) {
            int itemViewType = listAdapter.getItemViewType(i2);
            if (itemViewType != i3) {
                i3 = itemViewType;
                view = null;
            }
            if (frameLayout == null) {
                frameLayout = new FrameLayout(context);
            }
            view = listAdapter.getView(i2, view, frameLayout);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth < i) {
                if (measuredWidth <= i4) {
                    measuredWidth = i4;
                }
                i2++;
                i4 = measuredWidth;
            } else {
                return i;
            }
        }
        return i4;
    }

    protected static r a(ListAdapter listAdapter) {
        return listAdapter instanceof HeaderViewListAdapter ? (r) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (r) listAdapter;
    }

    protected static boolean i(s sVar) {
        int size = sVar.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = sVar.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                return true;
            }
        }
        return false;
    }

    protected boolean cQ() {
        return true;
    }
}
