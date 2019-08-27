package com.fx.model;

/**
 * Description: 分类标注
 * Created by Hanxinhu at 22:20 2018/4/2/002
 */
public class ClassificationLabel {
    /**
     * 文件名
     */
    String fileName;
    /**
     * 选择了第n个标注 从0开始
     */
    int select;

    public ClassificationLabel() {
    }

    public ClassificationLabel(String fileName, int select) {
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
