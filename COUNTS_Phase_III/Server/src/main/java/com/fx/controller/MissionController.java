package com.fx.controller;

import com.fx.bean.AutoMissionPresentation;
import com.fx.bean.MissionPresentation;
import com.fx.bean.OptMessage;
import com.fx.model.AcceptedMission;
import com.fx.model.AutoMission;
import com.fx.model.Mission;
import com.fx.service.MissionService;
import com.fx.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

/**
 * Created by thinkpad on 2018/4/9.
 */
@Controller
@RequestMapping("/counts/mission")
@CrossOrigin
public class MissionController {

    @Autowired
    MissionService missionService;

    /**
     * 获得所有任务，不区分发布者，工作者，是否结束等，其中MissionPresentation包含一个任务用于展示需要的基本信息
     *
     * @return
     */
    @RequestMapping(
            value = "/getmission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Mission> getAllMission(int i) {
        return missionService.getAllMission(i);
    }


    @RequestMapping(
            value = "/getmission/worker",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<MissionPresentation> getMissionByWorker(@RequestParam(value = "username") String userName) {
        return missionService.getMissionByWorker(userName);
    }


    @RequestMapping(
            value = "/getmission/requestor",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<MissionPresentation> getMissionByRequestor(@RequestParam(value = "username") String userName) {
        return missionService.getMissionByRequestor(userName);
    }


    /**
     * 获得一个用户的所有任务数，如果userID == "" 则返回所有任务,不限用户
     *
     * @param userName
     * @return
     */
    @RequestMapping(
            value = "/countmission/whole",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public int countWholeMissions(@RequestParam(value = "username") String userName) {
        return missionService.countWholeMissions(userName);
    }

    /**
     * 获得一个用户的已经完成所有任务数，如果userID == "" 则返回所有已经完成的任务,不限用户
     *
     * @param userName
     * @return
     */
    @RequestMapping(
            value = "/countmission/finished",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public int countFinishedMissions(@RequestParam(value = "username") String userName) {
        return missionService.countFinishedMissions(userName);
    }

    /**
     * 获得一个用户的未完成所有任务数，如果userID == "" 则返回所有未完成的任务,不限用户
     *
     * @param userName
     * @return
     */
    @RequestMapping(
            value = "/countmission/unfinished",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public int countUnfinishedMissions(@RequestParam(value = "username") String userName) {

        return missionService.countUnfinishedMissions(userName);
    }


    /**
     * 更新Mission的信息
     *
     * @param mission
     * @return
     */
    @RequestMapping(
            value = "/mission/update",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage updateMission(Mission mission) {
        ResultMessage resultMessage = missionService.updateMission(mission);
        OptMessage result = new OptMessage(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.setResult(true);

        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    /**
     * 根据id删除mission
     *
     * @param id
     * @return
     */
    @RequestMapping(
            value = "/mission/delete",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage deleteMission(int id) {
        ResultMessage resultMessage = missionService.deleteMission(id);
        OptMessage result = new OptMessage(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.setResult(true);

        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    /**
     * 根据ID查找对应的任务
     *
     * @param id
     * @return
     */
    @RequestMapping(
            value = "/findmission/id",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public Mission findMissionByID(int id) {
        return missionService.findMissionByID(id);
    }

    /**
     * 根据起始日期以及结束日期选择
     *
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(
            value = "/findmission/time",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Mission> findMissionByBeginAndEnd(String start, String end) {
        return missionService.findMissionByBeginAndEnd(start, end);
    }

    /**
     * 根据任务类型查找
     *
     * @param type
     * @return
     */
    @RequestMapping(
            value = "/findmission/kind",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Mission> findMissionByType(String type) {

        return missionService.findMissionByType(type);
    }

    /**
     * 根据发布者的id(实际为username)查找发布的任务
     *
     * @param requestorID
     * @return
     */
    @RequestMapping(
            value = "/findmission/requestorid",
            params = {"requestorid"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Mission> findMissionByRequestorID(@RequestParam(value = "requestorid") String requestorID) {

        return missionService.findMissionByRequestorID(requestorID);
    }

    /**
     * 获得一个mission的第一张图片的base64
     *
     * @param missionid
     * @return
     */
    @RequestMapping(
            value = "get/firstimage",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public String getFirstImage(int missionid) {
        System.out.println("first");
        return missionService.getFirstImage(missionid);
    }

    /**
     * 得到发布者发布的所有自动化标注任务
     *
     * @param username 用户名
     * @return
     */
    @RequestMapping(
            value = "/getAutoMission/requestor",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AutoMission> getAutoMissionByRequestorID(String username) {
        return missionService.getAutoMissionByRequestorID(username);
    }

    @RequestMapping(
            value = "/getAutoMission/worker",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AutoMissionPresentation> getAutoMissionByWorkerID(String username){
        return missionService.getAutoMissionByWorkerID(username);
    }

    @RequestMapping(
            value = "/getAutoMission/signalworker",
            params = {"username","missionid"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public AutoMissionPresentation getAutoMissionBySignalWorkerID(String username,String missionid){

        //System.out.println("123");
        List<AutoMissionPresentation> lists = missionService.getAutoMissionByWorkerID(username);
        for(int i=0;i<=lists.size()-1;i++){
            if(lists.get(i).getId()==Integer.parseInt(missionid)){
                System.out.println(lists.get(i).getStatus());
                return lists.get(i);
            }
        }
        System.out.println("456");
        return null;
    }


    /**
     * 发布自动化标注的任务,并且指派工人！！！
     */
    @RequestMapping(
            value = "/addAutoMission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public int addAutoMission(@RequestBody AutoMission autoMission) {
        missionService.addAutoMission(autoMission);
        int id = autoMission.getId();

        return autoMission.getId();
    }

    @RequestMapping(
            value = "/findAutoMission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public AutoMission getAutoMission(int missionid) {
        return missionService.getAutoMission(missionid);

    }



    /**
     * 工人接受任务
     *
     * @param username      用户名
     * @param recommendType 推荐的类型
     * @return
     */
    @RequestMapping(
            value = "/addAcceptMission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage addAcceptMission(String username, int missionID, int recommendType) {
        ResultMessage resultMessage = missionService.addAcceptedMission(username, missionID, recommendType);
        OptMessage result = new OptMessage(false);

        if (ResultMessage.SUCCESS == resultMessage) {
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());

        return result;
    }

    @RequestMapping(
            value = "/getAcceptedMission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AcceptedMission> getAcceptMissionByUsername(String username) {
        return missionService.findAcceptedMissionByUsername(username);
    }
    @RequestMapping(
            value = "/getAcceptedMissionByUsernameMissionID",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public AcceptedMission getAcceptMissionByUsernameAndMissionID(String username,int missionID) {

        return missionService.findAcceptedMissionByUsernameAndMissionID(username,missionID);
    }

    /**
     * 更新工人完成任务的信息
     *
     * @param acceptedMission
     * @return
     */
    @RequestMapping(
            value = "/updateAcceptedMission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage updateAcceptMission(@RequestBody AcceptedMission acceptedMission) {
        System.out.println("controller" + acceptedMission.getFinished());
        return missionService.updateAcceptedMission(acceptedMission);
    }


}
