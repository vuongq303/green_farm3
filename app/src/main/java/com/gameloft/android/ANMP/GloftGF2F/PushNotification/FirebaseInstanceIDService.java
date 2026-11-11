package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import com.gameloft.android.wrapper.au;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static String Ff = "";

    @Override // com.google.firebase.iid.FirebaseInstanceIdService
    public void onTokenRefresh() {
        Ff = FirebaseInstanceId.getInstance().getToken();
        if (au.getContext() != null) {
            q.q(au.getContext(), Ff);
        }
    }
}
