package com.fx.model;

/**
 * Description:
 * Created by Hanxinhu at 17:10 2018/6/6/006
 */
public class Message {
    /**
     * 唯一识别标注
     */
    private int id;
    /**
     * 信息的用户名
     */
    private String username;
    /**
     * 自动标注的任务 默认是0 0以外的值代表
     */
    private int missionID;

    /**
     * 已读 未读
     */
    private boolean read;
    /**
     * 消息的标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    public Message() {
    }

    /**
     * @param username  用户名
     * @param missionID 任务id
     * @param type      标注类型
     */
    public Message(String username, int missionID, String type) {
        setContent(username, missionID, type);
        this.title = "您有新的任务！";
        this.read = false;
    }

    private void setContent(String username, int missionID, String type) {
        this.content = username + " : \n" + "    您有新的任务，请您接单！\n" +
                "   任务号： " + missionID + "\n" +
                "   标注类型" + type + "\n";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public int getMissionID() {
        return missionID;
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }

}
