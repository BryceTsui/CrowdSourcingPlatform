package com.fx.controller;

import com.fx.bean.RecommendResult;
import com.fx.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 * Created by Hanxinhu at 22:32 2018/6/10/010
 */
@Controller
@RequestMapping("/counts/recommend")
@CrossOrigin
public class RecommendController{
    @Autowired
    RecommendService recommendService;
    @RequestMapping(
            value = "/mission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public RecommendResult recommend(String username){
        return recommendService.recommend(username);
    }
}
