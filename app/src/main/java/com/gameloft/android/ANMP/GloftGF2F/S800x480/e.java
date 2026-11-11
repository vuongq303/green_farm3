package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.appevents.AppEventsConstants;
import java.util.Vector;

/* JADX INFO: compiled from: CloudSave.java */
/* JADX INFO: loaded from: classes.dex */
public class e {
    private String Ko;
    private String Kq;
    private byte Kw;
    private Vector Kp = new Vector();
    private long Ks = 0;
    private int Kt = 0;
    private long Kn = 0;
    private String Kv = "anonymous";
    private String Ku = AppEventsConstants.EVENT_PARAM_VALUE_YES;
    private bp Kr = new bp();

    public void l(long j) {
        this.Kn = j;
    }

    public void as(String str) {
        this.Kq = str;
    }

    public void b(byte b) {
        this.Kw = b;
    }

    public byte hQ() {
        return this.Kw;
    }

    public void a(bp bpVar) {
        if (this.Kr.data != null) {
            int length = this.Kr.data.length;
            int length2 = bpVar.data.length + length;
            byte[] bArr = new byte[length];
            System.arraycopy(this.Kr.data, 0, bArr, 0, length);
            this.Kr.data = new byte[length2];
            System.arraycopy(bArr, 0, this.Kr.data, 0, length);
            System.arraycopy(bpVar.data, 0, this.Kr.data, length, bpVar.data.length);
        } else {
            this.Kr.data = new byte[bpVar.data.length];
            System.arraycopy(bpVar.data, 0, this.Kr.data, 0, bpVar.data.length);
        }
        this.Ks += (long) bpVar.data.length;
        this.Kt++;
    }

    public void at(String str) {
        this.Ko = str;
    }

    public void au(String str) {
        this.Kp.addElement(str);
    }

    public String hR() {
        return this.Kv;
    }

    public void av(String str) {
        this.Kv = str;
    }

    public long hS() {
        return this.Kn;
    }

    public bp hT() {
        return this.Kr;
    }

    public String hU() {
        return this.Ko;
    }

    public String hV() {
        return (String) this.Kp.lastElement();
    }

    public String hW() {
        return this.Ku;
    }

    public String hX() {
        return this.Kq;
    }
}
