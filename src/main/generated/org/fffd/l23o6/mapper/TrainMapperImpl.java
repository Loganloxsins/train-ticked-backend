package org.fffd.l23o6.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.enum_.TrainType;
import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-03T15:54:20+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
public class TrainMapperImpl implements TrainMapper {

    @Override
    public AdminTrainVO toAdminTrainVO(TrainEntity TrainEntity) {
        if ( TrainEntity == null ) {
            return null;
        }

        String trainType = null;
        Long id = null;
        String name = null;
        Long routeId = null;
        String date = null;
        List<Date> departureTimes = null;
        List<Date> arrivalTimes = null;
        List<String> extraInfos = null;

        trainType = trainEntityTrainTypeText( TrainEntity );
        id = TrainEntity.getId();
        name = TrainEntity.getName();
        routeId = TrainEntity.getRouteId();
        date = TrainEntity.getDate();
        List<Date> list = TrainEntity.getDepartureTimes();
        if ( list != null ) {
            departureTimes = new ArrayList<Date>( list );
        }
        List<Date> list1 = TrainEntity.getArrivalTimes();
        if ( list1 != null ) {
            arrivalTimes = new ArrayList<Date>( list1 );
        }
        List<String> list2 = TrainEntity.getExtraInfos();
        if ( list2 != null ) {
            extraInfos = new ArrayList<String>( list2 );
        }

        AdminTrainVO adminTrainVO = new AdminTrainVO( id, name, trainType, routeId, date, departureTimes, arrivalTimes, extraInfos );

        return adminTrainVO;
    }

    @Override
    public TrainVO toTrainVO(TrainEntity TrainEntity, Long startStationId, Long endStationId) {
        if ( TrainEntity == null && startStationId == null && endStationId == null ) {
            return null;
        }

        TrainVO.TrainVOBuilder trainVO = TrainVO.builder();

        if ( TrainEntity != null ) {
            trainVO.trainType( trainEntityTrainTypeText( TrainEntity ) );
            trainVO.id( TrainEntity.getId() );
            trainVO.name( TrainEntity.getName() );
        }
        trainVO.startStationId( startStationId );
        trainVO.endStationId( endStationId );

        return trainVO.build();
    }

    private String trainEntityTrainTypeText(TrainEntity trainEntity) {
        if ( trainEntity == null ) {
            return null;
        }
        TrainType trainType = trainEntity.getTrainType();
        if ( trainType == null ) {
            return null;
        }
        String text = trainType.getText();
        if ( text == null ) {
            return null;
        }
        return text;
    }
}
