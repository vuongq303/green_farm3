package com.gameloft.glads;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class TopLayer {
    private static ViewGroup container = null;

    class Tag {
        private Tag() {
        }
    }

    public static void SetContainer(ViewGroup viewGroup) {
        container = viewGroup;
        container.setOnHierarchyChangeListener(new HierarchyChangeListener());
    }

    public static void Present(View view) {
        view.setTag(new Tag());
        Refresh();
    }

    public static void Dismiss(View view) {
        view.setTag(null);
        Refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void Refresh() {
        if (container != null) {
            container.post(new Runnable() { // from class: com.gameloft.glads.TopLayer.1
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < TopLayer.container.getChildCount(); i++) {
                        View childAt = TopLayer.container.getChildAt(i);
                        if (childAt.getTag() instanceof Tag) {
                            arrayList.add(childAt);
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((View) it.next()).bringToFront();
                    }
                }
            });
        }
    }

    class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private HierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            TopLayer.Refresh();
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
        }
    }
}
