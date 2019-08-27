package com.fx.service.impl;

import com.fx.bean.RecommendResult;
import com.fx.model.Mission;
import com.fx.service.MissionService;
import com.fx.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 写在开始
 * 由于是众包市场 既要让requestor满意又要让worker找到自己喜欢的算法
 * 所以 在让requestor 的任务能够完成
 * worker也要找到自己的喜欢工作的类型 所以距离开始时间越久 结束时间越近的权重应该越大
 * 这里就应该有个影响因子 在这里
 *
 * 推荐算法的正确率怎么计算呢
 * 点击率 + 评分值
 * 点击率 = 接受任务数 + 推荐总数
 * 评分值 = average(评分值) // todo 待定
 */

/**
 * Description:
 * Created by Hanxinhu at 22:46 2018/6/10/010
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    MissionService missionService;

    @Override
    public RecommendResult recommend(String username) {
        List<Mission> missions = missionService.getAllMission(0);
        RecommendResult recommendResult = new RecommendResult();
        recommendResult.setMissions(missions);
        recommendResult.setType(1);
        return recommendResult;
    }

    /**
     * top5推荐法 推荐最热门的
     *
     * @param username
     * @return
     */
    //todo
    private List<Mission> top5recommend(String username) {
        return null;
    }

    /**
     * 协同过滤推荐算法 基于相似的用户喜欢的任务也是相似的
     *
     * @param username
     * @return
     */
    //todo
    private List<Mission> CFrecommend(String username) {
        return null;
    }

    /**
     * 基于内容的推荐 tags
     *
     * @param username
     * @return
     */
    //todo
    private List<Mission> contentRecommend(String username) {
        return null;
    }
}
