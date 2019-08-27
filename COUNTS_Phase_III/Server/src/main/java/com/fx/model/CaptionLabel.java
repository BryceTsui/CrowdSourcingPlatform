package com.fx.model;

/**
 * Description: 看图说话
 * Created by Hanxinhu at 22:30 2018/4/2/002
 */
public class CaptionLabel {
    /**
     * 文件名
     */
    String fileName;

    /**
     *  对该图片的描述
     */
    String caption;

    public CaptionLabel() {
    }

    public CaptionLabel(String fileName, String caption) {
        this.fileName = fileName;
        this.caption = caption;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
