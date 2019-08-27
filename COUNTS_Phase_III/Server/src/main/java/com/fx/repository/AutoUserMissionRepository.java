package com.fx.repository;

import com.fx.model.AutoUserMission;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:31 2018/6/12/012
 */

public interface AutoUserMissionRepository {
    public ResultMessage addAutoUserMission(String username,AutoUserMission autoUserMission);

    public ResultMessage updateAutoUserMission(String username,AutoUserMission autoUserMission);

    public List<AutoUserMission> findAutoUserMissionByUsername(String username);
}
