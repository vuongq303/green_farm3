package com.unity3d.services.monetization.placementcontent.core;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RewardablePlacementContent extends PlacementContent {
    private boolean isRewarded;
    private String rewardId;

    public RewardablePlacementContent(String str, Map<String, Object> map) {
        super(str, map);
        if (map.containsKey("rewarded")) {
            this.isRewarded = ((Boolean) map.get("rewarded")).booleanValue();
        }
        if (map.containsKey("rewardId")) {
            this.rewardId = (String) map.get("rewardId");
        }
    }

    public boolean isRewarded() {
        return this.isRewarded;
    }

    public String getRewardId() {
        return this.rewardId;
    }
}
