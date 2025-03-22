package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.papaswatch.psw.config.Constant.DB.PAPAS_SCHEMA;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_tag", uniqueConstraints = @UniqueConstraint(columnNames = "name"), schema = PAPAS_SCHEMA)
@Entity
public class HashtagEntity {
    @Id
    @Column(name = "hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private HashtagEntity(String name) {
        this.name = name;
    }

    public static HashtagEntity of(String name) {
        return new HashtagEntity(name);
    }
}
