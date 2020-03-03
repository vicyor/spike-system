package com.vicyor.spike.task;

import com.vicyor.spike.service.SpikeOrderService;
import lombok.Data;

/**
 * 作者:姚克威
 * 时间:2020/3/4 6:52
 * 对过期的订单处理
 **/
@Data
public class ExpireOrderTask implements Runnable {
    private SpikeOrderService spikeOrderService;
    private String userId;
    private Long goodsId;
    @Override
    public void run() {
        spikeOrderService.finishExpiredOrder(userId,goodsId);
    }

    public ExpireOrderTask(SpikeOrderService spikeOrderService, String userId, Long goodsId) {
        this.spikeOrderService = spikeOrderService;
        this.userId = userId;
        this.goodsId = goodsId;
    }
}
