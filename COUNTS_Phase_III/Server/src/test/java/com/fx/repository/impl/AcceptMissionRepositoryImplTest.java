package com.fx.repository.impl;

import com.fx.model.AcceptedMission;
import com.fx.repository.AcceptMissionRepository;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Description:
 * Created by Hanxinhu at 18:43 2018/6/15/015
 */
public class AcceptMissionRepositoryImplTest {
    AcceptMissionRepository repository = new AcceptMissionRepositoryImpl();
    @Test
    public void addAcceptMission() {
        AcceptedMission ac = new AcceptedMission();
        ac.setUsername("fjx");
        repository.addAcceptMission(ac);

    }

    @Test
    public void updateAcceptMission() {
        AcceptedMission ac = new AcceptedMission();
        ac.setId(0);
        ac.setFinished(10);
        ac.setUsername("fjx");
        repository.updateAcceptMission(ac);
    }

    @Test
    public void findAcceptMissionByUsername() {
    }

    @Test
    public void getAllAcceptMission() {
    }
}