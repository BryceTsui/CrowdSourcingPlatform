package com.fx.repository.impl;

import com.fx.model.ClassificationLabel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Description:
 * Created by Hanxinhu at 11:25 2018/4/10/010
 */
public class RepositoryHelperTest {

    @Test
    public void getAllLabels() {
        RepositoryHelper<ClassificationLabel> repositoryHelper = new RepositoryHelper<>(ClassificationLabel.class);
        List<ClassificationLabel> labels = repositoryHelper.getAllLabels(2, "hanxinhu");
        assertNotNull(labels);
        System.out.println(labels.size());

    }

    @Test
    public void printAllLabels() {
        RepositoryHelper<ClassificationLabel> repositoryHelper = new RepositoryHelper<>(ClassificationLabel.class);
        List<ClassificationLabel> labels = new ArrayList<>();
        labels.add(new ClassificationLabel("1.jpg", 0));
        labels.add(new ClassificationLabel("2.jpg", 0));
        labels.add(new ClassificationLabel("3.jpg", 0));
        labels.add(new ClassificationLabel("4.jpg", 0));
        labels.add(new ClassificationLabel("5.jpg", 0));
        repositoryHelper.printAllLabels(1, "hanxinhu", labels);

    }
}