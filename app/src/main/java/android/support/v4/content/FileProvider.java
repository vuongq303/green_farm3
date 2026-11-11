package android.support.v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class FileProvider extends ContentProvider {
    private static final String[] ex = {"_display_name", "_size"};
    private static final File ey = new File("/");
    private static HashMap<String, d> ez = new HashMap<>();
    private d eA;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        this.eA = b(context, providerInfo.authority);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int i;
        File fileB = this.eA.b(uri);
        if (strArr == null) {
            strArr = ex;
        }
        String[] strArr3 = new String[strArr.length];
        Object[] objArr = new Object[strArr.length];
        int length = strArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            String str3 = strArr[i2];
            if ("_display_name".equals(str3)) {
                strArr3[i3] = "_display_name";
                i = i3 + 1;
                objArr[i3] = fileB.getName();
            } else if ("_size".equals(str3)) {
                strArr3[i3] = "_size";
                i = i3 + 1;
                objArr[i3] = Long.valueOf(fileB.length());
            } else {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        String[] strArrA = a(strArr3, i3);
        Object[] objArrCopyOf = copyOf(objArr, i3);
        MatrixCursor matrixCursor = new MatrixCursor(strArrA, 1);
        matrixCursor.addRow(objArrCopyOf);
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        File fileB = this.eA.b(uri);
        int iLastIndexOf = fileB.getName().lastIndexOf(46);
        if (iLastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileB.getName().substring(iLastIndexOf + 1));
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
        }
        return "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("No external updates");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return this.eA.b(uri).delete() ? 1 : 0;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) {
        return ParcelFileDescriptor.open(this.eA.b(uri), j(str));
    }

    private static d b(Context context, String str) {
        d dVarC;
        synchronized (ez) {
            dVarC = ez.get(str);
            if (dVarC == null) {
                try {
                    try {
                        dVarC = c(context, str);
                        ez.put(str, dVarC);
                    } catch (XmlPullParserException e) {
                        throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
                    }
                } catch (IOException e2) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                }
            }
        }
        return dVarC;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00ac  */
    private static d c(Context context, String str) {
        File externalStorageDirectory;
        e eVar = new e(str);
        XmlResourceParser xmlResourceParserLoadXmlMetaData = context.getPackageManager().resolveContentProvider(str, 128).loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
        if (xmlResourceParserLoadXmlMetaData == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        while (true) {
            int next = xmlResourceParserLoadXmlMetaData.next();
            if (next != 1) {
                if (next == 2) {
                    String name = xmlResourceParserLoadXmlMetaData.getName();
                    String attributeValue = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "name");
                    String attributeValue2 = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "path");
                    if ("root-path".equals(name)) {
                        externalStorageDirectory = ey;
                    } else if ("files-path".equals(name)) {
                        externalStorageDirectory = context.getFilesDir();
                    } else if ("cache-path".equals(name)) {
                        externalStorageDirectory = context.getCacheDir();
                    } else if ("external-path".equals(name)) {
                        externalStorageDirectory = Environment.getExternalStorageDirectory();
                    } else if ("external-files-path".equals(name)) {
                        File[] fileArrA = c.a(context, (String) null);
                        if (fileArrA.length > 0) {
                            externalStorageDirectory = fileArrA[0];
                        } else {
                            externalStorageDirectory = null;
                        }
                    } else if ("external-cache-path".equals(name)) {
                        File[] fileArrE = c.e(context);
                        if (fileArrE.length > 0) {
                            externalStorageDirectory = fileArrE[0];
                        } else {
                            externalStorageDirectory = null;
                        }
                    } else {
                        externalStorageDirectory = null;
                    }
                    if (externalStorageDirectory != null) {
                        eVar.a(attributeValue, a(externalStorageDirectory, attributeValue2));
                    }
                }
            } else {
                return eVar;
            }
        }
    }

    private static int j(String str) {
        if ("r".equals(str)) {
            return 268435456;
        }
        if ("w".equals(str) || "wt".equals(str)) {
            return 738197504;
        }
        if ("wa".equals(str)) {
            return 704643072;
        }
        if ("rw".equals(str)) {
            return 939524096;
        }
        if ("rwt".equals(str)) {
            return 1006632960;
        }
        throw new IllegalArgumentException("Invalid mode: " + str);
    }

    private static File a(File file, String... strArr) {
        int length = strArr.length;
        int i = 0;
        File file2 = file;
        while (i < length) {
            String str = strArr[i];
            i++;
            file2 = str != null ? new File(file2, str) : file2;
        }
        return file2;
    }

    private static String[] a(String[] strArr, int i) {
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, i);
        return strArr2;
    }

    private static Object[] copyOf(Object[] objArr, int i) {
        Object[] objArr2 = new Object[i];
        System.arraycopy(objArr, 0, objArr2, 0, i);
        return objArr2;
    }
}
