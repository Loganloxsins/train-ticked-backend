package org.fffd.l23o6.service.impl;


import io.github.lyc8503.spring.starter.incantation.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.fffd.l23o6.dao.RouteDao;
import org.fffd.l23o6.dao.TrainDao;
import org.fffd.l23o6.exception.BizError;
import org.fffd.l23o6.mapper.RouteMapper;
import org.fffd.l23o6.mapper.TrainMapper;
import org.fffd.l23o6.pojo.entity.RouteEntity;
import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainDetailVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;
import org.fffd.l23o6.service.TrainService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainDao trainDao;

    @Override
    public TrainDetailVO getTrain(Long trainId){
        return TrainMapper.INSTANCE.toTrainDetailVO(trainDao.findById(trainId).get());
    }

    @Override
    public List<TrainVO> listTrains(Long startStationId, Long endStationId, String date){
        List<TrainEntity> trainEntities=trainDao.findListTrains(startStationId, endStationId, date);
        return trainEntities.stream().map(TrainMapper.INSTANCE::toTrainVO).collect(Collectors.toList());
    }

    @Override
    public List<AdminTrainVO> listTrainsAdmin(){
        return trainDao.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(TrainMapper.INSTANCE::toAdminTrainVO).collect(Collectors.toList());
    }

    @Override
    public void addTrain(String name, Long routeId, String type, String date, List<Date> arrivalTimes, List<Date> departureTimes, List<String> extraInfos){
        TrainEntity entity=trainDao.findByName(name);
        if(entity!=null){
            throw new BizException(BizError.TRAINNAME_EXISTS);
        }
        trainDao.save(TrainEntity.builder().name(name).routeId(routeId).trainType(TrainEntity.TrainType.fromString(type))
                .date(date).arrivalTimes(arrivalTimes).departureTimes(departureTimes).extraInfos(extraInfos).build());
    }

    @Override
    public void editTrain(Long trainId, String name, Long routeId, String type, String date, List<Date> arrivalTimes, List<Date> departureTimes, List<String> extraInfos){
        TrainEntity entity=trainDao.findById(trainId).get();
        entity.setName(name);
        entity.setRouteId(routeId);
        entity.setTrainType(TrainEntity.TrainType.fromString(type));
        entity.setDate(date);
        entity.setArrivalTimes(arrivalTimes);
        entity.setDepartureTimes(departureTimes);
        entity.setExtraInfos(extraInfos);
        trainDao.save(entity);
    }

    @Override
    public void deleteTrain(Long trainId){
        TrainEntity entity=trainDao.findById(trainId).get();
        trainDao.delete(entity);
    }
}
