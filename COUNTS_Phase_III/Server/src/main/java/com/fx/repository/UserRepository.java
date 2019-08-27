package com.fx.repository;

import com.fx.model.User;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/19.
 */
public interface UserRepository {
    /**
     * 查找到下一个账户应有的id
     * @return
     */
    public int findNextId();

    /**
     * 通过id 查找用户
     * @param id
     * @return
     */
    public User findUserById(int id);

    public List<User> findAllUsers();
    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 根据城市查找用户
     *
     * @param city
     * @return
     */
    public List<User> findUserByCity(String city);

    /**
     * 根据类型查找用户
     *
     * @param type
     * @return
     */
    public List<User> findUserByType(String type);

    /**
     * 通过用户名寻找密码
     * @param username
     * @return
     */
    public String findPasswordByUsername(String username);

    /**
     * 创建新的用户
     * @param user
     * @return
     */
    public ResultMessage addUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public ResultMessage updateUser(User user);

    /**
     * 删除用户信息
     * @param user
     * @return
     */
    public ResultMessage deleteUser(User user);
}
