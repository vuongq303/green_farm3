package android.support.v4.app;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public class SupportActivity extends Activity {
    private android.support.v4.e.r<Class<? extends dp>, dp> mExtraDataMap = new android.support.v4.e.r<>();

    public void putExtraData(dp dpVar) {
        this.mExtraDataMap.put((Class<? extends dp>) dpVar.getClass(), dpVar);
    }

    public <T extends dp> T getExtraData(Class<T> cls) {
        return (T) this.mExtraDataMap.get(cls);
    }
}
