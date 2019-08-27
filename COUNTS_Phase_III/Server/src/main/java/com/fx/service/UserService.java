package com.fx.service;

import com.fx.bean.OptMessage;
import com.fx.bean.UserData;
import com.fx.bean.UserProfile;
import com.fx.model.User;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/17.
 */
public interface UserService {

    /**
     * 增加用户
     *
     * @param user 新用户
     * @return 是否增加成功
     */
    public ResultMessage signUp(User user);

        /**
         * 登录
         *
         * @param username 用户账号
         * @param password 用户密码
         * @return 当前登录状态
         */
        public ResultMessage signIn(String username, String password);

        /**
         * 登出
         *
         * @return 当前登录状态
         */
        public ResultMessage signOut();

        /**
         * 获得当前登录的用户ID
         *
         * @return 当前登录用户ID
         */
        public String getCurrentUser();


    /**
     * 根据username查找用户
     *
     * @param username
     * @return 查到的用户类型
     */
    public String findUserByUsername(String username);

    /*****************************下面为迭代2新增方法***************/


    /**
     * 用户接任务
     * @param missionid,userid
     * @param userid
     * @return
     */
    public ResultMessage acceptMission(int missionid, int userid);


    /**
     * 获得用户个人中心中“我的资料”展示时需要的数据
     * @param userid
     * @return
     */
    public UserProfile getUserProfile(int userid);


    /**
     * 获得用户个人中心时“我的数据”展示需要的数据
     * @param userid
     * @return
     */
    public UserData getUserData(int userid);

    /**
     * 上传头像
     * @param userid,base64img
     * @return
     */
    public ResultMessage updateAvatar(int userid,String base64img);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public ResultMessage updateUserData( User user);

    public User getUser(String username);

    public List<User> getUserRank(String type);

    public int getCurrentUserRank(String username,String type);

}
