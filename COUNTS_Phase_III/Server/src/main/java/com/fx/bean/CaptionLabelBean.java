package com.fx.bean;

import com.fx.model.CaptionLabel;

/**
 * Created by thinkpad on 2018/4/25.
 */
public class CaptionLabelBean {
    private int missionID;
    private String userName;
    private CaptionLabel captionLabel;

    public CaptionLabelBean() {
    }

    public CaptionLabelBean(int missionID, String userName, CaptionLabel captionLabel) {
        this.missionID = missionID;
        this.userName = userName;
        this.captionLabel = captionLabel;
    }

    public int getMissionID() {
        return missionID;
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CaptionLabel getCaptionLabel() {
        return captionLabel;
    }

    public void setCaptionLabel(CaptionLabel captionLabel) {
        this.captionLabel = captionLabel;
    }
}
