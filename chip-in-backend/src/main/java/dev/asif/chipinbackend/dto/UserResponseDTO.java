package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;

    public UserResponseDTO(User user) {
        this.id = user.getId();;
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
