package com.fx.service;

import com.fx.bean.MissionMonthChart;
import com.fx.bean.UserLevelChart;
import com.fx.bean.UserLocationChart;

import java.util.List;

/**
 * Created by thinkpad on 2018/4/1.
 */
public interface AnalysisService {
    /**
     * 获得不同等级的工作者数
     * @return 代表不同等级的用户数目。一般是从1开始，到n结束，目前还没确定n这个上限,注意0一般是没有用的
     */
    public List<UserLevelChart> getWorkerLevelChart();

    /**
     * 获得不同等级发布者数
     * @return 代表不同等级的用户数目。一般是从1开始，到n结束，目前还没确定n这个上限,注意0一般是没有用的
     */
    public List<UserLevelChart> getRequestorLevelChart();

    /**
     * 获得工作者的地理位置图表
     * @return
     */
    public UserLocationChart getWorkerLocationChart();

    /**
     * 获得发布者的地理位置图表
     * @return
     */
    public UserLocationChart getRequestorLocationChart();

    public int getWorkerNumber();

    public int getRequestorNumber();

    public MissionMonthChart getMissionMonthChart();
}
