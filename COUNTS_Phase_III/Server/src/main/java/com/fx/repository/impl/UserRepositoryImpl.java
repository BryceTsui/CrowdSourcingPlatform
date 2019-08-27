package com.fx.repository.impl;

import com.fx.bean.UserData;
import com.fx.model.User;
import com.fx.repository.UserRepository;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 9:09 2018/3/20/020
 */
public class UserRepositoryImpl implements UserRepository {

    private Gson gson = new Gson();
    public UserRepositoryImpl(){

    }
    /**
     * 根据城市查找用户
     *
     * @param city
     * @return
     */
    @Override
    public List<User> findUserByCity(String city) {
        List<User> users = getUsers();
        ArrayList<User> usersA = new ArrayList<>();
        for (User user : users) {
            if (city.equals(user.getCity()))
                usersA.add(user);
        }
        return usersA;
    }



    @Override
    public List<User> findAllUsers() {
        List<User> users = getUsers();
        return users;
    }
/**
     * 根据类型查找用户
     *
     * @param type
     * @return
     */
    @Override
    public List<User> findUserByType(String type) {
        List<User> users = getUsers();
        ArrayList<User> usersA = new ArrayList<>();
        for (User user : users) {
            if (type.equals(user.getRole()))
                usersA.add(user);
        }
        return usersA;
    }





    /**
     * 查找到下一个账户应有的id
     *
     * @return
     */
    @Override
    public int findNextId() {
        List<User> users = getUsers();
        int id = 0;
        for (int i = 0; i < users.size() ; i++){
            id = Math.max(users.get(i).getId(),id);
        }
        return id + 1;
    }

    /**
     * 通过id 查找用户
     *
     * @param id
     * @return
     */
    @Override
    public User findUserById(int id) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id)
                return users.get(i);
        }
        return null;
    }

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username))
                return users.get(i);
        }
        return null;
    }

    /**
     * 通过用户名寻找密码
     *
     * @param username
     * @return
     */
    @Override
    public String findPasswordByUsername(String username) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username))
                return users.get(i).getPassword();
        }
        return null;
    }

    /**
     * 创建新的用户
     *
     * @param user
     * @return
     */
    @Override
    public ResultMessage addUser(User user) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername()))
                return ResultMessage.EXIST;
        }
        int id = findNextId();
        user.setId(id);
        users.add(user);
        return flushFile(users);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public ResultMessage updateUser(User user) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (user.getId() == users.get(i).getId())
            {
                users.remove(i);
                users.add(user);
                break;
            }
        }
        return flushFile(users);
    }

    /**
     * 删除用户信息
     *
     * @param user
     * @return
     */
    @Override
    public ResultMessage deleteUser(User user) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (user.getId() == users.get(i).getId())
            {
                users.remove(i);
                flushFile(users);
                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.NOT_EXIST;
    }
    private synchronized ResultMessage flushFile(List<User> users){
        try{
            File file = new File("UserData.txt");
            PrintWriter printWriter;
            printWriter = new PrintWriter(file);
            for (int i = 0; i < users.size() ; i++) {
                String str = gson.toJson(users.get(i));
                printWriter.println(str);
            }
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    private synchronized List<User> getUsers(){
        File file = new File("UserData.txt");
        List<User> users = new ArrayList<>();
        try{
            if (!file.exists())
                file.createNewFile();

            Scanner scanner = new Scanner(file);
            List<String> strings = new ArrayList<>();
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                strings.add(s);
            }
            scanner.close();
            for (int i = 0; i < strings.size(); i++) {
                String s = strings.get(i);
                User user = gson.fromJson(s,User.class);
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
}
