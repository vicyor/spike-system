package com.vicyor.spike.task;

import com.vicyor.spike.service.SpikeGoodsService;
import lombok.Data;

/**
 * 作者:姚克威
 * 时间:2020/3/2 14:24
 **/
@Data
public class StockDecrementTask implements Runnable{
    private Long spikeGoodsId;
    private SpikeGoodsService spikeGoodsService;

    public StockDecrementTask(Long spikeGoodsId, SpikeGoodsService spikeGoodsService) {
        this.spikeGoodsId = spikeGoodsId;
        this.spikeGoodsService = spikeGoodsService;
    }

    @Override
    public void run() {
        spikeGoodsService.decrementSpikeGoodsStocks(spikeGoodsId);
    }
}
