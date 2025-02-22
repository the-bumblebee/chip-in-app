package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.Group;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public GroupResponseDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.createdAt = group.getCreatedAt();
    }
}
