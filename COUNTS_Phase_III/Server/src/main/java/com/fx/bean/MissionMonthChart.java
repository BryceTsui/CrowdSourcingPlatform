package com.fx.bean;

/**
 * Created by thinkpad on 2018/4/18.
 */
public class MissionMonthChart {

    private int[] list1;
    private int[] list2;
    private int[] list3;

    public MissionMonthChart(int[] list1, int[] list2, int[] list3) {
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
    }

    public int[] getList1() {
        return list1;
    }

    public void setList1(int[] list1) {
        this.list1 = list1;
    }

    public int[] getList2() {
        return list2;
    }

    public void setList2(int[] list2) {
        this.list2 = list2;
    }

    public int[] getList3() {
        return list3;
    }

    public void setList3(int[] list3) {
        this.list3 = list3;
    }
}
