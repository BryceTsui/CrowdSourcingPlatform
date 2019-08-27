package com.fx.controller;

import com.fx.bean.MissionMonthChart;
import com.fx.bean.UserLevelChart;
import com.fx.bean.UserLocationChart;
import com.fx.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by thinkpad on 2018/4/1.
 */

@Controller
@RequestMapping("/counts/analysis")
@CrossOrigin
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;

    /**
     * 获得不同等级的工作者数
     * @return 代表不同等级的用户数目。一般是从1开始，到n结束，目前还没确定n这个上限,注意0一般是没有用的
     */
    @RequestMapping(
            value = "/levelchart/worker",
            method = RequestMethod.GET,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<UserLevelChart> getWorkerLevelChart(){

        return analysisService.getWorkerLevelChart();
    }

    /**
     * 获得不同等级发布者数
     * @return 代表不同等级的用户数目。一般是从1开始，到n结束，目前还没确定n这个上限,注意0一般是没有用的
     */
    @RequestMapping(
            value = "/levelchart/requestor",
            method = RequestMethod.GET,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<UserLevelChart> getRequestorLevelChart(){
        return analysisService.getRequestorLevelChart();
    }

    /**
     * 获得工作者的地理位置图表
     * @return
     */
    @RequestMapping(
            value = "/locationchart/worker",
            method = RequestMethod.GET,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public UserLocationChart getWorkerLocationChart(){
        return analysisService.getWorkerLocationChart();
    }

    /**
     * 获得发布者的地理位置图表
     * @return
     */
    @RequestMapping(
            value = "/locationchart/requestor",
            method = RequestMethod.GET,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public UserLocationChart getRequestorLocationChart(){
        return analysisService.getRequestorLocationChart();
    }


    /**
     * 获得工作者总数
     * @return
     */
    @RequestMapping(
            value = "getnumber/worker",
            method = RequestMethod.GET
    )
    @ResponseBody
    public int getWorkerNumber(){
        return analysisService.getWorkerNumber();
    }

    /**
     * 获得发布者总数
     * @return
     */
    @RequestMapping(
            value = "getnumber/requestor",
            method = RequestMethod.GET
    )
    @ResponseBody
    public int getRequestorNumber(){

        return analysisService.getRequestorNumber();
    }

    @RequestMapping(
            value = "getmission/monthcart",
            method = RequestMethod.GET
    )
    @ResponseBody
    public MissionMonthChart getMissionMonthChart(){

        return analysisService.getMissionMonthChart();
    }

}
