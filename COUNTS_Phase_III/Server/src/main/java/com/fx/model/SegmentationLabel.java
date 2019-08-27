package com.fx.model;

import java.util.List;

/**
 * Created by thinkpad on 2018/4/9.
 */
public class SegmentationLabel {
    /**
     * 文件名
     */
    String fileName;

    /**
     * 点集
     */
    List<SegmentationDot> dots;

    public SegmentationLabel() {
    }

    public SegmentationLabel(String fileName, List<SegmentationDot> dots) {
        this.fileName = fileName;
        this.dots = dots;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SegmentationDot> getDots() {
        return dots;
    }

    public void setDots(List<SegmentationDot> dots) {
        this.dots = dots;
    }
}
