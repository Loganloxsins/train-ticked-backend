package org.fffd.l23o6.dao;

import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainDao extends JpaRepository<TrainEntity, Long>{
    @Query(value = "select * from train_entity t where t.startStationId=?1 and t.endStationId=?2 and t.date=?3", nativeQuery = true)
    List<TrainEntity> findListTrains(Long startStationId, Long endStationId, String date);

    TrainEntity findByName(String name);
    
}
