package com.google.firebase.iid;

import android.os.Bundle;
import com.facebook.share.internal.ShareConstants;

/* JADX INFO: loaded from: classes.dex */
final class zzv extends zzt<Bundle> {
    zzv(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    @Override // com.google.firebase.iid.zzt
    final void zzh(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(ShareConstants.WEB_DIALOG_PARAM_DATA);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        finish(bundle2);
    }

    @Override // com.google.firebase.iid.zzt
    final boolean zzst() {
        return false;
    }
}
