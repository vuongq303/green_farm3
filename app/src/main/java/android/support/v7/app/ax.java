package android.support.v7.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.appcompat.R;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: compiled from: AppCompatDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class ax extends Dialog implements v {
    private w kJ;

    public ax(Context context, int i) {
        super(context, e(context, i));
        by().onCreate(null);
        by().bA();
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        by().bz();
        super.onCreate(bundle);
        by().onCreate(bundle);
    }

    @Override // android.app.Dialog
    public void setContentView(int i) {
        by().setContentView(i);
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        by().setContentView(view);
    }

    @Override // android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        by().setContentView(view, layoutParams);
    }

    @Override // android.app.Dialog
    public <T extends View> T findViewById(int i) {
        return (T) by().findViewById(i);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        by().setTitle(charSequence);
    }

    @Override // android.app.Dialog
    public void setTitle(int i) {
        super.setTitle(i);
        by().setTitle(getContext().getString(i));
    }

    @Override // android.app.Dialog
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        by().addContentView(view, layoutParams);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        by().onStop();
    }

    public boolean V(int i) {
        return by().requestWindowFeature(i);
    }

    @Override // android.app.Dialog
    public void invalidateOptionsMenu() {
        by().invalidateOptionsMenu();
    }

    public w by() {
        if (this.kJ == null) {
            this.kJ = w.a(this, this);
        }
        return this.kJ;
    }

    private static int e(Context context, int i) {
        if (i == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, typedValue, true);
            return typedValue.resourceId;
        }
        return i;
    }

    @Override // android.support.v7.app.v
    public void a(android.support.v7.c.b bVar) {
    }

    @Override // android.support.v7.app.v
    public void b(android.support.v7.c.b bVar) {
    }

    @Override // android.support.v7.app.v
    public android.support.v7.c.b b(android.support.v7.c.c cVar) {
        return null;
    }
}
