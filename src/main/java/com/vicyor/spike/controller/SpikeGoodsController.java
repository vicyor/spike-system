package com.vicyor.spike.controller;

import com.vicyor.spike.entity.SpikeGoods;
import com.vicyor.spike.service.SpikeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2020/2/28 10:42
 **/
@Controller
@RequestMapping("/spike-goods")
public class SpikeGoodsController {
    @Autowired
    SpikeGoodsService spikeGoodsService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<SpikeGoods> getAllSpikeGoods() {
        return spikeGoodsService.getAllSpikeGoods();
    }
    @GetMapping("/getSpikeGoods/{spikeGoodsId}")
    @ResponseBody
    public SpikeGoods getSpikeGoods(@PathVariable("spikeGoodsId")Long spikeGoodsId){
        return spikeGoodsService.getSpikeGoodsBySpikeGoodsId(spikeGoodsId);
    }
}
