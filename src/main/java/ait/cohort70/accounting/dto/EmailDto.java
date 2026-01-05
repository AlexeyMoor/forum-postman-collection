package ait.cohort70.accounting.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailDto {
    @NotBlank(message = "To field must not be blank")
    @Email(message = "To field must be a valid email address")
    private String to;
    @NotBlank(message = "Subject must not be blank")
    private String subject;
    @NotBlank(message = "Message must not be blank")
    private String message;
}
