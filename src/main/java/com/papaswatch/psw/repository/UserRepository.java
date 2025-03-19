package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByLoginId(String loginId);
}
