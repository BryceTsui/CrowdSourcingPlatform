package com.fx.repository;

import com.fx.model.CaptionClassificationResult;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 20:55 2018/5/29/029
 */
public interface CapAndClaResultRepository {
    /**
     * 根据任务id得到 标注结果
     *
     * @param missionID 任务ID
     * @return
     */
    public List<CaptionClassificationResult> getResultByID(int missionID);

    /**
     * 写入分类和整体描述的结果
     * @param missionID
     * @param results
     * @return
     */
    public ResultMessage addCapAndClaResult(int missionID, List<CaptionClassificationResult> results);
}
