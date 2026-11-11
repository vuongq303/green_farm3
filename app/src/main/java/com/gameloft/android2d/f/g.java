package com.gameloft.android2d.f;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import com.gameloft.android.wrapper.au;
import com.gameloft.android.wrapper.z;

/* JADX INFO: compiled from: SystemKeyboard.java */
/* JADX INFO: loaded from: classes.dex */
class g implements View.OnKeyListener {
    final /* synthetic */ e bse;

    g(e eVar) {
        this.bse = eVar;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        String str = " setOnKeyListener close keyboard keyCode = " + i;
        if (i != 66) {
            return false;
        }
        a.gC(66);
        d.bsb = false;
        d.bsa.hideSoftInputFromWindow(d.brY.getWindowToken(), 0);
        d.brY.setVisibility(4);
        if (Build.VERSION.SDK_INT >= 19) {
            z.k(au.getActivity());
        }
        return true;
    }
}
