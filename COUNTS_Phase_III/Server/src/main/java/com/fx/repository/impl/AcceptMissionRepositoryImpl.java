package com.fx.repository.impl;

import com.fx.model.AcceptedMission;
import com.fx.repository.AcceptMissionRepository;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 17:37 2018/6/11/011
 */
public class AcceptMissionRepositoryImpl implements AcceptMissionRepository {
    private static final String dir = "../data/AcceptMission";
    Gson gson = new Gson();

    public AcceptMissionRepositoryImpl() {
        File file = new File(dir);
        if (!file.exists())
            file.mkdir();
    }

    @Override
    public ResultMessage addAcceptMission(AcceptedMission acceptedMission) {
        String username = acceptedMission.getUsername();
        int id = acceptedMission.getId();
        List<AcceptedMission> missions = findAcceptMissionByUsername(username);
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() == id)
                return ResultMessage.EXIST;
        }
        missions.add(acceptedMission);
        write(missions, username);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage updateAcceptMission(AcceptedMission acceptedMission) {
        String username = acceptedMission.getUsername();
        int id = acceptedMission.getId();
        List<AcceptedMission> missions = findAcceptMissionByUsername(username);
        System.out.println(acceptedMission.getId());
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() == id) {
                missions.set(i, acceptedMission);
                write(missions, username);
                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.NOT_EXIST;
    }

    @Override
    public List<AcceptedMission> findAcceptMissionByUsername(String username) {
        String filename = getFilename(username);
        ArrayList<AcceptedMission> missions = new ArrayList<>();
        File file = new File(filename);
        try {
            if (!file.exists())
                file.createNewFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                AcceptedMission acceptedMission = gson.fromJson(s, AcceptedMission.class);
                missions.add(acceptedMission);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return missions;
    }

    private void write(List<AcceptedMission> missions, String username) {
        String filename = getFilename(username);
        try {
            File file = new File(filename);
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < missions.size(); i++) {
                String s = gson.toJson(missions.get(i));
                pw.println(s);
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 得到所有的acceptMission用户协调分析
     *
     * @return
     */
    @Override
    public List<AcceptedMission> getAllAcceptMission() {
        File file = new File(dir);
        File[] allFiles = file.listFiles();
        ArrayList<AcceptedMission> acceptedMissions = new ArrayList<>();
        for (int i = 0; i < allFiles.length; i++) {
            String username = allFiles[i].getName().split(".")[0];
            List<AcceptedMission> missions = findAcceptMissionByUsername(username);
            acceptedMissions.addAll(missions);
        }
        return acceptedMissions;
    }

    private String getFilename(String username) {
        return dir + "/" + username + ".txt";
    }

}
