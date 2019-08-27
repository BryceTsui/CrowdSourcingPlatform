package com.fx.repository.impl;

import com.fx.model.Message;
import com.fx.repository.MessageRepository;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Description:
 * Created by Hanxinhu at 18:46 2018/6/15/015
 */
public class MessageRepositoryImplTest {
    MessageRepository repository = new MessageRepositoryImpl();
    @Test
    public void addMessage() {
        repository.addMessage("hxh",new Message("hxh",1,"Classification"));
    }

    @Test
    public void addMessages() {
    }

    @Test
    public void updateMessage() {
    }

    @Test
    public void findMessageByUsername() {
    }

    @Test
    public void writeAll() {
    }

    @Test
    public void getFilename() {
    }
}