package com.google.android.gms.common.api;

import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.common.Feature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public interface l extends c {
    void connect(com.google.android.gms.common.internal.i iVar);

    void disconnect();

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    Feature[] getAvailableFeatures();

    String getEndpointPackageName();

    int getMinApkVersion();

    void getRemoteService(com.google.android.gms.common.internal.ah ahVar, Set<Scope> set);

    IBinder getServiceBrokerBinder();

    Intent getSignInIntent();

    boolean isConnected();

    boolean isConnecting();

    void onUserSignOut(com.google.android.gms.common.internal.o oVar);

    boolean providesSignIn();

    boolean requiresGooglePlayServices();

    boolean requiresSignIn();
}
