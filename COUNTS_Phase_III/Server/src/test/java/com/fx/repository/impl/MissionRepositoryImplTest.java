package com.fx.repository.impl;

import com.fx.model.Mission;
import com.fx.repository.MissionRepository;
import com.fx.util.ResultMessage;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Description:
 * Created by Hanxinhu at 10:18 2018/4/12/012
 */
public class MissionRepositoryImplTest {

    MissionRepository missionRepository = new MissionRepositoryImpl();
    @Test
    public void addMission() {
        Mission mission = new Mission();
        mission.setBegin("2018/01/02");
        mission.setEnd("2018/02/02");

        ResultMessage message = missionRepository.addMission(mission);

        System.out.println(mission.getID());
        assertEquals(ResultMessage.SUCCESS, message);
        int id = mission.getID();
        assertNotEquals(0, id);
    }

    @Test
    public void updateMission() {
        Mission mission = new Mission();
        mission.setID(2);
        mission.setBegin("2018/01/03");
        mission.setEnd("2018/01/04");
        missionRepository.updateMission(mission);
        Mission missionAfter = missionRepository.findMissionByID(mission.getID());
        System.out.println(mission.getID());
        assertEquals(mission.getBegin(), missionAfter.getBegin());
        assertEquals(mission.getEnd(), missionAfter.getEnd());

    }
    @Ignore
    @Test
    public void deleteMission() {
        ResultMessage message = missionRepository.deleteMission(3);
        assertEquals(ResultMessage.SUCCESS, message);
    }

    @Test
    public void findMissionByID() {
        Mission mission = new Mission();
        mission.setBegin("2018/01/03");
        mission.setEnd("2018/01/04");
        missionRepository.addMission(mission);
        Mission s = missionRepository.findMissionByID(mission.getID());
        assertNotNull(s);
    }

    @Ignore
    @Test
    public void findMissionByBeginAndEnd() {
        Mission mission = new Mission();
        mission.setBegin("2018/01/03");
        mission.setEnd("2018/01/04");
        missionRepository.addMission(mission);
        List<Mission> missions = missionRepository.findMissionByBeginAndEnd("2018/01/02", "2018/01/05");
        System.out.println(missions.size());
        assertNotEquals(0, missions.size());
    }

    @Test
    public void findMissionByRequestorID() {
        Mission mission = new Mission();
        mission.setRequestorID("980626han");
        missionRepository.addMission(mission);
        List<Mission> missions = missionRepository.findMissionByRequestorID("980626han");
        System.out.println(missions.size());
        assertNotEquals(0, missions.size());

    }


    @Test
    public void findMissionByType() {
    }
}