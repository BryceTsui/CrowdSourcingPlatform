package com.fx.model;

/**
 * Created by thinkpad on 2018/4/9.
 */
public class SegmentationDot {

    private int x;
    private int y;
    public SegmentationDot(){

    }
    public SegmentationDot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
