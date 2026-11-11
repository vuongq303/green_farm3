package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ListAdapter;

/* JADX INFO: compiled from: AlertDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class u {
    private final k kI;
    private final int mTheme;

    public u(Context context) {
        this(context, t.d(context, 0));
    }

    public u(Context context, int i) {
        this.kI = new k(new ContextThemeWrapper(context, t.d(context, i)));
        this.mTheme = i;
    }

    public Context getContext() {
        return this.kI.mContext;
    }

    public u i(CharSequence charSequence) {
        this.kI.jp = charSequence;
        return this;
    }

    public u y(View view) {
        this.kI.jN = view;
        return this;
    }

    public u f(Drawable drawable) {
        this.kI.jJ = drawable;
        return this;
    }

    public u a(DialogInterface.OnKeyListener onKeyListener) {
        this.kI.km = onKeyListener;
        return this;
    }

    public u a(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
        this.kI.jO = listAdapter;
        this.kI.ko = onClickListener;
        return this;
    }

    public t bu() {
        t tVar = new t(this.kI.mContext, this.mTheme);
        this.kI.a(tVar.kH);
        tVar.setCancelable(this.kI.mCancelable);
        if (this.kI.mCancelable) {
            tVar.setCanceledOnTouchOutside(true);
        }
        tVar.setOnCancelListener(this.kI.kk);
        tVar.setOnDismissListener(this.kI.kl);
        if (this.kI.km != null) {
            tVar.setOnKeyListener(this.kI.km);
        }
        return tVar;
    }
}
