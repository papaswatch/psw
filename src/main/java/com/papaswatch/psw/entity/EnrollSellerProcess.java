package com.papaswatch.psw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@ToString
@Entity(name = "enroll_seller_process")
public class EnrollSellerProcess {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "bank_validation")
    private Boolean bankValidation;

    @Column(name = "business_validation")
    private Boolean businessValidation;

    @Column(name = "final_validation")
    private Boolean finalValidation;

    @Column(name = "request_date")
    private LocalDateTime requestDate =  LocalDateTime.now();

    @Column(name = "reviewer_id")
    private String reviewerId;

    @Column(name = "reviewed_date")
    private LocalDateTime reviewedDate;

    @Column(name = "status")
    private String status = "PENDING";

    @Column(name = "reject_reason")
    private String rejectReason;

    public static EnrollSellerProcess create(Long userId, boolean bankValidation, boolean businessValidation) {
        return new EnrollSellerProcess(userId, bankValidation, businessValidation, false, LocalDateTime.now(), null, null, "PENDING", null);
    }

    public void approve(String reviewerId) {
        this.status = "APPROVED";
        this.reviewerId = reviewerId;
        this.reviewedDate = LocalDateTime.now();
    }

    public void reject(String reviewerId, String rejectReason) {
        this.status = "REJECTED";
        this.reviewerId = reviewerId;
        this.reviewedDate = LocalDateTime.now();
        this.rejectReason = rejectReason;
    }
}
