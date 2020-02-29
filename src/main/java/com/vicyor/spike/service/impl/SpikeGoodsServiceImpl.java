package com.vicyor.spike.service.impl;

import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.repository.SpikeGoodsRepository;
import com.vicyor.spike.service.SpikeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2020/2/28 10:49
 **/
@Service
public class SpikeGoodsServiceImpl implements SpikeGoodsService {
    @Autowired
    public SpikeGoodsRepository repository;
    @Override
    public List<SpikeGoods> getAllSpikeGoods() {
        List<SpikeGoods> spikeGoods = repository.findAll();
        spikeGoods.stream().forEach(goods->{
            if(goods.getEndTime()!=null){
                goods.setTimeLimit((goods.getEndTime().getTime()-System.currentTimeMillis())/1000);
            }
        });
        return spikeGoods;
    }
}
