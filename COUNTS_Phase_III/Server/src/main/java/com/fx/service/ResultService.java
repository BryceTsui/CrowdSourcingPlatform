package com.fx.service;

import com.fx.model.CaptionClassificationResult;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 16:48 2018/5/29/029
 */
public interface ResultService {
    /**
     * 得到整体描述的结果
     * @param missionID 任务ID
     * @return 整体描述的结果列表
     */
    public List<CaptionClassificationResult> getCapAndClaResult(int missionID);

    /**
     * 返回url
     * @param missionID
     * @param type
     * @return
     */
    public String getResult(int missionID,int type);

}
