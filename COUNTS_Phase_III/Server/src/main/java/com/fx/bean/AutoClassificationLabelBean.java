package com.fx.bean;

import com.fx.model.AutoClassificationLabel;

/**
 * Created by thinkpad on 2018/6/12.
 */
public class AutoClassificationLabelBean {
    private int missionid;
    private String kind;
    private String username;
    private AutoClassificationLabel autoClassificationLabel;

    public AutoClassificationLabel getAutoClassificationLabel() {
        return autoClassificationLabel;
    }

    public void setAutoClassificationLabel(AutoClassificationLabel autoClassificationLabel) {
        this.autoClassificationLabel = autoClassificationLabel;
    }

    public int getMissionid() {
        return missionid;
    }

    public void setMissionid(int missionid) {
        this.missionid = missionid;
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
