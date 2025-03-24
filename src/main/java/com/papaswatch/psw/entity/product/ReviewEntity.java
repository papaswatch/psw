package com.papaswatch.psw.entity.product;

import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.entity.BaseEntity;
import com.papaswatch.psw.entity.UserInfoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "review", schema = Constant.DB.PAPAS_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;

    @Column(name = "review_contents", length = 1000)
    private String reviewContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfoEntity user;
}
