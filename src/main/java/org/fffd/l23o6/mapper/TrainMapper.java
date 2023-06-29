package org.fffd.l23o6.mapper;

import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.entity.RouteEntity;
import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper
public interface TrainMapper {
    TrainMapper INSTANCE = Mappers.getMapper(TrainMapper.class);

    @Mapping(source = "TrainEntity.trainType.text", target = "trainType")
    AdminTrainVO toAdminTrainVO(TrainEntity TrainEntity);

    @Mapping(source = "TrainEntity.trainType.text", target = "trainType")
    @Mapping(source = "routeId", target = "startStationId")
    @Mapping(source = "routeId", target = "endStationId")
    @Mapping(source = "departureTimes", target = "departureTime", qualifiedByName = "getFirstDate")
    @Mapping(source = "arrivalTimes", target = "arrivalTime", qualifiedByName = "getFirstDate")
    @Mapping(target = "ticketInfo", ignore = true)
    TrainVO toTrainVO(TrainEntity TrainEntity);

    @Named("getFirstDate")
    default Date getFirstDate(List<Date> dates) {
        if (dates != null && !dates.isEmpty()) {
            return dates.get(0);
        }
        return null;
    }

    @Named("getEndStationId")
    default Long getEndStationId(@Context Long endStationId) {
        return endStationId;
    }

}

