package com.fx.bean;

import java.util.List;

/**
 * Created by thinkpad on 2018/6/11.
 */
public class MLDetectionLabel {
    private String fileName;
    private List<PercentRectangle> rectangles;
    private List<String> labels;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<PercentRectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<PercentRectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
