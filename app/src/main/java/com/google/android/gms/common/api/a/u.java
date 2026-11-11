package com.google.android.gms.common.api.a;

import android.app.Activity;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public interface u {
    Activity Zn();

    <T extends t> T a(String str, Class<T> cls);

    void a(String str, t tVar);

    void startActivityForResult(Intent intent, int i);
}
