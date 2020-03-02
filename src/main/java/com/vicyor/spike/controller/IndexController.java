package com.vicyor.spike.controller;

import com.vicyor.spike.service.SpikeGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 作者:姚克威
 * 时间:2020/3/1 8:50
 **/
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    SpikeGoodsService spikeGoodsService;
    //开启秒杀
    @PostMapping("/openSpike")
    @ResponseBody
    public String openSpike(@RequestParam("spikeGoodsId") Long spikeGoodsId,
    @RequestParam("ttl")Long ttl
    ){
       spikeGoodsService.openSpike(spikeGoodsId,ttl);
       return "success";
    }
}
