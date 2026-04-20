package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.ContentType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DigitalContentRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    private String title;

    @NotNull(message = "Content type is required")
    private ContentType contentType;

    @Size(max = 4000, message = "Description cannot exceed 4000 characters")
    private String description;

    @PastOrPresent(message = "Published date cannot be in the future")
    private LocalDate publishedDate;

    @NotNull(message = "Creator ID is required")
    private Integer createdById;
}
