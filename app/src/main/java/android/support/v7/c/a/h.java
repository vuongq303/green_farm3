package android.support.v7.c.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.v7.appcompat.R;
import android.support.v7.widget.ci;
import android.support.v7.widget.cj;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: compiled from: CascadingMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
final class h extends af implements ai, View.OnKeyListener, PopupWindow.OnDismissListener {
    private boolean jW;
    private final Context mContext;
    private View pG;
    View pH;
    private boolean pJ;
    private boolean pK;
    private int pL;
    private int pM;
    private aj pO;
    private ViewTreeObserver pP;
    private PopupWindow.OnDismissListener pQ;
    boolean pR;
    private final int pu;
    private final int pv;
    private final int pw;
    private final boolean px;
    final Handler py;
    private final List<s> pz = new LinkedList();
    final List<m> pA = new ArrayList();
    private final ViewTreeObserver.OnGlobalLayoutListener pB = new i(this);
    private final View.OnAttachStateChangeListener pC = new j(this);
    private final ci pD = new k(this);
    private int pE = 0;
    private int pF = 0;
    private boolean pN = false;
    private int pI = cP();

    public h(Context context, View view, int i, int i2, boolean z) {
        this.mContext = context;
        this.pG = view;
        this.pv = i;
        this.pw = i2;
        this.px = z;
        Resources resources = context.getResources();
        this.pu = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.py = new Handler();
    }

    @Override // android.support.v7.c.a.af
    public void setForceShowIcon(boolean z) {
        this.pN = z;
    }

    private cj cO() {
        cj cjVar = new cj(this.mContext, null, this.pv, this.pw);
        cjVar.a(this.pD);
        cjVar.setOnItemClickListener(this);
        cjVar.setOnDismissListener(this);
        cjVar.setAnchorView(this.pG);
        cjVar.setDropDownGravity(this.pF);
        cjVar.setModal(true);
        cjVar.setInputMethodMode(2);
        return cjVar;
    }

    @Override // android.support.v7.c.a.ao
    public void show() {
        if (!isShowing()) {
            Iterator<s> it = this.pz.iterator();
            while (it.hasNext()) {
                f(it.next());
            }
            this.pz.clear();
            this.pH = this.pG;
            if (this.pH != null) {
                boolean z = this.pP == null;
                this.pP = this.pH.getViewTreeObserver();
                if (z) {
                    this.pP.addOnGlobalLayoutListener(this.pB);
                }
                this.pH.addOnAttachStateChangeListener(this.pC);
            }
        }
    }

