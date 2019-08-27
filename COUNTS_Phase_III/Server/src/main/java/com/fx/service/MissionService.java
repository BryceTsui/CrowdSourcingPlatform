package com.fx.service;

import com.fx.bean.AutoMissionPresentation;
import com.fx.bean.MissionPresentation;
import com.fx.model.AcceptedMission;
import com.fx.model.AutoMission;
import com.fx.model.Mission;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Created by thinkpad on 2018/4/10.
 */
public interface MissionService {
    /**
     * 获得所有任务，不区分发布者，工作者，是否结束等，其中MissionPresentation包含一个任务用于展示需要的基本信息
     * @return
     */
    public List<Mission> getAllMission(int i);

    /**
     * 获得某个发布者的所有任务，其中MissionPresentation包含一个任务用于展示需要的基本信息
     * @param userName
     * @return
     */
    public List<MissionPresentation> getMissionByWorker(String userName);

    /**
     * 获得一个工作者的所有任务信息，其中MissionPresentation包含一个任务用于展示需要的基本信息
     * @param userName
     * @return
     */
    public List<MissionPresentation> getMissionByRequestor(String userName);



    /**
     * 获得一个用户的所有任务数，如果userID == "" 则返回所有任务,不限用户
     * @param userName
     * @return
     */

    public int countWholeMissions(String userName);

    /**
     * 获得一个用户的已经完成所有任务数，如果userID == "" 则返回所有已经完成的任务,不限用户
     * @param userName
     * @return
     */

    public int countFinishedMissions(String userName);

    /**
     * 获得一个用户的未完成所有任务数，如果userID == "" 则返回所有未完成的任务,不限用户
     * @param userName
     * @return
     */

    public int countUnfinishedMissions(String userName);


    /**
     * 添加Mission
     *
     * @param mission
     * @return
     */
    public ResultMessage addMission(Mission mission);

    /**
     * 更新Mission的信息
     *
     * @param mission
     * @return
     */
    public ResultMessage updateMission(Mission mission);

    /**
     * 根据id删除mission
     *
     * @param id
     * @return
     */
    public ResultMessage deleteMission(int id);

    /**
     * 根据ID查找对应的任务
     *
     * @param id
     * @return
     */
    public Mission findMissionByID(int id);

    /**
     * 根据起始日期以及结束日期选择
     *
     * @param start
     * @param end
     * @return
     */
    public List<Mission> findMissionByBeginAndEnd(String start, String end);

    /**
     * 根据发布者的id(实际为username)查找发布的任务
     *
     * @param requestorID
     * @return
     */
    public List<Mission> findMissionByRequestorID(String requestorID);

    /**
     * 根据任务类型查找
     *
     * @param type
     * @return
     */
    public List<Mission> findMissionByType(String type);


    public String getFirstImage(int missionid);

    /**
     * 根据发布者的用户名查询 所有的自动化标注
     * @param username
     * @return
     */
    public List<AutoMission> getAutoMissionByRequestorID(String username);

    public ResultMessage addAutoMission(AutoMission autoMission);

    public AutoMission findAutoMissionByID(int id);

    public ResultMessage addAcceptedMission(String username, int id, int recommendType);

    public ResultMessage updateAcceptedMission(AcceptedMission acceptedMission);

    public List<AcceptedMission> findAcceptedMissionByUsername(String username);

    public AcceptedMission findAcceptedMissionByUsernameAndMissionID(String username,int missionID);

    public List<AutoMissionPresentation> getAutoMissionByWorkerID(String username);

    public AutoMission getAutoMission(int missionid);
}
