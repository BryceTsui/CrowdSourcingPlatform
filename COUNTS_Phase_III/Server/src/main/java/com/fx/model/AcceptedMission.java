package com.fx.model;

/**
 * Created by thinkpad on 2018/4/9.
 */

/**
 * 用户已经接受的任务
 */
public class AcceptedMission {
    /**
     * 正在进行中的任务id
     */
    private  int id;
    /**
     * 任务名
     */
    private String missionName;

    /**
     * 用户名
     */
    private String username;

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
     * 已经完成的图片数目
     */
    private  int finished;

    /**
     * 该任务需要标注的总图片数目
     */
    private int sum;

    /**
     * 用户对任务的评分
     */
    private int score;
    /**
     * 该任务所占有的积分
     */
    private int point;
    /**
     * 以哪一种推荐接受到任务的
     * 1代表topFive
     * 2代表基于内容的推荐
     * 3代表基于用户协调CF的推荐
     */
    private int recommendType;

    public AcceptedMission() {
    }

    public AcceptedMission(String username ,Mission mission,int recommendType) {
        this.id = mission.getID();
        this.sum = mission.getSum();
        this.missionName =mission.getMissionName();
        this.begin = mission.getBegin();
        this.end = mission.getEnd();
        this.type = mission.getType();
        this.username = username;
        this.point = mission.getPoints();
        this.recommendType = recommendType;
    }

    public AcceptedMission(int id, String missionName, String begin, String end, String type, int finished, int sum) {
        this.id = id;
        this.missionName = missionName;
        this.begin = begin;
        this.end = end;
        this.type = type;
        this.finished = finished;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int addone(){
        if (finished<sum)
            finished++;
        return finished;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(int recommendType) {
        this.recommendType = recommendType;
    }
}

