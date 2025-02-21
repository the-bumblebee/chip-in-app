package dev.asif.chipinbackend.dto.core;

import dev.asif.chipinbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();;
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
