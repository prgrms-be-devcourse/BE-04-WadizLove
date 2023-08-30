package com.prgrms.wadiz.domain.item.entity;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.iteminfo.entity.ItemInfo;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "items")
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    private static final int ZERO_STOCK = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id")
    private Funding funding;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemInfo> itemInfo;

    @Column(nullable = false)
    private String name;

    public void removeStock(Integer quantity){
        Integer restStock = this.stockQuantity - quantity;
        if (restStock < ZERO_STOCK){
            throw new IllegalArgumentException("not good");
        }

        this.stockQuantity = restStock;
    }

}
