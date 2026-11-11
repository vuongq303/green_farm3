package com.gameloft.android2d.f;

import android.content.Context;
import android.os.Build;
import android.view.KeyEvent;
import android.widget.EditText;
import com.gameloft.android.wrapper.au;
import com.gameloft.android.wrapper.z;

/* JADX INFO: compiled from: ListenerEditText.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends EditText {
    private c brW;

    public b(Context context) {
        super(context);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        a.gC(i);
        String str = " onKeyPreIme close keyboard keyCode = " + i;
        if (i == 4) {
            d.bsb = false;
            setVisibility(4);
            if (Build.VERSION.SDK_INT >= 19) {
                z.k(au.getActivity());
            }
        }
        if (this.brW != null) {
            this.brW.e(i, keyEvent);
        }
        return false;
    }
}
