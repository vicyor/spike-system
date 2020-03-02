package com.vicyor.spike.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 作者:姚克威
 * 时间:2020/2/28 0:03
 **/
@Data
@Entity
@Table(name = "spike_goods",schema = "spike_system")
public class SpikeGoods {
    @Id
    private Long goodsId;
    @Column(name = "goods_name")
    private String goodsName;
    @Column(name = "stock")
    private long stock;
    @Column(name = "start_time")
    //spring mvc的HttpMessageConverter->ObjectMapper 对入参和返回参数进行json的转化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Timestamp endTime;
    @Transient
    private Long timeLimit;
    @Column(name = "stock_all")
    private Long stockAll;
    private int status;
}
