package com.hungdt.periodtracked;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class PeriodConfig {
    private static PeriodConfig _instance;
    private FirebaseRemoteConfig config;

    private PeriodConfig() {

    }

    public FirebaseRemoteConfig getConfig() {
        return this.config;
    }

    void setConfig(FirebaseRemoteConfig config) {
        this.config = config;
    }

    public static PeriodConfig getInstance() {
        if (_instance == null) {
            _instance = new PeriodConfig();
        }
        return _instance;
    }
}