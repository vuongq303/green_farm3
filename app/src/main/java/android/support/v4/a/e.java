package android.support.v4.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.d.h;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Map;

/* JADX INFO: compiled from: TypefaceCompatBaseImpl.java */
/* JADX INFO: loaded from: classes.dex */
class e implements c {
    e() {
    }

    @Override // android.support.v4.a.c
    public Typeface a(Context context, h[] hVarArr, Map<Uri, ByteBuffer> map) {
        if (hVarArr.length < 1) {
            return null;
        }
        ByteBuffer byteBuffer = map.get(hVarArr[0].getUri());
        File fileI = f.i(context);
        if (fileI == null) {
            return null;
        }
        try {
            if (f.a(fileI, byteBuffer)) {
                return Typeface.createFromFile(fileI.getPath());
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        } finally {
            fileI.delete();
        }
    }

    private android.support.v4.content.a.d a(android.support.v4.content.a.c cVar, int i, boolean z) {
        android.support.v4.content.a.d dVar = null;
        int i2 = Integer.MAX_VALUE;
        for (android.support.v4.content.a.d dVar2 : cVar.an()) {
            int iAbs = (z == dVar2.isItalic() ? 0 : 1) + (Math.abs(dVar2.getWeight() - i) * 2);
            if (dVar == null || i2 > iAbs) {
                i2 = iAbs;
                dVar = dVar2;
            }
        }
        return dVar;
    }

    @Override // android.support.v4.a.c
    public Typeface a(Context context, android.support.v4.content.a.c cVar, Resources resources, int i) {
        android.support.v4.content.a.d dVarA = a(cVar, (i & 1) == 0 ? 400 : 700, (i & 2) != 0);
        if (dVarA == null) {
            return null;
        }
        return b.a(context, resources, dVarA.ao(), i);
    }
}
