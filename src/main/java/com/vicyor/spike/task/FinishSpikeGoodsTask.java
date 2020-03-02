package com.vicyor.spike.task;

import com.vicyor.spike.service.SpikeGoodsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 作者:姚克威
 * 时间:2020/3/2 15:48
 **/
@Data
public class FinishSpikeGoodsTask implements Runnable {
    @Override
    public void run() {
        spikeGoodsService.finishSpike(goodsId);
    }
    private SpikeGoodsService spikeGoodsService;
    private Long goodsId;

    public FinishSpikeGoodsTask(SpikeGoodsService spikeGoodsService, Long goodsId) {
        this.spikeGoodsService = spikeGoodsService;
        this.goodsId = goodsId;
    }
}
