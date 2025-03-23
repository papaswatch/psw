package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.EnrollSellerProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollSellerProcessRepository extends JpaRepository<EnrollSellerProcessEntity, Long> {

    Optional<List<EnrollSellerProcessEntity>> findByStatusIn(List<String> status);
}
