package com.vicyor.spike.repository;

import com.vicyor.spike.entity.SpikeGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SpikeGoodsRepository extends JpaRepository<SpikeGoods,Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "update spike_goods set stock=stock-1 where goods_id=?1",nativeQuery = true)
    int decrementStockBySpikeGoodsId(Long id);

}
