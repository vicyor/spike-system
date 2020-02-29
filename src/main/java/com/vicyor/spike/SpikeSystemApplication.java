package com.vicyor.spike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 作者:姚克威
 * 时间:2020/2/27 18:28
 * 思路:
 *   从mysql中获取商品及数量传入redis中.
 *   put goods_1 100
 *   用户秒杀时候
 *   multi
 *    incr  goods_1 -1 //库存
 *    lpush goods_1 userid //记录用户id
 *  4s秒后
 *   lrange goods_1 0 100 可以获取秒杀的用户结果
 *   可以通过semaphore控制流量
 **/
@SpringBootApplication
@EnableJpaRepositories
public class SpikeSystemApplication {
    public static void main(String[] args) {
        //启动项目
        SpringApplication.run(SpikeSystemApplication.class, args);
    }
}
