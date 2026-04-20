package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.ContentStatus;
import com.Digital_Content.Digital_Content_Rights.Enum.ContentType;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DigitalContentResponseDTO {
    private Integer id;
    private String title;
    private ContentType contentType;
    private String description;
    private LocalDate publishedDate;
    private ContentStatus contentStatus;
    private Integer createdById;
    private LocalDateTime createdAt;
}
