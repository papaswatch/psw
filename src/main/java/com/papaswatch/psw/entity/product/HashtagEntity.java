package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_hashtag")
@Entity
public class HashtagEntity {
    @Id
    @Column(name = "hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Column(name = "name")
    private String name;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "hashtag")
    private List<HashtagEntity> hashtags;
}
