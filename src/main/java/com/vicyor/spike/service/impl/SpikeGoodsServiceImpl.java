package com.vicyor.spike.service.impl;

import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.repository.SpikeGoodsRepository;
import com.vicyor.spike.service.SpikeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者:姚克威
 * 时间:2020/2/28 10:49
 **/
@Service
public class SpikeGoodsServiceImpl implements SpikeGoodsService {
    @Autowired
    public SpikeGoodsRepository repository;
    @Autowired
    ExecutorService threadPool;

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
            spikeGoods.setStockAll(spikeGoods.getStock());
            repository.save(spikeGoods);
            //set id=spikeGoodsId stock ttl
            redisTemplate.opsForValue().set("id=" + spikeGoods.getGoodsId().toString(), spikeGoods.getStock(), ttl, TimeUnit.SECONDS);
        });
    }

    @Autowired
    TransactionTemplate transactionTemplate;

    @Override
    public void finishSpike(Long spikeGoodsId) {
        Optional<SpikeGoods> optional = repository.findById(spikeGoodsId);
        optional.ifPresent(spikeGoods -> {
            spikeGoods.setStatus(2);
            spikeGoods.setEndTime(new Timestamp(System.currentTimeMillis()));
            repository.save(spikeGoods);
        });
    }

    AtomicInteger count = new AtomicInteger();

    @Override
    @Transactional
    public void decrementSpikeGoodsStocks(Long spikeGoodsId) {
        //先获取再修改即使开了事务在rr下也没办法解决安全性.
        //rr不是当前读.....
        repository.decrementStockBySpikeGoodsId(spikeGoodsId);
    }

    @Override
    public void saveSpikeGoods(SpikeGoods goods) {
        repository.save(goods);
    }

    @Override
    public void resetAll() {
        SpikeGoods spikeGoods=new SpikeGoods();
        spikeGoods.setGoodsId(1l);
        spikeGoods.setStock(10);
        spikeGoods.setStockAll(10l);
        spikeGoods.setGoodsName("恰恰瓜子");
        repository.save(spikeGoods);
    }
}
