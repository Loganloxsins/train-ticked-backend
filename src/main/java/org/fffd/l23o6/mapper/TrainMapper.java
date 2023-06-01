package org.fffd.l23o6.mapper;

import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.vo.train.AdminTrainVO;
import org.fffd.l23o6.pojo.vo.train.TrainDetailVO;
import org.fffd.l23o6.pojo.vo.train.TrainVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainMapper {

    TrainMapper INSTANCE = Mappers.getMapper(TrainMapper.class);

    TrainDetailVO toTrainDetailVO(TrainEntity trainEntity);

    TrainVO toTrainVO(TrainEntity trainEntity);

    AdminTrainVO toAdminTrainVO(TrainEntity trainEntity);
}

