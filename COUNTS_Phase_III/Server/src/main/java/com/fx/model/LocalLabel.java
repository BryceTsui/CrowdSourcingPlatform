package com.fx.model;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/23.
 */
public class LocalLabel {

    private String fileName;
    private String url;
    private String missionID;
    private String userName;
    private List<SegmentationDot> dots;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMissionID() {
        return missionID;
    }

    public void setMissionID(String missionID) {
        this.missionID = missionID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SegmentationDot> getDots() {
        return dots;
    }

    public void setDots(List<SegmentationDot> dots) {
        this.dots = dots;
    }
}
