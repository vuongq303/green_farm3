package javax.microedition.lcdui;

import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
class ag {
    private boolean cmQ;
    private int cmR;
    private boolean cmS;
    private boolean cmT;
    private af cmU;

    private ag() {
    }

    public synchronized void b(af afVar) {
        String str = "exiting tid=" + afVar.getId();
        afVar.mExited = true;
        if (this.cmU == afVar) {
            this.cmU = null;
        }
        notifyAll();
    }

    public boolean c(af afVar) {
        if (this.cmU == afVar || this.cmU == null) {
            this.cmU = afVar;
            notifyAll();
            return true;
        }
        akq();
        return this.cmT;
    }

    public void d(af afVar) {
        if (this.cmU == afVar) {
            this.cmU = null;
        }
        notifyAll();
    }

    public synchronized void b(GL10 gl10) {
        if (!this.cmS) {
            akq();
            if (this.cmR < 131072) {
                gl10.glGetString(7937);
                this.cmT = false;
                notifyAll();
            }
            this.cmS = true;
        }
    }

    private void akq() {
        if (!this.cmQ) {
            this.cmR = 0;
            if (this.cmR >= 131072) {
                this.cmT = true;
            }
            this.cmQ = true;
        }
    }
}
