package com.google.firebase.components;

import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DependencyCycleException extends DependencyException {
    private final List<Component<?>> zzap;

    /* JADX WARN: Illegal instructions before constructor call */
    public DependencyCycleException(List<Component<?>> list) {
        String strValueOf = String.valueOf(Arrays.toString(list.toArray()));
        super(strValueOf.length() != 0 ? "Dependency cycle detected: ".concat(strValueOf) : new String("Dependency cycle detected: "));
        this.zzap = list;
    }

    public List<Component<?>> getComponentsInCycle() {
        return this.zzap;
    }
}
