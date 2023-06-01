package org.fffd.l23o6.service;

import java.util.Date;
import java.util.List;

import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainDetailVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;

public interface TrainService {
    public TrainDetailVO getTrain(Long trainId);
    public List<TrainVO> listTrains(Long startStationId, Long endStationId, String date);
    public List<AdminTrainVO> listTrainsAdmin();
    public void addTrain(String name, Long routeId, String type, String date, List<Date> arrivalTimes, List<Date> departureTimes, List<String> extraInfos);
    public void editTrain(Long trainId, String name, Long routeId, String type, String date, List<Date> arrivalTimes, List<Date> departureTimes, List<String> extraInfos);
    public void deleteTrain(Long trainId);
}
