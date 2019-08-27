package com.fx.service;

import com.fx.model.Message;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 15:02 2018/6/10/010
 */

public interface MessageService {
    /**
     * 添加单条信息
     */
    public ResultMessage addMessage(String username, Message message);

    /**
     * 添加多条信息
     *
     * @param username
     * @param messages
     * @return
     */
    public ResultMessage addMessages(String username, List<Message> messages);

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public List<Message> findMessageByUsername(String username);

    /**
     * 更新已读未读
     *
     * @param username
     * @param id
     * @return
     */
    public ResultMessage updateMessage(String username, int id);

}
