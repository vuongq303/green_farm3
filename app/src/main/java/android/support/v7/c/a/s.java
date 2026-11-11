package android.support.v7.c.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: compiled from: MenuBuilder.java */
/* JADX INFO: loaded from: classes.dex */
public class s implements android.support.v4.b.a.a {
    private static final int[] qr = {1, 4, 5, 3, 2, 0};
    private final Resources kL;
    private final Context mContext;
    private ContextMenu.ContextMenuInfo qC;
    CharSequence qD;
    Drawable qE;
    View qF;
    private w qN;
    private boolean qO;
    private boolean qs;
    private boolean qt;
    private t qu;
    private int qB = 0;
    private boolean qG = false;
    private boolean qH = false;
    private boolean qI = false;
    private boolean qJ = false;
    private boolean qK = false;
    private ArrayList<w> qL = new ArrayList<>();
    private CopyOnWriteArrayList<WeakReference<ai>> qM = new CopyOnWriteArrayList<>();
    private ArrayList<w> qv = new ArrayList<>();
    private ArrayList<w> qw = new ArrayList<>();
    private boolean qx = true;
    private ArrayList<w> qy = new ArrayList<>();
    private ArrayList<w> qz = new ArrayList<>();
    private boolean qA = true;

    public s(Context context) {
        this.mContext = context;
        this.kL = context.getResources();
        t(true);
    }

    public s ad(int i) {
        this.qB = i;
        return this;
    }

    public void a(ai aiVar) {
        a(aiVar, this.mContext);
    }

    public void a(ai aiVar, Context context) {
        this.qM.add(new WeakReference<>(aiVar));
        aiVar.a(context, this);
        this.qA = true;
    }

    public void b(ai aiVar) {
        for (WeakReference<ai> weakReference : this.qM) {
            ai aiVar2 = weakReference.get();
            if (aiVar2 == null || aiVar2 == aiVar) {
                this.qM.remove(weakReference);
            }
        }
    }

    private void s(boolean z) {
        if (!this.qM.isEmpty()) {
            db();
            for (WeakReference<ai> weakReference : this.qM) {
                ai aiVar = weakReference.get();
                if (aiVar == null) {
                    this.qM.remove(weakReference);
                } else {
                    aiVar.p(z);
                }
            }
            dc();
        }
    }

    private boolean a(as asVar, ai aiVar) {
        if (this.qM.isEmpty()) {
            return false;
        }
        boolean zA = aiVar != null ? aiVar.a(asVar) : false;
        Iterator<WeakReference<ai>> it = this.qM.iterator();
        while (true) {
            boolean zA2 = zA;
            if (!it.hasNext()) {
                return zA2;
            }
            WeakReference<ai> next = it.next();
            ai aiVar2 = next.get();
            if (aiVar2 == null) {
                this.qM.remove(next);
            } else if (!zA2) {
                zA2 = aiVar2.a(asVar);
            }
            zA = zA2;
        }
    }

