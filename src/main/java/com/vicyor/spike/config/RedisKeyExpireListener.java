package com.vicyor.spike.config;

import com.vicyor.spike.service.SpikeGoodsService;
import com.vicyor.spike.service.SpikeOrderService;
import com.vicyor.spike.task.ExpireOrderTask;
import com.vicyor.spike.task.FinishSpikeGoodsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者:姚克威
 * 时间:2020/3/1 10:00
 **/
@Component
public class RedisKeyExpireListener {
    @Autowired
    SpikeGoodsService spikeGoodsService;
    @Autowired
    SpikeOrderService spikeOrderService;
    @Autowired
    ExecutorService threadPool;
    public void handleMessage(String key){
        System.err.println(key);
        if(key.startsWith("id")){
            key=key.substring(key.indexOf('=')+1);
            threadPool.execute(new FinishSpikeGoodsTask(spikeGoodsService,Long.valueOf(key)));
        }else if(key.startsWith("order")){
            Pattern pattern=Pattern.compile("order-(.*)-(.*)");
            Matcher matcher = pattern.matcher(key);
            Long goodsId=Long.valueOf(matcher.group(1));
            String userId=matcher.group(2);
            //将未支付的订单设置过期
            threadPool.execute(new ExpireOrderTask( spikeOrderService,userId,goodsId));
        }
    }
}
