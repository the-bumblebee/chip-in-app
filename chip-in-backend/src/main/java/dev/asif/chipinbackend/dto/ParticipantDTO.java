package dev.asif.chipinbackend.dto;

import lombok.Data;

@Data
public class ParticipantDTO {
    private Long userId;
    private Double percentage;
    private Integer shares;
    private Double shareAmount;
}
