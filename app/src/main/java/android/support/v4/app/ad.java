package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
final class ad extends aa implements LayoutInflater.Factory2 {
    static boolean DEBUG = false;
    static Field bd = null;
    static final Interpolator br = new DecelerateInterpolator(2.5f);
    static final Interpolator bs = new DecelerateInterpolator(1.5f);
    static final Interpolator bt = new AccelerateInterpolator(2.5f);
    static final Interpolator bu = new AccelerateInterpolator(1.5f);
    ArrayList<ao> aO;
    boolean aP;
    SparseArray<Fragment> aS;
    ArrayList<i> aT;
    ArrayList<Fragment> aU;
    ArrayList<i> aV;
    ArrayList<Integer> aW;
    ArrayList<ac> aX;
    private CopyOnWriteArrayList<android.support.v4.e.p<ab, Boolean>> aY;
    x ba;
    Fragment bb;
    Fragment bc;
    boolean be;
    boolean bf;
    boolean bg;
    String bh;
    boolean bi;
    ArrayList<i> bj;
    ArrayList<Boolean> bk;
    ArrayList<Fragment> bl;
    ArrayList<aq> bo;
    ar bp;
    z mHost;
    int aQ = 0;
    final ArrayList<Fragment> aR = new ArrayList<>();
    int aZ = 0;
    Bundle bm = null;
    SparseArray<Parcelable> bn = null;
    Runnable bq = new ae(this);

    ad() {
    }

    static boolean a(al alVar) {
        if (alVar.bC instanceof AlphaAnimation) {
            return true;
        }
        if (alVar.bC instanceof AnimationSet) {
            List<Animation> animations = ((AnimationSet) alVar.bC).getAnimations();
            for (int i = 0; i < animations.size(); i++) {
                if (animations.get(i) instanceof AlphaAnimation) {
                    return true;
                }
            }
            return false;
        }
        return a(alVar.bD);
    }

    static boolean a(Animator animator) {
        if (animator == null) {
            return false;
        }
        if (animator instanceof ValueAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
            return false;
        }
        if (!(animator instanceof AnimatorSet)) {
            return false;
        }
        ArrayList<Animator> childAnimations = ((AnimatorSet) animator).getChildAnimations();
        for (int i = 0; i < childAnimations.size(); i++) {
            if (a(childAnimations.get(i))) {
                return true;
            }
        }
        return false;
    }

    static boolean a(View view, al alVar) {
        return view != null && alVar != null && Build.VERSION.SDK_INT >= 19 && view.getLayerType() == 0 && android.support.v4.view.aa.l(view) && a(alVar);
    }

