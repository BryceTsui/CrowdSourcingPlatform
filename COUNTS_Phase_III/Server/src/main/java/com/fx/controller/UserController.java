package com.fx.controller;

import com.fx.bean.OptMessage;
import com.fx.bean.UserData;
import com.fx.bean.UserProfile;
import com.fx.model.User;
import com.fx.service.UserService;
import com.fx.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by thinkpad on 2018/3/17.
 */

@Controller
@RequestMapping("/counts/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 注册
     *
     * @param user 用户名和密码
     * @return 注册结果，是否成功
     */
    @ResponseBody
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"})
    public OptMessage signUp(
            @RequestBody User user) {
        ResultMessage resultMessage = userService.signUp(user);
        OptMessage result = new OptMessage(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    /**
     * 登陆
     * @param user
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(
            value = "/signin",
            method = RequestMethod.POST)
    public OptMessage signIn(
            @RequestBody User user) {
        ResultMessage resultMessage = userService.signIn(user.getUsername(), user.getPassword());
        OptMessage result = new OptMessage(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.setResult(true);

        }
        result.setMessage(resultMessage.toString());
        return result;
    }

   /* @ResponseBody
    @RequestMapping(
            value = "/signout",
            method = RequestMethod.POST
    )
    public OptMessage signout() {
        return new Result(true);
    }
   */




    /**
     * 根据username查找用户
     *
     * @param username
     * @return 查到的用户类型
     */
    @ResponseBody
    @RequestMapping(
            value = "/findusername",
            method = RequestMethod.POST
    )
    public String findUserByUsername(String username){


        

        System.out.println(username);
        String s = userService.findUserByUsername(username);
        System.out.println(s);
        return  s;


    }

    /*****************************下面为迭代2新增方法***************/
/**
 * 将接任务转移到MissionController
 */
//
//    /**
//     * 用户接任务
//     * @param missionid
//     * @param userid
//     * @return
//     */
//
//    @RequestMapping(value = "/acceptmission",params= {"missionid","userid"},  method = RequestMethod.POST)
//    @ResponseBody
//    public OptMessage acceptMission(int missionid,int userid){
//
//
//        ResultMessage resultMessage = userService.acceptMission(missionid, userid);
//        OptMessage result = new OptMessage(false);
//
//        if(ResultMessage.SUCCESS == resultMessage){
//            result.setResult(true);
//        }
//        result.setMessage(resultMessage.toString());
//        return result;
//
//    }

    /**
     * 获得用户个人中心中“我的资料”展示时需要的数据
     * @param userid
     * @return
     */
    @RequestMapping(
            value = "/getuserprofile",
            params = "userid",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public UserProfile getUserProfile(@RequestParam(value = "userid") int userid){
        return userService.getUserProfile(userid);
    }

    /**
     * 获得用户个人中心时“我的数据”展示需要的数据
     * @param userid
     * @return
     */
    @RequestMapping(
            value = "/getuserdata",
            params = "userid",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public UserData getUserData(@RequestParam(value = "userid")int userid){
        return userService.getUserData(userid);
    }


    /**
     * 上传头像base64
     * @param base64img
     * @return
     */
    @RequestMapping(
            value = "/update/avatar",
            params = {"userid","base64img"},
            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    public OptMessage updateAvatar(@RequestParam(value = "userid") int userid,@RequestParam(value = "base64img") String base64img){

        ResultMessage resultMessage = userService.updateAvatar(userid, base64img);
        OptMessage result = new OptMessage(false);

        if(ResultMessage.SUCCESS == resultMessage){
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());
        return result;

    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping(
            value = "/update/userdata",

            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage updateUserData(@RequestBody User user){
        System.out.println("update???????");
        ResultMessage resultMessage = userService.updateUserData(user);
        OptMessage result = new OptMessage(false);

        if(ResultMessage.SUCCESS == resultMessage){
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    /**
     * 获得用户信息
     * @param username
     * @return
     */
    @RequestMapping(
            value = "/getuser",
            params = {"username"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public User getUser(@RequestParam(value = "username") String username){
       // System.out.println(username);
       // System.out.println(userService.getUser(username).getUsername());
       return userService.getUser(username);
    }


    /**
     * 获得前十名积分多的用户
     * @return
     */
    @RequestMapping(
            value = "/getuserrank",

            params = {"type"},
            method = RequestMethod.POST

    )
    @ResponseBody
    public List<User> getUserRank(@RequestParam(value = "type") String type){

        return userService.getUserRank(type);
    }

    /**
     * 获得当前用户排名
     * @param username
     * @return
     */
    @RequestMapping(
            value = "/getcurrentuserrank",
            params = {"username","type"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public int getCurrentUserRank(@RequestParam(value = "username") String username,@RequestParam(value = "type")String type){

        return userService.getCurrentUserRank(username,type);
    }


}
