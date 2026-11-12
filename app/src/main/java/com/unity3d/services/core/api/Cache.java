/*
 * setFileContent restored from Unity Technologies unity-ads-android 3.0.1:
 * https://github.com/Unity-Technologies/unity-ads-android/blob/3.0.1/lib/src/main/java/com/unity3d/services/core/api/Cache.java
 * Upstream code is licensed under the Apache License, Version 2.0.
 * You may obtain a copy at https://www.apache.org/licenses/LICENSE-2.0
 * Distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 * See the License for the specific language governing permissions and limitations.
 * Modified: recovered the original method to replace malformed JADX output.
 */
package com.unity3d.services.core.api;

import android.media.MediaMetadataRetriever;
import android.util.Base64;
import android.util.SparseArray;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.glads.AndroidWebView;
import com.unity3d.services.core.cache.CacheDirectory;
import com.unity3d.services.core.cache.CacheDirectoryType;
import com.unity3d.services.core.cache.CacheError;
import com.unity3d.services.core.cache.CacheThread;
import com.unity3d.services.core.device.Device;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;
import com.unity3d.services.core.properties.ClientProperties;
import com.unity3d.services.core.properties.SdkProperties;
import com.unity3d.services.core.request.WebRequestError;
import com.unity3d.services.core.webview.bridge.WebViewCallback;
import com.unity3d.services.core.webview.bridge.WebViewExposed;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Cache {
    @WebViewExposed
    public static void download(String str, String str2, JSONArray jSONArray, Boolean bool, WebViewCallback webViewCallback) {
        if (CacheThread.isActive()) {
            webViewCallback.error(CacheError.FILE_ALREADY_CACHING, new Object[0]);
            return;
        }
        if (!Device.isActiveNetworkConnected()) {
            webViewCallback.error(CacheError.NO_INTERNET, new Object[0]);
            return;
        }
        try {
            CacheThread.download(str, fileIdToFilename(str2), Request.getHeadersMap(jSONArray), bool.booleanValue());
            webViewCallback.invoke(new Object[0]);
        } catch (Exception e) {
            DeviceLog.exception("Error mapping headers for the request", e);
            webViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, str, str2);
        }
    }

    @WebViewExposed
    public static void stop(WebViewCallback webViewCallback) {
        if (!CacheThread.isActive()) {
            webViewCallback.error(CacheError.NOT_CACHING, new Object[0]);
        } else {
            CacheThread.cancel();
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void isCaching(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(CacheThread.isActive()));
    }

    @WebViewExposed
    public static void getFileContent(String str, String str2, WebViewCallback webViewCallback) {
        Object objEncodeToString;
        String strFileIdToFilename = fileIdToFilename(str);
        File file = new File(strFileIdToFilename);
        if (!file.exists()) {
            webViewCallback.error(CacheError.FILE_NOT_FOUND, str, strFileIdToFilename);
            return;
        }
        try {
            byte[] fileBytes = Utilities.readFileBytes(file);
            if (str2 == null) {
                webViewCallback.error(CacheError.UNSUPPORTED_ENCODING, str, strFileIdToFilename, str2);
                return;
            }
            if (str2.equals(AndroidWebView.UTF_8)) {
                objEncodeToString = Charset.forName(AndroidWebView.UTF_8).decode(ByteBuffer.wrap(fileBytes)).toString();
            } else if (str2.equals("Base64")) {
                objEncodeToString = Base64.encodeToString(fileBytes, 2);
            } else {
                webViewCallback.error(CacheError.UNSUPPORTED_ENCODING, str, strFileIdToFilename, str2);
                return;
            }
            webViewCallback.invoke(objEncodeToString);
        } catch (IOException e) {
            webViewCallback.error(CacheError.FILE_IO_ERROR, str, strFileIdToFilename, e.getMessage() + ", " + e.getClass().getName());
        }
    }

    // Restored from Unity Ads 3.0.1, tag commit 0885aed0c58fdec731065e189a8c31c98893131d.
    @WebViewExposed
    public static void setFileContent(String fileId, String encoding, String content, WebViewCallback callback) {
        String targetFileName = fileIdToFilename(fileId);
        FileOutputStream fileOutput = null;
        byte[] fileContents;
        boolean success = false;

        try {
            fileContents = content.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            callback.error(CacheError.UNSUPPORTED_ENCODING, fileId, targetFileName, encoding);
            return;
        }

        if (encoding != null && encoding.length() > 0) {
            if (encoding.equals("Base64")) {
                fileContents = Base64.decode(content, Base64.NO_WRAP);
            }
            else if (encoding.equals("UTF-8")) {
                // UTF-8 handled by default
            }
            else {
                callback.error(CacheError.UNSUPPORTED_ENCODING, fileId, targetFileName, encoding);
                return;
            }
        }

        try {
            fileOutput = new FileOutputStream(targetFileName);
            fileOutput.write(fileContents);
            fileOutput.flush();
            success = true;
        }
        catch (FileNotFoundException e) {
            callback.error(CacheError.FILE_NOT_FOUND, fileId, targetFileName, encoding);
        }
        catch (IOException e) {
            callback.error(CacheError.FILE_IO_ERROR, fileId, targetFileName, encoding);
        }
        finally {
            try {
                if (fileOutput != null)
                    fileOutput.close();
            }
            catch (Exception e) {
                DeviceLog.exception("Error closing FileOutputStream", e);
            }
        }
        if (success) {
            callback.invoke();
        }
    }


    @WebViewExposed
    public static void getFiles(WebViewCallback webViewCallback) {
        File cacheDirectory = SdkProperties.getCacheDirectory();
        if (cacheDirectory != null) {
            DeviceLog.debug("Unity Ads cache: checking app directory for Unity Ads cached files");
            File[] fileArrListFiles = cacheDirectory.listFiles(new FilenameFilter() { // from class: com.unity3d.services.core.api.Cache.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return str.startsWith(SdkProperties.getCacheFilePrefix());
                }
            });
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                webViewCallback.invoke(new JSONArray());
            }
            try {
                JSONArray jSONArray = new JSONArray();
                for (File file : fileArrListFiles) {
                    String strSubstring = file.getName().substring(SdkProperties.getCacheFilePrefix().length());
                    DeviceLog.debug("Unity Ads cache: found " + strSubstring + ", " + file.length() + " bytes");
                    jSONArray.put(getFileJson(strSubstring));
                }
                webViewCallback.invoke(jSONArray);
            } catch (JSONException e) {
                DeviceLog.exception("Error creating JSON", e);
                webViewCallback.error(CacheError.JSON_ERROR, new Object[0]);
            }
        }
    }

    @WebViewExposed
    public static void getFileInfo(String str, WebViewCallback webViewCallback) {
        try {
            webViewCallback.invoke(getFileJson(str));
        } catch (JSONException e) {
            DeviceLog.exception("Error creating JSON", e);
            webViewCallback.error(CacheError.JSON_ERROR, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getFilePath(String str, WebViewCallback webViewCallback) {
        if (new File(fileIdToFilename(str)).exists()) {
            webViewCallback.invoke(fileIdToFilename(str));
        } else {
            webViewCallback.error(CacheError.FILE_NOT_FOUND, new Object[0]);
        }
    }

    @WebViewExposed
    public static void deleteFile(String str, WebViewCallback webViewCallback) {
        if (new File(fileIdToFilename(str)).delete()) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(CacheError.FILE_IO_ERROR, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getHash(String str, WebViewCallback webViewCallback) {
        webViewCallback.invoke(Utilities.Sha256(str));
    }

    @WebViewExposed
    public static void setTimeouts(Integer num, Integer num2, WebViewCallback webViewCallback) {
        CacheThread.setConnectTimeout(num.intValue());
        CacheThread.setReadTimeout(num2.intValue());
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void getTimeouts(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(CacheThread.getConnectTimeout()), Integer.valueOf(CacheThread.getReadTimeout()));
    }

    @WebViewExposed
    public static void setProgressInterval(Integer num, WebViewCallback webViewCallback) {
        CacheThread.setProgressInterval(num.intValue());
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void getProgressInterval(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(CacheThread.getProgressInterval()));
    }

    @WebViewExposed
    public static void getFreeSpace(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getFreeSpace(SdkProperties.getCacheDirectory())));
    }

    @WebViewExposed
    public static void getTotalSpace(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getTotalSpace(SdkProperties.getCacheDirectory())));
    }

    @WebViewExposed
    public static void getMetaData(String str, JSONArray jSONArray, WebViewCallback webViewCallback) {
        try {
            SparseArray<String> metaData = getMetaData(fileIdToFilename(str), jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < metaData.size(); i++) {
                JSONArray jSONArray3 = new JSONArray();
                jSONArray3.put(metaData.keyAt(i));
                jSONArray3.put(metaData.valueAt(i));
                jSONArray2.put(jSONArray3);
            }
            webViewCallback.invoke(jSONArray2);
        } catch (IOException e) {
            webViewCallback.error(CacheError.FILE_IO_ERROR, e.getMessage());
        } catch (RuntimeException e2) {
            webViewCallback.error(CacheError.INVALID_ARGUMENT, e2.getMessage());
        } catch (JSONException e3) {
            webViewCallback.error(CacheError.JSON_ERROR, e3.getMessage());
        }
    }

    @WebViewExposed
    public static void getCacheDirectoryType(WebViewCallback webViewCallback) {
        CacheDirectory cacheDirectoryObject = SdkProperties.getCacheDirectoryObject();
        if (cacheDirectoryObject == null || cacheDirectoryObject.getCacheDirectory(ClientProperties.getApplicationContext()) == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
            return;
        }
        if (!cacheDirectoryObject.getCacheDirectory(ClientProperties.getApplicationContext()).exists()) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_DOESNT_EXIST, new Object[0]);
            return;
        }
        CacheDirectoryType type = cacheDirectoryObject.getType();
        if (type == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_TYPE_NULL, new Object[0]);
        } else {
            webViewCallback.invoke(type.name());
        }
    }

    @WebViewExposed
    public static void getCacheDirectoryExists(WebViewCallback webViewCallback) {
        File cacheDirectory = SdkProperties.getCacheDirectory();
        if (cacheDirectory == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
        } else {
            webViewCallback.invoke(Boolean.valueOf(cacheDirectory.exists()));
        }
    }

    @WebViewExposed
    public static void recreateCacheDirectory(WebViewCallback webViewCallback) {
        if (SdkProperties.getCacheDirectory().exists()) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_EXISTS, new Object[0]);
            return;
        }
        SdkProperties.setCacheDirectory(null);
        if (SdkProperties.getCacheDirectory() == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
        } else {
            webViewCallback.invoke(new Object[0]);
        }
    }

    private static SparseArray<String> getMetaData(String str, JSONArray jSONArray) throws JSONException, IOException {
        File file = new File(str);
        SparseArray<String> sparseArray = new SparseArray<>();
        if (file.exists()) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
            for (int i = 0; i < jSONArray.length(); i++) {
                int i2 = jSONArray.getInt(i);
                String strExtractMetadata = mediaMetadataRetriever.extractMetadata(i2);
                if (strExtractMetadata != null) {
                    sparseArray.put(i2, strExtractMetadata);
                }
            }
            return sparseArray;
        }
        throw new IOException("File: " + file.getAbsolutePath() + " doesn't exist");
    }

    private static String fileIdToFilename(String str) {
        return SdkProperties.getCacheDirectory() + "/" + SdkProperties.getCacheFilePrefix() + str;
    }

    private static JSONObject getFileJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, str);
        File file = new File(fileIdToFilename(str));
        if (file.exists()) {
            jSONObject.put("found", true);
            jSONObject.put("size", file.length());
            jSONObject.put("mtime", file.lastModified());
        } else {
            jSONObject.put("found", false);
        }
        return jSONObject;
    }
}
