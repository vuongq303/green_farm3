package android.support.v4.app;

import android.os.Build;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* JADX INFO: compiled from: BackStackRecord.java */
/* JADX INFO: loaded from: classes.dex */
final class i extends au implements ao {
    static final boolean K;
    final ad L;
    int N;
    int O;
    int P;
    int Q;
    int R;
    int S;
    boolean T;
    boolean V;
    int W;
    CharSequence X;
    int Y;
    CharSequence Z;
    ArrayList<String> aa;
    ArrayList<String> ab;
    ArrayList<Runnable> ad;
    String mName;
    ArrayList<j> M = new ArrayList<>();
    boolean U = true;
    int mIndex = -1;
    boolean ac = false;

    static {
        K = Build.VERSION.SDK_INT >= 21;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mName != null) {
            sb.append(" ");
            sb.append(this.mName);
        }
        sb.append("}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        a(str, printWriter, true);
    }

    public void a(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.V);
            if (this.R != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.R));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.S));
            }
            if (this.N != 0 || this.O != 0) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.N));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.O));
            }
            if (this.P != 0 || this.Q != 0) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.P));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.Q));
            }
            if (this.W != 0 || this.X != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.W));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.X);
            }
            if (this.Y != 0 || this.Z != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.Y));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.Z);
            }
        }
        if (!this.M.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            String str3 = String.valueOf(str) + "    ";
            int size = this.M.size();
            for (int i = 0; i < size; i++) {
                j jVar = this.M.get(i);
                switch (jVar.ae) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    case 8:
                        str2 = "SET_PRIMARY_NAV";
                        break;
                    case 9:
                        str2 = "UNSET_PRIMARY_NAV";
                        break;
                    default:
                        str2 = "cmd=" + jVar.ae;
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.println(jVar.fragment);
                if (z) {
                    if (jVar.af != 0 || jVar.ag != 0) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(jVar.af));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(jVar.ag));
                    }
                    if (jVar.ah != 0 || jVar.ai != 0) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(jVar.ah));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(jVar.ai));
                    }
                }
            }
        }
    }

    public i(ad adVar) {
        this.L = adVar;
    }

    void a(j jVar) {
        this.M.add(jVar);
        jVar.af = this.N;
        jVar.ag = this.O;
        jVar.ah = this.P;
        jVar.ai = this.Q;
    }

    @Override // android.support.v4.app.au
    public au a(Fragment fragment, String str) {
        a(0, fragment, str, 1);
        return this;
    }

    @Override // android.support.v4.app.au
    public au a(int i, Fragment fragment, String str) {
        a(i, fragment, str, 1);
        return this;
    }

    private void a(int i, Fragment fragment, String str, int i2) {
        Class<?> cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        fragment.mFragmentManager = this.L;
        if (str != null) {
            if (fragment.mTag != null && !str.equals(fragment.mTag)) {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + str);
            }
            fragment.mTag = str;
        }
        if (i != 0) {
            if (i == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            }
            if (fragment.mFragmentId != 0 && fragment.mFragmentId != i) {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i);
            }
            fragment.mFragmentId = i;
            fragment.mContainerId = i;
        }
        a(new j(i2, fragment));
    }

    @Override // android.support.v4.app.au
    public au a(Fragment fragment) {
        a(new j(3, fragment));
        return this;
    }

    void a(int i) {
        if (this.T) {
            if (ad.DEBUG) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + i);
            }
            int size = this.M.size();
            for (int i2 = 0; i2 < size; i2++) {
                j jVar = this.M.get(i2);
                if (jVar.fragment != null) {
                    jVar.fragment.mBackStackNesting += i;
                    if (ad.DEBUG) {
                        Log.v("FragmentManager", "Bump nesting of " + jVar.fragment + " to " + jVar.fragment.mBackStackNesting);
                    }
                }
            }
        }
    }

    public void n() {
        if (this.ad != null) {
            int size = this.ad.size();
            for (int i = 0; i < size; i++) {
                this.ad.get(i).run();
            }
            this.ad = null;
        }
    }

    @Override // android.support.v4.app.au
    public int commit() {
        return a(false);
    }

    @Override // android.support.v4.app.au
    public int commitAllowingStateLoss() {
        return a(true);
    }

    int a(boolean z) {
        if (this.V) {
            throw new IllegalStateException("commit already called");
        }
        if (ad.DEBUG) {
            Log.v("FragmentManager", "Commit: " + this);
            PrintWriter printWriter = new PrintWriter(new android.support.v4.e.g("FragmentManager"));
            dump("  ", null, printWriter, null);
            printWriter.close();
        }
        this.V = true;
        if (this.T) {
            this.mIndex = this.L.a(this);
        } else {
            this.mIndex = -1;
        }
        this.L.a(this, z);
        return this.mIndex;
    }

    @Override // android.support.v4.app.ao
    public boolean a(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2) {
        if (ad.DEBUG) {
            Log.v("FragmentManager", "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (this.T) {
            this.L.b(this);
            return true;
        }
        return true;
    }

    boolean b(int i) {
        int size = this.M.size();
        for (int i2 = 0; i2 < size; i2++) {
            j jVar = this.M.get(i2);
            int i3 = jVar.fragment != null ? jVar.fragment.mContainerId : 0;
            if (i3 != 0 && i3 == i) {
                return true;
            }
        }
        return false;
    }

    boolean a(ArrayList<i> arrayList, int i, int i2) {
        int i3;
        if (i2 == i) {
            return false;
        }
        int size = this.M.size();
        int i4 = -1;
        int i5 = 0;
        while (i5 < size) {
            j jVar = this.M.get(i5);
            int i6 = jVar.fragment != null ? jVar.fragment.mContainerId : 0;
            if (i6 == 0 || i6 == i4) {
                i3 = i4;
            } else {
                for (int i7 = i; i7 < i2; i7++) {
                    i iVar = arrayList.get(i7);
                    int size2 = iVar.M.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        j jVar2 = iVar.M.get(i8);
                        if ((jVar2.fragment != null ? jVar2.fragment.mContainerId : 0) == i6) {
                            return true;
                        }
                    }
                }
                i3 = i6;
            }
            i5++;
            i4 = i3;
        }
        return false;
    }

    void o() {
        int size = this.M.size();
        for (int i = 0; i < size; i++) {
            j jVar = this.M.get(i);
            Fragment fragment = jVar.fragment;
            if (fragment != null) {
                fragment.setNextTransition(this.R, this.S);
            }
            switch (jVar.ae) {
                case 1:
                    fragment.setNextAnim(jVar.af);
                    this.L.a(fragment, false);
                    break;
                case 2:
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + jVar.ae);
                case 3:
                    fragment.setNextAnim(jVar.ag);
                    this.L.k(fragment);
                    break;
                case 4:
                    fragment.setNextAnim(jVar.ag);
                    this.L.l(fragment);
                    break;
                case 5:
                    fragment.setNextAnim(jVar.af);
                    this.L.m(fragment);
                    break;
                case 6:
                    fragment.setNextAnim(jVar.ag);
                    this.L.n(fragment);
                    break;
                case 7:
                    fragment.setNextAnim(jVar.af);
                    this.L.o(fragment);
                    break;
                case 8:
                    this.L.s(fragment);
                    break;
                case 9:
                    this.L.s(null);
                    break;
            }
            if (!this.ac && jVar.ae != 1 && fragment != null) {
                this.L.h(fragment);
            }
        }
        if (!this.ac) {
            this.L.a(this.L.aZ, true);
        }
    }

    void b(boolean z) {
        for (int size = this.M.size() - 1; size >= 0; size--) {
            j jVar = this.M.get(size);
            Fragment fragment = jVar.fragment;
            if (fragment != null) {
                fragment.setNextTransition(ad.i(this.R), this.S);
            }
            switch (jVar.ae) {
                case 1:
                    fragment.setNextAnim(jVar.ai);
                    this.L.k(fragment);
                    break;
                case 2:
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + jVar.ae);
                case 3:
                    fragment.setNextAnim(jVar.ah);
                    this.L.a(fragment, false);
                    break;
                case 4:
                    fragment.setNextAnim(jVar.ah);
                    this.L.m(fragment);
                    break;
                case 5:
                    fragment.setNextAnim(jVar.ai);
                    this.L.l(fragment);
                    break;
                case 6:
                    fragment.setNextAnim(jVar.ah);
                    this.L.o(fragment);
                    break;
                case 7:
                    fragment.setNextAnim(jVar.ai);
                    this.L.n(fragment);
                    break;
                case 8:
                    this.L.s(null);
                    break;
                case 9:
                    this.L.s(fragment);
                    break;
            }
            if (!this.ac && jVar.ae != 3 && fragment != null) {
                this.L.h(fragment);
            }
        }
        if (!this.ac && z) {
            this.L.a(this.L.aZ, true);
        }
    }

    Fragment a(ArrayList<Fragment> arrayList, Fragment fragment) {
        boolean z;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.M.size()) {
                j jVar = this.M.get(i2);
                switch (jVar.ae) {
                    case 1:
                    case 7:
                        arrayList.add(jVar.fragment);
                        break;
                    case 2:
                        Fragment fragment2 = jVar.fragment;
                        int i3 = fragment2.mContainerId;
                        boolean z2 = false;
                        int size = arrayList.size() - 1;
                        int i4 = i2;
                        Fragment fragment3 = fragment;
                        while (size >= 0) {
                            Fragment fragment4 = arrayList.get(size);
                            if (fragment4.mContainerId != i3) {
                                z = z2;
                            } else if (fragment4 == fragment2) {
                                z = true;
                            } else {
                                if (fragment4 == fragment3) {
                                    this.M.add(i4, new j(9, fragment4));
                                    i4++;
                                    fragment3 = null;
                                }
                                j jVar2 = new j(3, fragment4);
                                jVar2.af = jVar.af;
                                jVar2.ah = jVar.ah;
                                jVar2.ag = jVar.ag;
                                jVar2.ai = jVar.ai;
                                this.M.add(i4, jVar2);
                                arrayList.remove(fragment4);
                                i4++;
                                z = z2;
                            }
                            size--;
                            z2 = z;
                        }
                        if (z2) {
                            this.M.remove(i4);
                            i2 = i4 - 1;
                            fragment = fragment3;
                        } else {
                            jVar.ae = 1;
                            arrayList.add(fragment2);
                            i2 = i4;
                            fragment = fragment3;
                        }
                        break;
                    case 3:
                    case 6:
                        arrayList.remove(jVar.fragment);
                        if (jVar.fragment == fragment) {
                            this.M.add(i2, new j(9, jVar.fragment));
                            i2++;
                            fragment = null;
                        }
                        break;
                    case 8:
                        this.M.add(i2, new j(9, fragment));
                        i2++;
                        fragment = jVar.fragment;
                        break;
                }
                i = i2 + 1;
            } else {
                return fragment;
            }
        }
    }

    Fragment b(ArrayList<Fragment> arrayList, Fragment fragment) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.M.size()) {
                j jVar = this.M.get(i2);
                switch (jVar.ae) {
                    case 1:
                    case 7:
                        arrayList.remove(jVar.fragment);
                        break;
                    case 3:
                    case 6:
                        arrayList.add(jVar.fragment);
                        break;
                    case 8:
                        fragment = null;
                        break;
                    case 9:
                        fragment = jVar.fragment;
                        break;
                }
                i = i2 + 1;
            } else {
                return fragment;
            }
        }
    }

    boolean isPostponed() {
        for (int i = 0; i < this.M.size(); i++) {
            if (b(this.M.get(i))) {
                return true;
            }
        }
        return false;
    }

    void a(s sVar) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.M.size()) {
                j jVar = this.M.get(i2);
                if (b(jVar)) {
                    jVar.fragment.setOnStartEnterTransitionListener(sVar);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static boolean b(j jVar) {
        Fragment fragment = jVar.fragment;
        return (fragment == null || !fragment.mAdded || fragment.mView == null || fragment.mDetached || fragment.mHidden || !fragment.isPostponed()) ? false : true;
    }

    public String getName() {
        return this.mName;
    }
}
