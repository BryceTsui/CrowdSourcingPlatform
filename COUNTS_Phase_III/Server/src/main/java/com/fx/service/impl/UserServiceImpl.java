package com.fx.service.impl;

import com.fx.bean.UserData;
import com.fx.bean.UserProfile;
import com.fx.controller.ImageController;
import com.fx.model.AcceptedMission;
import com.fx.model.Mission;
import com.fx.model.User;
import com.fx.repository.MissionRepository;
import com.fx.repository.UserRepository;
import com.fx.repository.impl.MissionRepositoryImpl;
import com.fx.repository.impl.UserRepositoryImpl;
import com.fx.service.UserService;
import com.fx.util.ResultMessage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2018/3/17.
 */
@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    MissionRepository missionRepository;
    public UserServiceImpl(){
        userRepository = new UserRepositoryImpl();
        missionRepository = new MissionRepositoryImpl();
    }
    /**
     * 增加用户
     *
     * @param user 新用户
     * @return 是否增加成功
     */
    public ResultMessage signUp(User user){
        return userRepository.addUser(user);
    }

    /**
     * 登录
     *
     * @param username 用户账号
     * @param password 用户密码
     * @return 当前登录状态
     */
    public ResultMessage signIn(String username, String password){
        String passwordReal = userRepository.findPasswordByUsername(username);
        if (passwordReal==null)
            return ResultMessage.NOT_EXIST;
        if (password .equals(passwordReal))
        return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * 登出
     *
     * @return 当前登录状态
     */
    public ResultMessage signOut(){
        return ResultMessage.SUCCESS;
    }

    /**
     * 获得当前登录的用户ID
     *
     * @return 当前登录用户ID
     */
    public String getCurrentUser(){
        return "161250164";
    }


    /**
     * 根据username查找用户
     *
     * @param username
     * @return 查到的用户类型
     */
    public String findUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if (user != null)
            return user.getRole() + "";
        return "";
    }

    /*****************************下面为迭代2新增方法***************/


    /**
     * 用户接任务
     * @param missionid
     * @param userid
     * @return
     */
    public ResultMessage acceptMission(int missionid, int userid){

        String url = ImageController.imageURL;
        url = url + missionid;
        File file = new File(url);
        int num = file.listFiles().length;


        Mission midmission = missionRepository.findMissionByID(missionid);
        AcceptedMission acceptedMission = new AcceptedMission(missionid,midmission.getMissionName(),midmission.getBegin(),midmission.getEnd(),midmission.getType(),0,num);

        User mid = userRepository.findUserById(userid);

        for(int i=0;i<=mid.getMissions().size()-1;i++){
            if(mid.getMissions().get(i).getId()==missionid){
                return ResultMessage.EXIST;
            }
        }
        mid.getMissions().add(acceptedMission);

        Mission mission = missionRepository.findMissionByID(missionid);
        mission.setCurrentNumber(mission.getCurrentNumber()+1);
        missionRepository.updateMission(mission);
        missionRepository.initialize(userid, missionid);
        return userRepository.updateUser(mid);
    }


    /**
     * 获得用户个人中心中“我的资料”展示时需要的数据
     * @param userid
     * @return
     */
    public UserProfile getUserProfile(int userid){
        return null;
    }


    /**
     * 获得用户个人中心时“我的数据”展示需要的数据
     * @param userid
     * @return
     */
    public UserData getUserData(int userid){
        return null;
    }


    /**
     * 上传头像
     * @param userid,base64img
     * @return
     */
    public ResultMessage updateAvatar(int userid,String base64img){
        User user = userRepository.findUserById(userid);
        user.setAvatar(base64img);

        return userRepository.updateUser(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public ResultMessage updateUserData( User user){
        return userRepository.updateUser(user);
    }


    public User getUser(String username){

        return userRepository.findUserByUsername(username);
    }

    public List<User> getUserRank(String type){
        List<User> list = userRepository.findUserByType(type);
        List<User> rank = new ArrayList<>();



        for(int i=0;i<=list.size()-1;i++){
            for(int j=1;j<=list.size()-1-i;j++){
                if(list.get(j).getExp()>=list.get(j-1).getExp()){
                    User mid = list.get(j);

                    list.set(j,list.get(j-1));
                    list.set(j-1,mid);
                }
            }
        }

        for(int i=0;i<=9;i++){
            if(i>=list.size()){
                break;
            }
            rank.add(list.get(i));
        }

        return rank;
    }

    public int getCurrentUserRank(String username,String type){
        List<User> list = userRepository.findUserByType(type);


        for(int i=0;i<=list.size()-1;i++){
            for(int j=1;j<=list.size()-1-i;j++){
                if(list.get(j).getExp()>=list.get(j-1).getExp()){
                    User mid = list.get(j);

                    list.set(j,list.get(j-1));
                    list.set(j-1,mid);
                }
            }
        }

        for(int i=0;i<=list.size()-1;i++){
            if(list.get(i).getName().equals(username)){
                return i+1;
            }
        }

        return 0;
    }




}
