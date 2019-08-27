package com.fx.model;

import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/18.
 */

// @Entity
//@Table(name = "user")
public class User {
    /**
     * 用户Id
     */
    //  @Id
    private int id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String name;
    /**
     * 用户简介
     */
    private String info;
    /**
     * 用base64格式存储的图片
     */
    private String avatar;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 角色 目前包括 Requestor Worker Admin
     */
    private String role;

    //下面的变量为迭代2新添加的：

    /**
     * 所正在进行的任务列表（存储的是任务名）
     */
    private List<AcceptedMission> missions;


    /**
     * 当前用户所持有的积分
     */
    private long points;
    /**
     *经验值
     */
    private int exp;
    /**
     * 用户当前级别
     */
    private int level;
    /**
     * 用户的评分
     * <p>
     * 根据正确性 满意度等
     */
    private double rate;
    /**
     * 省份
     */

    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 用户经常标注的类型 比如 军事、动物等
     */
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public User() {
    }

    public User(int id, String username, String password, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String username, String password, String email, String phone, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String username, String password, String email, String phone, String role, List<AcceptedMission> missions, long points) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.missions = missions;
        this.points = points;
    }

    public List<AcceptedMission> getMissions() {
        return missions;
    }

    public void setMissions(List<AcceptedMission> missions) {
        this.missions = missions;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        setLevel(judgeLevel(exp));

        this.exp = exp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    private int judgeLevel(int exp){
        int[] explevel = {0,200,600,1200,1800};

        for(int i=1;i<=explevel.length-1;i++){
            if(exp>=explevel[i-1]&&exp<=explevel[i]){
                return i;
            }
        }

        return 5;
    }

    public ResultMessage addAcceptedMission(int missionID){
        for (int i = 0; i < missions.size(); i++) {
            if(missions.get(i).getId()==missionID){
                int finishedFirst = missions.get(i).getFinished();
                int finishedSecond =   missions.get(i).addone();
                if (finishedFirst<finishedSecond&&finishedSecond>=missions.get(i).getSum()){
                    points += missions.get(i).getSum();
                    setExp(exp += missions.get(i).getSum());
                }
                return ResultMessage.SUCCESS;
            }
        }

        return ResultMessage.FAILED;
    }
}


