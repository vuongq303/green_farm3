package com.gameloft.android2d.iap.utils;

import com.facebook.appevents.UserDataStore;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: compiled from: IABXMLParser.java */
/* JADX INFO: loaded from: classes.dex */
public class q extends DefaultHandler {
    private final String TAG = "IAP-IABXMLParser";
    boolean bnK = false;
    boolean bnL = false;
    String bnM = null;
    String bnN = null;
    public r bnO = null;
    public com.gameloft.android2d.iap.a.a bnP = null;
    public JSONObject bnQ = null;
    public JSONObject bnR = null;
    public JSONArray bnS = null;
    private x bnT = null;
    private boolean bnU = false;
    private boolean bnV = false;
    private boolean bnW = false;
    private boolean bnX = false;
    private boolean bnY = false;

    public x Qr() {
        return this.bnT;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) {
        if (!com.gameloft.android2d.iap.b.QK() && this.bnK) {
            this.bnM = "";
        }
        this.bnK = true;
        if (str2.equals("shop_info")) {
            this.bnT = new x();
            this.bnT.Ua();
            this.bnU = true;
            return;
        }
        if (this.bnU) {
            if (str2.equals(UserDataStore.COUNTRY)) {
                this.bnT.go(attributes.getValue(ShareConstants.WEB_DIALOG_PARAM_ID));
                this.bnT.gq(attributes.getValue("iso"));
                return;
            }
            if (str2.equals("operator")) {
                this.bnT.gt(attributes.getValue(ShareConstants.WEB_DIALOG_PARAM_ID));
                return;
            }
            if (str2.equals("product")) {
                this.bnT.setProductId(attributes.getValue(ShareConstants.WEB_DIALOG_PARAM_ID));
                return;
            }
            if (str2.equals("platform")) {
                this.bnT.gw(attributes.getValue(ShareConstants.WEB_DIALOG_PARAM_ID));
                return;
            } else if (str2.equals("language")) {
                this.bnT.gy(attributes.getValue(ShareConstants.WEB_DIALOG_PARAM_ID));
                return;
            } else {
                if (str2.equals("text")) {
                    this.bnN = attributes.getValue("key");
                    return;
                }
                return;
            }
        }
        if (str2.equals("content_list")) {
            this.bnV = true;
            return;
        }
        if (this.bnV) {
            if (str2.equals(FirebaseAnalytics.Param.CONTENT)) {
                this.bnO = new r();
                this.bnO.fG(attributes.getValue(ShareConstants.WEB_DIALOG_PARAM_ID));
                this.bnO.setType(attributes.getValue("type").toLowerCase());
                return;
            }
            if (str2.equals("attribute")) {
                this.bnN = attributes.getValue("name");
                return;
            }
            if (str2.equals("bundle")) {
                try {
                    this.bnQ = new JSONObject();
                    this.bnQ.put("pointcut", attributes.getValue("pointcut"));
                    this.bnQ.put("sku", attributes.getValue("sku"));
                    this.bnQ.put("redirect", attributes.getValue("redirect"));
                    this.bnQ.put("countdown", attributes.getValue("countdown"));
                    this.bnQ.put(FirebaseAnalytics.Param.LOCATION, attributes.getValue(FirebaseAnalytics.Param.LOCATION));
                    this.bnQ.put("localization_id", attributes.getValue("localization_id"));
                    this.bnS = new JSONArray();
                    this.bnX = true;
                    return;
                } catch (JSONException e) {
                    return;
                }
            }
            if (this.bnX) {
                if (str2.equals("bundle_item")) {
                    this.bnR = new JSONObject();
                    this.bnY = true;
                    return;
                }
                return;
            }
            if (str2.equals("billing_list")) {
                this.bnW = true;
                return;
            }
            if (str2.equals("offline_items_bonus")) {
                this.bnO.SK();
                return;
            }
            if (this.bnW) {
                if (str2.equals("billing")) {
                    this.bnP = com.gameloft.android2d.iap.a.b.fo(attributes.getValue("type"));
                } else if (str2.equals("text")) {
                    this.bnN = attributes.getValue("key");
                }
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) {
        try {
            this.bnK = false;
            this.bnM = this.bnM.trim();
            if (this.bnU) {
                if (str2.equals(UserDataStore.COUNTRY)) {
                    this.bnT.gp(this.bnM.trim());
                } else if (str2.equals("operator")) {
                    this.bnT.gu(this.bnM.trim());
                } else if (str2.equals("product")) {
                    this.bnT.gv(this.bnM.trim());
                } else if (str2.equals("platform")) {
                    this.bnT.gx(this.bnM.trim());
                } else if (str2.equals("language")) {
                    this.bnT.gz(this.bnM.trim());
                } else if (str2.equals("promo_description")) {
                    if (this.bnM != null) {
                        this.bnT.gC(this.bnM.trim());
                    }
                } else if (str2.equals("promo_endtime")) {
                    if (this.bnM != null) {
                        this.bnT.gD(this.bnM.trim());
                    }
                } else if (str2.equals("server_time")) {
                    if (this.bnM != null) {
                        this.bnT.gE(this.bnM.trim());
                    }
                } else if (str2.equals("limits_validation_url")) {
                    this.bnT.gA(this.bnM.trim());
                } else if (str2.equals("check_limit_url")) {
                    this.bnT.gB(this.bnM.trim());
                } else if (str2.equals("text")) {
                    if (this.bnM != null) {
                        this.bnT.addText(this.bnN, this.bnM.trim());
                    }
                } else if (str2.equals("cash")) {
                    this.bnT.gr(this.bnM.trim() + ",");
                } else if (str2.equals("coin")) {
                    this.bnT.gs(this.bnM.trim() + ",");
                } else if (str2.equals("flow_type")) {
                    this.bnT.mC(v.fZ(this.bnM.trim()));
                } else if (str2.equals("shop_anon_id")) {
                    this.bnT.gH(this.bnM.trim());
                } else if (str2.equals("cashcard_game_id")) {
                    this.bnT.gI(this.bnM.trim());
                } else if (str2.equals("shop_info")) {
                    this.bnU = false;
                }
            } else if (this.bnV) {
                if (this.bnX) {
                    try {
                        if (this.bnY) {
                            if (str2.equals("type")) {
                                this.bnR.put("type", this.bnM.trim());
                            } else if (str2.equals("amount")) {
                                this.bnR.put("amount", this.bnM.trim());
                            } else if (str2.equals("bundle_item")) {
                                this.bnS.put(this.bnR);
                                this.bnY = false;
                            }
                        }
                        if (str2.equals("bundle")) {
                            this.bnQ.put("bundle_item", this.bnS);
                            this.bnO.ae("bundle", this.bnQ.toString());
                            this.bnX = false;
                        }
                    } catch (JSONException e) {
                        this.bnY = false;
                        this.bnX = false;
                    }
                } else if (this.bnW) {
                    if (str2.equals("billing")) {
                        this.bnP.fm(this.bnO.getId());
                        this.bnO.a(this.bnP);
                    } else if (str2.equals("shenzhoufu_billing")) {
                        this.bnP.fc(this.bnM.trim());
                    } else if (str2.equals("shenzhoufu_get_billing_result")) {
                        this.bnP.fg(this.bnM.trim());
                    } else if (str2.equals("msg_shenzhoufu_notice")) {
                        this.bnP.ff(this.bnM.trim());
                    } else if (str2.equals("shortcode")) {
                        this.bnP.eY(this.bnM.trim());
                    } else if (str2.equals("shortcode1")) {
                        this.bnP.eZ(this.bnM.trim());
                    } else if (str2.equals("shortcode2")) {
                        this.bnP.fa(this.bnM.trim());
                    } else if (str2.equals("alias")) {
                        this.bnP.fb(this.bnM.trim());
                    } else if (str2.equals("ump_get_transid")) {
                        this.bnP.fc(this.bnM.trim());
                    } else if (str2.equals("ump_get_billing_result")) {
                        this.bnP.fg(this.bnM.trim());
                    } else if (str2.equals("msg_psms_notice_1")) {
                        this.bnP.fd(this.bnM.trim());
                    } else if (str2.equals("msg_psms_notice_2")) {
                        this.bnP.fe(this.bnM.trim());
                    } else if (str2.equals("type")) {
                        this.bnP.setType(this.bnM.trim());
                    } else if (str2.equals("price")) {
                        this.bnP.setPrice(this.bnM.trim());
                    } else if (str2.equals("currency")) {
                        this.bnP.setCurrency(this.bnM.trim());
                    } else if (str2.equals("formatted_price")) {
                        this.bnP.eW(this.bnM.trim());
                    } else if (str2.equals("tnc")) {
                        this.bnP.eR(this.bnM.trim());
                    } else if (str2.equals("url")) {
                        this.bnP.eX(this.bnM.trim());
                    } else if (str2.equals("uid") || str2.equals("sku") || str2.equals("samsung_item_id")) {
                        this.bnP.eS(this.bnM.trim());
                    } else if (str2.equals("money")) {
                        this.bnP.eT(this.bnM.trim());
                    } else if (str2.equals("is_3g_only")) {
                        this.bnP.eU(this.bnM.trim());
                    } else if (str2.equals("pid")) {
                        this.bnP.eV(this.bnM.trim());
                    } else if (str2.equals(ShareConstants.WEB_DIALOG_PARAM_ID)) {
                        this.bnP.setProfileId(this.bnM.trim());
                    } else if (str2.equals("proxy")) {
                        this.bnP.fi(this.bnM.trim());
                    } else if (str2.equals("port")) {
                        this.bnP.fj(this.bnM.trim());
                    } else if (str2.equals("price_point")) {
                        this.bnP.fh(this.bnM.trim());
                    } else if (str2.equals("flow_type")) {
                        this.bnP.mC(v.fZ(this.bnM.trim()));
                    } else if (str2.equals("bill_type")) {
                        this.bnP.fl(this.bnM);
                    } else if (str2.equals("text")) {
                        if (this.bnM != null) {
                            this.bnP.addText(this.bnN, this.bnM.trim());
                        }
                    } else if (str2.equals("billing_list")) {
                        this.bnW = false;
                    }
                } else if (str2.equals(FirebaseAnalytics.Param.CONTENT)) {
                    this.bnT.e(this.bnO);
                } else if (str2.equals("attribute")) {
                    this.bnO.ae(this.bnN, this.bnM.trim());
                    this.bnN = null;
                } else if (str2.equals("billing_type_pref")) {
                    this.bnO.fH(this.bnM.trim());
                } else if (str2.equals("content_list")) {
                    this.bnV = false;
                } else if (str2.equals("offline_item")) {
                    this.bnO.fC(this.bnM.trim());
                }
            }
            this.bnM = "";
        } catch (Exception e2) {
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) {
        if (this.bnK) {
            this.bnM += new String(cArr, i, i2);
        }
    }
}
