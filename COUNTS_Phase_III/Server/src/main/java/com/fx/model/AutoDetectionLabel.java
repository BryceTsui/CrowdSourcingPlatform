package com.fx.model;

import java.util.List;

/**
 * Created by thinkpad on 2018/6/12.
 */
public class AutoDetectionLabel {
    private String fileName;
    private List<AutoPercentDot> dots;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<AutoPercentDot> getDots() {
        return dots;
    }

    public void setDots(List<AutoPercentDot> dots) {
        this.dots = dots;
    }
}
