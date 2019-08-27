package com.fx.bean;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/18.
 */

//局部标注的Bean文件
public class LocalLabelBean {

    //标注
    private String label;

    //点集合
    List<Dot> dotList;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Dot> getDotList() {
        return dotList;
    }

    public void setDotList(List<Dot> dotList) {
        this.dotList = dotList;
    }
}
