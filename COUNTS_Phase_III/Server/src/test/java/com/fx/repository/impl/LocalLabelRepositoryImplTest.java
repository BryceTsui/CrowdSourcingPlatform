package com.fx.repository.impl;

import com.fx.bean.Dot;
import com.fx.bean.LocalLabelBean;
import com.fx.repository.LocalLabelRepository;
import com.fx.util.ResultMessage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 * Created by Hanxinhu at 19:50 2018/3/21/021
 */
public class LocalLabelRepositoryImplTest {
    LocalLabelRepository localLabelRepository = new LocalLabelRepositoryImpl();

    @Test
    public void addLocalLabel() {
        List<LocalLabelBean> labels = new ArrayList<>();
        String s = "";
        List<Dot> dots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 100);
            int y = (int) (Math.random() * 100);
            Dot dot = new Dot();
            dot.setX(x);
            dot.setY(y);
            s = s + x + "" + y;

        }
        LocalLabelBean l = new LocalLabelBean();
        l.setLabel(s);
        l.setDotList(dots);
        ResultMessage message = localLabelRepository.addLocalLabel("hxh", "firstMission", "1", null);
        assertEquals(message, ResultMessage.SUCCESS);
    }
}