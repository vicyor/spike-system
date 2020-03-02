package com.vicyor.spike.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.service.SpikeGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpikeGoodsServiceImplTest {
    @Autowired
    SpikeGoodsService spikeGoodsService;
    ObjectMapper jsonMapper=new ObjectMapper();
    @Test
    public void getAllSpikeGoods() {
        List<SpikeGoods> allSpikeGoods = spikeGoodsService.getAllSpikeGoods();
        allSpikeGoods.stream().forEach(goods->{
            try {
                System.out.println(jsonMapper.writeValueAsString(goods));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
    @Autowired
    RedisTemplate<String,Object>redisTemplate;
    @Test
    public void redisTemplateTest(){
        redisTemplate.opsForValue().set("test","test");
        Object test = redisTemplate.opsForValue().get("test");
        System.out.println(test);
    }
}
