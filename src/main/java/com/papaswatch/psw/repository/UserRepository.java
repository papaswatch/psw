package com.papaswatch.psw.repository;

import com.papaswatch.psw.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findUserInfoByLoginIdAndPassword(String loginId, String password);
}
