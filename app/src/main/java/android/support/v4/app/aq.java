package android.support.v4.app;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class aq implements s {
    private final boolean bG;
    private final i bH;
    private int bI;

    aq(i iVar, boolean z) {
        this.bG = z;
        this.bH = iVar;
    }

    @Override // android.support.v4.app.s
    public void p() {
        this.bI--;
        if (this.bI == 0) {
            this.bH.L.B();
        }
    }

    @Override // android.support.v4.app.s
    public void startListening() {
        this.bI++;
    }

    public boolean isReady() {
        return this.bI == 0;
    }

    public void M() {
        boolean z = this.bI > 0;
        ad adVar = this.bH.L;
        int size = adVar.aR.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = adVar.aR.get(i);
            fragment.setOnStartEnterTransitionListener(null);
            if (z && fragment.isPostponed()) {
                fragment.startPostponedEnterTransition();
            }
        }
        this.bH.L.a(this.bH, this.bG, z ? false : true, true);
    }

    public void N() {
        this.bH.L.a(this.bH, this.bG, false, false);
    }
}
