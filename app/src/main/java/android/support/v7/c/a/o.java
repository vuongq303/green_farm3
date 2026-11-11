package android.support.v7.c.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/* JADX INFO: compiled from: ListMenuItemView.java */
/* JADX INFO: loaded from: classes.dex */
public class o extends LinearLayout implements al {
    private ImageView jK;
    private TextView jL;
    private LayoutInflater kc;
    private boolean pN;
    private RadioButton pZ;
    private w pb;
    private CheckBox qa;
    private TextView qb;
    private ImageView qc;
    private Drawable qd;
    private int qe;
    private Context qf;
    private boolean qg;
    private Drawable qh;
    private int qi;

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        android.support.v4.view.aa.setBackground(this, this.qd);
        this.jL = (TextView) findViewById(R.id.title);
        if (this.qe != -1) {
            this.jL.setTextAppearance(this.qf, this.qe);
        }
        this.qb = (TextView) findViewById(R.id.shortcut);
        this.qc = (ImageView) findViewById(R.id.submenuarrow);
        if (this.qc != null) {
            this.qc.setImageDrawable(this.qh);
        }
    }

    @Override // android.support.v7.c.a.al
    public void a(w wVar, int i) {
        this.pb = wVar;
        this.qi = i;
        setVisibility(wVar.isVisible() ? 0 : 8);
        setTitle(wVar.a(this));
        setCheckable(wVar.isCheckable());
        a(wVar.dq(), wVar.m0do());
        setIcon(wVar.getIcon());
        setEnabled(wVar.isEnabled());
        r(wVar.hasSubMenu());
        setContentDescription(wVar.getContentDescription());
    }

    public void setForceShowIcon(boolean z) {
        this.pN = z;
        this.qg = z;
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.jL.setText(charSequence);
            if (this.jL.getVisibility() != 0) {
                this.jL.setVisibility(0);
                return;
            }
            return;
        }
        if (this.jL.getVisibility() != 8) {
            this.jL.setVisibility(8);
        }
    }

    @Override // android.support.v7.c.a.al
    public w cE() {
        return this.pb;
    }

    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (z || this.pZ != null || this.qa != null) {
            if (this.pb.dr()) {
                if (this.pZ == null) {
                    cS();
                }
                compoundButton = this.pZ;
                compoundButton2 = this.qa;
            } else {
                if (this.qa == null) {
                    cT();
                }
                compoundButton = this.qa;
                compoundButton2 = this.pZ;
            }
            if (z) {
                compoundButton.setChecked(this.pb.isChecked());
                int i = z ? 0 : 8;
                if (compoundButton.getVisibility() != i) {
                    compoundButton.setVisibility(i);
                }
                if (compoundButton2 != null && compoundButton2.getVisibility() != 8) {
                    compoundButton2.setVisibility(8);
                    return;
                }
                return;
            }
            if (this.qa != null) {
                this.qa.setVisibility(8);
            }
            if (this.pZ != null) {
                this.pZ.setVisibility(8);
            }
        }
    }

    private void r(boolean z) {
        if (this.qc != null) {
            this.qc.setVisibility(z ? 0 : 8);
        }
    }

    public void a(boolean z, char c) {
        int i = (z && this.pb.dq()) ? 0 : 8;
        if (i == 0) {
            this.qb.setText(this.pb.dp());
        }
        if (this.qb.getVisibility() != i) {
            this.qb.setVisibility(i);
        }
    }

    public void setIcon(Drawable drawable) {
        boolean z = this.pb.shouldShowIcon() || this.pN;
        if (z || this.qg) {
            if (this.jK != null || drawable != null || this.qg) {
                if (this.jK == null) {
                    cR();
                }
                if (drawable != null || this.qg) {
                    ImageView imageView = this.jK;
                    if (!z) {
                        drawable = null;
                    }
                    imageView.setImageDrawable(drawable);
                    if (this.jK.getVisibility() != 0) {
                        this.jK.setVisibility(0);
                        return;
                    }
                    return;
                }
                this.jK.setVisibility(8);
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.jK != null && this.qg) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.jK.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i, i2);
    }

    private void cR() {
        this.jK = (ImageView) cU().inflate(R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
        addView(this.jK, 0);
    }

    private void cS() {
        this.pZ = (RadioButton) cU().inflate(R.layout.abc_list_menu_item_radio, (ViewGroup) this, false);
        addView(this.pZ);
    }

    private void cT() {
        this.qa = (CheckBox) cU().inflate(R.layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
        addView(this.qa);
    }

    @Override // android.support.v7.c.a.al
    public boolean cF() {
        return false;
    }

    private LayoutInflater cU() {
        if (this.kc == null) {
            this.kc = LayoutInflater.from(getContext());
        }
        return this.kc;
    }
}
