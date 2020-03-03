package com.vicyor.spike.controller;

import com.vicyor.spike.service.SpikeGoodsService;
import com.vicyor.spike.task.GetSpikeResultTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作者:姚克威
 * 时间:2020/3/2 23:22
 **/
@Controller
@RequestMapping("/spikeOrder")
public class SpikeOrderController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    SpikeGoodsService spikeGoodsService;

    @RequestMapping("/getOrders/{goodsId}")
    @ResponseBody
    public List<String> getOrders(@PathVariable("goodsId") Long goodsId) {
        GetSpikeResultTask getSpikeResultTask = null;
        getSpikeResultTask = new GetSpikeResultTask(goodsId, redisTemplate, spikeGoodsService);
        getSpikeResultTask.run();
        return getSpikeResultTask.getUsers();
    }
}
