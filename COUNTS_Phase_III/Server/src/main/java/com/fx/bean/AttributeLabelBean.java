package com.fx.bean;

import com.fx.model.AttributeLabel;

/**
 * Created by thinkpad on 2018/4/25.
 */
public class AttributeLabelBean {
    private int missionID;
    private String userName;
    private AttributeLabel attributeLabel;

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

    public AttributeLabel getAttributeLabel() {
        return attributeLabel;
    }

    public void setAttributeLabel(AttributeLabel attributeLabel) {
        this.attributeLabel = attributeLabel;
    }
}
