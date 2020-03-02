package com.vicyor.spike.config;

import com.vicyor.spike.service.SpikeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 作者:姚克威
 * 时间:2020/3/1 10:00
 **/
@Component
public class RedisKeyExpireListener {
    @Autowired
    SpikeGoodsService spikeGoodsService;
    public void handleMessage(String key){
        System.err.println(key);
        if(key.startsWith("id")){
            key=key.substring(key.indexOf('=')+1);
            spikeGoodsService.finishSpike(Long.valueOf(key));
        }
    }
}
