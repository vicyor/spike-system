package com.vicyor.spike.config;

import com.vicyor.spike.service.SpikeGoodsService;
import com.vicyor.spike.task.FinishSpikeGoodsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * 作者:姚克威
 * 时间:2020/3/1 10:00
 **/
@Component
public class RedisKeyExpireListener {
    @Autowired
    SpikeGoodsService spikeGoodsService;
    @Autowired
    ExecutorService threadPool;
    public void handleMessage(String key){
        System.err.println(key);
        if(key.startsWith("id")){
            key=key.substring(key.indexOf('=')+1);
            threadPool.execute(new FinishSpikeGoodsTask(spikeGoodsService,Long.valueOf(key)));
        }
    }
}
