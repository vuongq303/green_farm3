package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import com.facebook.R;
import com.facebook.share.internal.ShareConstants;
import java.util.Vector;
import org.json.JSONException;

/* JADX INFO: compiled from: CrmAction.java */
/* JADX INFO: loaded from: classes.dex */
public class f {
    private double KA;
    private bc[] KC;
    private int Kx;
    private String Ky;
    private Vector KD = new Vector();
    private Vector KE = new Vector();
    private boolean KF = false;
    private String Kz = "";
    private bc KB = new bc();

    public f() {
        this.KD.removeAllElements();
    }

    public int b(bc bcVar) {
        if (!bcVar.cb(ShareConstants.WEB_DIALOG_PARAM_ID) || !bcVar.co(ShareConstants.WEB_DIALOG_PARAM_ID)) {
            return -34;
        }
        this.Kz = bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_ID).toString();
        if (!bcVar.cb("type") || !bcVar.co("type")) {
            return -34;
        }
        this.Ky = bcVar.cd("type").toString();
        if (this.Ky.equals("popup")) {
            this.Kx = 6;
        } else if (this.Ky.equals("ad_non_game_popups")) {
            this.Kx = 7;
        } else if (this.Ky.equals("ad_3rd_party")) {
            this.Kx = 8;
        } else if (this.Ky.equals("give_object")) {
            this.Kx = 9;
        } else if (this.Ky.equals("launch_url")) {
            this.Kx = 10;
        } else if (this.Ky.equals("launch_store")) {
            this.Kx = 11;
        } else if (this.Ky.equals("log_event")) {
            this.Kx = 12;
        }
        this.KB.t("crm_action_type", this.Kx);
        if (!bcVar.cb("frequency") || !bcVar.cn("frequency")) {
            return -34;
        }
        this.KA = Double.parseDouble(bcVar.cd("frequency").toString());
        if (bcVar.cb("limit") && bcVar.cp("limit")) {
            try {
                bc[] bcVarArrCj = bcVar.cj("limit");
                for (int i = 0; i < bcVarArrCj.length; i++) {
                    if (!bcVarArrCj[i].cb("max") || !bcVarArrCj[i].cm("max") || !bcVarArrCj[i].cb("interval") || !bcVarArrCj[i].cm("interval")) {
                        return -34;
                    }
                    h hVar = new h(this);
                    hVar.KH = Long.parseLong(bcVarArrCj[i].cd("interval").toString());
                    hVar.KG = Long.parseLong(bcVarArrCj[i].cd("max").toString());
                    this.KD.addElement(hVar);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -34;
            }
        }
        switch (this.Kx) {
            case 6:
                if (!bcVar.cb("popup_id") || !bcVar.co("popup_id")) {
                    return -34;
                }
                this.KB.a("popup_id", bcVar.cd("popup_id").toString());
                break;
            case 7:
                if (!bcVar.cb("tags")) {
                    return -34;
                }
                try {
                    String[] strArrCi = bcVar.ci("tags");
                    int length = strArrCi.length;
                    String str = "tags_";
                    for (int i2 = 0; i2 < length; i2++) {
                        if (i2 != 0) {
                            str = str + "-";
                        }
                        str = str + strArrCi[i2];
                    }
                    this.KB.a("tags", str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return -34;
                }
                break;
            case 8:
                if (!bcVar.cb("tags")) {
                    return -34;
                }
                try {
                    String[] strArrCi2 = bcVar.ci("tags");
                    int length2 = strArrCi2.length;
                    String str2 = "";
                    for (int i3 = 0; i3 < length2; i3++) {
                        if (i3 != 0) {
                            str2 = str2 + "-";
                        }
                        str2 = str2 + strArrCi2[i3];
                    }
                    this.KB.a("tags", str2);
                } catch (Exception e3) {
                    e3.printStackTrace();
                    return -34;
                }
                break;
            case 9:
                if (!bcVar.cb("item") || !bcVar.co("item")) {
                    return -34;
                }
                this.KB.a("item", bcVar.cd("item").toString());
                if (!bcVar.cb("quantity") || !bcVar.cm("quantity")) {
                    return -34;
                }
                this.KB.a("quantity", new Integer(Integer.parseInt(bcVar.cd("quantity").toString())));
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                if (!bcVar.cb("url") || !bcVar.co("url")) {
                    return -34;
                }
                this.KB.a("url", bcVar.cd("url").toString());
                break;
        }
        if (bcVar.cb("pointcuts") && bcVar.cp("pointcuts")) {
            try {
                this.KC = bcVar.cj("pointcuts");
            } catch (Exception e4) {
                e4.printStackTrace();
                return -34;
            }
        }
        return 0;
    }

    public bc hZ() {
        if (this.KE.size() == 0) {
            return null;
        }
        bc bcVar = new bc();
        switch (this.Kx) {
            case 6:
                bcVar.a(ShareConstants.WEB_DIALOG_PARAM_ID, this.KB.cd("popup_id").toString());
                break;
            case 7:
                bcVar.a(ShareConstants.WEB_DIALOG_PARAM_ID, this.KB.cd("tags").toString());
                break;
            case 8:
                bcVar.a(ShareConstants.WEB_DIALOG_PARAM_ID, this.KB.cd("tags").toString());
                break;
            case 9:
                bcVar.a(ShareConstants.WEB_DIALOG_PARAM_ID, this.Kz);
                break;
        }
        int size = this.KE.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = Long.parseLong(this.KE.firstElement().toString());
            this.KE.removeElementAt(0);
        }
        for (long j : jArr) {
            this.KE.addElement(new Long(j));
        }
        bcVar.a("impressions", jArr);
        return bcVar;
    }

