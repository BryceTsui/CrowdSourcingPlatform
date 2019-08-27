package com.fx.service;

/**
 * Created by thinkpad on 2018/6/6.
 */
public interface MLService {
    public void predictCaptionLabel(String username,int missionid);

    public void predictCalssificationLabel(String username,int missionid);

    public void predictDetectionLabel(String username,int missionid);
}
