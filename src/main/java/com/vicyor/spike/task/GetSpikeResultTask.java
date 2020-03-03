package com.vicyor.spike.task;

import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.entity.SpikeOrder;
import com.vicyor.spike.service.SpikeGoodsService;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:姚克威
 * 时间:2020/3/2 23:50
 * 从redis中获取秒杀的用户
 **/
@Data
public class GetSpikeResultTask implements Runnable {
    private Long spikeGoodsId;
    private RedisTemplate<String, Object> redisTemplate;
    private SpikeGoodsService spikeGoodsService;
    private List<String> users = new ArrayList<>();

    @Override
    public void run() {
        SpikeGoods goods = spikeGoodsService.getSpikeGoodsBySpikeGoodsId(spikeGoodsId);
        long startTime = goods.getStartTime().getTime();
        String key = spikeGoodsId + "-list-" + startTime;
        long number = goods.getStockAll() - goods.getStock();
        //lrange key start end  emmm相当于批处理了
        List<Object> usersList = redisTemplate.opsForList().range(key, 0, number-1);
        users.addAll(usersList.stream().map(obj->obj.toString()).collect(Collectors.toList()));
    }

    public GetSpikeResultTask(Long spikeGoodsId, RedisTemplate<String, Object> redisTemplate, SpikeGoodsService spikeGoodsService) {
        this.spikeGoodsId = spikeGoodsId;
        this.redisTemplate = redisTemplate;
        this.spikeGoodsService = spikeGoodsService;
    }
}
