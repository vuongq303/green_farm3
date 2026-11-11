package javax.microedition.lcdui;

import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Canvas.java */
/* JADX INFO: loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ f cmb;

    g(f fVar) {
        this.cmb = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            synchronized (this.cmb.clV) {
                if (this.cmb.clV.size() == 0) {
                    this.cmb.clU = false;
                    this.cmb.clU = false;
                    return;
                }
            }
            boolean zBooleanValue = this.cmb.clV.get(0).booleanValue();
            if (zBooleanValue) {
                if (MIDlet.isLocked()) {
                    try {
                        Thread.sleep(300L);
                    } catch (Exception e) {
                    }
                } else {
                    MIDlet.cnz.akH();
                    this.cmb.jG();
                    this.cmb.postInvalidate();
                    f.clT = 1;
                    this.cmb.clV.remove(0);
                    if (this.cmb.clV.size() > 0 && this.cmb.clV.get(this.cmb.clV.size() - 1).booleanValue() == zBooleanValue) {
                        this.cmb.clV.clear();
                    }
                }
            } else {
                this.cmb.jF();
                MIDlet.cnz.akI();
                f.clT = 0;
                this.cmb.clV.remove(0);
                if (this.cmb.clV.size() > 0 && this.cmb.clV.get(this.cmb.clV.size() - 1).booleanValue() == zBooleanValue) {
                    this.cmb.clV.clear();
                }
            }
        }
    }
}
