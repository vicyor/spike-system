package com.vicyor.spike.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Long orderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "state")
    private Long state;
    @Column(name = "goods_id")
    private Long goodsId;
    @Column(name = "create_time")
    private Timestamp createTime;
}
