package android.support.v4.widget;

import android.widget.ListView;

/* JADX INFO: compiled from: ListViewAutoScrollHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class o extends a {
    private final ListView iF;

    public o(ListView listView) {
        super(listView);
        this.iF = listView;
    }

    @Override // android.support.v4.widget.a
    public void f(int i, int i2) {
        p.a(this.iF, i2);
    }

    @Override // android.support.v4.widget.a
    public boolean H(int i) {
        return false;
    }

    @Override // android.support.v4.widget.a
    public boolean I(int i) {
        ListView listView = this.iF;
        int count = listView.getCount();
        if (count == 0) {
            return false;
        }
        int childCount = listView.getChildCount();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int i2 = firstVisiblePosition + childCount;
        if (i > 0) {
            if (i2 >= count && listView.getChildAt(childCount - 1).getBottom() <= listView.getHeight()) {
                return false;
            }
        } else {
            if (i >= 0) {
                return false;
            }
            if (firstVisiblePosition <= 0 && listView.getChildAt(0).getTop() >= 0) {
                return false;
            }
        }
        return true;
    }
}
