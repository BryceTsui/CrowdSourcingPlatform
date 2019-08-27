package com.fx.bean;

/**
 * Created by thinkpad on 2018/4/13.
 */

import java.util.List;

/**
 * 显示用户不同等级的用户数的表所需要的数据
 */
public class UserLevelChart {

    private int value;
    private String name;

    public UserLevelChart(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