    public void b(Bundle bundle) {
        SparseArray<? extends Parcelable> sparseArray = null;
        int size = size();
        int i = 0;
        while (i < size) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            SparseArray<? extends Parcelable> sparseArray2 = sparseArray;
            if (item.hasSubMenu()) {
                ((as) item.getSubMenu()).b(bundle);
            }
            i++;
            sparseArray = sparseArray2;
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(cX(), sparseArray);
        }
    }

    public void c(Bundle bundle) {
        MenuItem menuItemFindItem;
        if (bundle != null) {
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(cX());
            int size = size();
            for (int i = 0; i < size; i++) {
                MenuItem item = getItem(i);
                View actionView = item.getActionView();
                if (actionView != null && actionView.getId() != -1) {
                    actionView.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((as) item.getSubMenu()).c(bundle);
                }
            }
            int i2 = bundle.getInt("android:menu:expandedactionview");
            if (i2 > 0 && (menuItemFindItem = findItem(i2)) != null) {
                menuItemFindItem.expandActionView();
            }
        }
    }

    protected String cX() {
        return "android:menu:actionviewstates";
    }

    public void a(t tVar) {
        this.qu = tVar;
    }

    protected MenuItem a(int i, int i2, int i3, CharSequence charSequence) {
        int iAg = ag(i3);
        w wVarA = a(i, i2, i3, iAg, charSequence, this.qB);
        if (this.qC != null) {
            wVarA.a(this.qC);
        }
        this.qv.add(c(this.qv, iAg), wVarA);
        v(true);
        return wVarA;
    }

    private w a(int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        return new w(this, i, i2, i3, i4, charSequence, i5);
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return a(0, 0, 0, this.kL.getString(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(i, i2, i3, this.kL.getString(i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.kL.getString(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        w wVar = (w) a(i, i2, i3, charSequence);
        as asVar = new as(this.mContext, this, wVar);
        wVar.b(asVar);
        return asVar;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.kL.getString(i4));
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i5 = 0; i5 < size; i5++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i5);
            Intent intent2 = new Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = add(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        e(ae(i), true);
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        int iAf = af(i);
        if (iAf >= 0) {
            int size = this.qv.size() - iAf;
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= size || this.qv.get(iAf).getGroupId() != i) {
                    break;
                }
                e(iAf, false);
                i2 = i3;
            }
            v(true);
        }
    }

    private void e(int i, boolean z) {
        if (i >= 0 && i < this.qv.size()) {
            this.qv.remove(i);
            if (z) {
                v(true);
            }
        }
    }

    @Override // android.view.Menu
    public void clear() {
        if (this.qN != null) {
            e(this.qN);
        }
        this.qv.clear();
        v(true);
    }

    void c(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.qv.size();
        db();
        for (int i = 0; i < size; i++) {
            w wVar = this.qv.get(i);
            if (wVar.getGroupId() == groupId && wVar.dr() && wVar.isCheckable()) {
                wVar.y(wVar == menuItem);
            }
        }
        dc();
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.qv.size();
        for (int i2 = 0; i2 < size; i2++) {
            w wVar = this.qv.get(i2);
            if (wVar.getGroupId() == i) {
                wVar.x(z2);
                wVar.setCheckable(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        int size = this.qv.size();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < size) {
            w wVar = this.qv.get(i2);
            i2++;
            z2 = (wVar.getGroupId() == i && wVar.z(z)) ? true : z2;
        }
        if (z2) {
            v(true);
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        int size = this.qv.size();
        for (int i2 = 0; i2 < size; i2++) {
            w wVar = this.qv.get(i2);
            if (wVar.getGroupId() == i) {
                wVar.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        if (this.qO) {
            return true;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.qv.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        MenuItem menuItemFindItem;
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            w wVar = this.qv.get(i2);
            if (wVar.getItemId() != i) {
                if (wVar.hasSubMenu() && (menuItemFindItem = wVar.getSubMenu().findItem(i)) != null) {
                    return menuItemFindItem;
                }
            } else {
                return wVar;
            }
        }
        return null;
    }

    public int ae(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.qv.get(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public int af(int i) {
        return i(i, 0);
    }

    public int i(int i, int i2) {
        int size = size();
        if (i2 < 0) {
            i2 = 0;
        }
        for (int i3 = i2; i3 < size; i3++) {
            if (this.qv.get(i3).getGroupId() == i) {
                return i3;
            }
        }
        return -1;
    }

    @Override // android.view.Menu
    public int size() {
        return this.qv.size();
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return this.qv.get(i);
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return d(i, keyEvent) != null;
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.qs = z;
        v(false);
    }

    private static int ag(int i) {
        int i2 = ((-65536) & i) >> 16;
        if (i2 < 0 || i2 >= qr.length) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }
        return (qr[i2] << 16) | (65535 & i);
    }

    boolean cY() {
        return this.qs;
    }

    private void t(boolean z) {
        this.qt = z && this.kL.getConfiguration().keyboard != 1 && this.kL.getBoolean(R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent);
    }

    public boolean cZ() {
        return this.qt;
    }

    Resources getResources() {
        return this.kL;
    }

    public Context getContext() {
        return this.mContext;
    }

    boolean d(s sVar, MenuItem menuItem) {
        return this.qu != null && this.qu.a(sVar, menuItem);
    }

    public void da() {
        if (this.qu != null) {
            this.qu.a(this);
        }
    }

    private static int c(ArrayList<w> arrayList, int i) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).getOrdering() <= i) {
                return size + 1;
            }
        }
        return 0;
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        w wVarD = d(i, keyEvent);
        boolean zA = false;
        if (wVarD != null) {
            zA = a(wVarD, i2);
        }
        if ((i2 & 2) != 0) {
            u(true);
        }
        return zA;
    }

    void a(List<w> list, int i, KeyEvent keyEvent) {
        boolean zCY = cY();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            int size = this.qv.size();
            for (int i2 = 0; i2 < size; i2++) {
                w wVar = this.qv.get(i2);
                if (wVar.hasSubMenu()) {
                    ((s) wVar.getSubMenu()).a(list, i, keyEvent);
                }
                char alphabeticShortcut = zCY ? wVar.getAlphabeticShortcut() : wVar.getNumericShortcut();
                if (((modifiers & 69647) == ((zCY ? wVar.getAlphabeticModifiers() : wVar.getNumericModifiers()) & 69647)) && alphabeticShortcut != 0 && ((alphabeticShortcut == keyData.meta[0] || alphabeticShortcut == keyData.meta[2] || (zCY && alphabeticShortcut == '\b' && i == 67)) && wVar.isEnabled())) {
                    list.add(wVar);
                }
            }
        }
    }

    w d(int i, KeyEvent keyEvent) {
        ArrayList<w> arrayList = this.qL;
        arrayList.clear();
        a(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean zCY = cY();
        for (int i2 = 0; i2 < size; i2++) {
            w wVar = arrayList.get(i2);
            char alphabeticShortcut = zCY ? wVar.getAlphabeticShortcut() : wVar.getNumericShortcut();
            if (alphabeticShortcut == keyData.meta[0] && (metaState & 2) == 0) {
                return wVar;
            }
            if (alphabeticShortcut == keyData.meta[2] && (metaState & 2) != 0) {
                return wVar;
            }
            if (zCY && alphabeticShortcut == '\b' && i == 67) {
                return wVar;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return a(findItem(i), i2);
    }

    public boolean a(MenuItem menuItem, int i) {
        return a(menuItem, (ai) null, i);
    }

    public boolean a(MenuItem menuItem, ai aiVar, int i) {
        w wVar = (w) menuItem;
        if (wVar == null || !wVar.isEnabled()) {
            return false;
        }
        boolean zDn = wVar.dn();
        android.support.v4.view.g gVarAw = wVar.aw();
        boolean z = gVarAw != null && gVarAw.hasSubMenu();
        if (wVar.dw()) {
            boolean zExpandActionView = wVar.expandActionView() | zDn;
            if (zExpandActionView) {
                u(true);
                return zExpandActionView;
            }
            return zExpandActionView;
        }
        if (wVar.hasSubMenu() || z) {
            if ((i & 4) == 0) {
                u(false);
            }
            if (!wVar.hasSubMenu()) {
                wVar.b(new as(getContext(), this, wVar));
            }
            as asVar = (as) wVar.getSubMenu();
            if (z) {
                gVarAw.onPrepareSubMenu(asVar);
            }
            boolean zA = a(asVar, aiVar) | zDn;
            if (!zA) {
                u(true);
                return zA;
            }
            return zA;
        }
        if ((i & 1) == 0) {
            u(true);
        }
        return zDn;
    }

    public final void u(boolean z) {
        if (!this.qK) {
            this.qK = true;
            for (WeakReference<ai> weakReference : this.qM) {
                ai aiVar = weakReference.get();
                if (aiVar == null) {
                    this.qM.remove(weakReference);
                } else {
                    aiVar.b(this, z);
                }
            }
            this.qK = false;
        }
    }

    @Override // android.view.Menu
    public void close() {
        u(true);
    }

    public void v(boolean z) {
        if (!this.qG) {
            if (z) {
                this.qx = true;
                this.qA = true;
            }
            s(z);
            return;
        }
        this.qH = true;
        if (z) {
            this.qI = true;
        }
    }

    public void db() {
        if (!this.qG) {
            this.qG = true;
            this.qH = false;
            this.qI = false;
        }
    }

    public void dc() {
        this.qG = false;
        if (this.qH) {
            this.qH = false;
            v(this.qI);
        }
    }

    void b(w wVar) {
        this.qx = true;
        v(true);
    }

    void c(w wVar) {
        this.qA = true;
        v(true);
    }

    public ArrayList<w> dd() {
        if (!this.qx) {
            return this.qw;
        }
        this.qw.clear();
        int size = this.qv.size();
        for (int i = 0; i < size; i++) {
            w wVar = this.qv.get(i);
            if (wVar.isVisible()) {
                this.qw.add(wVar);
            }
        }
        this.qx = false;
        this.qA = true;
        return this.qw;
    }

    public void de() {
        boolean zCM;
        ArrayList<w> arrayListDd = dd();
        if (this.qA) {
            boolean z = false;
            for (WeakReference<ai> weakReference : this.qM) {
                ai aiVar = weakReference.get();
                if (aiVar == null) {
                    this.qM.remove(weakReference);
                    zCM = z;
                } else {
                    zCM = aiVar.cM() | z;
                }
                z = zCM;
            }
            if (z) {
                this.qy.clear();
                this.qz.clear();
                int size = arrayListDd.size();
                for (int i = 0; i < size; i++) {
                    w wVar = arrayListDd.get(i);
                    if (wVar.ds()) {
                        this.qy.add(wVar);
                    } else {
                        this.qz.add(wVar);
                    }
                }
            } else {
                this.qy.clear();
                this.qz.clear();
                this.qz.addAll(dd());
            }
            this.qA = false;
        }
    }

    public ArrayList<w> df() {
        de();
        return this.qy;
    }

    public ArrayList<w> dg() {
        de();
        return this.qz;
    }

    public void clearHeader() {
        this.qE = null;
        this.qD = null;
        this.qF = null;
        v(false);
    }

    private void a(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        Resources resources = getResources();
        if (view != null) {
            this.qF = view;
            this.qD = null;
            this.qE = null;
        } else {
            if (i > 0) {
                this.qD = resources.getText(i);
            } else if (charSequence != null) {
                this.qD = charSequence;
            }
            if (i2 > 0) {
                this.qE = android.support.v4.content.c.a(getContext(), i2);
            } else if (drawable != null) {
                this.qE = drawable;
            }
            this.qF = null;
        }
        v(false);
    }

    protected s k(CharSequence charSequence) {
        a(0, charSequence, 0, null, null);
        return this;
    }

    protected s ah(int i) {
        a(i, null, 0, null, null);
        return this;
    }

    protected s g(Drawable drawable) {
        a(0, null, 0, drawable, null);
        return this;
    }

    protected s ai(int i) {
        a(0, null, i, null, null);
        return this;
    }

    protected s C(View view) {
        a(0, null, 0, null, view);
        return this;
    }

    public CharSequence dh() {
        return this.qD;
    }

    public Drawable di() {
        return this.qE;
    }

    public View dj() {
        return this.qF;
    }

    public s dk() {
        return this;
    }

    boolean dl() {
        return this.qJ;
    }

    public boolean d(w wVar) {
        boolean zA = false;
        if (!this.qM.isEmpty()) {
            db();
            Iterator<WeakReference<ai>> it = this.qM.iterator();
            while (true) {
                boolean z = zA;
                if (!it.hasNext()) {
                    zA = z;
                    break;
                }
                WeakReference<ai> next = it.next();
                ai aiVar = next.get();
                if (aiVar == null) {
                    this.qM.remove(next);
                    zA = z;
                } else {
                    zA = aiVar.a(this, wVar);
                    if (zA) {
                        break;
                    }
                }
            }
            dc();
            if (zA) {
                this.qN = wVar;
            }
        }
        return zA;
    }

    public boolean e(w wVar) {
        boolean zB = false;
        if (!this.qM.isEmpty() && this.qN == wVar) {
            db();
            Iterator<WeakReference<ai>> it = this.qM.iterator();
            while (true) {
                boolean z = zB;
                if (!it.hasNext()) {
                    zB = z;
                    break;
                }
                WeakReference<ai> next = it.next();
                ai aiVar = next.get();
                if (aiVar == null) {
                    this.qM.remove(next);
                    zB = z;
                } else {
                    zB = aiVar.b(this, wVar);
                    if (zB) {
                        break;
                    }
                }
            }
            dc();
            if (zB) {
                this.qN = null;
            }
        }
        return zB;
    }

    public w dm() {
        return this.qN;
    }

    public void w(boolean z) {
        this.qO = z;
    }
}
