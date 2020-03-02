package com.vicyor.spike.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.service.SpikeGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;
import java.util.List;
import java.util.Set;

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
        List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.incr("abc".getBytes());
                connection.incr("abc".getBytes());
                connection.get("abc".getBytes());
                return null;
            }
        });
        list.stream().forEach(System.out::println);
    }
    @Test
    public void base64Test(){
        String str="abc";
        System.out.println(new String(Base64.getEncoder().encode(str.getBytes()).clone()));
    }
    @Test
    public void redisTest(){
        Object execute = redisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.opsForValue().set("1", "2");
                operations.opsForValue().get("1");
                return null;
            }
        });
        System.out.println(execute);
    }
    @Test
    public void getSpikeUserTest(){
        for(int i=0;i<100;i++){
            System.out.println(redisTemplate.opsForList().leftPop("1-list-1583150641000"));
        }
    }
    @Test
    public void flushAll(){
        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(key->redisTemplate.delete(key));
    }
}
