package com.moat.analytics.mobile.glft;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
class x {

    class a implements Iterable<View> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final ViewGroup f23a;

        /* JADX INFO: renamed from: com.moat.analytics.mobile.glft.x$a$a, reason: collision with other inner class name */
        class C0007a implements Iterator<View> {
            private int b;

            private C0007a() {
                this.b = -1;
            }

            @Override // java.util.Iterator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public View next() {
                this.b++;
                return a.this.f23a.getChildAt(this.b);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b + 1 < a.this.f23a.getChildCount();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Not implemented. Under development.");
            }
        }

        private a(ViewGroup viewGroup) {
            this.f23a = viewGroup;
        }

        @Override // java.lang.Iterable
        public Iterator<View> iterator() {
            return new C0007a();
        }
    }

    x() {
    }

    public static com.moat.analytics.mobile.glft.a.b.a<WebView> a(ViewGroup viewGroup) {
        if (viewGroup instanceof WebView) {
            return com.moat.analytics.mobile.glft.a.b.a.a((WebView) viewGroup);
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(viewGroup);
        HashSet hashSet = new HashSet();
        int i = 0;
        WebView webView = null;
        while (!linkedList.isEmpty() && i < 100) {
            int i2 = i + 1;
            for (View view : new a((ViewGroup) linkedList.poll())) {
                if (view instanceof WebView) {
                    if (webView != null) {
                        n.a(3, "MoatWebViewHound", view, "Ambiguous ad container: multiple WebViews reside within it.");
                        webView = null;
                        break;
                    }
                    webView = (WebView) view;
                }
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) view;
                    if (!hashSet.contains(viewGroup2)) {
                        hashSet.add(viewGroup2);
                        linkedList.add(viewGroup2);
                    }
                }
            }
            i = i2;
        }
        return com.moat.analytics.mobile.glft.a.b.a.b(webView);
    }
}
