package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByLoginId(String loginId);
    //Optional<UserInfoEntity> findUserInfoByLoginIdAndPassword(String loginId, String password);
}
