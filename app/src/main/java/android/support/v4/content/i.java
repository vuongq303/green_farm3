package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: compiled from: LocalBroadcastManager.java */
/* JADX INFO: loaded from: classes.dex */
public final class i {
    private static i eN;
    private static final Object mLock = new Object();
    private final Context eJ;
    private final HashMap<BroadcastReceiver, ArrayList<l>> eK = new HashMap<>();
    private final HashMap<String, ArrayList<l>> eL = new HashMap<>();
    private final ArrayList<k> eM = new ArrayList<>();
    private final Handler mHandler;

    public static i h(Context context) {
        i iVar;
        synchronized (mLock) {
            if (eN == null) {
                eN = new i(context.getApplicationContext());
            }
            iVar = eN;
        }
        return iVar;
    }

    private i(Context context) {
        this.eJ = context;
        this.mHandler = new j(this, context.getMainLooper());
    }

    public void a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.eK) {
            l lVar = new l(intentFilter, broadcastReceiver);
            ArrayList<l> arrayList = this.eK.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.eK.put(broadcastReceiver, arrayList);
            }
            arrayList.add(lVar);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList<l> arrayList2 = this.eL.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>(1);
                    this.eL.put(action, arrayList2);
                }
                arrayList2.add(lVar);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.eK) {
            ArrayList<l> arrayListRemove = this.eK.remove(broadcastReceiver);
            if (arrayListRemove != null) {
                for (int size = arrayListRemove.size() - 1; size >= 0; size--) {
                    l lVar = arrayListRemove.get(size);
                    lVar.eR = true;
                    for (int i = 0; i < lVar.filter.countActions(); i++) {
                        String action = lVar.filter.getAction(i);
                        ArrayList<l> arrayList = this.eL.get(action);
                        if (arrayList != null) {
                            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                                l lVar2 = arrayList.get(size2);
                                if (lVar2.receiver == broadcastReceiver) {
                                    lVar2.eR = true;
                                    arrayList.remove(size2);
                                }
                            }
                            if (arrayList.size() <= 0) {
                                this.eL.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean b(Intent intent) {
        String str;
        ArrayList arrayList;
        synchronized (this.eK) {
            String action = intent.getAction();
            String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.eJ.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean z = (intent.getFlags() & 8) != 0;
            if (z) {
                Log.v("LocalBroadcastManager", "Resolving type " + strResolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
            }
            ArrayList<l> arrayList2 = this.eL.get(intent.getAction());
            if (arrayList2 != null) {
                if (z) {
                    Log.v("LocalBroadcastManager", "Action list: " + arrayList2);
                }
                ArrayList arrayList3 = null;
                int i = 0;
                while (i < arrayList2.size()) {
                    l lVar = arrayList2.get(i);
                    if (z) {
                        Log.v("LocalBroadcastManager", "Matching against filter " + lVar.filter);
                    }
                    if (lVar.eQ) {
                        if (z) {
                            Log.v("LocalBroadcastManager", "  Filter's target already added");
                            arrayList = arrayList3;
                        } else {
                            arrayList = arrayList3;
                        }
                    } else {
                        int iMatch = lVar.filter.match(action, strResolveTypeIfNeeded, scheme, data, categories, "LocalBroadcastManager");
                        if (iMatch >= 0) {
                            if (z) {
                                Log.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(iMatch));
                            }
                            arrayList = arrayList3 == null ? new ArrayList() : arrayList3;
                            arrayList.add(lVar);
                            lVar.eQ = true;
                        } else {
                            if (z) {
                                switch (iMatch) {
                                    case ProfilePictureView.LARGE /* -4 */:
                                        str = "category";
                                        break;
                                    case ProfilePictureView.NORMAL /* -3 */:
                                        str = NativeProtocol.WEB_DIALOG_ACTION;
                                        break;
                                    case -2:
                                        str = ShareConstants.WEB_DIALOG_PARAM_DATA;
                                        break;
                                    case -1:
                                        str = "type";
                                        break;
                                    default:
                                        str = "unknown reason";
                                        break;
                                }
                                Log.v("LocalBroadcastManager", "  Filter did not match: " + str);
                            }
                            arrayList = arrayList3;
                        }
                    }
                    i++;
                    arrayList3 = arrayList;
                }
                if (arrayList3 != null) {
                    for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                        ((l) arrayList3.get(i2)).eQ = false;
                    }
                    this.eM.add(new k(intent, arrayList3));
                    if (!this.mHandler.hasMessages(1)) {
                        this.mHandler.sendEmptyMessage(1);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ak() {
        k[] kVarArr;
        while (true) {
            synchronized (this.eK) {
                int size = this.eM.size();
                if (size <= 0) {
                    return;
                }
                kVarArr = new k[size];
                this.eM.toArray(kVarArr);
                this.eM.clear();
            }
            for (k kVar : kVarArr) {
                int size2 = kVar.eP.size();
                for (int i = 0; i < size2; i++) {
                    l lVar = kVar.eP.get(i);
                    if (!lVar.eR) {
                        lVar.receiver.onReceive(this.eJ, kVar.intent);
                    }
                }
            }
        }
    }
}
