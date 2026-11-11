package com.facebook.appevents;

import android.content.Context;
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/* JADX INFO: loaded from: classes.dex */
class AppEventStore {
    private static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
    private static final String TAG = AppEventStore.class.getName();

    AppEventStore() {
    }

    public static synchronized void persistEvents(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
        AppEventUtility.assertIsNotMainThread();
        PersistedEvents andClearStore = readAndClearStore();
        if (andClearStore.containsKey(accessTokenAppIdPair)) {
            andClearStore.get(accessTokenAppIdPair).addAll(sessionEventsState.getEventsToPersist());
        } else {
            andClearStore.addEvents(accessTokenAppIdPair, sessionEventsState.getEventsToPersist());
        }
        saveEventsToDisk(andClearStore);
    }

    public static synchronized void persistEvents(AppEventCollection appEventCollection) {
        AppEventUtility.assertIsNotMainThread();
        PersistedEvents andClearStore = readAndClearStore();
        for (AccessTokenAppIdPair accessTokenAppIdPair : appEventCollection.keySet()) {
            andClearStore.addEvents(accessTokenAppIdPair, appEventCollection.get(accessTokenAppIdPair).getEventsToPersist());
        }
        saveEventsToDisk(andClearStore);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0031 A[Catch: all -> 0x0085, TRY_ENTER, TRY_LEAVE, TryCatch #4 {, blocks: (B:4:0x0004, B:7:0x0022, B:8:0x0025, B:10:0x0031, B:14:0x0039, B:32:0x0077, B:33:0x007a, B:34:0x0084, B:39:0x0089, B:26:0x005f, B:27:0x0062, B:30:0x006f, B:17:0x0041, B:18:0x0044, B:21:0x0051), top: B:51:0x0004, inners: #0, #1, #6, #7 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.Closeable] */
    public static synchronized PersistedEvents readAndClearStore() {
        MovedClassObjectInputStream movedClassObjectInputStream;
        MovedClassObjectInputStream movedClassObjectInputStream2;
        PersistedEvents persistedEvents;
        Object obj;
        ?? r2 = 0;
        synchronized (AppEventStore.class) {
            try {
                AppEventUtility.assertIsNotMainThread();
                Context applicationContext = FacebookSdk.getApplicationContext();
                try {
                    movedClassObjectInputStream = new MovedClassObjectInputStream(new BufferedInputStream(applicationContext.openFileInput(PERSISTED_EVENTS_FILENAME)));
                    try {
                        persistedEvents = (PersistedEvents) movedClassObjectInputStream.readObject();
                        Utility.closeQuietly(movedClassObjectInputStream);
                        try {
                            File fileStreamPath = applicationContext.getFileStreamPath(PERSISTED_EVENTS_FILENAME);
                            fileStreamPath.delete();
                            obj = fileStreamPath;
                        } catch (Exception e) {
                            String str = TAG;
                            obj = "Got unexpected exception when removing events file: ";
                        }
                    } catch (FileNotFoundException e2) {
                        movedClassObjectInputStream2 = movedClassObjectInputStream;
                        Utility.closeQuietly(movedClassObjectInputStream2);
                        try {
                            applicationContext.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                            persistedEvents = null;
                        } catch (Exception e3) {
                            String str2 = TAG;
                            persistedEvents = null;
                        }
                        if (persistedEvents == null) {
                            persistedEvents = new PersistedEvents();
                        }
                        return persistedEvents;
                    } catch (Exception e4) {
                        String str3 = TAG;
                        Utility.closeQuietly(movedClassObjectInputStream);
                        try {
                            applicationContext.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                            persistedEvents = null;
                            obj = movedClassObjectInputStream;
                        } catch (Exception e5) {
                            String str4 = TAG;
                            persistedEvents = null;
                            obj = movedClassObjectInputStream;
                        }
                    }
                } catch (FileNotFoundException e6) {
                    movedClassObjectInputStream2 = null;
                } catch (Exception e7) {
                    movedClassObjectInputStream = null;
                } catch (Throwable th) {
                    th = th;
                    Utility.closeQuietly(r2);
                    try {
                        applicationContext.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                    } catch (Exception e8) {
                        String str5 = TAG;
                    }
                    throw th;
                }
                if (persistedEvents == null) {
                    persistedEvents = new PersistedEvents();
                }
            } catch (Throwable th2) {
                th = th2;
                r2 = obj;
            }
        }
        return persistedEvents;
    }

    private static void saveEventsToDisk(PersistedEvents persistedEvents) throws Throwable {
        Throwable th;
        ObjectOutputStream objectOutputStream;
        ObjectOutputStream objectOutputStream2;
        Context applicationContext = FacebookSdk.getApplicationContext();
        try {
            objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(applicationContext.openFileOutput(PERSISTED_EVENTS_FILENAME, 0)));
            try {
                try {
                    objectOutputStream2.writeObject(persistedEvents);
                    Utility.closeQuietly(objectOutputStream2);
                } catch (Throwable th2) {
                    th = th2;
                    objectOutputStream = objectOutputStream2;
                    Utility.closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Exception e) {
                String str = TAG;
                try {
                    applicationContext.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                } catch (Exception e2) {
                }
                Utility.closeQuietly(objectOutputStream2);
            }
        } catch (Exception e3) {
            objectOutputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            objectOutputStream = null;
            Utility.closeQuietly(objectOutputStream);
            throw th;
        }
    }

    class MovedClassObjectInputStream extends ObjectInputStream {
        private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
        private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";

        public MovedClassObjectInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.ObjectInputStream
        protected ObjectStreamClass readClassDescriptor() throws ClassNotFoundException, IOException {
            ObjectStreamClass classDescriptor = super.readClassDescriptor();
            if (classDescriptor.getName().equals(ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(AccessTokenAppIdPair.SerializationProxyV1.class);
            }
            if (classDescriptor.getName().equals(APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(AppEvent.SerializationProxyV1.class);
            }
            return classDescriptor;
        }
    }
}
