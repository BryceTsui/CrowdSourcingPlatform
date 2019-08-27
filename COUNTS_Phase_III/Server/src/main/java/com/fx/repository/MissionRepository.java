package com.fx.repository;

import com.fx.model.Mission;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:51 2018/4/10/010
 */
public interface MissionRepository {
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

    /**
     * 用于用户接受任务后初始化类
     *
     * @param userid
     * @param missionname
     * @return
     */
    public ResultMessage initialize(int userid, int missionname);

    /**
     * 得到所有的结果
     * @return
     */
    public  List<Mission> getAllMission();
}
