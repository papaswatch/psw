package com.papaswatch.psw.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.papaswatch.psw.config.Constant.DB.PAPAS_SCHEMA;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hashtag", uniqueConstraints = @UniqueConstraint(columnNames = "name"), schema = PAPAS_SCHEMA)
@Entity
public class HashtagEntity {
    @Id
    @Column(name = "hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private HashtagEntity(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public static HashtagEntity of(String name) {
        return new HashtagEntity(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashtagEntity that = (HashtagEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
