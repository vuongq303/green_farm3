package com.gameloft.adsmanager;

import com.gameloft.adsmanager.Incentivized.BaseIncentivizedObject;
import com.unity3d.ads.metadata.PlayerMetaData;

/* JADX INFO: loaded from: classes.dex */
public class UnityAdsIncentivized extends BaseIncentivizedObject {
    public String UUID;
    private UnityAds parent;
    public String sdkLocation;

    public UnityAdsIncentivized(UnityAds unityAds, String str) {
        super(unityAds);
        this.sdkLocation = str;
        this.parent = unityAds;
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectInterface
    public void Show(String str, String str2, String str3, String str4) {
        this.parent.onScreenIncentivized = this;
        this.UUID = str3;
        String str5 = str2 + "|" + str + "|" + str3 + (str4.length() == 0 ? "" : "|" + str4);
        JavaLoggingDefines.LOG_INFO("UnityAdsIncentivized", "Show", "sid = (" + str5 + ")");
        PlayerMetaData playerMetaData = new PlayerMetaData(AdsManager.mainActivity);
        playerMetaData.setServerId(str5);
        playerMetaData.commit();
        JavaUtils.PostAndLogException(AdsManager.parentViewGroup, new d(this), new e(this));
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectInterface
    public boolean IsValid() {
        return com.unity3d.ads.UnityAds.isReady(this.sdkLocation);
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectInterface
    public void Close() {
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectInterface
    public void Destroy() {
    }
}