    @Override // android.support.v7.c.a.ao
    public void dismiss() {
        int size = this.pA.size();
        if (size > 0) {
            m[] mVarArr = (m[]) this.pA.toArray(new m[size]);
            for (int i = size - 1; i >= 0; i--) {
                m mVar = mVarArr[i];
                if (mVar.pX.isShowing()) {
                    mVar.pX.dismiss();
                }
            }
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    private int cP() {
        return android.support.v4.view.aa.f(this.pG) == 1 ? 0 : 1;
    }

    private int ab(int i) {
        ListView listView = this.pA.get(this.pA.size() - 1).getListView();
        int[] iArr = new int[2];
        listView.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.pH.getWindowVisibleDisplayFrame(rect);
        if (this.pI == 1) {
            return (listView.getWidth() + iArr[0]) + i > rect.right ? 0 : 1;
        }
        return iArr[0] - i < 0 ? 1 : 0;
    }

    @Override // android.support.v7.c.a.af
    public void e(s sVar) {
        sVar.a(this, this.mContext);
        if (isShowing()) {
            f(sVar);
        } else {
            this.pz.add(sVar);
        }
    }

    private void f(s sVar) {
        View viewA;
        m mVar;
        int i;
        int i2;
        int width;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        r rVar = new r(sVar, layoutInflaterFrom, this.px);
        if (!isShowing() && this.pN) {
            rVar.setForceShowIcon(true);
        } else if (isShowing()) {
            rVar.setForceShowIcon(af.i(sVar));
        }
        int iA = a(rVar, null, this.mContext, this.pu);
        cj cjVarCO = cO();
        cjVarCO.setAdapter(rVar);
        cjVarCO.setContentWidth(iA);
        cjVarCO.setDropDownGravity(this.pF);
        if (this.pA.size() > 0) {
            m mVar2 = this.pA.get(this.pA.size() - 1);
            viewA = a(mVar2, sVar);
            mVar = mVar2;
        } else {
            viewA = null;
            mVar = null;
        }
        if (viewA != null) {
            cjVarCO.setTouchModal(false);
            cjVarCO.setEnterTransition(null);
            int iAb = ab(iA);
            boolean z = iAb == 1;
            this.pI = iAb;
            if (Build.VERSION.SDK_INT >= 26) {
                cjVarCO.setAnchorView(viewA);
                i2 = 0;
                i = 0;
            } else {
                int[] iArr = new int[2];
                this.pG.getLocationOnScreen(iArr);
                int[] iArr2 = new int[2];
                viewA.getLocationOnScreen(iArr2);
                i = iArr2[0] - iArr[0];
                i2 = iArr2[1] - iArr[1];
            }
            if ((this.pF & 5) == 5) {
                if (z) {
                    width = i + iA;
                } else {
                    width = i - viewA.getWidth();
                }
            } else if (z) {
                width = viewA.getWidth() + i;
            } else {
                width = i - iA;
            }
            cjVarCO.setHorizontalOffset(width);
            cjVarCO.setOverlapAnchor(true);
            cjVarCO.setVerticalOffset(i2);
        } else {
            if (this.pJ) {
                cjVarCO.setHorizontalOffset(this.pL);
            }
            if (this.pK) {
                cjVarCO.setVerticalOffset(this.pM);
            }
            cjVarCO.setEpicenterBounds(getEpicenterBounds());
        }
        this.pA.add(new m(cjVarCO, sVar, this.pI));
        cjVarCO.show();
        ListView listView = cjVarCO.getListView();
        listView.setOnKeyListener(this);
        if (mVar == null && this.jW && sVar.dh() != null) {
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false);
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
            frameLayout.setEnabled(false);
            textView.setText(sVar.dh());
            listView.addHeaderView(frameLayout, null, false);
            cjVarCO.show();
        }
    }

    private MenuItem a(s sVar, s sVar2) {
        int size = sVar.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = sVar.getItem(i);
            if (item.hasSubMenu() && sVar2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    private View a(m mVar, s sVar) {
        r rVar;
        int headersCount;
        int i;
        int i2 = 0;
        MenuItem menuItemA = a(mVar.lZ, sVar);
        if (menuItemA == null) {
            return null;
        }
        ListView listView = mVar.getListView();
        ListAdapter adapter = listView.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            headersCount = headerViewListAdapter.getHeadersCount();
            rVar = (r) headerViewListAdapter.getWrappedAdapter();
        } else {
            rVar = (r) adapter;
            headersCount = 0;
        }
        int count = rVar.getCount();
        while (true) {
            if (i2 >= count) {
                i = -1;
                break;
            }
            if (menuItemA == rVar.getItem(i2)) {
                i = i2;
                break;
            }
            i2++;
        }
        if (i == -1) {
            return null;
        }
        int firstVisiblePosition = (i + headersCount) - listView.getFirstVisiblePosition();
        if (firstVisiblePosition < 0 || firstVisiblePosition >= listView.getChildCount()) {
            return null;
        }
        return listView.getChildAt(firstVisiblePosition);
    }

    @Override // android.support.v7.c.a.ao
    public boolean isShowing() {
        return this.pA.size() > 0 && this.pA.get(0).pX.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        m mVar;
        int size = this.pA.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                mVar = null;
                break;
            }
            mVar = this.pA.get(i);
            if (!mVar.pX.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (mVar != null) {
            mVar.lZ.u(false);
        }
    }

    @Override // android.support.v7.c.a.ai
    public void p(boolean z) {
        Iterator<m> it = this.pA.iterator();
        while (it.hasNext()) {
            a(it.next().getListView().getAdapter()).notifyDataSetChanged();
        }
    }

    @Override // android.support.v7.c.a.ai
    public void b(aj ajVar) {
        this.pO = ajVar;
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(as asVar) {
        for (m mVar : this.pA) {
            if (asVar == mVar.lZ) {
                mVar.getListView().requestFocus();
                return true;
            }
        }
        if (asVar.hasVisibleItems()) {
            e(asVar);
            if (this.pO != null) {
                this.pO.c(asVar);
            }
            return true;
        }
        return false;
    }

    private int g(s sVar) {
        int size = this.pA.size();
        for (int i = 0; i < size; i++) {
            if (sVar == this.pA.get(i).lZ) {
                return i;
            }
        }
        return -1;
    }

    @Override // android.support.v7.c.a.ai
    public void b(s sVar, boolean z) {
        int iG = g(sVar);
        if (iG >= 0) {
            int i = iG + 1;
            if (i < this.pA.size()) {
                this.pA.get(i).lZ.u(false);
            }
            m mVarRemove = this.pA.remove(iG);
            mVarRemove.lZ.b(this);
            if (this.pR) {
                mVarRemove.pX.setExitTransition(null);
                mVarRemove.pX.setAnimationStyle(0);
            }
            mVarRemove.pX.dismiss();
            int size = this.pA.size();
            if (size > 0) {
                this.pI = this.pA.get(size - 1).position;
            } else {
                this.pI = cP();
            }
            if (size == 0) {
                dismiss();
                if (this.pO != null) {
                    this.pO.b(sVar, true);
                }
                if (this.pP != null) {
                    if (this.pP.isAlive()) {
                        this.pP.removeGlobalOnLayoutListener(this.pB);
                    }
                    this.pP = null;
                }
                this.pH.removeOnAttachStateChangeListener(this.pC);
                this.pQ.onDismiss();
                return;
            }
            if (z) {
                this.pA.get(0).lZ.u(false);
            }
        }
    }

    @Override // android.support.v7.c.a.ai
    public boolean cM() {
        return false;
    }

    @Override // android.support.v7.c.a.af
    public void setGravity(int i) {
        if (this.pE != i) {
            this.pE = i;
            this.pF = android.support.v4.view.j.getAbsoluteGravity(i, android.support.v4.view.aa.f(this.pG));
        }
    }

    @Override // android.support.v7.c.a.af
    public void setAnchorView(View view) {
        if (this.pG != view) {
            this.pG = view;
            this.pF = android.support.v4.view.j.getAbsoluteGravity(this.pE, android.support.v4.view.aa.f(this.pG));
        }
    }

    @Override // android.support.v7.c.a.af
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.pQ = onDismissListener;
    }

    @Override // android.support.v7.c.a.ao
    public ListView getListView() {
        if (this.pA.isEmpty()) {
            return null;
        }
        return this.pA.get(this.pA.size() - 1).getListView();
    }

    @Override // android.support.v7.c.a.af
    public void setHorizontalOffset(int i) {
        this.pJ = true;
        this.pL = i;
    }

    @Override // android.support.v7.c.a.af
    public void setVerticalOffset(int i) {
        this.pK = true;
        this.pM = i;
    }

    @Override // android.support.v7.c.a.af
    public void q(boolean z) {
        this.jW = z;
    }

    @Override // android.support.v7.c.a.af
    protected boolean cQ() {
        return false;
    }
}
