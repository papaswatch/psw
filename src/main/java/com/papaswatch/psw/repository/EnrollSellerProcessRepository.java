package com.papaswatch.psw.repository;

import com.papaswatch.psw.entity.EnrollSellerProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollSellerProcessRepository extends JpaRepository<EnrollSellerProcess, Long> {

    Optional<List<EnrollSellerProcess>> findByStatusIn(List<String> status);
}
