package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.Role;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private String organizationName;
    private Role role;
    private LocalDateTime createdAt;
}
