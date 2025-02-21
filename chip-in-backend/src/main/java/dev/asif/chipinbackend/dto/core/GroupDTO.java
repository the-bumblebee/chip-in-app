package dev.asif.chipinbackend.dto.core;

import dev.asif.chipinbackend.model.Group;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.createdAt = group.getCreatedAt();
    }
}
