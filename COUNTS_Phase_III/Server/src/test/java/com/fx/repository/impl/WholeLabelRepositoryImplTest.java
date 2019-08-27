package com.fx.repository.impl;

import com.fx.repository.WholeLabelRepository;
import com.fx.util.ResultMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 * Created by Hanxinhu at 19:51 2018/3/21/021
 */
public class WholeLabelRepositoryImplTest {
    WholeLabelRepository wholeLabelRepository = new WholeLabelRepositoryImpl();

    @Test
    public void addWholeLabel() {
        String s = "";
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 10);
            s += x;
        }
        ResultMessage message = wholeLabelRepository.addWholeLabel("hxh", "secode", "2", s);
        assertEquals(ResultMessage.SUCCESS, message);
    }

    @Test
    public void updateWholeLabel() {
        String s = "";
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 10);
            s += x;
        }
        ResultMessage message = wholeLabelRepository.updateWholeLabel("hxh", "secode", "2", s);
        assertEquals(ResultMessage.SUCCESS, message);
    }

    @Test
    public void getWholeLabel() {
        String s = "";
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 10);
            s += x;
        }
        ResultMessage message = wholeLabelRepository.addWholeLabel(s, "secode", "2", s);
        assertEquals(ResultMessage.SUCCESS, message);
        String answer = wholeLabelRepository.getWholeLabelByUserIDAndLocation(s, "secode", "2");
        assertEquals(s, answer);
    }
}