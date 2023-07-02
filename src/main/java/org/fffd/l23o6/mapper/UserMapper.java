package org.fffd.l23o6.mapper;

import org.fffd.l23o6.pojo.entity.UserEntity;
import org.fffd.l23o6.pojo.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "mileagePoints", source = "userEntity.mileagePoints")
    UserVO toUserVO(UserEntity userEntity);
}