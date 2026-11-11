package javax.microedition.lcdui;

import android.view.MotionEvent;

/* JADX INFO: compiled from: Canvas.java */
/* JADX INFO: loaded from: classes.dex */
class i implements n {
    final /* synthetic */ f cmb;
    private float cmc;
    private float cmd;
    private int cme;

    private i(f fVar) {
        this.cmb = fVar;
        this.cmc = -1.0f;
        this.cmd = -1.0f;
        this.cme = -1;
    }

    /* synthetic */ i(f fVar, g gVar) {
        this(fVar);
    }

    @Override // javax.microedition.lcdui.n
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = ((motionEvent.getX() * 1.0f) - f.bgr) / f.clI;
        float y = ((motionEvent.getY() * 1.0f) - f.bgs) / f.clJ;
        this.cme = motionEvent.getAction();
        String str = "Scale X = " + f.clI + " Y = " + f.clJ;
        String str2 = "Translate X = " + f.bgr + " Y = " + f.bgs;
        String str3 = "Device X = " + motionEvent.getX() + " Y = " + motionEvent.getY();
        String str4 = "Game X = " + x + " Y = " + y;
        switch (this.cme) {
            case 0:
                this.cmb.V((int) x, (int) y);
                this.cmc = x;
                this.cmd = y;
                break;
            case 1:
                if (!this.cmb.clS) {
                    this.cmb.U(-1, -1);
                } else {
                    this.cmb.U((int) this.cmc, (int) this.cmd);
                }
                break;
            case 2:
                this.cmc = x;
                this.cmd = y;
                this.cmb.W((int) x, (int) y);
                break;
        }
        return true;
    }
}
