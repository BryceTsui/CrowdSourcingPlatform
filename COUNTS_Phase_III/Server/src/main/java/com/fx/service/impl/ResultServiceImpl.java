package com.fx.service.impl;

import com.fx.model.CaptionClassificationResult;
import com.fx.service.ResultService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 17:52 2018/5/29/029
 */
@Service
public class ResultServiceImpl  implements ResultService {
    public ResultServiceImpl() {
    }

    /**
     * 得到整体描述的结果
     *
     * @param missionID 任务ID
     * @return 整体描述的结果列表
     */
    @Override
    public List<CaptionClassificationResult> getCapAndClaResult(int missionID) {
        return null;
    }

    /**
     * 返回url
     *
     * @param missionID
     * @param type
     * @return
     */
    @Override
    public String getResult(int missionID, int type) {
        return null;
    }
}
