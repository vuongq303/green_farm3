package com.gameloft.android2d.iap.utils;

import com.facebook.appevents.AppEventsConstants;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* JADX INFO: compiled from: ServerInfo.java */
/* JADX INFO: loaded from: classes.dex */
public class w {
    y bnr;
    private SAXParserFactory boq;
    private SAXParser bor;
    private XMLReader bos;
    private q bot;
    d bov;
    private InputSource bou = null;
    private String bow = null;
    private String box = null;
    private x boy = null;

    public w() {
        try {
            if (!com.gameloft.android2d.iap.b.QK()) {
                this.boq = SAXParserFactory.newInstance();
                this.bor = this.boq.newSAXParser();
                this.bos = this.bor.getXMLReader();
            }
            this.bot = new q();
            if (!com.gameloft.android2d.iap.b.QK()) {
                this.bos.setContentHandler(this.bot);
            }
            this.bov = new d();
            this.bnr = new y(this.bov, true, v.mJ(com.b.d.cgk));
        } catch (Exception e) {
        }
    }

    public x Qr() {
        return this.boy;
    }

    public boolean gl(String str) {
        if (str == null || str.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO) || str.equals("")) {
            str = v.mJ(com.b.d.cgi);
        }
        if (!com.gameloft.android2d.iap.b.QK()) {
            if (com.gameloft.android2d.iap.b.bkv) {
                this.bnr.gO(a.Sy());
            }
            this.bnr.gJ(str);
            long jCurrentTimeMillis = 0;
            while (!this.bnr.Uk()) {
                try {
                    Thread.sleep(50L);
                } catch (Exception e) {
                }
                if (System.currentTimeMillis() - jCurrentTimeMillis > 1500) {
                    jCurrentTimeMillis = System.currentTimeMillis();
                }
            }
        }
        if (!com.gameloft.android2d.iap.b.QK()) {
            if (this.bnr.Uj() == 0) {
                if (com.gameloft.android2d.iap.b.bkv && (com.gameloft.android2d.iap.b.Ra() == null || !a.l(this.bnr.Ur(), this.bnr.Uq(), this.bnr.Up(), com.gameloft.android2d.iap.b.Ra()))) {
                    return false;
                }
                InputSource inputSource = new InputSource(new ByteArrayInputStream(this.bnr.Uo().BM.getBytes()));
                if (com.gameloft.android2d.iap.b.bkl) {
                    this.bou = inputSource;
                    return true;
                }
                this.bow = null;
                a(inputSource);
                x xVarQr = com.gameloft.android2d.iap.b.Qr();
                if (xVarQr.TU()) {
                    return true;
                }
                xVarQr.Ub();
                xVarQr.aj(v.mJ(com.b.d.cgz).toLowerCase(), v.mJ(com.b.d.cgA).toLowerCase());
                if (!com.gameloft.android2d.iap.b.bjN) {
                    return true;
                }
                if (v.TO() && v.TQ()) {
                    return true;
                }
                return false;
            }
            if (this.bnr.Uj() != 304 && (!com.gameloft.android2d.iap.b.bkl || (this.bnr.Uj() != -2 && this.bnr.Uj() != -100))) {
                return false;
            }
            com.gameloft.android2d.iap.b.bkl = false;
            if (this.bnr.Uj() != 304) {
                this.bou = null;
                com.gameloft.android2d.iap.b.bkm = "";
            }
            return true;
        }
        x xVarQr2 = com.gameloft.android2d.iap.b.Qr();
        if (xVarQr2.TU()) {
            return true;
        }
        xVarQr2.aj(v.mJ(com.b.d.cgz).toLowerCase(), v.mJ(com.b.d.cgA).toLowerCase());
        if (!com.gameloft.android2d.iap.b.bjN) {
            return true;
        }
        if (v.TO() && v.TQ()) {
            return true;
        }
        return false;
    }

    private void a(InputSource inputSource) {
        try {
            this.bos.parse(inputSource);
            this.boy = this.bot.Qr();
        } catch (Exception e) {
        }
    }

    public q TT() {
        return this.bot;
    }
}
