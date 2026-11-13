package com.quanhoang.greenfarm3;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class aa implements Runnable {
    final /* synthetic */ z Do;

    aa(z zVar) {
        this.Do = zVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.Do.Dn.Dj.Dg != null) {
            this.Do.Dn.Dj.Dg.setVisibility(4);
        }
        try {
            this.Do.Dn.Dj.gI();
            javax.microedition.lcdui.a aVar = new javax.microedition.lcdui.a(this.Do.Dn.Dj.getContext());
            aVar.setButton(-1, com.gameloft.android.wrapper.au.getActivity().getString(IGPFreemiumActivity.Db[IGPFreemiumActivity.CJ]), new ab(this));
            aVar.setOnCancelListener(new ac(this));
            aVar.setMessage(com.gameloft.android.wrapper.au.getActivity().getString(IGPFreemiumActivity.Da[IGPFreemiumActivity.CJ]));
            aVar.setCanceledOnTouchOutside(false);
            aVar.show();
        } catch (Exception e) {
        }
    }
}
