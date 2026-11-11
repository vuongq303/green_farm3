package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ao implements Runnable {
    final /* synthetic */ an EA;

    ao(an anVar) {
        this.EA = anVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.EA.Ex.Ed != null) {
            float x = this.EA.Ex.Ed.getX() + ((this.EA.Ex.Eh + this.EA.Ex.Ej) * 10.0f);
            float y = this.EA.Ex.Ed.getY() + ((this.EA.Ex.Ei + this.EA.Ex.Ek) * 10.0f);
            this.EA.Ex.Ed.setX(x);
            this.EA.Ex.Ed.setY(y);
            if (x > this.EA.Ex.CR - (this.EA.Ex.Ed.getWidth() / 2)) {
                this.EA.Ex.Ed.setX(this.EA.Ex.CR - (this.EA.Ex.Ed.getWidth() / 2));
            }
            if (x < 0 - (this.EA.Ex.Ed.getWidth() / 2)) {
                this.EA.Ex.Ed.setX(0 - (this.EA.Ex.Ed.getWidth() / 2));
            }
            if (y > this.EA.Ex.CS - (this.EA.Ex.Ed.getHeight() / 2)) {
                this.EA.Ex.Ed.setY(this.EA.Ex.CS - (this.EA.Ex.Ed.getHeight() / 2));
            }
            if (y < 0 - (this.EA.Ex.Ed.getHeight() / 2)) {
                this.EA.Ex.Ed.setY(0 - (this.EA.Ex.Ed.getHeight() / 2));
            }
        }
    }
}
