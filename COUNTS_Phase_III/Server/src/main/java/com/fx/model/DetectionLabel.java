package com.fx.model;

import java.util.List;

/**
 * Description: 矩形标注
 * Created by Hanxinhu at 22:29 2018/4/2/002
 */
public class DetectionLabel {
    /**
     * 文件名
     */
    String fileName;
    /**
     * 矩形的列表
     */
    List<SegmentationDot> rectangles;

    public DetectionLabel() {
    }

    public DetectionLabel(String fileName, List<SegmentationDot> rectangles) {
        this.fileName = fileName;
        this.rectangles = rectangles;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SegmentationDot> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<SegmentationDot> rectangles) {
        this.rectangles = rectangles;
    }
}
