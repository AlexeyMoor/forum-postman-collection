package ait.cohort70.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewPasswordDto  {

    @NotBlank(message = "Password must not be blank")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;
}
