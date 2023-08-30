package com.prgrms.wadiz.domain.orderitem.entity;

import com.prgrms.wadiz.domain.item.entity.Item;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Integer orderItemPrice;

    @Column(nullable = false)
    private Integer orderItemQuantity;

    @Builder
    public OrderItem(Item item, Integer orderItemPrice, Integer orderItemQuantity) {
        this.item = item;
        this.orderItemPrice = orderItemPrice;
        this.orderItemQuantity = orderItemQuantity;
    }

    public static OrderItem createOrderReward(Item item, Integer price, Integer count) {
        OrderItem orderReward = OrderItem.builder()
                .item(item)
                .orderItemPrice(price)
                .orderItemQuantity(count)
                .build();

        item.removeStock(count);

        return orderReward;
    }
}
