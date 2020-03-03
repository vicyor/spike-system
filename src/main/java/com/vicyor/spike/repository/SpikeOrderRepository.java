package com.vicyor.spike.repository;

import com.vicyor.spike.entity.SpikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 作者:姚克威
 * 时间:2020/2/28 9:45
 **/
@Repository
public interface SpikeOrderRepository extends JpaRepository<SpikeOrder,Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "update spike_order set status=2 where goods_id=?2 and user_id=?1",nativeQuery = true)
    int updateOrderStatus(String userId, Long goodsId);
}
