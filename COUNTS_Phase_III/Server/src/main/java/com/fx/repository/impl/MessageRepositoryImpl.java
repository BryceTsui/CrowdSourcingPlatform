package com.fx.repository.impl;

import com.fx.model.Message;
import com.fx.repository.MessageRepository;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 15:11 2018/6/10/010
 */
public class MessageRepositoryImpl implements MessageRepository {
    private static final String dirname = "../data/message";

    Gson gson = new Gson();

    public MessageRepositoryImpl() {
        File file = new File(dirname);
        try {
            if (!file.exists())
                file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultMessage addMessage(String username, Message message) {
        int maxID = 0;
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            if (maxID < messages.get(i).getId())
                maxID = messages.get(i).getId();
        }
        message.setId(maxID);
        messages.add(message);
        writeAll(username, messages);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addMessages(String username, List<Message> messages) {
        int maxID = 0;
        List<Message> oldMessages = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            if (maxID < oldMessages.get(i).getId())
                maxID = oldMessages.get(i).getId();
        }
        maxID++;
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).setId(maxID++);
        }
        oldMessages.addAll(messages);
        writeAll(username, oldMessages);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage updateMessage(String username, int messageID) {
        List<Message> messages = findMessageByUsername(username);
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message.getId() == messageID) {
                message.setRead(true);
                writeAll(username, messages);
                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.NOT_EXIST;
    }

    @Override
    public List<Message> findMessageByUsername(String username) {
        String filename = getFilename(username);
        File file = new File(filename);
        List<Message> messages = new ArrayList<>();
        try {
            if (!file.exists())
                file.createNewFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                Message message = gson.fromJson(s, Message.class);
                messages.add(message);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void writeAll(String username, List<Message> messages) {
        String filename = getFilename(username);
        File file = new File(filename);
        try {
            PrintWriter pw = new PrintWriter(file);
            for (Message i : messages) {
                pw.println(gson.toJson(i));
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilename(String username) {
        return dirname + "/" + username + ".txt";
    }
}
