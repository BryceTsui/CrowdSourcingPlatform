package com.fx.bean;

import com.fx.model.AutoCaptionLabel;

/**
 * Created by thinkpad on 2018/6/12.
 */
public class AutoCaptionLabelBean {
    private int missionid;

    private String kind;
    private String username;
    private AutoCaptionLabel autoCaptionLabel;

    public int getMissionid() {
        return missionid;
    }

    public void setMissionid(int missionid) {
        this.missionid = missionid;
    }

    public AutoCaptionLabel getAutoCaptionLabel() {
        return autoCaptionLabel;
    }

    public void setAutoCaptionLabel(AutoCaptionLabel autoCaptionLabel) {
        this.autoCaptionLabel = autoCaptionLabel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
