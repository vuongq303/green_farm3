package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* JADX INFO: loaded from: classes.dex */
final class zzi<T> implements Provider<T> {
    private static final Object zzaq = new Object();
    private volatile Object zzar = zzaq;
    private volatile Provider<T> zzas;

    zzi(final ComponentFactory<T> componentFactory, final ComponentContainer componentContainer) {
        this.zzas = new Provider(componentFactory, componentContainer) { // from class: com.google.firebase.components.zzj
            private final ComponentFactory zzat;
            private final ComponentContainer zzau;

            {
                this.zzat = componentFactory;
                this.zzau = componentContainer;
            }

            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return this.zzat.create(this.zzau);
            }
        };
    }

    @Override // com.google.firebase.inject.Provider
    public final T get() {
        T t = (T) this.zzar;
        if (t == zzaq) {
            synchronized (this) {
                t = (T) this.zzar;
                if (t == zzaq) {
                    t = this.zzas.get();
                    this.zzar = t;
                    this.zzas = null;
                }
            }
        }
        return t;
    }
}
