package com.fx.repository.impl;

import com.fx.model.AttributeLabel;
import com.fx.model.User;
import com.fx.repository.UserRepository;
import com.fx.util.DataConst;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 11:10 2018/4/10/010
 */
public class RepositoryHelper<T> {
    private Type type;

    private Gson gson = new Gson();

    public RepositoryHelper(Type t) {
        this.type = t;
    }

    ResultMessage updateLable(int missionID, String username, T t) {
        if (t instanceof AttributeLabel) {
            ((AttributeLabel) t).getFileName();
        }

        return ResultMessage.SUCCESS;
    }

    List<T> getAllLabels(int missionID, String username) {
        File file = getFile(missionID, username);

        if (!file.exists())
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        Scanner scanner;
        List<String> strings = new ArrayList<>();
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                System.out.println(s);
                strings.add(s);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<T> labels = new ArrayList<>();
        for (String s : strings) {
            System.out.println(s);
            T t = gson.fromJson(s, type);
            labels.add(t);
        }
        return labels;
    }

    boolean printAllLabels(int missionID, String username, List<T> labels) {

        List<String> strings = new ArrayList<>();
        for (T t : labels) {
            String s = gson.toJson(t);
            strings.add(s);
        }
        File f = getFile(missionID, username);
        try {

            PrintWriter printWriter = new PrintWriter(f);
            for (String s : strings
                    ) {
                printWriter.println(s);
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private File getFile(int missionID, String username) {
        String dirName = DataConst.FILE_PATH + missionID;
        File dir = new File(dirName);
        if (!dir.exists())
            dir.mkdir();
        String fileName = dirName + "/" + username + ".txt";
        File file = new File(fileName);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return file;
    }

    public ResultMessage initialize(int userid, int missionID) {
        String DataDir = DataConst.FILE_PATH + missionID;
        File dir = new File(DataDir);
        if (!dir.exists())
            dir.mkdir();
        UserRepository userRepository = new UserRepositoryImpl();
        User user = userRepository.findUserById(userid);
        String username = user.getUsername();
        String datafile = DataDir + "/" + username + ".txt";
        File file = new File(datafile);
        try {
            if (!file.exists())
                file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            String imageDir = DataConst.FILE_PATH + "image/" + missionID;
            File dirs = new File(imageDir);
            File[] images = dirs.listFiles();
            String[] filenames = new String[images.length];
            for (int i = 0; i < filenames.length; i++) {
                filenames[i] = images[i].getName();
            }
            String prefix = "{\"fileName\":\"";
            String postfix = "\"}";

            for (int i = 0; i < filenames.length; i++) {
                String json = prefix + filenames[i] + postfix;
                printWriter.println(json);
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultMessage.SUCCESS;
    }
}
