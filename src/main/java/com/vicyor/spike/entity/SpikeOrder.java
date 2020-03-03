package com.vicyor.spike.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 作者:姚克威
 * 时间:2020/2/28 0:02
 **/
@Data
@Entity
@Table(name = "spike_order",schema = "spike_system")
public class SpikeOrder {
    @Id
    //设置自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "state")
    private int state;
    @Column(name = "goods_id")
    private Long goodsId;
    @Column(name = "create_time")
    private Timestamp createTime;
}
