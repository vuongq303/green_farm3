package android.support.v7.c;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/* JADX INFO: compiled from: ActionMode.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class b {
    private Object nE;
    private boolean nF;

    public abstract void finish();

    public abstract View getCustomView();

    public abstract Menu getMenu();

    public abstract MenuInflater getMenuInflater();

    public abstract CharSequence getSubtitle();

    public abstract CharSequence getTitle();

    public abstract void invalidate();

    public abstract void setCustomView(View view);

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(CharSequence charSequence);

    public abstract void setTitle(int i);

    public abstract void setTitle(CharSequence charSequence);

    public void setTag(Object obj) {
        this.nE = obj;
    }

    public Object getTag() {
        return this.nE;
    }

    public void setTitleOptionalHint(boolean z) {
        this.nF = z;
    }

    public boolean getTitleOptionalHint() {
        return this.nF;
    }

    public boolean isTitleOptional() {
        return false;
    }
}
