package com.facebook.appevents.codeless;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ParameterComponent;
import com.facebook.appevents.codeless.internal.PathComponent;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Utility;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class CodelessMatcher {
    private static final String CURRENT_CLASS_NAME = ".";
    private static final String PARENT_CLASS_NAME = "..";
    private static final String TAG = CodelessMatcher.class.getCanonicalName();
    private static CodelessMatcher codelessMatcher = null;
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
    private Set<Activity> activitiesSet = new HashSet();
    private Set<ViewMatcher> viewMatchers = new HashSet();
    private HashSet<String> listenerSet = new HashSet<>();
    private HashMap<Integer, HashSet<String>> activityToListenerMap = new HashMap<>();

    private CodelessMatcher() {
    }

    public static synchronized CodelessMatcher getInstance() {
        if (codelessMatcher == null) {
            codelessMatcher = new CodelessMatcher();
        }
        return codelessMatcher;
    }

    public void add(Activity activity) {
        if (!InternalSettings.isUnityApp()) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new FacebookException("Can't add activity to CodelessMatcher on non-UI thread");
            }
            this.activitiesSet.add(activity);
            this.listenerSet.clear();
            if (this.activityToListenerMap.containsKey(Integer.valueOf(activity.hashCode()))) {
                this.listenerSet = this.activityToListenerMap.get(Integer.valueOf(activity.hashCode()));
            }
            startTracking();
        }
    }

    public void remove(Activity activity) {
        if (!InternalSettings.isUnityApp()) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new FacebookException("Can't remove activity from CodelessMatcher on non-UI thread");
            }
            this.activitiesSet.remove(activity);
            this.viewMatchers.clear();
            this.activityToListenerMap.put(Integer.valueOf(activity.hashCode()), (HashSet) this.listenerSet.clone());
            this.listenerSet.clear();
        }
    }

    public void destroy(Activity activity) {
        this.activityToListenerMap.remove(Integer.valueOf(activity.hashCode()));
    }

    public static Bundle getParameters(EventBinding eventBinding, View view, View view2) {
        List<MatchedView> listFindViewByPath;
        Bundle bundle = new Bundle();
        if (eventBinding == null) {
            return bundle;
        }
        List<ParameterComponent> viewParameters = eventBinding.getViewParameters();
        if (viewParameters != null) {
            for (ParameterComponent parameterComponent : viewParameters) {
                if (parameterComponent.value != null && parameterComponent.value.length() > 0) {
                    bundle.putString(parameterComponent.name, parameterComponent.value);
                } else if (parameterComponent.path.size() > 0) {
                    if (parameterComponent.pathType.equals(Constants.PATH_TYPE_RELATIVE)) {
                        listFindViewByPath = ViewMatcher.findViewByPath(eventBinding, view2, parameterComponent.path, 0, -1, view2.getClass().getSimpleName());
                    } else {
                        listFindViewByPath = ViewMatcher.findViewByPath(eventBinding, view, parameterComponent.path, 0, -1, view.getClass().getSimpleName());
                    }
                    for (MatchedView matchedView : listFindViewByPath) {
                        if (matchedView.getView() != null) {
                            String textOfView = ViewHierarchy.getTextOfView(matchedView.getView());
                            if (textOfView.length() > 0) {
                                bundle.putString(parameterComponent.name, textOfView);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return bundle;
    }

    private void startTracking() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            matchViews();
        } else {
            this.uiThreadHandler.post(new Runnable() { // from class: com.facebook.appevents.codeless.CodelessMatcher.1
                @Override // java.lang.Runnable
                public void run() {
                    CodelessMatcher.this.matchViews();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void matchViews() {
        for (Activity activity : this.activitiesSet) {
            this.viewMatchers.add(new ViewMatcher(activity.getWindow().getDecorView().getRootView(), this.uiThreadHandler, this.listenerSet, activity.getClass().getSimpleName()));
        }
    }

    public class MatchedView {
        private WeakReference<View> view;
        private String viewMapKey;

        public MatchedView(View view, String str) {
            this.view = new WeakReference<>(view);
            this.viewMapKey = str;
        }

        public View getView() {
            if (this.view == null) {
                return null;
            }
            return this.view.get();
        }

        public String getViewMapKey() {
            return this.viewMapKey;
        }
    }

    public class ViewMatcher implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener, Runnable {
        private final String activityName;
        private List<EventBinding> eventBindings;
        private final Handler handler;
        private HashSet<String> listenerSet;
        private WeakReference<View> rootView;

        public ViewMatcher(View view, Handler handler, HashSet<String> hashSet, String str) {
            this.rootView = new WeakReference<>(view);
            this.handler = handler;
            this.listenerSet = hashSet;
            this.activityName = str;
            this.handler.postDelayed(this, 200L);
        }

        @Override // java.lang.Runnable
        public void run() {
            View view;
            FetchedAppSettings appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (appSettingsWithoutQuery != null && appSettingsWithoutQuery.getCodelessEventsEnabled()) {
                this.eventBindings = EventBinding.parseArray(appSettingsWithoutQuery.getEventBindings());
                if (this.eventBindings != null && (view = this.rootView.get()) != null) {
                    ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
                    if (viewTreeObserver.isAlive()) {
                        viewTreeObserver.addOnGlobalLayoutListener(this);
                        viewTreeObserver.addOnScrollChangedListener(this);
                    }
                    startMatch();
                }
            }
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            startMatch();
        }

        @Override // android.view.ViewTreeObserver.OnScrollChangedListener
        public void onScrollChanged() {
            startMatch();
        }

        private void startMatch() {
            if (this.eventBindings != null && this.rootView.get() != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < this.eventBindings.size()) {
                        findView(this.eventBindings.get(i2), this.rootView.get());
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            }
        }

        public void findView(EventBinding eventBinding, View view) {
            if (eventBinding != null && view != null) {
                if (TextUtils.isEmpty(eventBinding.getActivityName()) || eventBinding.getActivityName().equals(this.activityName)) {
                    List<PathComponent> viewPath = eventBinding.getViewPath();
                    if (viewPath.size() <= 25) {
                        Iterator<MatchedView> it = findViewByPath(eventBinding, view, viewPath, 0, -1, this.activityName).iterator();
                        while (it.hasNext()) {
                            attachListener(it.next(), view, eventBinding);
                        }
                    }
                }
            }
        }

        public static List<MatchedView> findViewByPath(EventBinding eventBinding, View view, List<PathComponent> list, int i, int i2, String str) {
            int i3 = 0;
            String str2 = str + CodelessMatcher.CURRENT_CLASS_NAME + String.valueOf(i2);
            ArrayList arrayList = new ArrayList();
            if (view == null) {
                return arrayList;
            }
            if (i >= list.size()) {
                arrayList.add(new MatchedView(view, str2));
            } else {
                PathComponent pathComponent = list.get(i);
                if (pathComponent.className.equals(CodelessMatcher.PARENT_CLASS_NAME)) {
                    ViewParent parent = view.getParent();
                    if (parent instanceof ViewGroup) {
                        List<View> listFindVisibleChildren = findVisibleChildren((ViewGroup) parent);
                        int size = listFindVisibleChildren.size();
                        while (i3 < size) {
                            arrayList.addAll(findViewByPath(eventBinding, listFindVisibleChildren.get(i3), list, i + 1, i3, str2));
                            i3++;
                        }
                    }
                    return arrayList;
                }
                if (pathComponent.className.equals(CodelessMatcher.CURRENT_CLASS_NAME)) {
                    arrayList.add(new MatchedView(view, str2));
                    return arrayList;
                }
                if (!isTheSameView(view, pathComponent, i2)) {
                    return arrayList;
                }
                if (i == list.size() - 1) {
                    arrayList.add(new MatchedView(view, str2));
                }
            }
            if (view instanceof ViewGroup) {
                List<View> listFindVisibleChildren2 = findVisibleChildren((ViewGroup) view);
                int size2 = listFindVisibleChildren2.size();
                while (i3 < size2) {
                    arrayList.addAll(findViewByPath(eventBinding, listFindVisibleChildren2.get(i3), list, i + 1, i3, str2));
                    i3++;
                }
            }
            return arrayList;
        }

        private static boolean isTheSameView(View view, PathComponent pathComponent, int i) {
            if (pathComponent.index != -1 && i != pathComponent.index) {
                return false;
            }
            if (!view.getClass().getCanonicalName().equals(pathComponent.className)) {
                if (!pathComponent.className.matches(".*android\\..*")) {
                    return false;
                }
                String[] strArrSplit = pathComponent.className.split("\\.");
                if (strArrSplit.length <= 0) {
                    return false;
                }
                if (!view.getClass().getSimpleName().equals(strArrSplit[strArrSplit.length - 1])) {
                    return false;
                }
            }
            if ((pathComponent.matchBitmask & PathComponent.MatchBitmaskType.ID.getValue()) > 0 && pathComponent.id != view.getId()) {
                return false;
            }
            if ((pathComponent.matchBitmask & PathComponent.MatchBitmaskType.TEXT.getValue()) > 0) {
                String str = pathComponent.text;
                String textOfView = ViewHierarchy.getTextOfView(view);
                String strCoerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(textOfView), "");
                if (!str.equals(textOfView) && !str.equals(strCoerceValueIfNullOrEmpty)) {
                    return false;
                }
            }
            if ((pathComponent.matchBitmask & PathComponent.MatchBitmaskType.DESCRIPTION.getValue()) > 0) {
                String str2 = pathComponent.description;
                String strValueOf = view.getContentDescription() == null ? "" : String.valueOf(view.getContentDescription());
                String strCoerceValueIfNullOrEmpty2 = Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(strValueOf), "");
                if (!str2.equals(strValueOf) && !str2.equals(strCoerceValueIfNullOrEmpty2)) {
                    return false;
                }
            }
            if ((pathComponent.matchBitmask & PathComponent.MatchBitmaskType.HINT.getValue()) > 0) {
                String str3 = pathComponent.hint;
                String hintOfView = ViewHierarchy.getHintOfView(view);
                String strCoerceValueIfNullOrEmpty3 = Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(hintOfView), "");
                if (!str3.equals(hintOfView) && !str3.equals(strCoerceValueIfNullOrEmpty3)) {
                    return false;
                }
            }
            if ((pathComponent.matchBitmask & PathComponent.MatchBitmaskType.TAG.getValue()) > 0) {
                String str4 = pathComponent.tag;
                String strValueOf2 = view.getTag() == null ? "" : String.valueOf(view.getTag());
                String strCoerceValueIfNullOrEmpty4 = Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(strValueOf2), "");
                if (!str4.equals(strValueOf2) && !str4.equals(strCoerceValueIfNullOrEmpty4)) {
                    return false;
                }
            }
            return true;
        }

        private static List<View> findVisibleChildren(ViewGroup viewGroup) {
            ArrayList arrayList = new ArrayList();
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    arrayList.add(childAt);
                }
            }
            return arrayList;
        }

        private void attachListener(MatchedView matchedView, View view, EventBinding eventBinding) {
            if (eventBinding != null) {
                try {
                    View view2 = matchedView.getView();
                    if (view2 != null) {
                        View viewFindRCTRootView = ViewHierarchy.findRCTRootView(view2);
                        if (viewFindRCTRootView != null && ViewHierarchy.isRCTButton(view2, viewFindRCTRootView)) {
                            attachRCTListener(matchedView, view, viewFindRCTRootView, eventBinding);
                            return;
                        }
                        if (!view2.getClass().getName().startsWith("com.facebook.react")) {
                            String viewMapKey = matchedView.getViewMapKey();
                            View.OnClickListener existingOnClickListener = ViewHierarchy.getExistingOnClickListener(view2);
                            boolean z = existingOnClickListener != null;
                            boolean z2 = z && (existingOnClickListener instanceof CodelessLoggingEventListener.AutoLoggingOnClickListener);
                            boolean z3 = z2 && ((CodelessLoggingEventListener.AutoLoggingOnClickListener) existingOnClickListener).getSupportCodelessLogging();
                            if (this.listenerSet.contains(viewMapKey)) {
                                return;
                            }
                            if (!z || !z2 || !z3) {
                                view2.setOnClickListener(CodelessLoggingEventListener.getOnClickListener(eventBinding, view, view2));
                                this.listenerSet.add(viewMapKey);
                            }
                        }
                    }
                } catch (FacebookException e) {
                    String unused = CodelessMatcher.TAG;
                }
            }
        }

        private void attachRCTListener(MatchedView matchedView, View view, View view2, EventBinding eventBinding) {
            View view3;
            if (eventBinding != null && (view3 = matchedView.getView()) != null && ViewHierarchy.isRCTButton(view3, view2)) {
                String viewMapKey = matchedView.getViewMapKey();
                View.OnTouchListener existingOnTouchListener = ViewHierarchy.getExistingOnTouchListener(view3);
                boolean z = existingOnTouchListener != null;
                boolean z2 = z && (existingOnTouchListener instanceof RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener);
                boolean z3 = z2 && ((RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener) existingOnTouchListener).getSupportCodelessLogging();
                if (this.listenerSet.contains(viewMapKey)) {
                    return;
                }
                if (!z || !z2 || !z3) {
                    view3.setOnTouchListener(RCTCodelessLoggingEventListener.getOnTouchListener(eventBinding, view, view3));
                    this.listenerSet.add(viewMapKey);
                }
            }
        }
    }
}
