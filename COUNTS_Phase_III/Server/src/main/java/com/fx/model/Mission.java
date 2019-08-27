package com.fx.model;

import java.util.List;

/**
 * Created by thinkpad on 2018/4/2.
 */

//此model为迭
// 代2新增
public class Mission {
    /**
     * 任务的唯一标识
     */
    private int ID;

    /**
     * 众包发起者id，注意：发起者也是user
     * User的Username
     */
    private String requestorID;


    /**
     * 任务名
     */
    private String missionName;
    /**
     * 任务需求描述
     */
    private String description;
    /**
     * 任务起始日期
     */
    private String begin;
    /**
     * 任务结束时间
     */
    private String end;

    /**
     * 任务类型  分别为字符串
     * Classification、Detection、Segmentation、Caption、Attribute
     */
    private String type;
    /**
     * Classification的时候是可能的类别
     * Attribute的时候 是可选的属性
     */
    private List<String> selects;
    /**
     * 任务需要的最大参与人数
     */
    private int maxNumber;
    /**
     * 当前已经接受的任务数
     */
    private int currentNumber;
    /**
     * 一人完成一次任务给予的奖励数目
     */
    private int points;
    /**
     * 任务内容的标签
     */
    private List<String> tags;
    /**
     * 该任务需要标注的总图片数
     */
    private int sum;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getRequestorID() {
        return requestorID;
    }

    public void setRequestorID(String requestorID) {
        this.requestorID = requestorID;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Mission() {
    }

    /**
     * 用于新建任务时 Classification Attribute
     * @param requestorID
     * @param missionName
     * @param begin
     * @param end
     * @param description
     * @param type
     * @param selects
     * @param maxNumber
     * @param points
     */
    public Mission(String requestorID, String missionName, String begin, String end, String description, String type, List<String> selects, int maxNumber, int points) {
        this.requestorID = requestorID;
        this.missionName = missionName;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.type = type;
        this.selects = selects;
        this.maxNumber = maxNumber;
        this.points = points;
    }

    /**
     * 没有 selects时， 表示是Detection、Segmentation、Caption中的一种
     * @param requestorID
     * @param missionName
     * @param begin
     * @param end
     * @param description
     * @param type
     * @param maxNumber
     * @param points
     */
    public Mission(String requestorID, String missionName, String begin, String end, String description, String type, int maxNumber, int points) {
        this.requestorID = requestorID;
        this.missionName = missionName;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.type = type;
        this.maxNumber = maxNumber;
        this.points = points;
    }

    /**
     * 添加currentNumber从数据库中返回
     * @param requestorID
     * @param missionName
     * @param begin
     * @param end
     * @param description
     * @param type
     * @param selects
     * @param maxNumber
     * @param currentNumber
     * @param points
     */
    public Mission(String requestorID, String missionName, String begin, String end, String description, String type, List<String> selects, int maxNumber, int currentNumber, int points) {
        this.requestorID = requestorID;
        this.missionName = missionName;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.type = type;
        this.selects = selects;
        this.maxNumber = maxNumber;
        this.currentNumber = currentNumber;
        this.points = points;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSelects() {
        return selects;
    }

    public void setSelects(List<String> selects) {
        this.selects = selects;
    }
}
