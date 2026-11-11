package com.google.ads.mediation;

import android.app.Activity;
import android.view.View;
import com.google.ads.mediation.n;
import com.google.ads.mediation.q;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface j<ADDITIONAL_PARAMETERS extends q, SERVER_PARAMETERS extends n> extends i<ADDITIONAL_PARAMETERS, SERVER_PARAMETERS> {
    void a(k kVar, Activity activity, SERVER_PARAMETERS server_parameters, com.google.ads.d dVar, h hVar, ADDITIONAL_PARAMETERS additional_parameters);

    View getBannerView();
}
