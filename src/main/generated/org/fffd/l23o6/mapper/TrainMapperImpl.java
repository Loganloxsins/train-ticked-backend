package org.fffd.l23o6.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainDetailVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-01T15:14:38+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
public class TrainMapperImpl implements TrainMapper {

    @Override
    public TrainDetailVO toTrainDetailVO(TrainEntity trainEntity) {
        if ( trainEntity == null ) {
            return null;
        }

        TrainDetailVO.TrainDetailVOBuilder trainDetailVO = TrainDetailVO.builder();

        trainDetailVO.id( trainEntity.getId() );
        trainDetailVO.name( trainEntity.getName() );
        trainDetailVO.date( trainEntity.getDate() );
        List<Date> list = trainEntity.getDepartureTimes();
        if ( list != null ) {
            trainDetailVO.departureTimes( new ArrayList<Date>( list ) );
        }
        List<Date> list1 = trainEntity.getArrivalTimes();
        if ( list1 != null ) {
            trainDetailVO.arrivalTimes( new ArrayList<Date>( list1 ) );
        }
        List<String> list2 = trainEntity.getExtraInfos();
        if ( list2 != null ) {
            trainDetailVO.extraInfos( new ArrayList<String>( list2 ) );
        }

        return trainDetailVO.build();
    }

    @Override
    public TrainVO toTrainVO(TrainEntity trainEntity) {
        if ( trainEntity == null ) {
            return null;
        }

        TrainVO.TrainVOBuilder trainVO = TrainVO.builder();

        trainVO.id( trainEntity.getId() );
        trainVO.name( trainEntity.getName() );

        return trainVO.build();
    }

    @Override
    public AdminTrainVO toAdminTrainVO(TrainEntity trainEntity) {
        if ( trainEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Long routeId = null;
        String date = null;
        List<Date> departureTimes = null;
        List<Date> arrivalTimes = null;
        List<String> extraInfos = null;

        id = trainEntity.getId();
        name = trainEntity.getName();
        routeId = trainEntity.getRouteId();
        date = trainEntity.getDate();
        List<Date> list = trainEntity.getDepartureTimes();
        if ( list != null ) {
            departureTimes = new ArrayList<Date>( list );
        }
        List<Date> list1 = trainEntity.getArrivalTimes();
        if ( list1 != null ) {
            arrivalTimes = new ArrayList<Date>( list1 );
        }
        List<String> list2 = trainEntity.getExtraInfos();
        if ( list2 != null ) {
            extraInfos = new ArrayList<String>( list2 );
        }

        AdminTrainVO adminTrainVO = new AdminTrainVO( id, name, routeId, date, departureTimes, arrivalTimes, extraInfos );

        return adminTrainVO;
    }
}
