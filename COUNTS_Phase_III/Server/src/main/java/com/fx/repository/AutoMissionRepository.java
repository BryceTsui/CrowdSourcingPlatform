package com.fx.repository;

import com.fx.model.AutoMission;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 16:04 2018/6/8/008
 */
public interface AutoMissionRepository {

    public List<AutoMission> getAllAutoMissions();

    public AutoMission findAutoMissionByID(int ID);

    public List<AutoMission> findAutoMissionByRequestorID(String requestorID);

    public List<AutoMission> findAutoMissionByType(String type);

    public ResultMessage addAutoMission(AutoMission autoMission);

    public ResultMessage deleteAutoMission(int id);
}
