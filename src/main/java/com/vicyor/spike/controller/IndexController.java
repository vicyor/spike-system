package com.vicyor.spike.controller;

import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.service.SpikeGoodsService;
import com.vicyor.spike.service.SpikeOrderService;
import com.vicyor.spike.task.FinishSpikeGoodsTask;
import com.vicyor.spike.task.GenerateOrdersTask;
import com.vicyor.spike.task.StockDecrementTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作者:姚克威
 * 时间:2020/3/1 8:50
 **/
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    SpikeGoodsService spikeGoodsService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //开启秒杀
    @PostMapping("/openSpike")
    @ResponseBody
    public String openSpike(@RequestParam("spikeGoodsId") Long spikeGoodsId,
                            @RequestParam("ttl") Long ttl
    ) {
        spikeGoodsService.openSpike(spikeGoodsId, ttl);
        return "success";
    }

    /**
     * 接口防刷
     */
    @GetMapping("/token")
    @ResponseBody
    public String getToken(HttpSession session) {
        String digestStr = "";
        digestStr = updateToken(session );
        return digestStr;
    }

    private String updateToken(HttpSession session ) {
        byte[] digest = Base64.getEncoder().encode(( System.currentTimeMillis()+"").getBytes());
        session.setAttribute("token", new String(digest));
        return session.getAttribute("token").toString();
    }

    @PostMapping("/spike")
    @ResponseBody
    public String spike(@RequestParam("goodsId") Long goodsId,
                        @RequestParam("token") String token,
                        HttpSession session
    ) {
        String result = "";
        String sessionToken = session.getAttribute("token").toString();
        SpikeGoods spikeGoods = spikeGoodsService.getSpikeGoodsBySpikeGoodsId(goodsId);
        if (!sessionToken.equals(token)) {
            result = "操作错误";
        } else {
            //更新token
            updateToken(session);
            HashMap map = redisTemplate.execute(new SessionCallback<HashMap>() {
                @Override
                public HashMap execute(RedisOperations operations) throws DataAccessException {

                    HashMap<String, Object> map = new HashMap<>();
                    //key ->  goodsId-list-startTime
                    String key = goodsId + "-" + "list" + "-" + spikeGoods.getStartTime().getTime();
                    //value ->sessionId
                    String value = session.getId();
                    //lpush key value
                    operations.opsForList().leftPush(key, value);
                    //incr
                    key = "id=" + goodsId;
                    Long count = operations.opsForValue().increment(key, Integer.valueOf(-1));
                    map.put("count", count);
                    Long expire = operations.getExpire(("id=" + goodsId));
                    map.put("ttl", expire);
                    //将订单也放入到redis中
                    operations
                            .opsForValue()
                            .set("order-"+goodsId+"-"+value,"100RMB",15*60, TimeUnit.SECONDS);
                    return map;
                }
            });
            Long count= (Long) map.get("count");
            Long ttl= (Long) map.get("ttl");
            if (count< 0) {
                if (ttl <= 0) {
                    result = "很抱歉，活动已结束";
                } else {
                    result = "很抱歉，商品已售空";
                    threadPool.execute(new FinishSpikeGoodsTask(spikeGoodsService, goodsId));
                }
            } else {
                result = "恭喜你,你是第" + (spikeGoods.getStockAll() - count) + "个幸运用户";
                //异步执行减库存任务
                threadPool.execute(new StockDecrementTask(goodsId, spikeGoodsService));
                //异步执行下订单操作
                threadPool.execute(new GenerateOrdersTask(session.getId(),goodsId,0,spikeOrderService));
            }
        }
        return result;
    }
    @Autowired
    SpikeOrderService spikeOrderService;
    @Autowired
    ExecutorService threadPool;
    @PostMapping("/reset")
    @ResponseBody
    public void resetSytem(){
        spikeGoodsService.resetAll();
    }
}
