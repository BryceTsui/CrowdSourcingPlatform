package com.fx.repository;

import com.fx.model.AcceptedMission;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 17:31 2018/6/11/011
 */
public interface AcceptMissionRepository {
    public ResultMessage addAcceptMission(AcceptedMission acceptedMission);

    public ResultMessage updateAcceptMission(AcceptedMission acceptedMission);

    public List<AcceptedMission>  findAcceptMissionByUsername(String username);

    /**
     * 得到所有的acceptMission用户协调分析
     * @return
     */
    public List<AcceptedMission> getAllAcceptMission();
}
