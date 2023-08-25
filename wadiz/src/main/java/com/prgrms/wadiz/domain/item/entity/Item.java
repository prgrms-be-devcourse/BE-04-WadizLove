package com.prgrms.wadiz.domain.item.entity;

import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
// TODO: import와 static import의 차이를 학습해보자.
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "items")
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String name;

    // quantity와 price는 정해지지 않을 수 있다는 가정하에 null을 허용하도록 구성
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

}
