package com.fx.controller;

import com.fx.model.Message;
import com.fx.service.MessageService;
import com.fx.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 14:42 2018/6/10/010
 */
@Controller
@RequestMapping("/counts/message")
@CrossOrigin
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping(
            value = "/getMessage",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}

    )
    @ResponseBody
    public List<Message> getMessageByUsername(String username) {

        return messageService.findMessageByUsername(username);
    }

    /**
     * 更新消息的状态 把未读改为已读
     *
     * @param id 消息的id
     * @return
     */
    @RequestMapping(
            value = "/updateMessage",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}

    )
    @ResponseBody
    public ResultMessage updateMessage(String username, int id) {
        
        return messageService.updateMessage(username, id);
    }
}
