package com.gameloft.android2d.socialnetwork;

import android.database.CursorIndexOutOfBoundsException;
import com.gameloft.android2d.socialnetwork.i;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: GraphObjectCursor_SimpleManager.java */
/* JADX INFO: loaded from: classes.dex */
public class h<T extends i> {
    private int pos = -1;
    private boolean closed = false;
    private ArrayList<i> bpL = new ArrayList<>();
    private boolean bpM = false;
    private boolean bpN = false;

    h() {
    }

    public void a(Collection<T> collection, boolean z) {
        this.bpL.size();
        for (T t : collection) {
            i iVarUS = r.US();
            this.bpL.add(iVarUS);
            iVarUS.bL(t.UO());
            iVarUS.setName(t.getName());
            iVarUS.gU(t.getFirstName());
            iVarUS.gV(t.getLastName());
            iVarUS.gW(t.getMiddleName());
            iVarUS.fG(t.getId());
            iVarUS.gX(t.getCountry());
            iVarUS.a(t.UQ());
        }
        this.bpN |= z;
    }

    public int getCount() {
        return this.bpL.size();
    }

    public i mS(int i) {
        if (i < 0) {
            throw new CursorIndexOutOfBoundsException("Before first object.");
        }
        if (i >= this.bpL.size()) {
            throw new CursorIndexOutOfBoundsException("After last object.");
        }
        return this.bpL.get(i);
    }
}
