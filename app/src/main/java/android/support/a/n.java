package android.support.a;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: compiled from: ICustomTabsService.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class n extends Binder implements m {
    public static m b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.support.customtabs.ICustomTabsService");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof m)) {
            return (m) iInterfaceQueryLocalInterface;
        }
        return new o(iBinder);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 2:
                parcel.enforceInterface("android.support.customtabs.ICustomTabsService");
                boolean zB = b(parcel.readLong());
                parcel2.writeNoException();
                parcel2.writeInt(zB ? 1 : 0);
                return true;
            case 3:
                parcel.enforceInterface("android.support.customtabs.ICustomTabsService");
                boolean zA = a(k.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(zA ? 1 : 0);
                return true;
            case 4:
                parcel.enforceInterface("android.support.customtabs.ICustomTabsService");
                boolean zA2 = a(k.a(parcel.readStrongBinder()), parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.createTypedArrayList(Bundle.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zA2 ? 1 : 0);
                return true;
            case 5:
                parcel.enforceInterface("android.support.customtabs.ICustomTabsService");
                Bundle bundleB = b(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                if (bundleB != null) {
                    parcel2.writeInt(1);
                    bundleB.writeToParcel(parcel2, 1);
                    return true;
                }
                parcel2.writeInt(0);
                return true;
            case 6:
                parcel.enforceInterface("android.support.customtabs.ICustomTabsService");
                boolean zA3 = a(k.a(parcel.readStrongBinder()), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(zA3 ? 1 : 0);
                return true;
            case 1598968902:
                parcel2.writeString("android.support.customtabs.ICustomTabsService");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
