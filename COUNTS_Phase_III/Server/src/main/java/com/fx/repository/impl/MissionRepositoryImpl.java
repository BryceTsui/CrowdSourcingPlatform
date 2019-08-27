package com.fx.repository.impl;

import com.fx.model.Mission;
import com.fx.repository.MissionRepository;
import com.fx.util.DataConst;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 22:47 2018/4/10/010
 */
public class MissionRepositoryImpl implements MissionRepository {
    File file;
    Gson gson;
    RepositoryHelper<Mission> helper = new RepositoryHelper<>(Mission.class);
    public MissionRepositoryImpl() {
        // String fileName =  "Mission.txt";
        //String fileName = "E:\\大二下\\软工\\COUNTS_Phase_II\\data\\Mission.txt";
        String fileName = DataConst.FILE_PATH + "Mission.txt";
        file = new File(fileName);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        gson = new Gson();
        System.out.println(file.getAbsolutePath());
    }

    /**
     * 添加Mission
     *
     * @param mission
     * @return
     */
    @Override
    public ResultMessage addMission(Mission mission) {
        int id = findNextID();
        mission.setID(id);
        List<Mission> missions = getAllMission();
        missions.add(mission);
        printAllMission(missions);
        return ResultMessage.SUCCESS;
    }

    /**
     * 更新Mission的信息
     *
     * @param mission
     * @return
     */
    @Override
    public ResultMessage updateMission(Mission mission) {
        List<Mission> missions = getAllMission();
        int id = mission.getID();
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getID() == id) {
                missions.set(i, mission);
            }
        }
        printAllMission(missions);
        return ResultMessage.SUCCESS;
    }

    /**
     * 根据id删除mission
     *
     * @param id
     * @return
     */
    @Override
    public ResultMessage deleteMission(int id) {
        List<Mission> missions = getAllMission();
        for (Mission m : missions) {
            if (m.getID() == id)
                missions.remove(m);
        }
        printAllMission(missions);
        return ResultMessage.SUCCESS;
    }

    /**
     * 根据ID查找对应的任务
     *
     * @param id
     * @return
     */
    @Override
    public Mission findMissionByID(int id) {
        List<Mission> missions = getAllMission();
        for (Mission m : missions) {
            if (m.getID() == id)
                return m;
        }
        return null;
    }

    /**
     * 根据起始日期以及结束日期选择
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Mission> findMissionByBeginAndEnd(String start, String end) {
        List<Mission> missions = getAllMission();
        List<Mission> selected = new ArrayList<>();
        for (Mission m : missions) {
            if (m.getBegin().compareTo(start) >= 0 && m.getEnd().compareTo(end) <= 0)
                selected.add(m);
        }

        return selected;
    }

    /**
     * 根据发布者的id(实际为username)查找发布的任务
     *
     * @param requestorID
     * @return
     */
    @Override
    public List<Mission> findMissionByRequestorID(String requestorID) {
        List<Mission> missions = getAllMission();
        List<Mission> selected = new ArrayList<>();
        System.out.println(missions.size());
        for (Mission m : missions) {
            if (requestorID.equals(m.getRequestorID()))
                selected.add(m);
        }
        return selected;
    }

    /**
     * 根据任务类型查找
     *
     * @param type
     * @return
     */
    @Override
    public List<Mission> findMissionByType(String type) {
        List<Mission> missions = getAllMission();
        List<Mission> selected = new ArrayList<>();
        for (Mission m : missions) {
            if (type.equals(m.getType()))
                selected.add(m);
        }
        return selected;
    }

    /**
     * 用于用户接受任务后初始化类
     *
     * @param userid
     * @param missionname
     * @return
     */
    @Override
    public ResultMessage initialize(int userid, int missionname) {
        helper.initialize(userid, missionname);
        return ResultMessage.SUCCESS;
    }

    private int findNextID() {
        List<Mission> missions = getAllMission();
        int max = missions.size() == 0 ? 1 : missions.get(0).getID();
        for (Mission m :
                missions) {
            if (max < m.getID())
                max = m.getID();
        }
        return max + 1;
    }
    @Override
    public synchronized List<Mission> getAllMission() {
        List<Mission> missions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            List<String> strs = new ArrayList<>();
            while (scanner.hasNextLine()) {
                strs.add(scanner.nextLine());
            }
            scanner.close();

            for (String s : strs) {
                Mission mission = gson.fromJson(s, Mission.class);
                missions.add(mission);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return missions;
    }

    private void printAllMission(List<Mission> missions) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for (Mission m : missions) {
                String s = gson.toJson(m);
                printWriter.println(s);
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
