package com.moat.analytics.mobile.glft;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class v<T> implements InvocationHandler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final Object[] f21a = new Object[0];
    private final a<T> b;
    private final c<T> c;
    private final LinkedList<v<T>.b> d;
    private final LinkedList<v<T>.b> e;
    private boolean f;
    private T g;

    interface a<T> {
        com.moat.analytics.mobile.glft.a.b.a<T> a();
    }

    class b {
        private final WeakReference[] b;
        private final LinkedList<Object> c;
        private final Method d;

        private b(Method method, Object... objArr) {
            this.c = new LinkedList<>();
            objArr = objArr == null ? v.f21a : objArr;
            WeakReference[] weakReferenceArr = new WeakReference[objArr.length];
            int length = objArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                Object obj = objArr[i];
                if ((obj instanceof Map) || (obj instanceof Integer) || (obj instanceof Double)) {
                    this.c.add(obj);
                }
                weakReferenceArr[i2] = new WeakReference(obj);
                i++;
                i2++;
            }
            this.b = weakReferenceArr;
            this.d = method;
        }
    }

    interface c<T> {
        Class<T> a();
    }

    v(a<T> aVar, c<T> cVar) {
        com.moat.analytics.mobile.glft.a.a.a.a(aVar);
        com.moat.analytics.mobile.glft.a.a.a.a(cVar);
        this.b = aVar;
        this.c = cVar;
        this.d = new LinkedList<>();
        this.e = new LinkedList<>();
        u.ajL().a(new u.b() { // from class: com.moat.analytics.mobile.glft.v.1
            @Override // com.moat.analytics.mobile.glft.u.b
            public void b() {
                v.this.c();
            }

            @Override // com.moat.analytics.mobile.glft.u.b
            public void c() {
            }
        });
    }

    static <T> T a(a<T> aVar, c<T> cVar) {
        Class<T> clsA = cVar.a();
        return (T) Proxy.newProxyInstance(clsA.getClassLoader(), new Class[]{clsA}, new v(aVar, cVar));
    }

    private Object a(Method method) {
        try {
            if (Boolean.TYPE.equals(method.getReturnType())) {
                return true;
            }
        } catch (Exception e) {
            l.j(e);
        }
        return null;
    }

    private Object a(Method method, Object[] objArr) throws IllegalAccessException, InvocationTargetException {
        Class<?> declaringClass = method.getDeclaringClass();
        u uVarAjL = u.ajL();
        if (Object.class.equals(declaringClass)) {
            String name = method.getName();
            Class<T> clsA = this.c.a();
            if ("getClass".equals(name)) {
                return clsA;
            }
            if (!"toString".equals(name)) {
                return method.invoke(this, objArr);
            }
            return (method.invoke(this, objArr) + "").replace(v.class.getName(), clsA.getName());
        }
        if (this.f && this.g == null) {
            d();
            return a(method);
        }
        if (uVarAjL.a() == u.d.ON) {
            c();
            if (this.g != null) {
                return method.invoke(this.g, objArr);
            }
        }
        if (uVarAjL.a() == u.d.OFF && (!this.f || this.g != null)) {
            b(method, objArr);
        }
        return a(method);
    }

    private void b() {
        if (this.f) {
            return;
        }
        try {
            this.g = this.b.a().c(null);
        } catch (Exception e) {
            n.a("OnOffTrackerProxy", this, "Could not create instance", e);
            l.j(e);
        }
        this.f = true;
    }

    private void b(Method method, Object[] objArr) {
        if (this.d.size() < 5) {
            this.d.add(new b(method, objArr));
            return;
        }
        if (this.e.size() >= 10) {
            this.e.removeFirst();
        }
        this.e.add(new b(method, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        b();
        if (this.g == null) {
            return;
        }
        LinkedList<LinkedList> linkedList = new LinkedList();
        linkedList.add(this.d);
        linkedList.add(this.e);
        for (LinkedList<b> linkedList2 : linkedList) {
            for (b bVar : linkedList2) {
                try {
                    Object[] objArr = new Object[bVar.b.length];
                    WeakReference[] weakReferenceArr = bVar.b;
                    int length = weakReferenceArr.length;
                    int i = 0;
                    int i2 = 0;
                    while (i < length) {
                        objArr[i2] = weakReferenceArr[i].get();
                        i++;
                        i2++;
                    }
                    bVar.d.invoke(this.g, objArr);
                } catch (Exception e) {
                    l.j(e);
                }
            }
            linkedList2.clear();
        }
    }

    private void d() {
        this.d.clear();
        this.e.clear();
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        try {
            return a(method, objArr);
        } catch (Exception e) {
            l.j(e);
            return a(method);
        }
    }
}
