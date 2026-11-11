package com.gameloft.android2d.f;

import android.text.InputFilter;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: SystemKeyboard.java */
/* JADX INFO: loaded from: classes.dex */
final class e implements Runnable {
    final /* synthetic */ int bhK;

    e(int i) {
        this.bhK = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        d.bsb = true;
        d.brY = new b(au.getContext());
        d.brY.setText(d.brX, TextView.BufferType.EDITABLE);
        if (this.bhK == 0) {
            d.brY.setFilters(new InputFilter[]{new InputFilter.LengthFilter(d.bsd)});
            d.brY.setInputType(12290);
        } else if (this.bhK == 1) {
            d.brY.setFilters(new InputFilter[]{new InputFilter.LengthFilter(d.bsc)});
            d.brY.setInputType(1);
        }
        d.brY.setVisibility(0);
        ViewGroup viewGroupOZ = au.OZ();
        if (viewGroupOZ.indexOfChild(d.brY) == -1) {
            d.brZ.setMargins(1, 1, 1, 1);
            viewGroupOZ.addView(d.brY, d.brZ);
        }
        d.brY.requestFocus();
        d.bsa = (InputMethodManager) au.getContext().getSystemService("input_method");
        d.bsa.showSoftInput(d.brY, 2);
        d.brY.addTextChangedListener(new f(this));
        d.brY.setOnKeyListener(new g(this));
    }
}
