package com.vicyor.spike.service;

import com.vicyor.spike.entity.SpikeOrder;

public interface SpikeOrderService {
    void saveSpikeOrder(SpikeOrder order);

    void finishExpiredOrder(String userId, Long goodsId);
}
