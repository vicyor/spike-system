package com.vicyor.spike.service.impl;

import com.vicyor.spike.entity.SpikeOrder;
import com.vicyor.spike.repository.SpikeOrderRepository;
import com.vicyor.spike.service.SpikeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者:姚克威
 * 时间:2020/3/3 12:05
 **/
@Service
public class SpikeOrderServiceImpl implements SpikeOrderService {
    @Autowired
    SpikeOrderRepository spikeOrderRepository;

    @Override
    public void saveSpikeOrder(SpikeOrder order) {
        spikeOrderRepository.save(order);
    }

    @Override
    public void finishExpiredOrder(String userId, Long goodsId) {
        spikeOrderRepository.updateOrderStatus(userId,goodsId);
    }
}