    public int c(bc bcVar) {
        if (bcVar.cb(ShareConstants.WEB_DIALOG_PARAM_ID) && bcVar.co(ShareConstants.WEB_DIALOG_PARAM_ID) && this.Kz.length() > 0) {
            switch (this.Kx) {
                case 6:
                    if (!this.KB.cd("popup_id").toString().equals(bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_ID).toString())) {
                        return -33;
                    }
                    break;
                case 7:
                    if (!this.KB.cd("tags").toString().equals(bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_ID).toString())) {
                        return -33;
                    }
                    break;
                case 8:
                    if (!this.KB.cd("tags").toString().equals(bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_ID).toString())) {
                        return -33;
                    }
                    break;
                case 9:
                    if (!this.Kz.equals(bcVar.cd(ShareConstants.WEB_DIALOG_PARAM_ID).toString())) {
                        return -33;
                    }
                    break;
            }
        }
        while (!this.KE.isEmpty()) {
            this.KE.removeElementAt(0);
        }
        if (bcVar.cb("impressions") && bcVar.cq("impressions")) {
            try {
                for (long j : bcVar.cg("impressions")) {
                    this.KE.addElement(new Long(j));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0010. Please report as an issue. */
    public int m(long j) {
        if (j > 100) {
        }
        if (this.KF) {
            this.KF = false;
            this.KE.addElement(new Long(hY()));
            switch (this.Kx) {
                case 6:
                    a(6, true, this.KB);
                    break;
                case 7:
                    a(7, true, this.KB);
                    break;
                case 8:
                    a(8, true, this.KB);
                    break;
                case 9:
                    a(9, true, this.KB);
                    bc bcVar = new bc();
                    bcVar.t("type", 51855);
                    bc bcVar2 = new bc();
                    bcVar2.a("pointcut_id", this.KB.cd("pointcut_id"));
                    bcVar2.a("reward_name", this.KB.cd("item"));
                    bcVar2.a("reward_amount", this.KB.cd("quantity"));
                    bcVar.a(ShareConstants.WEB_DIALOG_PARAM_DATA, bcVar2);
                    a(12, true, bcVar);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    a(10, true, this.KB);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    a(11, true, this.KB);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    a(12, true, this.KB);
                    break;
            }
            this.KB.a("pointcut_id", "");
        } else {
            switch (this.Kx) {
            }
        }
        return 0;
    }

    public int ia() {
        return this.Kx;
    }

    public bc ib() {
        return this.KB;
    }

    // The original DEX method has no exception handler or checked-throws annotation.
    public void a(int i, boolean z, bc bcVar) {
        try {
            if (i == 12 || i == 5) {
                com.gameloft.android.ANMP.GloftGF2F.S800x480.i.ic().a(z, bcVar);
            } else {
                com.gameloft.android.ANMP.GloftGF2F.S800x480.i.ic().a(i, z, bcVar);
            }
        } catch (JSONException failure) {
            throw f.<RuntimeException>propagateChecked(failure);
        }
    }

    // Keep the same thrown instance; Java's checked-exception rules do not apply to DEX.
    @SuppressWarnings("unchecked")
    private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure) throws Failure {
        throw (Failure) failure;
    }

    private long hY() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        return jCurrentTimeMillis / 1000;
    }
}
