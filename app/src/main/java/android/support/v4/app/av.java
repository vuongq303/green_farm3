package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: compiled from: FragmentTransition.java */
/* JADX INFO: loaded from: classes.dex */
class av {
    private static final int[] bR = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    static void a(ad adVar, ArrayList<i> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        if (adVar.aZ >= 1 && Build.VERSION.SDK_INT >= 21) {
            SparseArray sparseArray = new SparseArray();
            for (int i3 = i; i3 < i2; i3++) {
                i iVar = arrayList.get(i3);
                if (arrayList2.get(i3).booleanValue()) {
                    b(iVar, (SparseArray<ba>) sparseArray, z);
                } else {
                    a(iVar, (SparseArray<ba>) sparseArray, z);
                }
            }
            if (sparseArray.size() != 0) {
                View view = new View(adVar.mHost.getContext());
                int size = sparseArray.size();
                for (int i4 = 0; i4 < size; i4++) {
                    int iKeyAt = sparseArray.keyAt(i4);
                    android.support.v4.e.a<String, String> aVarA = a(iKeyAt, arrayList, arrayList2, i, i2);
                    ba baVar = (ba) sparseArray.valueAt(i4);
                    if (z) {
                        a(adVar, iKeyAt, baVar, view, aVarA);
                    } else {
                        b(adVar, iKeyAt, baVar, view, aVarA);
                    }
                }
            }
        }
    }

