package com.facebook.internal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.a.d;
import android.support.a.f;
import com.facebook.FacebookSdk;

/* JADX INFO: loaded from: classes.dex */
public class CustomTab {
    private Uri uri;

    public CustomTab(String str, Bundle bundle) {
        this.uri = Utility.buildUri(ServerProtocol.getDialogAuthority(), FacebookSdk.getGraphApiVersion() + "/" + ServerProtocol.DIALOG_PATH + str, bundle == null ? new Bundle() : bundle);
    }

    public void openCustomTab(Activity activity, String str) {
        d dVarM = new f().m();
        dVarM.intent.setPackage(str);
        dVarM.intent.addFlags(1073741824);
        dVarM.a(activity, this.uri);
    }
}
