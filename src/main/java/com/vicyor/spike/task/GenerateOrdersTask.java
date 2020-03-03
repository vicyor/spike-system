package com.vicyor.spike.task;

import com.vicyor.spike.entity.SpikeOrder;
import com.vicyor.spike.service.SpikeOrderService;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 作者:姚克威
 * 时间:2020/3/3 10:37
 **/
@Data
public class GenerateOrdersTask implements Runnable {
    private String userId;
    private Long goodsId;
    private Integer state;
    private SpikeOrderService spikeOrderService;
    @Override
    public void run() {
        SpikeOrder order=new SpikeOrder();
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setState(state);
        spikeOrderService.saveSpikeOrder(order);
    }

    public GenerateOrdersTask(String userId, Long goodsId, Integer state, SpikeOrderService spikeOrderService) {
        this.userId = userId;
        this.goodsId = goodsId;
        this.state = state;
        this.spikeOrderService = spikeOrderService;
    }
}
