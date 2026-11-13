package com.quanhoang.greenfarm3;

/* JADX INFO: compiled from: PackageInfo.java */
/* JADX INFO: loaded from: classes.dex */
public class bg {
    String EP;
    String ER;
    String EO = bx.Nx();
    int EN = bx.Nv();
    String EQ = "Java-GLOTv3";
    int ES = Integer.valueOf("330").intValue();

    public bg(String str) {
        this.EP = str;
        this.ER = bx.Nw();
        this.ER = this.ER.substring(7);
    }

    public void a(be beVar) {
        beVar.ba(20);
        beVar.ba(18);
        beVar.f(7, true);
        beVar.f(0, true);
        beVar.aa(this.EQ);
        beVar.f(1, true);
        beVar.f(this.EN, true);
        beVar.f(3, true);
        beVar.aa(this.EO);
        beVar.f(2, true);
        beVar.f(2, true);
        beVar.f(4, true);
        beVar.aa(this.EP);
        beVar.f(5, true);
        beVar.aa(this.ER);
        beVar.f(6, true);
        beVar.f(this.ES, true);
    }
}
