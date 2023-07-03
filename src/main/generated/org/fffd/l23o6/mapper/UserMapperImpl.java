package org.fffd.l23o6.mapper;

import javax.annotation.processing.Generated;
import org.fffd.l23o6.pojo.entity.UserEntity;
import org.fffd.l23o6.pojo.vo.user.UserVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-03T21:57:56+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserVO toUserVO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserVO.UserVOBuilder userVO = UserVO.builder();

        userVO.username( userEntity.getUsername() );
        userVO.name( userEntity.getName() );
        userVO.phone( userEntity.getPhone() );
        userVO.idn( userEntity.getIdn() );
        userVO.type( userEntity.getType() );
        userVO.mileagePoints( userEntity.getMileagePoints() );
        if ( userEntity.getIsMember() != null ) {
            userVO.isMember( userEntity.getIsMember() );
        }

        return userVO.build();
    }
}
