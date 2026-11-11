package android.support.v4.app;

import java.util.List;

/* JADX INFO: compiled from: FragmentManagerNonConfig.java */
/* JADX INFO: loaded from: classes.dex */
public class ar {
    private final List<Fragment> bJ;
    private final List<ar> bK;

    ar(List<Fragment> list, List<ar> list2) {
        this.bJ = list;
        this.bK = list2;
    }

    List<Fragment> getFragments() {
        return this.bJ;
    }

    List<ar> O() {
        return this.bK;
    }
}
