package android.support.v4.a;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: compiled from: TypefaceCompatUtil.java */
/* JADX INFO: loaded from: classes.dex */
class f {
    public static File i(Context context) {
        String str = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < 100) {
                File file = new File(context.getCacheDir(), String.valueOf(str) + i2);
                try {
                    if (file.createNewFile()) {
                        return file;
                    }
                    i = i2 + 1;
                } catch (IOException e) {
                }
            } else {
                return null;
            }
        }
    }

    private static ByteBuffer b(File file) throws Throwable {
        Throwable th;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                FileChannel channel = fileInputStream.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                if (fileInputStream != null) {
                    fileInputStream.close();
                    return map;
                }
                return map;
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream == null) {
                    throw th;
                }
                try {
                    fileInputStream.close();
                    throw th;
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            th = null;
        }
        if (th != null) {
            if (th != th) {
                try {
                    th.addSuppressed(th);
                } catch (IOException e) {
                    return null;
                }
            }
            th = th;
        }
        throw th;
    }

    public static ByteBuffer a(Context context, Resources resources, int i) {
        ByteBuffer byteBufferB = null;
        File fileI = i(context);
        if (fileI != null) {
            try {
                if (a(fileI, resources, i)) {
                    byteBufferB = b(fileI);
                }
            } finally {
                fileI.delete();
            }
        }
        return byteBufferB;
    }

    public static boolean a(File file, ByteBuffer byteBuffer) {
        return a(file, new g(byteBuffer));
    }

    public static boolean a(File file, InputStream inputStream) throws Throwable {
        FileOutputStream fileOutputStream;
        try {
            try {
                fileOutputStream = new FileOutputStream(file, false);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i != -1) {
                            fileOutputStream.write(bArr, 0, i);
                        } else {
                            closeQuietly(fileOutputStream);
                            return true;
                        }
                    }
                } catch (IOException e) {
                    e = e;
                    Log.e("TypefaceCompatUtil", "Error copying resource contents to temp file: " + e.getMessage());
                    closeQuietly(fileOutputStream);
                    return false;
                }
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = null;
            } catch (Throwable th) {
                th = th;
                closeQuietly(null);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(null);
            throw th;
        }
    }

    public static boolean a(File file, Resources resources, int i) {
        InputStream inputStreamOpenRawResource = null;
        try {
            inputStreamOpenRawResource = resources.openRawResource(i);
            return a(file, inputStreamOpenRawResource);
        } finally {
            closeQuietly(inputStreamOpenRawResource);
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
