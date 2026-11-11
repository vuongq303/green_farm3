package android.support.v4.app;

import android.app.RemoteInput;

/* JADX INFO: compiled from: RemoteInputCompatApi20.java */
/* JADX INFO: loaded from: classes.dex */
class dh {
    static RemoteInput[] a(dj[] djVarArr) {
        if (djVarArr == null) {
            return null;
        }
        RemoteInput[] remoteInputArr = new RemoteInput[djVarArr.length];
        for (int i = 0; i < djVarArr.length; i++) {
            dj djVar = djVarArr[i];
            remoteInputArr[i] = new RemoteInput.Builder(djVar.getResultKey()).setLabel(djVar.getLabel()).setChoices(djVar.getChoices()).setAllowFreeFormInput(djVar.getAllowFreeFormInput()).addExtras(djVar.getExtras()).build();
        }
        return remoteInputArr;
    }
}
