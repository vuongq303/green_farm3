package javax.microedition.lcdui;

import android.view.KeyEvent;

/* JADX INFO: compiled from: Canvas.java */
/* JADX INFO: loaded from: classes.dex */
class j implements m {
    final /* synthetic */ f cmb;

    private j(f fVar) {
        this.cmb = fVar;
    }

    /* synthetic */ j(f fVar, g gVar) {
        this(fVar);
    }

    @Override // javax.microedition.lcdui.m
    public boolean onKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 82) {
            int action = keyEvent.getAction();
            if (action == 0) {
                keyEvent.startTracking();
                if (keyEvent.getRepeatCount() == 0) {
                    this.cmb.cM(f.clY);
                    return true;
                }
                this.cmb.pe(f.clY);
                return true;
            }
            if (action != 1) {
                return true;
            }
            this.cmb.cN(f.clY);
            return true;
        }
        if (keyCode != 4) {
            if (f.clZ) {
                int action2 = keyEvent.getAction();
                if (action2 == 0) {
                    if (keyEvent.getRepeatCount() == 0) {
                        this.cmb.cM(keyCode);
                    } else {
                        this.cmb.pe(keyCode);
                    }
                } else if (action2 == 1) {
                    this.cmb.cN(keyCode);
                }
            }
            return false;
        }
        int action3 = keyEvent.getAction();
        if (action3 == 0) {
            if (keyEvent.getRepeatCount() == 0) {
                this.cmb.cM(f.clX);
                return true;
            }
            this.cmb.pe(f.clX);
            return true;
        }
        if (action3 != 1) {
            return true;
        }
        this.cmb.cN(f.clX);
        return true;
    }
}