    private void a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new android.support.v4.e.g("FragmentManager"));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, printWriter, new String[0]);
                throw runtimeException;
            } catch (Exception e) {
                Log.e("FragmentManager", "Failed dumping state", e);
                throw runtimeException;
            }
        }
        try {
            dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        } catch (Exception e2) {
            Log.e("FragmentManager", "Failed dumping state", e2);
            throw runtimeException;
        }
    }

    @Override // android.support.v4.app.aa
    public au y() {
        return new i(this);
    }

    @Override // android.support.v4.app.aa
    public boolean popBackStackImmediate() {
        A();
        return a((String) null, -1, 0);
    }

    @Override // android.support.v4.app.aa
    public void popBackStack(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Bad id: " + i);
        }
        a((ao) new ap(this, null, i, i2), false);
    }

    private boolean a(String str, int i, int i2) {
        aa aaVarPeekChildFragmentManager;
        execPendingActions();
        c(true);
        if (this.bc != null && i < 0 && str == null && (aaVarPeekChildFragmentManager = this.bc.peekChildFragmentManager()) != null && aaVarPeekChildFragmentManager.popBackStackImmediate()) {
            return true;
        }
        boolean zA = a(this.bj, this.bk, str, i, i2);
        if (zA) {
            this.aP = true;
            try {
                c(this.bj, this.bk);
                C();
            } catch (Throwable th) {
                C();
                throw th;
            }
        }
        F();
        J();
        return zA;
    }

    public void a(Bundle bundle, String str, Fragment fragment) {
        if (fragment.mIndex < 0) {
            a(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(str, fragment.mIndex);
    }

    public Fragment a(Bundle bundle, String str) {
        int i = bundle.getInt(str, -1);
        if (i == -1) {
            return null;
        }
        Fragment fragment = this.aS.get(i);
        if (fragment == null) {
            a(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
            return fragment;
        }
        return fragment;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.bb != null) {
            android.support.v4.e.f.a(this.bb, sb);
        } else {
            android.support.v4.e.f.a(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    @Override // android.support.v4.app.aa
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        String str2 = String.valueOf(str) + "    ";
        if (this.aS != null && (size5 = this.aS.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i = 0; i < size5; i++) {
                Fragment fragmentValueAt = this.aS.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragmentValueAt);
                if (fragmentValueAt != null) {
                    fragmentValueAt.dump(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        int size6 = this.aR.size();
        if (size6 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size6; i2++) {
                Fragment fragment = this.aR.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment.toString());
            }
        }
        if (this.aU != null && (size4 = this.aU.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < size4; i3++) {
                Fragment fragment2 = this.aU.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
        if (this.aT != null && (size3 = this.aT.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i4 = 0; i4 < size3; i4++) {
                i iVar = this.aT.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(iVar.toString());
                iVar.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        synchronized (this) {
            if (this.aV != null && (size2 = this.aV.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i5 = 0; i5 < size2; i5++) {
                    Object obj = (i) this.aV.get(i5);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(obj);
                }
            }
            if (this.aW != null && this.aW.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.aW.toArray()));
            }
        }
        if (this.aO != null && (size = this.aO.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i6 = 0; i6 < size; i6++) {
                Object obj2 = (ao) this.aO.get(i6);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i6);
                printWriter.print(": ");
                printWriter.println(obj2);
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.ba);
        if (this.bb != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.bb);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.aZ);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.bf);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.bg);
        if (this.be) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.be);
        }
        if (this.bh != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.bh);
        }
    }

    static al a(Context context, float f, float f2, float f3, float f4) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(br);
        scaleAnimation.setDuration(220L);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f4);
        alphaAnimation.setInterpolator(bs);
        alphaAnimation.setDuration(220L);
        animationSet.addAnimation(alphaAnimation);
        return new al(animationSet, (al) null);
    }

    static al a(Context context, float f, float f2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setInterpolator(bs);
        alphaAnimation.setDuration(220L);
        return new al(alphaAnimation, (al) null);
    }

    al a(Fragment fragment, int i, boolean z, int i2) {
        int iB;
        boolean z2;
        int nextAnim = fragment.getNextAnim();
        Animation animationOnCreateAnimation = fragment.onCreateAnimation(i, z, nextAnim);
        if (animationOnCreateAnimation != null) {
            return new al(animationOnCreateAnimation, (al) null);
        }
        Animator animatorOnCreateAnimator = fragment.onCreateAnimator(i, z, nextAnim);
        if (animatorOnCreateAnimator != null) {
            return new al(animatorOnCreateAnimator, (al) null);
        }
        if (nextAnim != 0) {
            boolean zEquals = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(nextAnim));
            if (!zEquals) {
                z2 = false;
            } else {
                try {
                    Animation animationLoadAnimation = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animationLoadAnimation != null) {
                        return new al(animationLoadAnimation, (al) null);
                    }
                    z2 = true;
                } catch (Resources.NotFoundException e) {
                    throw e;
                } catch (RuntimeException e2) {
                    z2 = false;
                }
            }
            if (!z2) {
                try {
                    Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this.mHost.getContext(), nextAnim);
                    if (animatorLoadAnimator != null) {
                        return new al(animatorLoadAnimator, (al) null);
                    }
                } catch (RuntimeException e3) {
                    if (zEquals) {
                        throw e3;
                    }
                    Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animationLoadAnimation2 != null) {
                        return new al(animationLoadAnimation2, (al) null);
                    }
                }
            }
        }
        if (i != 0 && (iB = b(i, z)) >= 0) {
            switch (iB) {
                case 1:
                    return a(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
                case 2:
                    return a(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
                case 3:
                    return a(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
                case 4:
                    return a(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
                case 5:
                    return a(this.mHost.getContext(), 0.0f, 1.0f);
                case 6:
                    return a(this.mHost.getContext(), 1.0f, 0.0f);
                default:
                    if (i2 == 0 && this.mHost.onHasWindowAnimations()) {
                        i2 = this.mHost.onGetWindowAnimations();
                    }
                    return i2 == 0 ? null : null;
            }
        }
        return null;
    }

    public void d(Fragment fragment) {
        if (fragment.mDeferStart) {
            if (this.aP) {
                this.bi = true;
            } else {
                fragment.mDeferStart = false;
                a(fragment, this.aZ, 0, 0, false);
            }
        }
    }

    private static void b(View view, al alVar) {
        if (view != null && alVar != null && a(view, alVar)) {
            if (alVar.bD != null) {
                alVar.bD.addListener(new am(view));
                return;
            }
            Animation.AnimationListener animationListenerA = a(alVar.bC);
            view.setLayerType(2, null);
            alVar.bC.setAnimationListener(new ai(view, animationListenerA));
        }
    }

    private static Animation.AnimationListener a(Animation animation) {
        try {
            if (bd == null) {
                bd = Animation.class.getDeclaredField("mListener");
                bd.setAccessible(true);
            }
            return (Animation.AnimationListener) bd.get(animation);
        } catch (IllegalAccessException e) {
            Log.e("FragmentManager", "Cannot access Animation's mListener field", e);
            return null;
        } catch (NoSuchFieldException e2) {
            Log.e("FragmentManager", "No field with the name mListener is found in Animation class", e2);
            return null;
        }
    }

    boolean e(int i) {
        return this.aZ >= i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:139:0x0332  */
    /* JADX WARN: Code duplicated, block: B:141:0x0336  */
    /* JADX WARN: Code duplicated, block: B:143:0x033c  */
    /* JADX WARN: Code duplicated, block: B:146:0x034c  */
    /* JADX WARN: Code duplicated, block: B:148:0x0352  */
    /* JADX WARN: Code duplicated, block: B:156:0x037d  */
    /* JADX WARN: Code duplicated, block: B:158:0x0381  */
    /* JADX WARN: Code duplicated, block: B:161:0x039f  */
    /* JADX WARN: Code duplicated, block: B:163:0x03a3  */
    /* JADX WARN: Code duplicated, block: B:167:0x03bf  */
    /* JADX WARN: Code duplicated, block: B:169:0x03c3  */
    /* JADX WARN: Code duplicated, block: B:172:0x03dd  */
    /* JADX WARN: Code duplicated, block: B:179:0x03f6  */
    /* JADX WARN: Code duplicated, block: B:183:0x040a  */
    /* JADX WARN: Code duplicated, block: B:192:0x0426  */
    /* JADX WARN: Code duplicated, block: B:195:0x043a  */
    /* JADX WARN: Code duplicated, block: B:197:0x0440  */
    /* JADX WARN: Code duplicated, block: B:211:0x048c  */
    /* JADX WARN: Code duplicated, block: B:215:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x0063  */
    /* JADX WARN: Switch 'out' block B:38:0x005f for B:136:0x032b already processed. Defaulting to fallback option. */
    void a(Fragment fragment, int i, int i2, int i3, boolean z) {
        al alVarA;
        ViewGroup viewGroup;
        String resourceName;
        if ((!fragment.mAdded || fragment.mDetached) && i > 1) {
            i = 1;
        }
        if (fragment.mRemoving && i > fragment.mState) {
            i = (fragment.mState == 0 && fragment.isInBackStack()) ? 1 : fragment.mState;
        }
        if (fragment.mDeferStart && fragment.mState < 4 && i > 3) {
            i = 3;
        }
        if (fragment.mState <= i) {
            if (!fragment.mFromLayout || fragment.mInLayout) {
                if (fragment.getAnimatingAway() != null || fragment.getAnimator() != null) {
                    fragment.setAnimatingAway(null);
                    fragment.setAnimator(null);
                    a(fragment, fragment.getStateAfterAnimating(), 0, 0, true);
                }
                switch (fragment.mState) {
                    case 0:
                        if (i > 0) {
                            if (DEBUG) {
                                Log.v("FragmentManager", "moveto CREATED: " + fragment);
                            }
                            if (fragment.mSavedFragmentState != null) {
                                fragment.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                                fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                                fragment.mTarget = a(fragment.mSavedFragmentState, "android:target_state");
                                if (fragment.mTarget != null) {
                                    fragment.mTargetRequestCode = fragment.mSavedFragmentState.getInt("android:target_req_state", 0);
                                }
                                fragment.mUserVisibleHint = fragment.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
                                if (!fragment.mUserVisibleHint) {
                                    fragment.mDeferStart = true;
                                    if (i > 3) {
                                        i = 3;
                                    }
                                }
                            }
                            fragment.mHost = this.mHost;
                            fragment.mParentFragment = this.bb;
                            fragment.mFragmentManager = this.bb != null ? this.bb.mChildFragmentManager : this.mHost.v();
                            if (fragment.mTarget != null) {
                                if (this.aS.get(fragment.mTarget.mIndex) != fragment.mTarget) {
                                    throw new IllegalStateException("Fragment " + fragment + " declared target fragment " + fragment.mTarget + " that does not belong to this FragmentManager!");
                                }
                                if (fragment.mTarget.mState < 1) {
                                    a(fragment.mTarget, 1, 0, 0, true);
                                }
                            }
                            a(fragment, this.mHost.getContext(), false);
                            fragment.mCalled = false;
                            fragment.onAttach(this.mHost.getContext());
                            if (!fragment.mCalled) {
                                throw new Cdo("Fragment " + fragment + " did not call through to super.onAttach()");
                            }
                            if (fragment.mParentFragment == null) {
                                this.mHost.onAttachFragment(fragment);
                            } else {
                                fragment.mParentFragment.onAttachFragment(fragment);
                            }
                            b(fragment, this.mHost.getContext(), false);
                            if (!fragment.mRetaining) {
                                a(fragment, fragment.mSavedFragmentState, false);
                                fragment.performCreate(fragment.mSavedFragmentState);
                                b(fragment, fragment.mSavedFragmentState, false);
                            } else {
                                fragment.restoreChildFragmentState(fragment.mSavedFragmentState);
                                fragment.mState = 1;
                            }
                            fragment.mRetaining = false;
                            break;
                        }
                    case 1:
                        f(fragment);
                        if (i > 1) {
                            if (DEBUG) {
                                Log.v("FragmentManager", "moveto ACTIVITY_CREATED: " + fragment);
                            }
                            if (!fragment.mFromLayout) {
                                if (fragment.mContainerId != 0) {
                                    if (fragment.mContainerId == -1) {
                                        a(new IllegalArgumentException("Cannot create fragment " + fragment + " for a container view with no id"));
                                    }
                                    viewGroup = (ViewGroup) this.ba.onFindViewById(fragment.mContainerId);
                                    if (viewGroup == null && !fragment.mRestored) {
                                        try {
                                            resourceName = fragment.getResources().getResourceName(fragment.mContainerId);
                                        } catch (Resources.NotFoundException e) {
                                            resourceName = "unknown";
                                        }
                                        a(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(fragment.mContainerId) + " (" + resourceName + ") for fragment " + fragment));
                                    }
                                } else {
                                    viewGroup = null;
                                }
                                fragment.mContainer = viewGroup;
                                fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), viewGroup, fragment.mSavedFragmentState);
                                if (fragment.mView != null) {
                                    fragment.mInnerView = fragment.mView;
                                    fragment.mView.setSaveFromParentEnabled(false);
                                    if (viewGroup != null) {
                                        viewGroup.addView(fragment.mView);
                                    }
                                    if (fragment.mHidden) {
                                        fragment.mView.setVisibility(8);
                                    }
                                    fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                                    a(fragment, fragment.mView, fragment.mSavedFragmentState, false);
                                    fragment.mIsNewlyAdded = fragment.mView.getVisibility() == 0 && fragment.mContainer != null;
                                } else {
                                    fragment.mInnerView = null;
                                }
                            }
                            fragment.performActivityCreated(fragment.mSavedFragmentState);
                            c(fragment, fragment.mSavedFragmentState, false);
                            if (fragment.mView != null) {
                                fragment.restoreViewState(fragment.mSavedFragmentState);
                            }
                            fragment.mSavedFragmentState = null;
                            break;
                        }
                    case 2:
                        if (i > 2) {
                            fragment.mState = 3;
                            break;
                        }
                    case 3:
                        if (i > 3) {
                            if (DEBUG) {
                                Log.v("FragmentManager", "moveto STARTED: " + fragment);
                            }
                            fragment.performStart();
                            b(fragment, false);
                            break;
                        }
                    case 4:
                        if (i > 4) {
                            if (DEBUG) {
                                Log.v("FragmentManager", "moveto RESUMED: " + fragment);
                            }
                            fragment.performResume();
                            c(fragment, false);
                            fragment.mSavedFragmentState = null;
                            fragment.mSavedViewState = null;
                            break;
                        }
                    default:
                        if (fragment.mState != i) {
                            Log.w("FragmentManager", "moveToState: Fragment state for " + fragment + " not updated inline; expected state " + i + " found " + fragment.mState);
                            fragment.mState = i;
                        }
                }
            } else {
                return;
            }
        } else if (fragment.mState > i) {
            switch (fragment.mState) {
                case 1:
                    if (i < 1) {
                        if (this.bg) {
                            if (fragment.getAnimatingAway() != null) {
                                View animatingAway = fragment.getAnimatingAway();
                                fragment.setAnimatingAway(null);
                                animatingAway.clearAnimation();
                            } else if (fragment.getAnimator() != null) {
                                Animator animator = fragment.getAnimator();
                                fragment.setAnimator(null);
                                animator.cancel();
                            }
                        }
                        if (fragment.getAnimatingAway() == null || fragment.getAnimator() != null) {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        } else {
                            if (DEBUG) {
                                Log.v("FragmentManager", "movefrom CREATED: " + fragment);
                            }
                            if (!fragment.mRetaining) {
                                fragment.performDestroy();
                                g(fragment, false);
                            } else {
                                fragment.mState = 0;
                            }
                            fragment.performDetach();
                            h(fragment, false);
                            if (!z) {
                                if (!fragment.mRetaining) {
                                    j(fragment);
                                } else {
                                    fragment.mHost = null;
                                    fragment.mParentFragment = null;
                                    fragment.mFragmentManager = null;
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    if (i < 2) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + fragment);
                        }
                        if (fragment.mView != null && this.mHost.b(fragment) && fragment.mSavedViewState == null) {
                            q(fragment);
                        }
                        fragment.performDestroyView();
                        f(fragment, false);
                        if (fragment.mView != null && fragment.mContainer != null) {
                            fragment.mView.clearAnimation();
                            fragment.mContainer.endViewTransition(fragment.mView);
                            if (this.aZ > 0 || this.bg || fragment.mView.getVisibility() != 0 || fragment.mPostponedAlpha < 0.0f) {
                                alVarA = null;
                            } else {
                                alVarA = a(fragment, i2, false, i3);
                            }
                            fragment.mPostponedAlpha = 0.0f;
                            if (alVarA != null) {
                                a(fragment, alVarA, i);
                            }
                            fragment.mContainer.removeView(fragment.mView);
                        }
                        fragment.mContainer = null;
                        fragment.mView = null;
                        fragment.mInnerView = null;
                        fragment.mInLayout = false;
                    }
                    if (i < 1) {
                        if (this.bg) {
                            if (fragment.getAnimatingAway() != null) {
                                View animatingAway2 = fragment.getAnimatingAway();
                                fragment.setAnimatingAway(null);
                                animatingAway2.clearAnimation();
                            } else if (fragment.getAnimator() != null) {
                                Animator animator2 = fragment.getAnimator();
                                fragment.setAnimator(null);
                                animator2.cancel();
                            }
                        }
                        if (fragment.getAnimatingAway() == null) {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        } else {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        }
                    }
                    break;
                case 3:
                    if (i < 3) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom STOPPED: " + fragment);
                        }
                        fragment.performReallyStop();
                    }
                    if (i < 2) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + fragment);
                        }
                        if (fragment.mView != null) {
                            q(fragment);
                        }
                        fragment.performDestroyView();
                        f(fragment, false);
                        if (fragment.mView != null) {
                            fragment.mView.clearAnimation();
                            fragment.mContainer.endViewTransition(fragment.mView);
                            if (this.aZ > 0) {
                                alVarA = null;
                            } else {
                                alVarA = null;
                            }
                            fragment.mPostponedAlpha = 0.0f;
                            if (alVarA != null) {
                                a(fragment, alVarA, i);
                            }
                            fragment.mContainer.removeView(fragment.mView);
                        }
                        fragment.mContainer = null;
                        fragment.mView = null;
                        fragment.mInnerView = null;
                        fragment.mInLayout = false;
                    }
                    if (i < 1) {
                        if (this.bg) {
                            if (fragment.getAnimatingAway() != null) {
                                View animatingAway3 = fragment.getAnimatingAway();
                                fragment.setAnimatingAway(null);
                                animatingAway3.clearAnimation();
                            } else if (fragment.getAnimator() != null) {
                                Animator animator3 = fragment.getAnimator();
                                fragment.setAnimator(null);
                                animator3.cancel();
                            }
                        }
                        if (fragment.getAnimatingAway() == null) {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        } else {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        }
                    }
                    break;
                case 4:
                    if (i < 4) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom STARTED: " + fragment);
                        }
                        fragment.performStop();
                        e(fragment, false);
                    }
                    if (i < 3) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom STOPPED: " + fragment);
                        }
                        fragment.performReallyStop();
                    }
                    if (i < 2) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + fragment);
                        }
                        if (fragment.mView != null) {
                            q(fragment);
                        }
                        fragment.performDestroyView();
                        f(fragment, false);
                        if (fragment.mView != null) {
                            fragment.mView.clearAnimation();
                            fragment.mContainer.endViewTransition(fragment.mView);
                            if (this.aZ > 0) {
                                alVarA = null;
                            } else {
                                alVarA = null;
                            }
                            fragment.mPostponedAlpha = 0.0f;
                            if (alVarA != null) {
                                a(fragment, alVarA, i);
                            }
                            fragment.mContainer.removeView(fragment.mView);
                        }
                        fragment.mContainer = null;
                        fragment.mView = null;
                        fragment.mInnerView = null;
                        fragment.mInLayout = false;
                    }
                    if (i < 1) {
                        if (this.bg) {
                            if (fragment.getAnimatingAway() != null) {
                                View animatingAway4 = fragment.getAnimatingAway();
                                fragment.setAnimatingAway(null);
                                animatingAway4.clearAnimation();
                            } else if (fragment.getAnimator() != null) {
                                Animator animator4 = fragment.getAnimator();
                                fragment.setAnimator(null);
                                animator4.cancel();
                            }
                        }
                        if (fragment.getAnimatingAway() == null) {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        } else {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        }
                    }
                    break;
                case 5:
                    if (i < 5) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom RESUMED: " + fragment);
                        }
                        fragment.performPause();
                        d(fragment, false);
                    }
                    if (i < 4) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom STARTED: " + fragment);
                        }
                        fragment.performStop();
                        e(fragment, false);
                    }
                    if (i < 3) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom STOPPED: " + fragment);
                        }
                        fragment.performReallyStop();
                    }
                    if (i < 2) {
                        if (DEBUG) {
                            Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + fragment);
                        }
                        if (fragment.mView != null) {
                            q(fragment);
                        }
                        fragment.performDestroyView();
                        f(fragment, false);
                        if (fragment.mView != null) {
                            fragment.mView.clearAnimation();
                            fragment.mContainer.endViewTransition(fragment.mView);
                            if (this.aZ > 0) {
                                alVarA = null;
                            } else {
                                alVarA = null;
                            }
                            fragment.mPostponedAlpha = 0.0f;
                            if (alVarA != null) {
                                a(fragment, alVarA, i);
                            }
                            fragment.mContainer.removeView(fragment.mView);
                        }
                        fragment.mContainer = null;
                        fragment.mView = null;
                        fragment.mInnerView = null;
                        fragment.mInLayout = false;
                    }
                    if (i < 1) {
                        if (this.bg) {
                            if (fragment.getAnimatingAway() != null) {
                                View animatingAway5 = fragment.getAnimatingAway();
                                fragment.setAnimatingAway(null);
                                animatingAway5.clearAnimation();
                            } else if (fragment.getAnimator() != null) {
                                Animator animator5 = fragment.getAnimator();
                                fragment.setAnimator(null);
                                animator5.cancel();
                            }
                        }
                        if (fragment.getAnimatingAway() == null) {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        } else {
                            fragment.setStateAfterAnimating(i);
                            i = 1;
                        }
                    }
                    break;
            }
        }
        if (fragment.mState != i) {
            Log.w("FragmentManager", "moveToState: Fragment state for " + fragment + " not updated inline; expected state " + i + " found " + fragment.mState);
            fragment.mState = i;
        }
    }

    private void a(Fragment fragment, al alVar, int i) {
        View view = fragment.mView;
        fragment.setStateAfterAnimating(i);
        if (alVar.bC != null) {
            Animation animation = alVar.bC;
            fragment.setAnimatingAway(fragment.mView);
            animation.setAnimationListener(new af(this, a(animation), fragment));
            b(view, alVar);
            fragment.mView.startAnimation(animation);
            return;
        }
        Animator animator = alVar.bD;
        fragment.setAnimator(alVar.bD);
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            viewGroup.startViewTransition(view);
        }
        animator.addListener(new ag(this, viewGroup, view, fragment));
        animator.setTarget(fragment.mView);
        b(fragment.mView, alVar);
        animator.start();
    }

    void e(Fragment fragment) {
        a(fragment, this.aZ, 0, 0, false);
    }

    void f(Fragment fragment) {
        if (fragment.mFromLayout && !fragment.mPerformedCreateView) {
            fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
            if (fragment.mView != null) {
                fragment.mInnerView = fragment.mView;
                fragment.mView.setSaveFromParentEnabled(false);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                a(fragment, fragment.mView, fragment.mSavedFragmentState, false);
                return;
            }
            fragment.mInnerView = null;
        }
    }

    void g(Fragment fragment) {
        if (fragment.mView != null) {
            al alVarA = a(fragment, fragment.getNextTransition(), !fragment.mHidden, fragment.getNextTransitionStyle());
            if (alVarA != null && alVarA.bD != null) {
                alVarA.bD.setTarget(fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        ViewGroup viewGroup = fragment.mContainer;
                        View view = fragment.mView;
                        viewGroup.startViewTransition(view);
                        alVarA.bD.addListener(new ah(this, viewGroup, view, fragment));
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                b(fragment.mView, alVarA);
                alVarA.bD.start();
            } else {
                if (alVarA != null) {
                    b(fragment.mView, alVarA);
                    fragment.mView.startAnimation(alVarA.bC);
                    alVarA.bC.start();
                }
                fragment.mView.setVisibility((!fragment.mHidden || fragment.isHideReplaced()) ? 0 : 8);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.be = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    void h(Fragment fragment) {
        if (fragment != null) {
            int iMin = this.aZ;
            if (fragment.mRemoving) {
                if (fragment.isInBackStack()) {
                    iMin = Math.min(iMin, 1);
                } else {
                    iMin = Math.min(iMin, 0);
                }
            }
            a(fragment, iMin, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false);
            if (fragment.mView != null) {
                Fragment fragmentP = p(fragment);
                if (fragmentP != null) {
                    View view = fragmentP.mView;
                    ViewGroup viewGroup = fragment.mContainer;
                    int iIndexOfChild = viewGroup.indexOfChild(view);
                    int iIndexOfChild2 = viewGroup.indexOfChild(fragment.mView);
                    if (iIndexOfChild2 < iIndexOfChild) {
                        viewGroup.removeViewAt(iIndexOfChild2);
                        viewGroup.addView(fragment.mView, iIndexOfChild);
                    }
                }
                if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                    if (fragment.mPostponedAlpha > 0.0f) {
                        fragment.mView.setAlpha(fragment.mPostponedAlpha);
                    }
                    fragment.mPostponedAlpha = 0.0f;
                    fragment.mIsNewlyAdded = false;
                    al alVarA = a(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle());
                    if (alVarA != null) {
                        b(fragment.mView, alVarA);
                        if (alVarA.bC != null) {
                            fragment.mView.startAnimation(alVarA.bC);
                        } else {
                            alVarA.bD.setTarget(fragment.mView);
                            alVarA.bD.start();
                        }
                    }
                }
            }
            if (fragment.mHiddenChanged) {
                g(fragment);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:42:0x008c  */
    void a(int i, boolean z) {
        boolean zP;
        if (this.mHost == null && i != 0) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.aZ) {
            this.aZ = i;
            if (this.aS != null) {
                int size = this.aR.size();
                int i2 = 0;
                boolean zP2 = false;
                while (i2 < size) {
                    Fragment fragment = this.aR.get(i2);
                    h(fragment);
                    i2++;
                    zP2 = fragment.mLoaderManager != null ? fragment.mLoaderManager.P() | zP2 : zP2;
                }
                int size2 = this.aS.size();
                int i3 = 0;
                while (i3 < size2) {
                    Fragment fragmentValueAt = this.aS.valueAt(i3);
                    if (fragmentValueAt == null || (!(fragmentValueAt.mRemoving || fragmentValueAt.mDetached) || fragmentValueAt.mIsNewlyAdded)) {
                        zP = zP2;
                    } else {
                        h(fragmentValueAt);
                        if (fragmentValueAt.mLoaderManager != null) {
                            zP = fragmentValueAt.mLoaderManager.P() | zP2;
                        } else {
                            zP = zP2;
                        }
                    }
                    i3++;
                    zP2 = zP;
                }
                if (!zP2) {
                    z();
                }
                if (this.be && this.mHost != null && this.aZ == 5) {
                    this.mHost.r();
                    this.be = false;
                }
            }
        }
    }

    void z() {
        if (this.aS != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.aS.size()) {
                    Fragment fragmentValueAt = this.aS.valueAt(i2);
                    if (fragmentValueAt != null) {
                        d(fragmentValueAt);
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    void i(Fragment fragment) {
        if (fragment.mIndex < 0) {
            int i = this.aQ;
            this.aQ = i + 1;
            fragment.setIndex(i, this.bb);
            if (this.aS == null) {
                this.aS = new SparseArray<>();
            }
            this.aS.put(fragment.mIndex, fragment);
            if (DEBUG) {
                Log.v("FragmentManager", "Allocated fragment index " + fragment);
            }
        }
    }

    void j(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            if (DEBUG) {
                Log.v("FragmentManager", "Freeing fragment index " + fragment);
            }
            this.aS.put(fragment.mIndex, null);
            this.mHost.g(fragment.mWho);
            fragment.initState();
        }
    }

    public void a(Fragment fragment, boolean z) {
        if (DEBUG) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        i(fragment);
        if (!fragment.mDetached) {
            if (this.aR.contains(fragment)) {
                throw new IllegalStateException("Fragment already added: " + fragment);
            }
            synchronized (this.aR) {
                this.aR.add(fragment);
            }
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.be = true;
            }
            if (z) {
                e(fragment);
            }
        }
    }

    public void k(Fragment fragment) {
        if (DEBUG) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            synchronized (this.aR) {
                this.aR.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.be = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
        }
    }

    public void l(Fragment fragment) {
        if (DEBUG) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = fragment.mHiddenChanged ? false : true;
        }
    }

    public void m(Fragment fragment) {
        if (DEBUG) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = fragment.mHiddenChanged ? false : true;
        }
    }

    public void n(Fragment fragment) {
        if (DEBUG) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (DEBUG) {
                    Log.v("FragmentManager", "remove from detach: " + fragment);
                }
                synchronized (this.aR) {
                    this.aR.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.be = true;
                }
                fragment.mAdded = false;
            }
        }
    }

    public void o(Fragment fragment) {
        if (DEBUG) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.aR.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    Log.v("FragmentManager", "add from attach: " + fragment);
                }
                synchronized (this.aR) {
                    this.aR.add(fragment);
                }
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.be = true;
                }
            }
        }
    }

    public Fragment f(int i) {
        for (int size = this.aR.size() - 1; size >= 0; size--) {
            Fragment fragment = this.aR.get(size);
            if (fragment != null && fragment.mFragmentId == i) {
                return fragment;
            }
        }
        if (this.aS != null) {
            for (int size2 = this.aS.size() - 1; size2 >= 0; size2--) {
                Fragment fragmentValueAt = this.aS.valueAt(size2);
                if (fragmentValueAt != null && fragmentValueAt.mFragmentId == i) {
                    return fragmentValueAt;
                }
            }
        }
        return null;
    }

    @Override // android.support.v4.app.aa
    public Fragment h(String str) {
        if (str != null) {
            for (int size = this.aR.size() - 1; size >= 0; size--) {
                Fragment fragment = this.aR.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (this.aS != null && str != null) {
            for (int size2 = this.aS.size() - 1; size2 >= 0; size2--) {
                Fragment fragmentValueAt = this.aS.valueAt(size2);
                if (fragmentValueAt != null && str.equals(fragmentValueAt.mTag)) {
                    return fragmentValueAt;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String str) {
        Fragment fragmentFindFragmentByWho;
        if (this.aS != null && str != null) {
            for (int size = this.aS.size() - 1; size >= 0; size--) {
                Fragment fragmentValueAt = this.aS.valueAt(size);
                if (fragmentValueAt != null && (fragmentFindFragmentByWho = fragmentValueAt.findFragmentByWho(str)) != null) {
                    return fragmentFindFragmentByWho;
                }
            }
        }
        return null;
    }

    private void A() {
        if (this.bf) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.bh != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.bh);
        }
    }

    @Override // android.support.v4.app.aa
    public boolean isStateSaved() {
        return this.bf;
    }

    public void a(ao aoVar, boolean z) {
        if (!z) {
            A();
        }
        synchronized (this) {
            if (this.bg || this.mHost == null) {
                if (!z) {
                    throw new IllegalStateException("Activity has been destroyed");
                }
            } else {
                if (this.aO == null) {
                    this.aO = new ArrayList<>();
                }
                this.aO.add(aoVar);
                B();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void B() {
        synchronized (this) {
            boolean z = (this.bo == null || this.bo.isEmpty()) ? false : true;
            boolean z2 = this.aO != null && this.aO.size() == 1;
            if (z || z2) {
                this.mHost.getHandler().removeCallbacks(this.bq);
                this.mHost.getHandler().post(this.bq);
            }
        }
    }

    public int a(i iVar) {
        int size;
        synchronized (this) {
            if (this.aW == null || this.aW.size() <= 0) {
                if (this.aV == null) {
                    this.aV = new ArrayList<>();
                }
                size = this.aV.size();
                if (DEBUG) {
                    Log.v("FragmentManager", "Setting back stack index " + size + " to " + iVar);
                }
                this.aV.add(iVar);
            } else {
                size = this.aW.remove(this.aW.size() - 1).intValue();
                if (DEBUG) {
                    Log.v("FragmentManager", "Adding back stack index " + size + " with " + iVar);
                }
                this.aV.set(size, iVar);
            }
        }
        return size;
    }

    public void a(int i, i iVar) {
        synchronized (this) {
            if (this.aV == null) {
                this.aV = new ArrayList<>();
            }
            int size = this.aV.size();
            if (i < size) {
                if (DEBUG) {
                    Log.v("FragmentManager", "Setting back stack index " + i + " to " + iVar);
                }
                this.aV.set(i, iVar);
            } else {
                while (size < i) {
                    this.aV.add(null);
                    if (this.aW == null) {
                        this.aW = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.v("FragmentManager", "Adding available back stack index " + size);
                    }
                    this.aW.add(Integer.valueOf(size));
                    size++;
                }
                if (DEBUG) {
                    Log.v("FragmentManager", "Adding back stack index " + i + " with " + iVar);
                }
                this.aV.add(iVar);
            }
        }
    }

    public void g(int i) {
        synchronized (this) {
            this.aV.set(i, null);
            if (this.aW == null) {
                this.aW = new ArrayList<>();
            }
            if (DEBUG) {
                Log.v("FragmentManager", "Freeing back stack index " + i);
            }
            this.aW.add(Integer.valueOf(i));
        }
    }

    private void c(boolean z) {
        if (this.aP) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z) {
            A();
        }
        if (this.bj == null) {
            this.bj = new ArrayList<>();
            this.bk = new ArrayList<>();
        }
        this.aP = true;
        try {
            b((ArrayList<i>) null, (ArrayList<Boolean>) null);
        } finally {
            this.aP = false;
        }
    }

    private void C() {
        this.aP = false;
        this.bk.clear();
        this.bj.clear();
    }

    public boolean execPendingActions() {
        c(true);
        boolean z = false;
        while (d(this.bj, this.bk)) {
            this.aP = true;
            try {
                c(this.bj, this.bk);
                C();
                z = true;
            } catch (Throwable th) {
                C();
                throw th;
            }
        }
        F();
        J();
        return z;
    }

    private void b(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2) {
        int iIndexOf;
        int iIndexOf2;
        int i = 0;
        int size = this.bo == null ? 0 : this.bo.size();
        while (i < size) {
            aq aqVar = this.bo.get(i);
            if (arrayList != null && !aqVar.bG && (iIndexOf2 = arrayList.indexOf(aqVar.bH)) != -1 && arrayList2.get(iIndexOf2).booleanValue()) {
                aqVar.N();
            } else if (aqVar.isReady() || (arrayList != null && aqVar.bH.a(arrayList, 0, arrayList.size()))) {
                this.bo.remove(i);
                i--;
                size--;
                if (arrayList != null && !aqVar.bG && (iIndexOf = arrayList.indexOf(aqVar.bH)) != -1 && arrayList2.get(iIndexOf).booleanValue()) {
                    aqVar.N();
                } else {
                    aqVar.M();
                }
            }
            i++;
            size = size;
        }
    }

    private void c(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2) {
        int i;
        int i2;
        if (arrayList != null && !arrayList.isEmpty()) {
            if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
                throw new IllegalStateException("Internal error with the back stack records");
            }
            b(arrayList, arrayList2);
            int size = arrayList.size();
            int i3 = 0;
            int i4 = 0;
            while (i3 < size) {
                if (arrayList.get(i3).ac) {
                    i = i3;
                    i2 = i4;
                } else {
                    if (i4 != i3) {
                        a(arrayList, arrayList2, i4, i3);
                    }
                    int i5 = i3 + 1;
                    if (arrayList2.get(i3).booleanValue()) {
                        while (i5 < size && arrayList2.get(i5).booleanValue() && !arrayList.get(i5).ac) {
                            i5++;
                        }
                    }
                    i2 = i5;
                    a(arrayList, arrayList2, i3, i2);
                    i = i2 - 1;
                }
                i3 = i + 1;
                i4 = i2;
            }
            if (i4 != size) {
                a(arrayList, arrayList2, i4, size);
            }
        }
    }

    private void a(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        int iA;
        Fragment fragmentB;
        boolean z = arrayList.get(i).ac;
        if (this.bl == null) {
            this.bl = new ArrayList<>();
        } else {
            this.bl.clear();
        }
        this.bl.addAll(this.aR);
        int i3 = i;
        Fragment fragmentK = K();
        boolean z2 = false;
        while (i3 < i2) {
            i iVar = arrayList.get(i3);
            if (!arrayList2.get(i3).booleanValue()) {
                fragmentB = iVar.a(this.bl, fragmentK);
            } else {
                fragmentB = iVar.b(this.bl, fragmentK);
            }
            i3++;
            fragmentK = fragmentB;
            z2 = z2 || iVar.T;
        }
        this.bl.clear();
        if (!z) {
            av.a(this, arrayList, arrayList2, i, i2, false);
        }
        b(arrayList, arrayList2, i, i2);
        if (z) {
            android.support.v4.e.c<Fragment> cVar = new android.support.v4.e.c<>();
            b(cVar);
            iA = a(arrayList, arrayList2, i, i2, cVar);
            a(cVar);
        } else {
            iA = i2;
        }
        if (iA != i && z) {
            av.a(this, arrayList, arrayList2, i, iA, true);
            a(this.aZ, true);
        }
        while (i < i2) {
            i iVar2 = arrayList.get(i);
            if (arrayList2.get(i).booleanValue() && iVar2.mIndex >= 0) {
                g(iVar2.mIndex);
                iVar2.mIndex = -1;
            }
            iVar2.n();
            i++;
        }
        if (z2) {
            G();
        }
    }

    private void a(android.support.v4.e.c<Fragment> cVar) {
        int size = cVar.size();
        for (int i = 0; i < size; i++) {
            Fragment fragmentValueAt = cVar.valueAt(i);
            if (!fragmentValueAt.mAdded) {
                View view = fragmentValueAt.getView();
                fragmentValueAt.mPostponedAlpha = view.getAlpha();
                view.setAlpha(0.0f);
            }
        }
    }

    private int a(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, android.support.v4.e.c<Fragment> cVar) {
        int i3;
        int i4 = i2 - 1;
        int i5 = i2;
        while (i4 >= i) {
            i iVar = arrayList.get(i4);
            boolean zBooleanValue = arrayList2.get(i4).booleanValue();
            if (iVar.isPostponed() && !iVar.a(arrayList, i4 + 1, i2)) {
                if (this.bo == null) {
                    this.bo = new ArrayList<>();
                }
                aq aqVar = new aq(iVar, zBooleanValue);
                this.bo.add(aqVar);
                iVar.a(aqVar);
                if (zBooleanValue) {
                    iVar.o();
                } else {
                    iVar.b(false);
                }
                int i6 = i5 - 1;
                if (i4 != i6) {
                    arrayList.remove(i4);
                    arrayList.add(i6, iVar);
                }
                b(cVar);
                i3 = i6;
            } else {
                i3 = i5;
            }
            i4--;
            i5 = i3;
        }
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(i iVar, boolean z, boolean z2, boolean z3) {
        if (z) {
            iVar.b(z3);
        } else {
            iVar.o();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(iVar);
        arrayList2.add(Boolean.valueOf(z));
        if (z2) {
            av.a(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z3) {
            a(this.aZ, true);
        }
        if (this.aS != null) {
            int size = this.aS.size();
            for (int i = 0; i < size; i++) {
                Fragment fragmentValueAt = this.aS.valueAt(i);
                if (fragmentValueAt != null && fragmentValueAt.mView != null && fragmentValueAt.mIsNewlyAdded && iVar.b(fragmentValueAt.mContainerId)) {
                    if (fragmentValueAt.mPostponedAlpha > 0.0f) {
                        fragmentValueAt.mView.setAlpha(fragmentValueAt.mPostponedAlpha);
                    }
                    if (z3) {
                        fragmentValueAt.mPostponedAlpha = 0.0f;
                    } else {
                        fragmentValueAt.mPostponedAlpha = -1.0f;
                        fragmentValueAt.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    private Fragment p(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup == null || view == null) {
            return null;
        }
        for (int iIndexOf = this.aR.indexOf(fragment) - 1; iIndexOf >= 0; iIndexOf--) {
            Fragment fragment2 = this.aR.get(iIndexOf);
            if (fragment2.mContainer == viewGroup && fragment2.mView != null) {
                return fragment2;
            }
        }
        return null;
    }

    private static void b(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            i iVar = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                iVar.a(-1);
                iVar.b(i == i2 + (-1));
            } else {
                iVar.a(1);
                iVar.o();
            }
            i++;
        }
    }

    private void b(android.support.v4.e.c<Fragment> cVar) {
        if (this.aZ >= 1) {
            int iMin = Math.min(this.aZ, 4);
            int size = this.aR.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = this.aR.get(i);
                if (fragment.mState < iMin) {
                    a(fragment, iMin, fragment.getNextAnim(), fragment.getNextTransition(), false);
                    if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                        cVar.add(fragment);
                    }
                }
            }
        }
    }

    private void D() {
        if (this.bo != null) {
            while (!this.bo.isEmpty()) {
                this.bo.remove(0).M();
            }
        }
    }

    private void E() {
        int size = this.aS == null ? 0 : this.aS.size();
        for (int i = 0; i < size; i++) {
            Fragment fragmentValueAt = this.aS.valueAt(i);
            if (fragmentValueAt != null) {
                if (fragmentValueAt.getAnimatingAway() != null) {
                    int stateAfterAnimating = fragmentValueAt.getStateAfterAnimating();
                    View animatingAway = fragmentValueAt.getAnimatingAway();
                    fragmentValueAt.setAnimatingAway(null);
                    Animation animation = animatingAway.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        animatingAway.clearAnimation();
                    }
                    a(fragmentValueAt, stateAfterAnimating, 0, 0, false);
                } else if (fragmentValueAt.getAnimator() != null) {
                    fragmentValueAt.getAnimator().end();
                }
            }
        }
    }

    private boolean d(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2) {
        synchronized (this) {
            if (this.aO == null || this.aO.size() == 0) {
                return false;
            }
            int size = this.aO.size();
            boolean zA = false;
            for (int i = 0; i < size; i++) {
                zA |= this.aO.get(i).a(arrayList, arrayList2);
            }
            this.aO.clear();
            this.mHost.getHandler().removeCallbacks(this.bq);
            return zA;
        }
    }

    void F() {
        if (this.bi) {
            boolean zP = false;
            for (int i = 0; i < this.aS.size(); i++) {
                Fragment fragmentValueAt = this.aS.valueAt(i);
                if (fragmentValueAt != null && fragmentValueAt.mLoaderManager != null) {
                    zP |= fragmentValueAt.mLoaderManager.P();
                }
            }
            if (!zP) {
                this.bi = false;
                z();
            }
        }
    }

    void G() {
        if (this.aX != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.aX.size()) {
                    this.aX.get(i2).onBackStackChanged();
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    void b(i iVar) {
        if (this.aT == null) {
            this.aT = new ArrayList<>();
        }
        this.aT.add(iVar);
    }

    boolean a(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2, String str, int i, int i2) {
        int size;
        if (this.aT == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size2 = this.aT.size() - 1;
            if (size2 < 0) {
                return false;
            }
            arrayList.add(this.aT.remove(size2));
            arrayList2.add(true);
        } else {
            if (str == null && i < 0) {
                size = -1;
            } else {
                size = this.aT.size() - 1;
                while (size >= 0) {
                    i iVar = this.aT.get(size);
                    if ((str != null && str.equals(iVar.getName())) || (i >= 0 && i == iVar.mIndex)) {
                        break;
                    }
                    size--;
                }
                if (size < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    size--;
                    while (size >= 0) {
                        i iVar2 = this.aT.get(size);
                        if ((str == null || !str.equals(iVar2.getName())) && (i < 0 || i != iVar2.mIndex)) {
                            break;
                        }
                        size--;
                    }
                }
            }
            if (size == this.aT.size() - 1) {
                return false;
            }
            for (int size3 = this.aT.size() - 1; size3 > size; size3--) {
                arrayList.add(this.aT.remove(size3));
                arrayList2.add(true);
            }
        }
        return true;
    }

    ar H() {
        a(this.bp);
        return this.bp;
    }

    private static void a(ar arVar) {
        if (arVar != null) {
            List<Fragment> fragments = arVar.getFragments();
            if (fragments != null) {
                Iterator<Fragment> it = fragments.iterator();
                while (it.hasNext()) {
                    it.next().mRetaining = true;
                }
            }
            List<ar> listO = arVar.O();
            if (listO != null) {
                Iterator<ar> it2 = listO.iterator();
                while (it2.hasNext()) {
                    a(it2.next());
                }
            }
        }
    }

    void I() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ar arVar;
        if (this.aS != null) {
            int i = 0;
            arrayList = null;
            arrayList2 = null;
            while (i < this.aS.size()) {
                Fragment fragmentValueAt = this.aS.valueAt(i);
                if (fragmentValueAt != null) {
                    if (fragmentValueAt.mRetainInstance) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        arrayList2.add(fragmentValueAt);
                        fragmentValueAt.mTargetIndex = fragmentValueAt.mTarget != null ? fragmentValueAt.mTarget.mIndex : -1;
                        if (DEBUG) {
                            Log.v("FragmentManager", "retainNonConfig: keeping retained " + fragmentValueAt);
                        }
                    }
                    if (fragmentValueAt.mChildFragmentManager != null) {
                        fragmentValueAt.mChildFragmentManager.I();
                        arVar = fragmentValueAt.mChildFragmentManager.bp;
                    } else {
                        arVar = fragmentValueAt.mChildNonConfig;
                    }
                    if (arrayList == null && arVar != null) {
                        arrayList = new ArrayList(this.aS.size());
                        for (int i2 = 0; i2 < i; i2++) {
                            arrayList.add(null);
                        }
                    }
                    arrayList3 = arrayList;
                    if (arrayList3 != null) {
                        arrayList3.add(arVar);
                    }
                } else {
                    arrayList3 = arrayList;
                }
                i++;
                arrayList = arrayList3;
            }
        } else {
            arrayList = null;
            arrayList2 = null;
        }
        if (arrayList2 == null && arrayList == null) {
            this.bp = null;
        } else {
            this.bp = new ar(arrayList2, arrayList);
        }
    }

    void q(Fragment fragment) {
        if (fragment.mInnerView != null) {
            if (this.bn == null) {
                this.bn = new SparseArray<>();
            } else {
                this.bn.clear();
            }
            fragment.mInnerView.saveHierarchyState(this.bn);
            if (this.bn.size() > 0) {
                fragment.mSavedViewState = this.bn;
                this.bn = null;
            }
        }
    }

    Bundle r(Fragment fragment) {
        Bundle bundle;
        if (this.bm == null) {
            this.bm = new Bundle();
        }
        fragment.performSaveInstanceState(this.bm);
        d(fragment, this.bm, false);
        if (this.bm.isEmpty()) {
            bundle = null;
        } else {
            bundle = this.bm;
            this.bm = null;
        }
        if (fragment.mView != null) {
            q(fragment);
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.mSavedViewState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", fragment.mUserVisibleHint);
        }
        return bundle;
    }

    Parcelable saveAllState() {
        int[] iArr;
        int size;
        boolean z;
        BackStackState[] backStackStateArr = null;
        D();
        E();
        execPendingActions();
        this.bf = true;
        this.bp = null;
        if (this.aS == null || this.aS.size() <= 0) {
            return null;
        }
        int size2 = this.aS.size();
        FragmentState[] fragmentStateArr = new FragmentState[size2];
        int i = 0;
        boolean z2 = false;
        while (i < size2) {
            Fragment fragmentValueAt = this.aS.valueAt(i);
            if (fragmentValueAt != null) {
                if (fragmentValueAt.mIndex < 0) {
                    a(new IllegalStateException("Failure saving state: active " + fragmentValueAt + " has cleared index: " + fragmentValueAt.mIndex));
                }
                FragmentState fragmentState = new FragmentState(fragmentValueAt);
                fragmentStateArr[i] = fragmentState;
                if (fragmentValueAt.mState > 0 && fragmentState.mSavedFragmentState == null) {
                    fragmentState.mSavedFragmentState = r(fragmentValueAt);
                    if (fragmentValueAt.mTarget != null) {
                        if (fragmentValueAt.mTarget.mIndex < 0) {
                            a(new IllegalStateException("Failure saving state: " + fragmentValueAt + " has target not in fragment manager: " + fragmentValueAt.mTarget));
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new Bundle();
                        }
                        a(fragmentState.mSavedFragmentState, "android:target_state", fragmentValueAt.mTarget);
                        if (fragmentValueAt.mTargetRequestCode != 0) {
                            fragmentState.mSavedFragmentState.putInt("android:target_req_state", fragmentValueAt.mTargetRequestCode);
                        }
                    }
                } else {
                    fragmentState.mSavedFragmentState = fragmentValueAt.mSavedFragmentState;
                }
                if (DEBUG) {
                    Log.v("FragmentManager", "Saved state of " + fragmentValueAt + ": " + fragmentState.mSavedFragmentState);
                }
                z = true;
            } else {
                z = z2;
            }
            i++;
            z2 = z;
        }
        if (!z2) {
            if (!DEBUG) {
                return null;
            }
            Log.v("FragmentManager", "saveAllState: no fragments!");
            return null;
        }
        int size3 = this.aR.size();
        if (size3 > 0) {
            iArr = new int[size3];
            for (int i2 = 0; i2 < size3; i2++) {
                iArr[i2] = this.aR.get(i2).mIndex;
                if (iArr[i2] < 0) {
                    a(new IllegalStateException("Failure saving state: active " + this.aR.get(i2) + " has cleared index: " + iArr[i2]));
                }
                if (DEBUG) {
                    Log.v("FragmentManager", "saveAllState: adding fragment #" + i2 + ": " + this.aR.get(i2));
                }
            }
        } else {
            iArr = null;
        }
        if (this.aT != null && (size = this.aT.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i3 = 0; i3 < size; i3++) {
                backStackStateArr[i3] = new BackStackState(this.aT.get(i3));
                if (DEBUG) {
                    Log.v("FragmentManager", "saveAllState: adding back stack #" + i3 + ": " + this.aT.get(i3));
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.bL = fragmentStateArr;
        fragmentManagerState.bM = iArr;
        fragmentManagerState.bN = backStackStateArr;
        if (this.bc != null) {
            fragmentManagerState.bO = this.bc.mIndex;
        }
        fragmentManagerState.aQ = this.aQ;
        I();
        return fragmentManagerState;
    }

    void a(Parcelable parcelable, ar arVar) {
        List<ar> list;
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.bL != null) {
                if (arVar != null) {
                    List<Fragment> fragments = arVar.getFragments();
                    List<ar> listO = arVar.O();
                    int size = fragments != null ? fragments.size() : 0;
                    for (int i = 0; i < size; i++) {
                        Fragment fragment = fragments.get(i);
                        if (DEBUG) {
                            Log.v("FragmentManager", "restoreAllState: re-attaching retained " + fragment);
                        }
                        int i2 = 0;
                        while (i2 < fragmentManagerState.bL.length && fragmentManagerState.bL[i2].mIndex != fragment.mIndex) {
                            i2++;
                        }
                        if (i2 == fragmentManagerState.bL.length) {
                            a(new IllegalStateException("Could not find active fragment with index " + fragment.mIndex));
                        }
                        FragmentState fragmentState = fragmentManagerState.bL[i2];
                        fragmentState.bQ = fragment;
                        fragment.mSavedViewState = null;
                        fragment.mBackStackNesting = 0;
                        fragment.mInLayout = false;
                        fragment.mAdded = false;
                        fragment.mTarget = null;
                        if (fragmentState.mSavedFragmentState != null) {
                            fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                            fragment.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                            fragment.mSavedFragmentState = fragmentState.mSavedFragmentState;
                        }
                    }
                    list = listO;
                } else {
                    list = null;
                }
                this.aS = new SparseArray<>(fragmentManagerState.bL.length);
                int i3 = 0;
                while (i3 < fragmentManagerState.bL.length) {
                    FragmentState fragmentState2 = fragmentManagerState.bL[i3];
                    if (fragmentState2 != null) {
                        Fragment fragmentA = fragmentState2.a(this.mHost, this.ba, this.bb, (list == null || i3 >= list.size()) ? null : list.get(i3));
                        if (DEBUG) {
                            Log.v("FragmentManager", "restoreAllState: active #" + i3 + ": " + fragmentA);
                        }
                        this.aS.put(fragmentA.mIndex, fragmentA);
                        fragmentState2.bQ = null;
                    }
                    i3++;
                }
                if (arVar != null) {
                    List<Fragment> fragments2 = arVar.getFragments();
                    int size2 = fragments2 != null ? fragments2.size() : 0;
                    for (int i4 = 0; i4 < size2; i4++) {
                        Fragment fragment2 = fragments2.get(i4);
                        if (fragment2.mTargetIndex >= 0) {
                            fragment2.mTarget = this.aS.get(fragment2.mTargetIndex);
                            if (fragment2.mTarget == null) {
                                Log.w("FragmentManager", "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.mTargetIndex);
                            }
                        }
                    }
                }
                this.aR.clear();
                if (fragmentManagerState.bM != null) {
                    for (int i5 = 0; i5 < fragmentManagerState.bM.length; i5++) {
                        Fragment fragment3 = this.aS.get(fragmentManagerState.bM[i5]);
                        if (fragment3 == null) {
                            a(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.bM[i5]));
                        }
                        fragment3.mAdded = true;
                        if (DEBUG) {
                            Log.v("FragmentManager", "restoreAllState: added #" + i5 + ": " + fragment3);
                        }
                        if (this.aR.contains(fragment3)) {
                            throw new IllegalStateException("Already added!");
                        }
                        synchronized (this.aR) {
                            this.aR.add(fragment3);
                        }
                    }
                }
                if (fragmentManagerState.bN != null) {
                    this.aT = new ArrayList<>(fragmentManagerState.bN.length);
                    for (int i6 = 0; i6 < fragmentManagerState.bN.length; i6++) {
                        i iVarA = fragmentManagerState.bN[i6].a(this);
                        if (DEBUG) {
                            Log.v("FragmentManager", "restoreAllState: back stack #" + i6 + " (index " + iVarA.mIndex + "): " + iVarA);
                            PrintWriter printWriter = new PrintWriter(new android.support.v4.e.g("FragmentManager"));
                            iVarA.a("  ", printWriter, false);
                            printWriter.close();
                        }
                        this.aT.add(iVarA);
                        if (iVarA.mIndex >= 0) {
                            a(iVarA.mIndex, iVarA);
                        }
                    }
                } else {
                    this.aT = null;
                }
                if (fragmentManagerState.bO >= 0) {
                    this.bc = this.aS.get(fragmentManagerState.bO);
                }
                this.aQ = fragmentManagerState.aQ;
            }
        }
    }

    private void J() {
        if (this.aS != null) {
            for (int size = this.aS.size() - 1; size >= 0; size--) {
                if (this.aS.valueAt(size) == null) {
                    this.aS.delete(this.aS.keyAt(size));
                }
            }
        }
    }

    public void a(z zVar, x xVar, Fragment fragment) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = zVar;
        this.ba = xVar;
        this.bb = fragment;
    }

    public void noteStateNotSaved() {
        this.bp = null;
        this.bf = false;
        int size = this.aR.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = this.aR.get(i);
            if (fragment != null) {
                fragment.noteStateNotSaved();
            }
        }
    }

    public void dispatchCreate() {
        this.bf = false;
        h(1);
    }

    public void dispatchActivityCreated() {
        this.bf = false;
        h(2);
    }

    public void dispatchStart() {
        this.bf = false;
        h(4);
    }

    public void dispatchResume() {
        this.bf = false;
        h(5);
    }

    public void dispatchPause() {
        h(4);
    }

    public void dispatchStop() {
        this.bf = true;
        h(3);
    }

    public void t() {
        h(2);
    }

    public void dispatchDestroyView() {
        h(1);
    }

    public void dispatchDestroy() {
        this.bg = true;
        execPendingActions();
        h(0);
        this.mHost = null;
        this.ba = null;
        this.bb = null;
    }

    private void h(int i) {
        try {
            this.aP = true;
            a(i, false);
            this.aP = false;
            execPendingActions();
        } catch (Throwable th) {
            this.aP = false;
            throw th;
        }
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        for (int size = this.aR.size() - 1; size >= 0; size--) {
            Fragment fragment = this.aR.get(size);
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z);
            }
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        for (int size = this.aR.size() - 1; size >= 0; size--) {
            Fragment fragment = this.aR.get(size);
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z);
            }
        }
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.aR.size()) {
                Fragment fragment = this.aR.get(i2);
                if (fragment != null) {
                    fragment.performConfigurationChanged(configuration);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public void dispatchLowMemory() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.aR.size()) {
                Fragment fragment = this.aR.get(i2);
                if (fragment != null) {
                    fragment.performLowMemory();
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        ArrayList<Fragment> arrayList = null;
        int i = 0;
        boolean z = false;
        while (i < this.aR.size()) {
            Fragment fragment = this.aR.get(i);
            if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                z = true;
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
            }
            i++;
            z = z;
        }
        if (this.aU != null) {
            for (int i2 = 0; i2 < this.aU.size(); i2++) {
                Fragment fragment2 = this.aU.get(i2);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.aU = arrayList;
        return z;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        for (int i = 0; i < this.aR.size(); i++) {
            Fragment fragment = this.aR.get(i);
            if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        for (int i = 0; i < this.aR.size(); i++) {
            Fragment fragment = this.aR.get(i);
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        for (int i = 0; i < this.aR.size(); i++) {
            Fragment fragment = this.aR.get(i);
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.aR.size()) {
                Fragment fragment = this.aR.get(i2);
                if (fragment != null) {
                    fragment.performOptionsMenuClosed(menu);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public void s(Fragment fragment) {
        if (fragment != null && (this.aS.get(fragment.mIndex) != fragment || (fragment.mHost != null && fragment.getFragmentManager() != this))) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        this.bc = fragment;
    }

    public Fragment K() {
        return this.bc;
    }

    void a(Fragment fragment, Context context, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).a(fragment, context, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.a(this, fragment, context);
                }
            }
        }
    }

    void b(Fragment fragment, Context context, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).b(fragment, context, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.b(this, fragment, context);
                }
            }
        }
    }

    void a(Fragment fragment, Bundle bundle, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).a(fragment, bundle, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.a(this, fragment, bundle);
                }
            }
        }
    }

    void b(Fragment fragment, Bundle bundle, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).b(fragment, bundle, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.b(this, fragment, bundle);
                }
            }
        }
    }

    void c(Fragment fragment, Bundle bundle, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).c(fragment, bundle, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.c(this, fragment, bundle);
                }
            }
        }
    }

    void a(Fragment fragment, View view, Bundle bundle, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).a(fragment, view, bundle, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.a(this, fragment, view, bundle);
                }
            }
        }
    }

    void b(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).b(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.a(this, fragment);
                }
            }
        }
    }

    void c(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).c(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.b(this, fragment);
                }
            }
        }
    }

    void d(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).d(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.c(this, fragment);
                }
            }
        }
    }

    void e(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).e(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.d(this, fragment);
                }
            }
        }
    }

    void d(Fragment fragment, Bundle bundle, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).d(fragment, bundle, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.d(this, fragment, bundle);
                }
            }
        }
    }

    void f(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).f(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.e(this, fragment);
                }
            }
        }
    }

    void g(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).g(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.f(this, fragment);
                }
            }
        }
    }

    void h(Fragment fragment, boolean z) {
        if (this.bb != null) {
            aa fragmentManager = this.bb.getFragmentManager();
            if (fragmentManager instanceof ad) {
                ((ad) fragmentManager).h(fragment, true);
            }
        }
        if (this.aY != null) {
            for (android.support.v4.e.p<ab, Boolean> pVar : this.aY) {
                if (!z || pVar.second.booleanValue()) {
                    pVar.first.g(this, fragment);
                }
            }
        }
    }

    public static int i(int i) {
        switch (i) {
            case 4097:
                return 8194;
            case 4099:
                return 4099;
            case 8194:
                return 4097;
            default:
                return 0;
        }
    }

    public static int b(int i, boolean z) {
        switch (i) {
            case 4097:
                return z ? 1 : 2;
            case 4099:
                return z ? 5 : 6;
            case 8194:
                return z ? 3 : 4;
            default:
                return -1;
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Fragment fragment;
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, an.bE);
        String string = attributeValue == null ? typedArrayObtainStyledAttributes.getString(0) : attributeValue;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        String string2 = typedArrayObtainStyledAttributes.getString(2);
        typedArrayObtainStyledAttributes.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), string)) {
            return null;
        }
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string2 == null) {
            throw new IllegalArgumentException(String.valueOf(attributeSet.getPositionDescription()) + ": Must specify unique android:id, android:tag, or have a parent with an id for " + string);
        }
        Fragment fragmentF = resourceId != -1 ? f(resourceId) : null;
        if (fragmentF == null && string2 != null) {
            fragmentF = h(string2);
        }
        if (fragmentF == null && id != -1) {
            fragmentF = f(id);
        }
        if (DEBUG) {
            Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + string + " existing=" + fragmentF);
        }
        if (fragmentF == null) {
            Fragment fragmentInstantiate = this.ba.instantiate(context, string, null);
            fragmentInstantiate.mFromLayout = true;
            fragmentInstantiate.mFragmentId = resourceId != 0 ? resourceId : id;
            fragmentInstantiate.mContainerId = id;
            fragmentInstantiate.mTag = string2;
            fragmentInstantiate.mInLayout = true;
            fragmentInstantiate.mFragmentManager = this;
            fragmentInstantiate.mHost = this.mHost;
            fragmentInstantiate.onInflate(this.mHost.getContext(), attributeSet, fragmentInstantiate.mSavedFragmentState);
            a(fragmentInstantiate, true);
            fragment = fragmentInstantiate;
        } else {
            if (fragmentF.mInLayout) {
                throw new IllegalArgumentException(String.valueOf(attributeSet.getPositionDescription()) + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + string);
            }
            fragmentF.mInLayout = true;
            fragmentF.mHost = this.mHost;
            if (!fragmentF.mRetaining) {
                fragmentF.onInflate(this.mHost.getContext(), attributeSet, fragmentF.mSavedFragmentState);
            }
            fragment = fragmentF;
        }
        if (this.aZ < 1 && fragment.mFromLayout) {
            a(fragment, 1, 0, 0, false);
        } else {
            e(fragment);
        }
        if (fragment.mView == null) {
            throw new IllegalStateException("Fragment " + string + " did not create a view.");
        }
        if (resourceId != 0) {
            fragment.mView.setId(resourceId);
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(string2);
        }
        return fragment.mView;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    LayoutInflater.Factory2 L() {
        return this;
    }
}
