package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z\\s\\-'.]*$", message = "Name contains invalid characters")
    private String name;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Size(max = 255, message = "Organization name cannot exceed 255 characters")
    private String organizationName;

    @NotNull(message = "System role is required")
    private Role role;
}
