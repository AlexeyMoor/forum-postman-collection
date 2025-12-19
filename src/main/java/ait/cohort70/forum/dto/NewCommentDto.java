package ait.cohort70.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewCommentDto {

    @NotBlank(message = "Message is required")
    @Size(min = 4, max = 500, message = "Message length must be between 4 and 500 characters")
    private String message;
}