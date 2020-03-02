package com.vicyor.spike.service.impl;

import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.repository.SpikeGoodsRepository;
import com.vicyor.spike.service.SpikeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        spikeGoods.stream().forEach(goods -> {
            if (goods.getEndTime() != null) {
                goods.setTimeLimit((goods.getEndTime().getTime() - System.currentTimeMillis()) / 1000);
            }
        });
        return spikeGoods;
    }

    @Override
    public SpikeGoods getSpikeGoodsBySpikeGoodsId(Long spikeGoodsId) {
        Optional<SpikeGoods> optional = repository.findById(spikeGoodsId);
        optional.ifPresent(goods -> {
            Timestamp endTime = goods.getEndTime();
            Timestamp startTime = goods.getStartTime();
            if (endTime != null && startTime != null) {
                goods.setTimeLimit((System.currentTimeMillis() - startTime.getTime()) / 1000);
            }
        });
        return optional.get();
    }

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void openSpike(Long goodsId, Long ttl) {
        Optional<SpikeGoods> optional = repository.findById(goodsId);
        optional.ifPresent(spikeGoods -> {
            Long startTime = System.currentTimeMillis();
            Long endTime = startTime + ttl * 1000;
            spikeGoods.setStartTime(new Timestamp(startTime));
            spikeGoods.setEndTime(new Timestamp(endTime));
            spikeGoods.setStatus(1);
            repository.save(spikeGoods);
            //向redis中插入  spikeGoodsid <-> ""    ttl为ttl
            redisTemplate.opsForValue().set("id=" + spikeGoods.getGoodsId().toString(), "", ttl, TimeUnit.SECONDS);
        });
    }

    @Override
    public void finishSpike(Long spikeGoodsId) {
        Optional<SpikeGoods> optional = repository.findById(spikeGoodsId);
        optional.ifPresent(spikeGoods -> {
            spikeGoods.setStatus(2);
            repository.save(spikeGoods);
        });
    }

}
