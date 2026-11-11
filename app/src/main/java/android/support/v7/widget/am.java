package android.support.v7.widget;

import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/* JADX INFO: compiled from: AppCompatPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
final class am implements ViewTreeObserver.OnScrollChangedListener {
    final /* synthetic */ Field ua;
    final /* synthetic */ PopupWindow ub;
    final /* synthetic */ ViewTreeObserver.OnScrollChangedListener uc;

    am(Field field, PopupWindow popupWindow, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        this.ua = field;
        this.ub = popupWindow;
        this.uc = onScrollChangedListener;
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        try {
            WeakReference weakReference = (WeakReference) this.ua.get(this.ub);
            if (weakReference != null && weakReference.get() != null) {
                this.uc.onScrollChanged();
            }
        } catch (IllegalAccessException e) {
        }
    }
}
