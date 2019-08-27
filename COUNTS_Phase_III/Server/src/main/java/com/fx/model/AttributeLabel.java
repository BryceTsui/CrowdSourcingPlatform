package com.fx.model;

import java.util.List;

/**
 * Description: 属性标注
 * Created by Hanxinhu at 22:30 2018/4/2/002
 */
public class AttributeLabel {
    /**
     * 文件名
     */
    String fileName;

    /**
     * 属性
     */
    List<String> attributes;

    public AttributeLabel() {
    }


    public AttributeLabel(String fileName, List<String> attributes) {
        this.fileName = fileName;
        this.attributes = attributes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
}
