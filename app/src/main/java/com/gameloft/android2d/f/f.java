package com.gameloft.android2d.f;

import android.text.Editable;
import android.text.TextWatcher;

/* JADX INFO: compiled from: SystemKeyboard.java */
/* JADX INFO: loaded from: classes.dex */
class f implements TextWatcher {
    final /* synthetic */ e bse;

    f(e eVar) {
        this.bse = eVar;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        d.brX = d.brY.getText().toString();
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        d.brX = d.brY.getText().toString();
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }
}
