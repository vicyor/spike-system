package com.vicyor.spike.service;

import com.vicyor.spike.entity.SpikeGoods;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2020/2/28 10:48
 **/
public interface SpikeGoodsService {
    List<SpikeGoods> getAllSpikeGoods();

    SpikeGoods getSpikeGoodsBySpikeGoodsId(Long spikeGoodsId);

    void openSpike(Long goodsId, Long ttl);

    void finishSpike(Long spikeGoodsId);

    void decrementSpikeGoodsStocks(Long spikeGoodsId);

    void saveSpikeGoods(SpikeGoods goods);


    void resetAll();
}
