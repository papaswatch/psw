package com.papaswatch.psw.dto;

import com.papaswatch.psw.entity.EnrollSellerProcessEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class EnrollSellerProcessDto {
    private long userId;
    private Boolean bankValidation;
    private Boolean businessValidation;
    private Boolean finalValidation;
    private LocalDateTime requestDate;
    private String reviewerId;
    private LocalDateTime reviewedDate;
    private String status;
    private String rejectReason;

    public static List<EnrollSellerProcessDto> response(List<EnrollSellerProcessEntity> entities) {
        return entities.stream()
                .map(entity -> EnrollSellerProcessDto.of(
                entity.getUserId(),
                entity.getBankValidation(),
                entity.getBusinessValidation(),
                entity.getFinalValidation(),
                entity.getRequestDate(),
                entity.getReviewerId(),
                entity.getReviewedDate(),
                entity.getStatus(),
                entity.getRejectReason()
        )).toList();
    }
}
