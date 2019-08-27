package com.fx.bean;

/**
 * Created by thinkpad on 2018/6/11.
 */
public class MLClassificationLabel {
    private String fileName;
    private int label;
    private double possibility;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double getPossibility() {
        return possibility;
    }

    public void setPossibility(double possibility) {
        this.possibility = possibility;
    }
}
