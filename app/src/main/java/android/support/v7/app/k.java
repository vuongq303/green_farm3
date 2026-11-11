package android.support.v7.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    public Drawable jJ;
    public View jN;
    public ListAdapter jO;
    public CharSequence jp;
    public CharSequence jq;
    public int js;
    public int jt;
    public int ju;
    public int jv;
    public int jw;
    public final LayoutInflater kc;
    public CharSequence ke;
    public DialogInterface.OnClickListener kf;
    public CharSequence kg;
    public DialogInterface.OnClickListener kh;
    public CharSequence ki;
    public DialogInterface.OnClickListener kj;
    public DialogInterface.OnCancelListener kk;
    public DialogInterface.OnDismissListener kl;
    public DialogInterface.OnKeyListener km;
    public CharSequence[] kn;
    public DialogInterface.OnClickListener ko;
    public boolean[] kp;
    public boolean kq;
    public boolean kr;
    public DialogInterface.OnMultiChoiceClickListener ks;
    public Cursor kt;
    public String ku;
    public String kv;
    public AdapterView.OnItemSelectedListener kw;
    public p kx;
    public final Context mContext;
    public View mView;
    public int jI = 0;
    public int kd = 0;
    public boolean jx = false;
    public int jP = -1;
    public boolean ky = true;
    public boolean mCancelable = true;

    public k(Context context) {
        this.mContext = context;
        this.kc = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public void a(e eVar) {
        if (this.jN != null) {
            eVar.setCustomTitle(this.jN);
        } else {
            if (this.jp != null) {
                eVar.setTitle(this.jp);
            }
            if (this.jJ != null) {
                eVar.setIcon(this.jJ);
            }
            if (this.jI != 0) {
                eVar.setIcon(this.jI);
            }
            if (this.kd != 0) {
                eVar.setIcon(eVar.P(this.kd));
            }
        }
        if (this.jq != null) {
            eVar.setMessage(this.jq);
        }
        if (this.ke != null) {
            eVar.a(-1, this.ke, this.kf, (Message) null);
        }
        if (this.kg != null) {
            eVar.a(-2, this.kg, this.kh, (Message) null);
        }
        if (this.ki != null) {
            eVar.a(-3, this.ki, this.kj, (Message) null);
        }
        if (this.kn != null || this.kt != null || this.jO != null) {
            b(eVar);
        }
        if (this.mView != null) {
            if (this.jx) {
                eVar.setView(this.mView, this.jt, this.ju, this.jv, this.jw);
                return;
            } else {
                eVar.setView(this.mView);
                return;
            }
        }
        if (this.js != 0) {
            eVar.O(this.js);
        }
    }

    private void b(e eVar) {
        int i;
        ListAdapter rVar;
        s sVar = (s) this.kc.inflate(eVar.jS, (ViewGroup) null);
        if (this.kq) {
            if (this.kt == null) {
                rVar = new l(this, this.mContext, eVar.jT, R.id.text1, this.kn, sVar);
            } else {
                rVar = new m(this, this.mContext, this.kt, false, sVar, eVar);
            }
        } else {
            if (this.kr) {
                i = eVar.jU;
            } else {
                i = eVar.jV;
            }
            if (this.kt != null) {
                rVar = new SimpleCursorAdapter(this.mContext, i, this.kt, new String[]{this.ku}, new int[]{R.id.text1});
            } else if (this.jO != null) {
                rVar = this.jO;
            } else {
                rVar = new r(this.mContext, i, R.id.text1, this.kn);
            }
        }
        if (this.kx != null) {
            this.kx.a(sVar);
        }
        eVar.jO = rVar;
        eVar.jP = this.jP;
        if (this.ko != null) {
            sVar.setOnItemClickListener(new n(this, eVar));
        } else if (this.ks != null) {
            sVar.setOnItemClickListener(new o(this, sVar, eVar));
        }
        if (this.kw != null) {
            sVar.setOnItemSelectedListener(this.kw);
        }
        if (this.kr) {
            sVar.setChoiceMode(1);
        } else if (this.kq) {
            sVar.setChoiceMode(2);
        }
        eVar.jr = sVar;
    }
}
