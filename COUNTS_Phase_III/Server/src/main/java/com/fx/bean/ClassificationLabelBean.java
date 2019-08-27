package com.fx.bean;

import com.fx.model.ClassificationLabel;

/**
 * Created by thinkpad on 2018/4/25.
 */
public class ClassificationLabelBean {
    private int missionID;
    private String userName;
    private ClassificationLabel classificationLabel;

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

    public ClassificationLabel getClassificationLabel() {
        return classificationLabel;
    }

    public void setClassificationLabel(ClassificationLabel classificationLabel) {
        this.classificationLabel = classificationLabel;
    }
}
