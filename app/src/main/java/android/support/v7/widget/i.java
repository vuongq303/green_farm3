package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class i extends android.support.v7.c.a.e implements android.support.v4.view.h {
    m sA;
    private Drawable sB;
    private boolean sC;
    private boolean sD;
    private boolean sE;
    private int sF;
    private int sG;
    private int sH;
    private boolean sI;
    private boolean sJ;
    private boolean sK;
    private boolean sL;
    private int sM;
    private final SparseBooleanArray sN;
    private View sO;
    o sP;
    j sQ;
    l sR;
    private k sS;
    final p sT;
    int sU;

    public i(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
        this.sN = new SparseBooleanArray();
        this.sT = new p(this);
    }

    @Override // android.support.v7.c.a.e, android.support.v7.c.a.ai
    public void a(Context context, android.support.v7.c.a.s sVar) {
        super.a(context, sVar);
        Resources resources = context.getResources();
        android.support.v7.c.a aVarM = android.support.v7.c.a.m(context);
        if (!this.sE) {
            this.sD = aVarM.cm();
        }
        if (!this.sK) {
            this.sF = aVarM.cn();
        }
        if (!this.sI) {
            this.sH = aVarM.cl();
        }
        int measuredWidth = this.sF;
        if (this.sD) {
            if (this.sA == null) {
                this.sA = new m(this, this.pl);
                if (this.sC) {
                    this.sA.setImageDrawable(this.sB);
                    this.sB = null;
                    this.sC = false;
                }
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.sA.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.sA.getMeasuredWidth();
        } else {
            this.sA = null;
        }
        this.sG = measuredWidth;
        this.sM = (int) (56.0f * resources.getDisplayMetrics().density);
        this.sO = null;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.sI) {
            this.sH = android.support.v7.c.a.m(this.mContext).cl();
        }
        if (this.nw != null) {
            this.nw.v(true);
        }
    }

    public void H(boolean z) {
        this.sD = z;
        this.sE = true;
    }

    public void I(boolean z) {
        this.sL = z;
    }

    @Override // android.support.v7.c.a.e
    public android.support.v7.c.a.ak g(ViewGroup viewGroup) {
        android.support.v7.c.a.ak akVar = this.pq;
        android.support.v7.c.a.ak akVarG = super.g(viewGroup);
        if (akVar != akVarG) {
            ((q) akVarG).g(this);
        }
        return akVarG;
    }

    @Override // android.support.v7.c.a.e
    public View a(android.support.v7.c.a.w wVar, View view, ViewGroup viewGroup) {
        View actionView = wVar.getActionView();
        if (actionView == null || wVar.dw()) {
            actionView = super.a(wVar, view, viewGroup);
        }
        actionView.setVisibility(wVar.isActionViewExpanded() ? 8 : 0);
        q qVar = (q) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!qVar.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(qVar.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // android.support.v7.c.a.e
    public void a(android.support.v7.c.a.w wVar, android.support.v7.c.a.al alVar) {
        alVar.a(wVar, 0);
        android.support.v7.c.a.b bVar = (android.support.v7.c.a.b) alVar;
        bVar.a((q) this.pq);
        if (this.sS == null) {
            this.sS = new k(this);
        }
        bVar.a(this.sS);
    }

    @Override // android.support.v7.c.a.e
    public boolean a(int i, android.support.v7.c.a.w wVar) {
        return wVar.ds();
    }

    @Override // android.support.v7.c.a.e, android.support.v7.c.a.ai
    public void p(boolean z) {
        boolean z2 = false;
        super.p(z);
        ((View) this.pq).requestLayout();
        if (this.nw != null) {
            ArrayList<android.support.v7.c.a.w> arrayListDf = this.nw.df();
            int size = arrayListDf.size();
            for (int i = 0; i < size; i++) {
                android.support.v4.view.g gVarAw = arrayListDf.get(i).aw();
                if (gVarAw != null) {
                    gVarAw.a(this);
                }
            }
        }
        ArrayList<android.support.v7.c.a.w> arrayListDg = this.nw != null ? this.nw.dg() : null;
        if (this.sD && arrayListDg != null) {
            int size2 = arrayListDg.size();
            if (size2 == 1) {
                z2 = !arrayListDg.get(0).isActionViewExpanded();
            } else {
                z2 = size2 > 0;
            }
        }
        if (z2) {
            if (this.sA == null) {
                this.sA = new m(this, this.pl);
            }
            ViewGroup viewGroup = (ViewGroup) this.sA.getParent();
            if (viewGroup != this.pq) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.sA);
                }
                q qVar = (q) this.pq;
                qVar.addView(this.sA, qVar.dZ());
            }
        } else if (this.sA != null && this.sA.getParent() == this.pq) {
            ((ViewGroup) this.pq).removeView(this.sA);
        }
        ((q) this.pq).J(this.sD);
    }

    @Override // android.support.v7.c.a.e
    public boolean a(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.sA) {
            return false;
        }
        return super.a(viewGroup, i);
    }

    @Override // android.support.v7.c.a.e, android.support.v7.c.a.ai
    public boolean a(android.support.v7.c.a.as asVar) {
        boolean z;
        if (!asVar.hasVisibleItems()) {
            return false;
        }
        android.support.v7.c.a.as asVar2 = asVar;
        while (asVar2.dC() != this.nw) {
            asVar2 = (android.support.v7.c.a.as) asVar2.dC();
        }
        View viewD = d(asVar2.getItem());
        if (viewD == null) {
            return false;
        }
        this.sU = asVar.getItem().getItemId();
        int size = asVar.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = asVar.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                this.sQ = new j(this, this.mContext, asVar, viewD);
                this.sQ.setForceShowIcon(z);
                this.sQ.show();
                super.a(asVar);
                return true;
            }
        }
        z = false;
        this.sQ = new j(this, this.mContext, asVar, viewD);
        this.sQ.setForceShowIcon(z);
        this.sQ.show();
        super.a(asVar);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private View d(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.pq;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof android.support.v7.c.a.al) && ((android.support.v7.c.a.al) childAt).cE() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() {
        if (!this.sD || isOverflowMenuShowing() || this.nw == null || this.pq == null || this.sR != null || this.nw.dg().isEmpty()) {
            return false;
        }
        this.sR = new l(this, new o(this, this.mContext, this.nw, this.sA, true));
        ((View) this.pq).post(this.sR);
        super.a((android.support.v7.c.a.as) null);
        return true;
    }

    public boolean hideOverflowMenu() {
        if (this.sR != null && this.pq != null) {
            ((View) this.pq).removeCallbacks(this.sR);
            this.sR = null;
            return true;
        }
        o oVar = this.sP;
        if (oVar != null) {
            oVar.dismiss();
            return true;
        }
        return false;
    }

    public boolean dU() {
        return hideOverflowMenu() | dV();
    }

    public boolean dV() {
        if (this.sQ == null) {
            return false;
        }
        this.sQ.dismiss();
        return true;
    }

    public boolean isOverflowMenuShowing() {
        return this.sP != null && this.sP.isShowing();
    }

    public boolean dS() {
        return this.sR != null || isOverflowMenuShowing();
    }

    @Override // android.support.v7.c.a.e, android.support.v7.c.a.ai
    public boolean cM() {
        int size;
        ArrayList<android.support.v7.c.a.w> arrayList;
        int i;
        int i2;
        int measuredWidth;
        boolean z;
        int i3;
        int i4;
        if (this.nw != null) {
            ArrayList<android.support.v7.c.a.w> arrayListDd = this.nw.dd();
            size = arrayListDd.size();
            arrayList = arrayListDd;
        } else {
            size = 0;
            arrayList = null;
        }
        int i5 = this.sH;
        int i6 = this.sG;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) this.pq;
        int i7 = 0;
        int i8 = 0;
        boolean z2 = false;
        int i9 = 0;
        while (i9 < size) {
            android.support.v7.c.a.w wVar = arrayList.get(i9);
            if (wVar.du()) {
                i7++;
            } else if (wVar.dt()) {
                i8++;
            } else {
                z2 = true;
            }
            i9++;
            i5 = (this.sL && wVar.isActionViewExpanded()) ? 0 : i5;
        }
        if (this.sD && (z2 || i7 + i8 > i5)) {
            i5--;
        }
        int i10 = i5 - i7;
        SparseBooleanArray sparseBooleanArray = this.sN;
        sparseBooleanArray.clear();
        int i11 = 0;
        if (!this.sJ) {
            i = 0;
        } else {
            i11 = i6 / this.sM;
            i = ((i6 % this.sM) / i11) + this.sM;
        }
        int i12 = 0;
        int iA = i11;
        int i13 = 0;
        while (i12 < size) {
            android.support.v7.c.a.w wVar2 = arrayList.get(i12);
            if (wVar2.du()) {
                View viewA = a(wVar2, this.sO, viewGroup);
                if (this.sO == null) {
                    this.sO = viewA;
                }
                if (this.sJ) {
                    iA -= q.a(viewA, i, iA, iMakeMeasureSpec, 0);
                } else {
                    viewA.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                }
                measuredWidth = viewA.getMeasuredWidth();
                int i14 = i6 - measuredWidth;
                if (i13 != 0) {
                    measuredWidth = i13;
                }
                int groupId = wVar2.getGroupId();
                if (groupId != 0) {
                    sparseBooleanArray.put(groupId, true);
                }
                wVar2.A(true);
                i2 = iA;
                i6 = i14;
            } else if (wVar2.dt()) {
                int groupId2 = wVar2.getGroupId();
                boolean z3 = sparseBooleanArray.get(groupId2);
                boolean z4 = (i10 > 0 || z3) && i6 > 0 && (!this.sJ || iA > 0);
                if (z4) {
                    View viewA2 = a(wVar2, this.sO, viewGroup);
                    if (this.sO == null) {
                        this.sO = viewA2;
                    }
                    if (this.sJ) {
                        int iA2 = q.a(viewA2, i, iA, iMakeMeasureSpec, 0);
                        i3 = iA - iA2;
                        z4 = iA2 == 0 ? false : z4;
                    } else {
                        viewA2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                        i3 = iA;
                    }
                    int measuredWidth2 = viewA2.getMeasuredWidth();
                    i6 -= measuredWidth2;
                    if (i13 == 0) {
                        i13 = measuredWidth2;
                    }
                    if (this.sJ) {
                        z = z4 & (i6 >= 0);
                    } else {
                        z = z4 & (i6 + i13 > 0);
                    }
                } else {
                    z = z4;
                    i3 = iA;
                }
                if (z && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                    i4 = i10;
                } else if (z3) {
                    sparseBooleanArray.put(groupId2, false);
                    int i15 = i10;
                    for (int i16 = 0; i16 < i12; i16++) {
                        android.support.v7.c.a.w wVar3 = arrayList.get(i16);
                        if (wVar3.getGroupId() == groupId2) {
                            if (wVar3.ds()) {
                                i15++;
                            }
                            wVar3.A(false);
                        }
                    }
                    i4 = i15;
                } else {
                    i4 = i10;
                }
                if (z) {
                    i4--;
                }
                wVar2.A(z);
                i2 = i3;
                measuredWidth = i13;
                i10 = i4;
            } else {
                wVar2.A(false);
                i2 = iA;
                measuredWidth = i13;
            }
            i12++;
            iA = i2;
            i13 = measuredWidth;
        }
        return true;
    }

    @Override // android.support.v7.c.a.e, android.support.v7.c.a.ai
    public void b(android.support.v7.c.a.s sVar, boolean z) {
        dU();
        super.b(sVar, z);
    }

    public void a(q qVar) {
        this.pq = qVar;
        qVar.h(this.nw);
    }
}
