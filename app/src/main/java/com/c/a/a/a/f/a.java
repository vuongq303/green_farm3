package com.c.a.a.a.f;

import android.app.Activity;
import android.view.View;
import com.c.a.a.a.h;
import java.util.UUID;

/* JADX INFO: compiled from: AbstractAvidAdSession.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a<T extends View> {
    private String cjy = UUID.randomUUID().toString();

    public String aiZ() {
        return this.cjy;
    }

    public void a(T t, Activity activity) {
        com.c.a.a.a.f.a.a aVarIN = h.aiF().iN(this.cjy);
        if (aVarIN != null) {
            aVarIN.R(t);
        }
        h.aiF().r(activity);
    }

    public void aja() {
        com.c.a.a.a.f.a.a aVarIN = h.aiF().iN(aiZ());
        if (aVarIN != null) {
            aVarIN.cB();
        }
    }
}
