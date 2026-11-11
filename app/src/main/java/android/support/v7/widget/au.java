package android.support.v7.widget;

import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

/* JADX INFO: compiled from: AppCompatSpinner.java */
/* JADX INFO: loaded from: classes.dex */
class au implements ListAdapter, SpinnerAdapter {
    private SpinnerAdapter uu;
    private ListAdapter uv;

    public au(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
        this.uu = spinnerAdapter;
        if (spinnerAdapter instanceof ListAdapter) {
            this.uv = (ListAdapter) spinnerAdapter;
        }
        if (theme != null) {
            if (Build.VERSION.SDK_INT >= 23 && (spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
                if (themedSpinnerAdapter.getDropDownViewTheme() != theme) {
                    themedSpinnerAdapter.setDropDownViewTheme(theme);
                    return;
                }
                return;
            }
            if (spinnerAdapter instanceof cw) {
                cw cwVar = (cw) spinnerAdapter;
                if (cwVar.getDropDownViewTheme() == null) {
                    cwVar.setDropDownViewTheme(theme);
                }
            }
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.uu == null) {
            return 0;
        }
        return this.uu.getCount();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        if (this.uu == null) {
            return null;
        }
        return this.uu.getItem(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        if (this.uu == null) {
            return -1L;
        }
        return this.uu.getItemId(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getDropDownView(i, view, viewGroup);
    }

    @Override // android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (this.uu == null) {
            return null;
        }
        return this.uu.getDropDownView(i, view, viewGroup);
    }

    @Override // android.widget.Adapter
    public boolean hasStableIds() {
        return this.uu != null && this.uu.hasStableIds();
    }

    @Override // android.widget.Adapter
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.uu != null) {
            this.uu.registerDataSetObserver(dataSetObserver);
        }
    }

    @Override // android.widget.Adapter
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.uu != null) {
            this.uu.unregisterDataSetObserver(dataSetObserver);
        }
    }

    @Override // android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        ListAdapter listAdapter = this.uv;
        if (listAdapter != null) {
            return listAdapter.areAllItemsEnabled();
        }
        return true;
    }

    @Override // android.widget.ListAdapter
    public boolean isEnabled(int i) {
        ListAdapter listAdapter = this.uv;
        if (listAdapter != null) {
            return listAdapter.isEnabled(i);
        }
        return true;
    }

    @Override // android.widget.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.widget.Adapter
    public int getViewTypeCount() {
        return 1;
    }

    @Override // android.widget.Adapter
    public boolean isEmpty() {
        return getCount() == 0;
    }
}
