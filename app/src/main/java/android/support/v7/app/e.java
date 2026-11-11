package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.appcompat.R;
import android.support.v7.widget.bx;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.login.widget.ProfilePictureView;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class e {
    Message jA;
    Button jB;
    private CharSequence jC;
    Message jD;
    Button jE;
    private CharSequence jF;
    Message jG;
    NestedScrollView jH;
    private Drawable jJ;
    private ImageView jK;
    private TextView jL;
    private TextView jM;
    private View jN;
    ListAdapter jO;
    private int jQ;
    private int jR;
    int jS;
    int jT;
    int jU;
    int jV;
    private boolean jW;
    final ax jn;
    private final Window jo;
    private CharSequence jp;
    private CharSequence jq;
    ListView jr;
    private int js;
    private int jt;
    private int ju;
    private int jv;
    private int jw;
    Button jy;
    private CharSequence jz;
    private final Context mContext;
    Handler mHandler;
    private View mView;
    private boolean jx = false;
    private int jI = 0;
    int jP = -1;
    private int jX = 0;
    private final View.OnClickListener jY = new f(this);

    private static boolean j(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public e(Context context, ax axVar, Window window) {
        this.mContext = context;
        this.jn = axVar;
        this.jo = window;
        this.mHandler = new q(axVar);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.jQ = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_android_layout, 0);
        this.jR = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.jS = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listLayout, 0);
        this.jT = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.jU = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.jV = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listItemLayout, 0);
        this.jW = typedArrayObtainStyledAttributes.getBoolean(R.styleable.AlertDialog_showTitle, true);
        typedArrayObtainStyledAttributes.recycle();
        axVar.V(1);
    }

    static boolean x(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (x(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    public void br() {
        this.jn.setContentView(bs());
        bt();
    }

    private int bs() {
        if (this.jR == 0) {
            return this.jQ;
        }
        if (this.jX == 1) {
            return this.jR;
        }
        return this.jQ;
    }

    public void setTitle(CharSequence charSequence) {
        this.jp = charSequence;
        if (this.jL != null) {
            this.jL.setText(charSequence);
        }
    }

    public void setCustomTitle(View view) {
        this.jN = view;
    }

    public void setMessage(CharSequence charSequence) {
        this.jq = charSequence;
        if (this.jM != null) {
            this.jM.setText(charSequence);
        }
    }

    public void O(int i) {
        this.mView = null;
        this.js = i;
        this.jx = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.js = 0;
        this.jx = false;
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        this.mView = view;
        this.js = 0;
        this.jx = true;
        this.jt = i;
        this.ju = i2;
        this.jv = i3;
        this.jw = i4;
    }

    public void a(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener);
        }
        switch (i) {
            case ProfilePictureView.NORMAL /* -3 */:
                this.jF = charSequence;
                this.jG = message;
                return;
            case -2:
                this.jC = charSequence;
                this.jD = message;
                return;
            case -1:
                this.jz = charSequence;
                this.jA = message;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setIcon(int i) {
        this.jJ = null;
        this.jI = i;
        if (this.jK != null) {
            if (i != 0) {
                this.jK.setVisibility(0);
                this.jK.setImageResource(this.jI);
            } else {
                this.jK.setVisibility(8);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.jJ = drawable;
        this.jI = 0;
        if (this.jK != null) {
            if (drawable != null) {
                this.jK.setVisibility(0);
                this.jK.setImageDrawable(drawable);
            } else {
                this.jK.setVisibility(8);
            }
        }
    }

    public int P(int i) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.jH != null && this.jH.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.jH != null && this.jH.executeKeyEvent(keyEvent);
    }

    private ViewGroup b(View view, View view2) {
        if (view == null) {
            return (ViewGroup) (view2 instanceof ViewStub ? ((ViewStub) view2).inflate() : view2);
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        return (ViewGroup) (view instanceof ViewStub ? ((ViewStub) view).inflate() : view);
    }

    private void bt() {
        View viewFindViewById;
        View viewFindViewById2;
        View viewFindViewById3 = this.jo.findViewById(R.id.parentPanel);
        View viewFindViewById4 = viewFindViewById3.findViewById(R.id.topPanel);
        View viewFindViewById5 = viewFindViewById3.findViewById(R.id.contentPanel);
        View viewFindViewById6 = viewFindViewById3.findViewById(R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById3.findViewById(R.id.customPanel);
        b(viewGroup);
        View viewFindViewById7 = viewGroup.findViewById(R.id.topPanel);
        View viewFindViewById8 = viewGroup.findViewById(R.id.contentPanel);
        View viewFindViewById9 = viewGroup.findViewById(R.id.buttonPanel);
        ViewGroup viewGroupB = b(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupB2 = b(viewFindViewById8, viewFindViewById5);
        ViewGroup viewGroupB3 = b(viewFindViewById9, viewFindViewById6);
        d(viewGroupB2);
        e(viewGroupB3);
        c(viewGroupB);
        boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true;
        boolean z2 = (viewGroupB == null || viewGroupB.getVisibility() == 8) ? false : true;
        boolean z3 = (viewGroupB3 == null || viewGroupB3.getVisibility() == 8) ? false : true;
        if (!z3 && viewGroupB2 != null && (viewFindViewById2 = viewGroupB2.findViewById(R.id.textSpacerNoButtons)) != null) {
            viewFindViewById2.setVisibility(0);
        }
        if (z2) {
            if (this.jH != null) {
                this.jH.setClipToPadding(true);
            }
            View viewFindViewById10 = null;
            if (this.jq != null || this.jr != null) {
                viewFindViewById10 = viewGroupB.findViewById(R.id.titleDividerNoCustom);
            }
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        } else if (viewGroupB2 != null && (viewFindViewById = viewGroupB2.findViewById(R.id.textSpacerNoTitle)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (this.jr instanceof s) {
            ((s) this.jr).a(z2, z3);
        }
        if (!z) {
            View view = this.jr != null ? this.jr : this.jH;
            if (view != null) {
                a(viewGroupB2, view, (z3 ? 2 : 0) | (z2 ? 1 : 0), 3);
            }
        }
        ListView listView = this.jr;
        if (listView != null && this.jO != null) {
            listView.setAdapter(this.jO);
            int i = this.jP;
            if (i > -1) {
                listView.setItemChecked(i, true);
                listView.setSelection(i);
            }
        }
    }

    private void a(ViewGroup viewGroup, View view, int i, int i2) {
        View view2 = null;
        View viewFindViewById = this.jo.findViewById(R.id.scrollIndicatorUp);
        View viewFindViewById2 = this.jo.findViewById(R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT >= 23) {
            android.support.v4.view.aa.a(view, i, i2);
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById);
            }
            if (viewFindViewById2 != null) {
                viewGroup.removeView(viewFindViewById2);
                return;
            }
            return;
        }
        if (viewFindViewById != null && (i & 1) == 0) {
            viewGroup.removeView(viewFindViewById);
            viewFindViewById = null;
        }
        if (viewFindViewById2 == null || (i & 2) != 0) {
            view2 = viewFindViewById2;
        } else {
            viewGroup.removeView(viewFindViewById2);
        }
        if (viewFindViewById != null || view2 != null) {
            if (this.jq != null) {
                this.jH.a(new g(this, viewFindViewById, view2));
                this.jH.post(new h(this, viewFindViewById, view2));
            } else {
                if (this.jr != null) {
                    this.jr.setOnScrollListener(new i(this, viewFindViewById, view2));
                    this.jr.post(new j(this, viewFindViewById, view2));
                    return;
                }
                if (viewFindViewById != null) {
                    viewGroup.removeView(viewFindViewById);
                }
                if (view2 != null) {
                    viewGroup.removeView(view2);
                }
            }
        }
    }

    private void b(ViewGroup viewGroup) {
        View viewInflate;
        if (this.mView != null) {
            viewInflate = this.mView;
        } else if (this.js != 0) {
            viewInflate = LayoutInflater.from(this.mContext).inflate(this.js, viewGroup, false);
        } else {
            viewInflate = null;
        }
        boolean z = viewInflate != null;
        if (!z || !x(viewInflate)) {
            this.jo.setFlags(131072, 131072);
        }
        if (z) {
            FrameLayout frameLayout = (FrameLayout) this.jo.findViewById(R.id.custom);
            frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
            if (this.jx) {
                frameLayout.setPadding(this.jt, this.ju, this.jv, this.jw);
            }
            if (this.jr != null) {
                ((bx) viewGroup.getLayoutParams()).weight = 0.0f;
                return;
            }
            return;
        }
        viewGroup.setVisibility(8);
    }

    private void c(ViewGroup viewGroup) {
        if (this.jN != null) {
            viewGroup.addView(this.jN, 0, new ViewGroup.LayoutParams(-1, -2));
            this.jo.findViewById(R.id.title_template).setVisibility(8);
            return;
        }
        this.jK = (ImageView) this.jo.findViewById(android.R.id.icon);
        if ((!TextUtils.isEmpty(this.jp)) && this.jW) {
            this.jL = (TextView) this.jo.findViewById(R.id.alertTitle);
            this.jL.setText(this.jp);
            if (this.jI != 0) {
                this.jK.setImageResource(this.jI);
                return;
            } else if (this.jJ != null) {
                this.jK.setImageDrawable(this.jJ);
                return;
            } else {
                this.jL.setPadding(this.jK.getPaddingLeft(), this.jK.getPaddingTop(), this.jK.getPaddingRight(), this.jK.getPaddingBottom());
                this.jK.setVisibility(8);
                return;
            }
        }
        this.jo.findViewById(R.id.title_template).setVisibility(8);
        this.jK.setVisibility(8);
        viewGroup.setVisibility(8);
    }

    private void d(ViewGroup viewGroup) {
        this.jH = (NestedScrollView) this.jo.findViewById(R.id.scrollView);
        this.jH.setFocusable(false);
        this.jH.setNestedScrollingEnabled(false);
        this.jM = (TextView) viewGroup.findViewById(android.R.id.message);
        if (this.jM != null) {
            if (this.jq != null) {
                this.jM.setText(this.jq);
                return;
            }
            this.jM.setVisibility(8);
            this.jH.removeView(this.jM);
            if (this.jr != null) {
                ViewGroup viewGroup2 = (ViewGroup) this.jH.getParent();
                int iIndexOfChild = viewGroup2.indexOfChild(this.jH);
                viewGroup2.removeViewAt(iIndexOfChild);
                viewGroup2.addView(this.jr, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
                return;
            }
            viewGroup.setVisibility(8);
        }
    }

    static void a(View view, View view2, View view3) {
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4);
        }
        if (view3 != null) {
            view3.setVisibility(view.canScrollVertically(1) ? 0 : 4);
        }
    }

    private void e(ViewGroup viewGroup) {
        int i;
        this.jy = (Button) viewGroup.findViewById(android.R.id.button1);
        this.jy.setOnClickListener(this.jY);
        if (TextUtils.isEmpty(this.jz)) {
            this.jy.setVisibility(8);
            i = 0;
        } else {
            this.jy.setText(this.jz);
            this.jy.setVisibility(0);
            i = 1;
        }
        this.jB = (Button) viewGroup.findViewById(android.R.id.button2);
        this.jB.setOnClickListener(this.jY);
        if (TextUtils.isEmpty(this.jC)) {
            this.jB.setVisibility(8);
        } else {
            this.jB.setText(this.jC);
            this.jB.setVisibility(0);
            i |= 2;
        }
        this.jE = (Button) viewGroup.findViewById(android.R.id.button3);
        this.jE.setOnClickListener(this.jY);
        if (TextUtils.isEmpty(this.jF)) {
            this.jE.setVisibility(8);
        } else {
            this.jE.setText(this.jF);
            this.jE.setVisibility(0);
            i |= 4;
        }
        if (j(this.mContext)) {
            if (i == 1) {
                a(this.jy);
            } else if (i == 2) {
                a(this.jB);
            } else if (i == 4) {
                a(this.jE);
            }
        }
        if (!(i != 0)) {
            viewGroup.setVisibility(8);
        }
    }

    private void a(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }
}
