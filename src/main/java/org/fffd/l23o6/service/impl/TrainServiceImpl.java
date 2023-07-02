package org.fffd.l23o6.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.fffd.l23o6.dao.RouteDao;
import org.fffd.l23o6.dao.TrainDao;
import org.fffd.l23o6.mapper.StationMapper;
import org.fffd.l23o6.mapper.TrainMapper;
import org.fffd.l23o6.pojo.entity.RouteEntity;
import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.enum_.TrainType;
import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;
import org.fffd.l23o6.pojo.vo.train.TicketInfo;
import org.fffd.l23o6.pojo.vo.train.TrainDetailVO;
import org.fffd.l23o6.service.TrainService;
import org.fffd.l23o6.util.strategy.train.GSeriesSeatStrategy;
import org.fffd.l23o6.util.strategy.train.KSeriesSeatStrategy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.github.lyc8503.spring.starter.incantation.exception.BizException;
import io.github.lyc8503.spring.starter.incantation.exception.CommonErrorType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainDao trainDao;
    private final RouteDao routeDao;

    private final double SPEED = 300;

    @Override
    public TrainDetailVO getTrain(Long trainId) {
        TrainEntity train = trainDao.findById(trainId).get();
        RouteEntity route = routeDao.findById(train.getRouteId()).get();
        return TrainDetailVO.builder().id(trainId).date(train.getDate()).name(train.getName())
                .stationIds(route.getStationIds()).arrivalTimes(train.getArrivalTimes())
                .departureTimes(train.getDepartureTimes()).extraInfos(train.getExtraInfos()).build();
    }

    @Override
    public List<TrainVO> listTrains(Long startStationId, Long endStationId, String date) {
        // TODO
        // First, get all routes contains [startCity, endCity]
        // Then, Get all trains on that day with the wanted routes
        List<RouteEntity> routeEntities=routeDao.findAll();
        Long routeId;
        List<TrainVO> trainVOS=new ArrayList<>();
        for(RouteEntity routeEntity:routeEntities){
            List<Long> stationIds=routeEntity.getStationIds();
            int i=stationIds.indexOf(startStationId);
            int j=stationIds.indexOf(endStationId);
            if(i!=-1&&j!=-1){
                if(i<j){
                    routeId=routeEntity.getId();
                    List<TrainEntity> trainEntities=trainDao.findByRouteIdAndDate(routeId, date);
                    for(TrainEntity trainEntity:trainEntities){
                        TrainVO trainVO=TrainMapper.INSTANCE.toTrainVO(trainEntity,startStationId,endStationId);
                        Date departureTime=trainEntity.getDepartureTimes().get(i);
                        Date arrivalTime=trainEntity.getArrivalTimes().get(j);
                        trainVO.setDepartureTime(departureTime);
                        trainVO.setArrivalTime(arrivalTime);
                        float hours= (float) (arrivalTime.getTime() - departureTime.getTime()) /(60 * 60 * 1000);
                        List<TicketInfo> ticketInfos=new ArrayList<>();
                        switch (trainEntity.getTrainType()) {
                            case HIGH_SPEED:
                                int business_seat_price=(int) Math.round(1.455*hours*SPEED/2);
                                int first_class_price=(int) Math.round(0.775*hours*SPEED/2);
                                int second_class_price=(int) Math.round(0.485*hours*SPEED/2);
                                int g_no_seat_price=(int) Math.round(0.485*hours*SPEED/2);
                                TicketInfo businessSeat = new TicketInfo("商务座", 3, business_seat_price);
                                ticketInfos.add(businessSeat);
                                TicketInfo first_class = new TicketInfo("一等座", 12, first_class_price);
                                ticketInfos.add(first_class);
                                TicketInfo second_class = new TicketInfo("二等座", 15, second_class_price);
                                ticketInfos.add(second_class);
                                TicketInfo g_no_seat = new TicketInfo("无座", 30, g_no_seat_price);
                                ticketInfos.add(g_no_seat);
                                break;
                            case NORMAL_SPEED:
                                int soft_sleeper_price=(int) Math.round(0.37026*hours*SPEED/4);
                                int hard_sleeper_price=(int) Math.round(0.30855*hours*SPEED/4);
                                int soft_seat_price=(int) Math.round(0.2057*hours*SPEED/4);
                                int hard_seat_price=(int) Math.round(0.1542*hours*SPEED/4);
                                int k_no_seat_price=(int) Math.round(0.1542*hours*SPEED/4);
                                TicketInfo soft_sleeper = new TicketInfo("软卧",8,soft_sleeper_price);
                                ticketInfos.add(soft_sleeper);
                                TicketInfo hard_sleeper = new TicketInfo("硬卧",12,hard_sleeper_price);
                                ticketInfos.add(hard_sleeper);
                                TicketInfo soft_seat = new TicketInfo("软座",16,soft_seat_price);
                                ticketInfos.add(soft_seat);
                                TicketInfo hard_seat = new TicketInfo("硬座",20,hard_seat_price);
                                ticketInfos.add(hard_seat);
                                TicketInfo k_no_seat = new TicketInfo("无座",30,k_no_seat_price);
                                ticketInfos.add(k_no_seat);
                                break;
                            default:
                                break;
                        }
                        trainVO.setTicketInfo(ticketInfos);
                        trainVOS.add(trainVO);
                    }
                }
            }
        }

        return trainVOS;
    }

    @Override
    public List<AdminTrainVO> listTrainsAdmin() {
        return trainDao.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
                .map(TrainMapper.INSTANCE::toAdminTrainVO).collect(Collectors.toList());
    }

    @Override
    public void addTrain(String name, Long routeId, TrainType type, String date, List<Date> arrivalTimes,
            List<Date> departureTimes) {
        TrainEntity entity = TrainEntity.builder().name(name).routeId(routeId).trainType(type)
                .date(date).arrivalTimes(arrivalTimes).departureTimes(departureTimes).build();
        RouteEntity route = routeDao.findById(routeId).get();
        if (route.getStationIds().size() != entity.getArrivalTimes().size()
                || route.getStationIds().size() != entity.getDepartureTimes().size()) {
            throw new BizException(CommonErrorType.ILLEGAL_ARGUMENTS, "列表长度错误");
        }
        entity.setExtraInfos(new ArrayList<String>(Collections.nCopies(route.getStationIds().size(), "预计正点")));
        switch (entity.getTrainType()) {
            case HIGH_SPEED:
                entity.setSeats(GSeriesSeatStrategy.INSTANCE.initSeatMap(route.getStationIds().size()));
                break;
            case NORMAL_SPEED:
                entity.setSeats(KSeriesSeatStrategy.INSTANCE.initSeatMap(route.getStationIds().size()));
                break;
        }
        trainDao.save(entity);
    }

    @Override
    public void changeTrain(Long id, String name, Long routeId, TrainType type, String date, List<Date> arrivalTimes,
                            List<Date> departureTimes) {
        TrainEntity entity=trainDao.findById(id).get();
        RouteEntity route=routeDao.findById(routeId).get();
        if (route.getStationIds().size() != entity.getArrivalTimes().size()
                || route.getStationIds().size() != entity.getDepartureTimes().size()) {
            throw new BizException(CommonErrorType.ILLEGAL_ARGUMENTS, "列表长度错误");
        }
        entity.setName(name);
        entity.setRouteId(routeId);
        entity.setTrainType(type);
        entity.setDate(date);
        entity.setArrivalTimes(arrivalTimes);
        entity.setDepartureTimes(departureTimes);

        entity.setExtraInfos(new ArrayList<String>(Collections.nCopies(route.getStationIds().size(), "预计正点")));
        switch (type) {
            case HIGH_SPEED:
                entity.setSeats(GSeriesSeatStrategy.INSTANCE.initSeatMap(route.getStationIds().size()));
                break;
            case NORMAL_SPEED:
                entity.setSeats(KSeriesSeatStrategy.INSTANCE.initSeatMap(route.getStationIds().size()));
                break;
        }
        trainDao.save(entity);

    }

    @Override
    public void deleteTrain(Long id) {
        trainDao.deleteById(id);
    }
}
