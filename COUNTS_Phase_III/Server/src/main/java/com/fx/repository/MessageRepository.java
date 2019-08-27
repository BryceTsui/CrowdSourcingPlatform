package com.fx.repository;

import com.fx.model.Message;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 15:09 2018/6/10/010
 */
public interface MessageRepository {

    public ResultMessage addMessage(String username, Message message);

    public ResultMessage addMessages(String username, List<Message> messages);

    public ResultMessage updateMessage(String username,int messageID);

    public List<Message> findMessageByUsername(String username);


}
