package com.gameloft.android2d.d;

import com.gameloft.android.ANMP.GloftGF2F.bx;
import org.json.JSONException;

/* JADX INFO: compiled from: TrackingManager.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends a {
    protected static b bjh = null;
    protected static bx CC = null;

    protected b() {
    }

    public static b i(String str, String str2, String str3, String str4, String str5) {
        if (bjh == null) {
            bjh = new b();
            CC = bx.h(str, str2, str3, str4, str5);
        }
        return bjh;
    }

    public static b Qm() {
        return bjh;
    }

    public void NF() {
        CC.NF();
    }

    public void OnResume() {
        CC.OnResume();
    }

    public void OnPause() {
        CC.OnPause();
    }

    public int iC() {
        return CC.iC();
    }

    public long Nj() {
        return CC.Nj();
    }

    public long Nk() {
        return CC.Nk();
    }

    public void dS(String str) {
        CC.dS(str);
    }

    public void b(int i, String[] strArr) throws JSONException {
        CC.b(i, strArr);
    }

    public void b(int i, Object[] objArr) {
        CC.b(i, objArr);
    }

    public void O(int i, String str) throws JSONException {
        CC.O(i, str);
    }

    public int Nv() {
        bx bxVar = CC;
        return bx.Nv();
    }

    public String ga() {
        return CC.ga();
    }

    public boolean aU(int i) {
        return CC.aU(i);
    }

    public String R(String str, String str2) {
        return CC.R(str, str2);
    }

    public static boolean Om() {
        bx bxVar = CC;
        return bx.Om();
    }
}
