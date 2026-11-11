package android.support.v7.c;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: SupportMenuInflater.java */
/* JADX INFO: loaded from: classes.dex */
public class i extends MenuInflater {
    static final Class<?>[] nP = {Context.class};
    static final Class<?>[] nQ = nP;
    Context mContext;
    final Object[] nR;
    final Object[] nS;
    private Object nT;

    public i(Context context) {
        super(context);
        this.mContext = context;
        this.nR = new Object[]{context};
        this.nS = this.nR;
    }

    @Override // android.view.MenuInflater
    public void inflate(int i, Menu menu) {
        if (!(menu instanceof android.support.v4.b.a.a)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser layout = null;
        try {
            try {
                layout = this.mContext.getResources().getLayout(i);
                a(layout, Xml.asAttributeSet(layout), menu);
                if (layout != null) {
                    layout.close();
                }
            } catch (IOException e) {
                throw new InflateException("Error inflating menu XML", e);
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } catch (Throwable th) {
            if (layout != null) {
                layout.close();
            }
            throw th;
        }
    }

    private void a(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        k kVar = new k(this, menu);
        int eventType = xmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("menu")) {
                    eventType = xmlPullParser.next();
                    break;
                }
                throw new RuntimeException("Expecting menu, got " + name);
            }
            eventType = xmlPullParser.next();
        } while (eventType != 1);
        boolean z = false;
        String str = null;
        boolean z2 = false;
        int next = eventType;
        while (!z) {
            switch (next) {
                case 1:
                    throw new RuntimeException("Unexpected end of document");
                case 2:
                    if (z2) {
                        z = z;
                        continue;
                    } else {
                        String name2 = xmlPullParser.getName();
                        if (name2.equals("group")) {
                            kVar.a(attributeSet);
                            z = z;
                        } else if (name2.equals("item")) {
                            kVar.b(attributeSet);
                            z = z;
                        } else if (name2.equals("menu")) {
                            a(xmlPullParser, attributeSet, kVar.cy());
                            z = z;
                        } else {
                            z = z;
                            str = name2;
                            z2 = true;
                        }
                    }
                    next = xmlPullParser.next();
                    z = z;
                    break;
                case 3:
                    String name3 = xmlPullParser.getName();
                    if (!z2 || !name3.equals(str)) {
                        if (name3.equals("group")) {
                            kVar.cw();
                            z = z;
                        } else if (name3.equals("item")) {
                            if (kVar.cz()) {
                                break;
                            } else if (kVar.ow != null && kVar.ow.hasSubMenu()) {
                                kVar.cy();
                                z = z;
                            } else {
                                kVar.cx();
                                z = z;
                            }
                        } else {
                            if (name3.equals("menu")) {
                                z = true;
                            }
                            break;
                        }
                    } else {
                        z = z;
                        str = null;
                        z2 = false;
                    }
                    next = xmlPullParser.next();
                    z = z;
                    break;
            }
            next = xmlPullParser.next();
            z = z;
        }
    }

    Object cv() {
        if (this.nT == null) {
            this.nT = n(this.mContext);
        }
        return this.nT;
    }

    private Object n(Object obj) {
        if (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) {
            return n(((ContextWrapper) obj).getBaseContext());
        }
        return obj;
    }
}
