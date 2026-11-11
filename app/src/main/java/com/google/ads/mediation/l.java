package com.google.ads.mediation;

import android.app.Activity;
import com.google.ads.mediation.n;
import com.google.ads.mediation.q;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface l<ADDITIONAL_PARAMETERS extends q, SERVER_PARAMETERS extends n> extends i<ADDITIONAL_PARAMETERS, SERVER_PARAMETERS> {
    void a(m mVar, Activity activity, SERVER_PARAMETERS server_parameters, h hVar, ADDITIONAL_PARAMETERS additional_parameters);

    void showInterstitial();
}
