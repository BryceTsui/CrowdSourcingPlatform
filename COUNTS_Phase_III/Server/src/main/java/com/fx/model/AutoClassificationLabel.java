package com.fx.model;

/**
 * Created by thinkpad on 2018/6/12.
 */
public class AutoClassificationLabel {
    /**
     * 文件名
     */
    String fileName;
    /**
     * 选择了第n个标注 从0开始
     */
    int select;

    public AutoClassificationLabel() {
    }

    public AutoClassificationLabel(String fileName, int select) {
        this.fileName = fileName;
        this.select = select;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }
}