    private static android.support.v4.e.a<String, String> a(int i, ArrayList<i> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        android.support.v4.e.a<String, String> aVar = new android.support.v4.e.a<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            i iVar = arrayList.get(i4);
            if (iVar.b(i)) {
                boolean zBooleanValue = arrayList2.get(i4).booleanValue();
                if (iVar.aa != null) {
                    int size = iVar.aa.size();
                    if (zBooleanValue) {
                        arrayList3 = iVar.aa;
                        arrayList4 = iVar.ab;
                    } else {
                        ArrayList<String> arrayList5 = iVar.aa;
                        arrayList3 = iVar.ab;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = arrayList4.get(i5);
                        String str2 = arrayList3.get(i5);
                        String strRemove = aVar.remove(str2);
                        if (strRemove != null) {
                            aVar.put(str, strRemove);
                        } else {
                            aVar.put(str, str2);
                        }
                    }
                }
            }
        }
        return aVar;
    }

    private static void a(ad adVar, int i, ba baVar, View view, android.support.v4.e.a<String, String> aVar) {
        ViewGroup viewGroup = null;
        if (adVar.ba.onHasView()) {
            viewGroup = (ViewGroup) adVar.ba.onFindViewById(i);
        }
        if (viewGroup != null) {
            Fragment fragment = baVar.cj;
            Fragment fragment2 = baVar.cm;
            boolean z = baVar.ck;
            boolean z2 = baVar.cn;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Object objI = i(fragment, z);
            Object objJ = j(fragment2, z2);
            Object objA = a(viewGroup, view, aVar, baVar, (ArrayList<View>) arrayList2, (ArrayList<View>) arrayList, objI, objJ);
            if (objI != null || objA != null || objJ != null) {
                ArrayList<View> arrayListA = a(objJ, fragment2, (ArrayList<View>) arrayList2, view);
                ArrayList<View> arrayListA2 = a(objI, fragment, (ArrayList<View>) arrayList, view);
                a(arrayListA2, 4);
                Object objA2 = a(objI, objJ, objA, fragment, z);
                if (objA2 != null) {
                    a(objJ, fragment2, arrayListA);
                    ArrayList<String> arrayListA3 = bb.a((ArrayList<View>) arrayList);
                    bb.a(objA2, objI, arrayListA2, objJ, arrayListA, objA, arrayList);
                    bb.a(viewGroup, objA2);
                    bb.a(viewGroup, arrayList2, arrayList, arrayListA3, aVar);
                    a(arrayListA2, 0);
                    bb.a(objA, (ArrayList<View>) arrayList2, (ArrayList<View>) arrayList);
                }
            }
        }
    }

    private static void a(Object obj, Fragment fragment, ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            bb.b(obj, fragment.getView(), arrayList);
            da.a(fragment.mContainer, new aw(arrayList));
        }
    }

    private static void b(ad adVar, int i, ba baVar, View view, android.support.v4.e.a<String, String> aVar) {
        ViewGroup viewGroup = null;
        if (adVar.ba.onHasView()) {
            viewGroup = (ViewGroup) adVar.ba.onFindViewById(i);
        }
        if (viewGroup != null) {
            Fragment fragment = baVar.cj;
            Fragment fragment2 = baVar.cm;
            boolean z = baVar.ck;
            boolean z2 = baVar.cn;
            Object objI = i(fragment, z);
            Object objJ = j(fragment2, z2);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Object objB = b(viewGroup, view, aVar, baVar, arrayList, arrayList2, objI, objJ);
            if (objI != null || objB != null || objJ != null) {
                ArrayList<View> arrayListA = a(objJ, fragment2, (ArrayList<View>) arrayList, view);
                Object obj = (arrayListA == null || arrayListA.isEmpty()) ? null : objJ;
                bb.b(objI, view);
                Object objA = a(objI, obj, objB, fragment, baVar.ck);
                if (objA != null) {
                    ArrayList arrayList3 = new ArrayList();
                    bb.a(objA, objI, arrayList3, obj, arrayListA, objB, arrayList2);
                    a(viewGroup, fragment, view, (ArrayList<View>) arrayList2, objI, (ArrayList<View>) arrayList3, obj, arrayListA);
                    bb.a((View) viewGroup, (ArrayList<View>) arrayList2, (Map<String, String>) aVar);
                    bb.a(viewGroup, objA);
                    bb.a(viewGroup, (ArrayList<View>) arrayList2, (Map<String, String>) aVar);
                }
            }
        }
    }

    private static void a(ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Object obj, ArrayList<View> arrayList2, Object obj2, ArrayList<View> arrayList3) {
        da.a(viewGroup, new ax(obj, view, fragment, arrayList, arrayList2, arrayList3, obj2));
    }

    private static Object a(Fragment fragment, Fragment fragment2, boolean z) {
        Object sharedElementEnterTransition;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        if (z) {
            sharedElementEnterTransition = fragment2.getSharedElementReturnTransition();
        } else {
            sharedElementEnterTransition = fragment.getSharedElementEnterTransition();
        }
        return bb.e(bb.d(sharedElementEnterTransition));
    }

    private static Object i(Fragment fragment, boolean z) {
        Object enterTransition;
        if (fragment == null) {
            return null;
        }
        if (z) {
            enterTransition = fragment.getReenterTransition();
        } else {
            enterTransition = fragment.getEnterTransition();
        }
        return bb.d(enterTransition);
    }

    private static Object j(Fragment fragment, boolean z) {
        Object exitTransition;
        if (fragment == null) {
            return null;
        }
        if (z) {
            exitTransition = fragment.getReturnTransition();
        } else {
            exitTransition = fragment.getExitTransition();
        }
        return bb.d(exitTransition);
    }

    private static Object a(ViewGroup viewGroup, View view, android.support.v4.e.a<String, String> aVar, ba baVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Rect rect;
        View viewA;
        Fragment fragment = baVar.cj;
        Fragment fragment2 = baVar.cm;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = baVar.ck;
        Object objA = aVar.isEmpty() ? null : a(fragment, fragment2, z);
        android.support.v4.e.a<String, View> aVarA = a(aVar, objA, baVar);
        android.support.v4.e.a<String, View> aVarB = b(aVar, objA, baVar);
        if (aVar.isEmpty()) {
            obj3 = null;
            if (aVarA != null) {
                aVarA.clear();
            }
            if (aVarB != null) {
                aVarB.clear();
            }
        } else {
            a(arrayList, aVarA, aVar.keySet());
            a(arrayList2, aVarB, aVar.values());
            obj3 = objA;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        a(fragment, fragment2, z, aVarA, true);
        if (obj3 != null) {
            arrayList2.add(view);
            bb.a(obj3, view, arrayList);
            a(obj3, obj2, aVarA, baVar.cn, baVar.co);
            rect = new Rect();
            viewA = a(aVarB, baVar, obj, z);
            if (viewA != null) {
                bb.a(obj, rect);
            }
        } else {
            rect = null;
            viewA = null;
        }
        da.a(viewGroup, new ay(fragment, fragment2, z, aVarB, viewA, rect));
        return obj3;
    }

    private static void a(ArrayList<View> arrayList, android.support.v4.e.a<String, View> aVar, Collection<String> collection) {
        for (int size = aVar.size() - 1; size >= 0; size--) {
            View viewValueAt = aVar.valueAt(size);
            if (collection.contains(android.support.v4.view.aa.i(viewValueAt))) {
                arrayList.add(viewValueAt);
            }
        }
    }

    private static Object b(ViewGroup viewGroup, View view, android.support.v4.e.a<String, String> aVar, ba baVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Rect rect;
        Fragment fragment = baVar.cj;
        Fragment fragment2 = baVar.cm;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = baVar.ck;
        Object objA = aVar.isEmpty() ? null : a(fragment, fragment2, z);
        android.support.v4.e.a<String, View> aVarA = a(aVar, objA, baVar);
        if (aVar.isEmpty()) {
            obj3 = null;
        } else {
            arrayList.addAll(aVarA.values());
            obj3 = objA;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        a(fragment, fragment2, z, aVarA, true);
        if (obj3 != null) {
            rect = new Rect();
            bb.a(obj3, view, arrayList);
            a(obj3, obj2, aVarA, baVar.cn, baVar.co);
            if (obj != null) {
                bb.a(obj, rect);
            }
        } else {
            rect = null;
        }
        da.a(viewGroup, new az(aVar, obj3, baVar, arrayList2, view, fragment, fragment2, z, arrayList, obj, rect));
        return obj3;
    }

    private static android.support.v4.e.a<String, View> a(android.support.v4.e.a<String, String> aVar, Object obj, ba baVar) {
        dm exitTransitionCallback;
        ArrayList<String> arrayList;
        if (aVar.isEmpty() || obj == null) {
            aVar.clear();
            return null;
        }
        Fragment fragment = baVar.cm;
        android.support.v4.e.a<String, View> aVar2 = new android.support.v4.e.a<>();
        bb.a((Map<String, View>) aVar2, fragment.getView());
        i iVar = baVar.co;
        if (baVar.cn) {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = iVar.ab;
        } else {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = iVar.aa;
        }
        aVar2.retainAll(arrayList);
        if (exitTransitionCallback != null) {
            exitTransitionCallback.onMapSharedElements(arrayList, aVar2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = aVar2.get(str);
                if (view == null) {
                    aVar.remove(str);
                } else if (!str.equals(android.support.v4.view.aa.i(view))) {
                    aVar.put(android.support.v4.view.aa.i(view), aVar.remove(str));
                }
            }
        } else {
            aVar.retainAll(aVar2.keySet());
        }
        return aVar2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.support.v4.e.a<String, View> b(android.support.v4.e.a<String, String> aVar, Object obj, ba baVar) {
        dm enterTransitionCallback;
        ArrayList<String> arrayList;
        String strA;
        Fragment fragment = baVar.cj;
        View view = fragment.getView();
        if (aVar.isEmpty() || obj == null || view == null) {
            aVar.clear();
            return null;
        }
        android.support.v4.e.a<String, View> aVar2 = new android.support.v4.e.a<>();
        bb.a((Map<String, View>) aVar2, view);
        i iVar = baVar.cl;
        if (baVar.ck) {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = iVar.aa;
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = iVar.ab;
        }
        if (arrayList != null) {
            aVar2.retainAll(arrayList);
        }
        if (enterTransitionCallback != null) {
            enterTransitionCallback.onMapSharedElements(arrayList, aVar2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view2 = aVar2.get(str);
                if (view2 == null) {
                    String strA2 = a(aVar, str);
                    if (strA2 != null) {
                        aVar.remove(strA2);
                    }
                } else if (!str.equals(android.support.v4.view.aa.i(view2)) && (strA = a(aVar, str)) != null) {
                    aVar.put(strA, android.support.v4.view.aa.i(view2));
                }
            }
        } else {
            a(aVar, aVar2);
        }
        return aVar2;
    }

    private static String a(android.support.v4.e.a<String, String> aVar, String str) {
        int size = aVar.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(aVar.valueAt(i))) {
                return aVar.keyAt(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static View a(android.support.v4.e.a<String, View> aVar, ba baVar, Object obj, boolean z) {
        String str;
        i iVar = baVar.cl;
        if (obj != null && aVar != null && iVar.aa != null && !iVar.aa.isEmpty()) {
            if (z) {
                str = iVar.aa.get(0);
            } else {
                str = iVar.ab.get(0);
            }
            return aVar.get(str);
        }
        return null;
    }

    private static void a(Object obj, Object obj2, android.support.v4.e.a<String, View> aVar, boolean z, i iVar) {
        String str;
        if (iVar.aa != null && !iVar.aa.isEmpty()) {
            if (z) {
                str = iVar.ab.get(0);
            } else {
                str = iVar.aa.get(0);
            }
            View view = aVar.get(str);
            bb.a(obj, view);
            if (obj2 != null) {
                bb.a(obj2, view);
            }
        }
    }

    private static void a(android.support.v4.e.a<String, String> aVar, android.support.v4.e.a<String, View> aVar2) {
        for (int size = aVar.size() - 1; size >= 0; size--) {
            if (!aVar2.containsKey(aVar.valueAt(size))) {
                aVar.removeAt(size);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Fragment fragment, Fragment fragment2, boolean z, android.support.v4.e.a<String, View> aVar, boolean z2) {
        dm enterTransitionCallback;
        if (z) {
            enterTransitionCallback = fragment2.getEnterTransitionCallback();
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
        }
        if (enterTransitionCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = aVar == null ? 0 : aVar.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(aVar.keyAt(i));
                arrayList.add(aVar.valueAt(i));
            }
            if (z2) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList<View> a(Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        ArrayList<View> arrayList2 = null;
        if (obj != null) {
            arrayList2 = new ArrayList<>();
            View view2 = fragment.getView();
            if (view2 != null) {
                bb.a(arrayList2, view2);
            }
            if (arrayList != null) {
                arrayList2.removeAll(arrayList);
            }
            if (!arrayList2.isEmpty()) {
                arrayList2.add(view);
                bb.a(obj, arrayList2);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(ArrayList<View> arrayList, int i) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).setVisibility(i);
            }
        }
    }

    private static Object a(Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean allowReturnTransitionOverlap = true;
        if (obj != null && obj2 != null && fragment != null) {
            allowReturnTransitionOverlap = z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap();
        }
        if (allowReturnTransitionOverlap) {
            return bb.a(obj2, obj, obj3);
        }
        return bb.b(obj2, obj, obj3);
    }

    public static void a(i iVar, SparseArray<ba> sparseArray, boolean z) {
        int size = iVar.M.size();
        for (int i = 0; i < size; i++) {
            a(iVar, iVar.M.get(i), sparseArray, false, z);
        }
    }

    public static void b(i iVar, SparseArray<ba> sparseArray, boolean z) {
        if (iVar.L.ba.onHasView()) {
            for (int size = iVar.M.size() - 1; size >= 0; size--) {
                a(iVar, iVar.M.get(size), sparseArray, true, z);
            }
        }
    }

    private static void a(i iVar, j jVar, SparseArray<ba> sparseArray, boolean z, boolean z2) {
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        ba baVarA;
        ba baVarA2;
        Fragment fragment = jVar.fragment;
        if (fragment != null && (i = fragment.mContainerId) != 0) {
            switch (z ? bR[jVar.ae] : jVar.ae) {
                case 1:
                case 7:
                    if (z2) {
                        z9 = fragment.mIsNewlyAdded;
                    } else {
                        z9 = (fragment.mAdded || fragment.mHidden) ? false : true;
                    }
                    z4 = true;
                    z5 = false;
                    z6 = false;
                    z7 = z9;
                    break;
                case 2:
                default:
                    z4 = false;
                    z5 = false;
                    z6 = false;
                    z7 = false;
                    break;
                case 3:
                case 6:
                    if (z2) {
                        z3 = !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mPostponedAlpha >= 0.0f;
                    } else {
                        z3 = fragment.mAdded && !fragment.mHidden;
                    }
                    z4 = false;
                    z5 = z3;
                    z6 = true;
                    z7 = false;
                    break;
                case 4:
                    if (z2) {
                        z8 = fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden;
                    } else {
                        z8 = fragment.mAdded && !fragment.mHidden;
                    }
                    z4 = false;
                    z5 = z8;
                    z6 = true;
                    z7 = false;
                    break;
                case 5:
                    if (z2) {
                        z10 = fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded;
                    } else {
                        z10 = fragment.mHidden;
                    }
                    z4 = true;
                    z5 = false;
                    z6 = false;
                    z7 = z10;
                    break;
            }
            ba baVar = sparseArray.get(i);
            if (z7) {
                baVarA = a(baVar, sparseArray, i);
                baVarA.cj = fragment;
                baVarA.ck = z;
                baVarA.cl = iVar;
            } else {
                baVarA = baVar;
            }
            if (!z2 && z4) {
                if (baVarA != null && baVarA.cm == fragment) {
                    baVarA.cm = null;
                }
                ad adVar = iVar.L;
                if (fragment.mState < 1 && adVar.aZ >= 1 && !iVar.ac) {
                    adVar.i(fragment);
                    adVar.a(fragment, 1, 0, 0, false);
                }
            }
            if (z5 && (baVarA == null || baVarA.cm == null)) {
                baVarA2 = a(baVarA, sparseArray, i);
                baVarA2.cm = fragment;
                baVarA2.cn = z;
                baVarA2.co = iVar;
            } else {
                baVarA2 = baVarA;
            }
            if (!z2 && z6 && baVarA2 != null && baVarA2.cj == fragment) {
                baVarA2.cj = null;
            }
        }
    }

    private static ba a(ba baVar, SparseArray<ba> sparseArray, int i) {
        if (baVar == null) {
            ba baVar2 = new ba();
            sparseArray.put(i, baVar2);
            return baVar2;
        }
        return baVar;
    }
}
