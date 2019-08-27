package com.fx.repository.impl;

import com.fx.model.AutoMission;
import com.fx.repository.AutoMissionRepository;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 16:09 2018/6/8/008
 */
public class AutoMissionRepositoryImpl implements AutoMissionRepository {
    private static final String filename = "../data/AutoMission.txt";
    Gson gson = new Gson();

    public AutoMissionRepositoryImpl() {
        File file = new File("../data");
        try {
            if (!file.exists())
                file.mkdir();
            File file1 = new File(filename);
            if (!file1.exists())
                file1.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AutoMission> getAllAutoMissions() {
        return readAll();
    }

    @Override
    public AutoMission findAutoMissionByID(int ID) {
        List<AutoMission> missions = readAll();
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() == ID)
                return missions.get(i);
        }
        return null;
    }

    @Override
    public List<AutoMission> findAutoMissionByRequestorID(String requestorID) {
        List<AutoMission> list = new LinkedList<>();
        List<AutoMission> missions = readAll();
        for (int i = 0; i < missions.size(); i++) {
            if (requestorID.equals(missions.get(i).getRequestorID()))
                list.add(missions.get(i));
        }
        return list;
    }

    @Override
    public List<AutoMission> findAutoMissionByType(String type) {
        List<AutoMission> list = new LinkedList<>();
        List<AutoMission> missions = readAll();
        for (int i = 0; i < missions.size(); i++) {
            if (type.equals(missions.get(i).getType()))
                list.add(missions.get(i));
        }
        return list;
    }

    @Override
    public ResultMessage addAutoMission(AutoMission autoMission) {
        List<AutoMission> missions = readAll();
        int maxID = 0;
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() > maxID)
                maxID = missions.get(i).getId();
        }
        maxID += 1;
        autoMission.setId(maxID);
        missions.add(autoMission);
        writeAll(missions);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage deleteAutoMission(int id) {
        List<AutoMission> list = new LinkedList<>();
        List<AutoMission> missions = readAll();
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() == id) {
                missions.remove(i);
                writeAll(missions);
                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.NOT_EXIST;
    }

    private List<AutoMission> readAll() {
        File file = new File(filename);
        List<AutoMission> missions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                AutoMission a = gson.fromJson(s, AutoMission.class);
                missions.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return missions;
    }

    private void writeAll(List<AutoMission> missions) {
        File file = new File(filename);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < missions.size(); i++) {
            String s = gson.toJson(missions.get(i));
            strings.add(s);
        }
        try {
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < strings.size(); i++) {
                pw.println(strings.get(i));
            }
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
