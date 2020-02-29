package com.vicyor.spike.repository;

import com.vicyor.spike.entity.SpikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 作者:姚克威
 * 时间:2020/2/28 9:45
 **/
@Repository
public interface SpikeOrderRepository extends JpaRepository<SpikeOrder,Long> {
    
}
