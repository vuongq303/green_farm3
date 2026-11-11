package android.support.v4.app;

import android.os.Bundle;
import java.util.Set;

/* JADX INFO: compiled from: RemoteInputCompatBase.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class dj {
    protected abstract boolean getAllowFreeFormInput();

    protected abstract Set<String> getAllowedDataTypes();

    protected abstract CharSequence[] getChoices();

    protected abstract Bundle getExtras();

    protected abstract CharSequence getLabel();

    protected abstract String getResultKey();
}
