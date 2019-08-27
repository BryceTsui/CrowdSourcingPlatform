package com.fx.bean;

import com.fx.model.Mission;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 22:38 2018/6/10/010
 */
public class RecommendResult {
    /**
     * 推荐的类型
     * 1代表topFive
     * 2代表基于内容的推荐
     * 3代表基于用户协调CF的推荐
     */
    int type;
    /**
     * 推荐的任务
     */
    List<Mission> missions;

    public RecommendResult() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }
}
