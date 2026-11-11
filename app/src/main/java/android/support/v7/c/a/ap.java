package android.support.v7.c.a;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.appcompat.R;
import android.support.v7.widget.cj;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/* JADX INFO: compiled from: StandardMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
final class ap extends af implements ai, View.OnKeyListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {
    private boolean jW;
    private final Context mContext;
    private final s nw;
    private final ViewTreeObserver.OnGlobalLayoutListener pB = new aq(this);
    private final View.OnAttachStateChangeListener pC = new ar(this);
    private int pF = 0;
    private View pG;
    View pH;
    private aj pO;
    private ViewTreeObserver pP;
    private PopupWindow.OnDismissListener pQ;
    private final int pv;
    private final int pw;
    private final boolean px;
    private final r rp;
    private final int rq;
    final cj rr;
    private boolean rs;
    private boolean rt;
    private int ru;

    public ap(Context context, s sVar, View view, int i, int i2, boolean z) {
        this.mContext = context;
        this.nw = sVar;
        this.px = z;
        this.rp = new r(sVar, LayoutInflater.from(context), this.px);
        this.pv = i;
        this.pw = i2;
        Resources resources = context.getResources();
        this.rq = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.pG = view;
        this.rr = new cj(this.mContext, null, this.pv, this.pw);
        sVar.a(this, context);
    }

    @Override // android.support.v7.c.a.af
    public void setForceShowIcon(boolean z) {
        this.rp.setForceShowIcon(z);
    }

    @Override // android.support.v7.c.a.af
    public void setGravity(int i) {
        this.pF = i;
    }

    private boolean dA() {
        if (isShowing()) {
            return true;
        }
        if (this.rs || this.pG == null) {
            return false;
        }
        this.pH = this.pG;
        this.rr.setOnDismissListener(this);
        this.rr.setOnItemClickListener(this);
        this.rr.setModal(true);
        View view = this.pH;
        boolean z = this.pP == null;
        this.pP = view.getViewTreeObserver();
        if (z) {
            this.pP.addOnGlobalLayoutListener(this.pB);
        }
        view.addOnAttachStateChangeListener(this.pC);
        this.rr.setAnchorView(view);
        this.rr.setDropDownGravity(this.pF);
        if (!this.rt) {
            this.ru = a(this.rp, null, this.mContext, this.rq);
            this.rt = true;
        }
        this.rr.setContentWidth(this.ru);
        this.rr.setInputMethodMode(2);
        this.rr.setEpicenterBounds(getEpicenterBounds());
        this.rr.show();
        ListView listView = this.rr.getListView();
        listView.setOnKeyListener(this);
        if (this.jW && this.nw.dh() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false);
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
            if (textView != null) {
                textView.setText(this.nw.dh());
            }
            frameLayout.setEnabled(false);
            listView.addHeaderView(frameLayout, null, false);
        }
        this.rr.setAdapter(this.rp);
        this.rr.show();
        return true;
    }

    @Override // android.support.v7.c.a.ao
    public void show() {
        if (!dA()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // android.support.v7.c.a.ao
    public void dismiss() {
        if (isShowing()) {
            this.rr.dismiss();
        }
    }

    @Override // android.support.v7.c.a.af
    public void e(s sVar) {
    }

    @Override // android.support.v7.c.a.ao
    public boolean isShowing() {
        return !this.rs && this.rr.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.rs = true;
        this.nw.close();
        if (this.pP != null) {
            if (!this.pP.isAlive()) {
                this.pP = this.pH.getViewTreeObserver();
            }
            this.pP.removeGlobalOnLayoutListener(this.pB);
            this.pP = null;
        }
        this.pH.removeOnAttachStateChangeListener(this.pC);
        if (this.pQ != null) {
            this.pQ.onDismiss();
        }
    }

    @Override // android.support.v7.c.a.ai
    public void p(boolean z) {
        this.rt = false;
        if (this.rp != null) {
            this.rp.notifyDataSetChanged();
        }
    }

    @Override // android.support.v7.c.a.ai
    public void b(aj ajVar) {
        this.pO = ajVar;
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(as asVar) {
        if (asVar.hasVisibleItems()) {
            ag agVar = new ag(this.mContext, asVar, this.pH, this.px, this.pv, this.pw);
            agVar.c(this.pO);
            agVar.setForceShowIcon(af.i(asVar));
            agVar.setGravity(this.pF);
            agVar.setOnDismissListener(this.pQ);
            this.pQ = null;
            this.nw.u(false);
            if (agVar.j(this.rr.getHorizontalOffset(), this.rr.getVerticalOffset())) {
                if (this.pO != null) {
                    this.pO.c(asVar);
                }
                return true;
            }
        }
        return false;
    }

    @Override // android.support.v7.c.a.ai
    public void b(s sVar, boolean z) {
        if (sVar == this.nw) {
            dismiss();
            if (this.pO != null) {
                this.pO.b(sVar, z);
            }
        }
    }

    @Override // android.support.v7.c.a.ai
    public boolean cM() {
        return false;
    }

    @Override // android.support.v7.c.a.af
    public void setAnchorView(View view) {
        this.pG = view;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // android.support.v7.c.a.af
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.pQ = onDismissListener;
    }

    @Override // android.support.v7.c.a.ao
    public ListView getListView() {
        return this.rr.getListView();
    }

    @Override // android.support.v7.c.a.af
    public void setHorizontalOffset(int i) {
        this.rr.setHorizontalOffset(i);
    }

    @Override // android.support.v7.c.a.af
    public void setVerticalOffset(int i) {
        this.rr.setVerticalOffset(i);
    }

    @Override // android.support.v7.c.a.af
    public void q(boolean z) {
        this.jW = z;
    }
}
