package thelancers01.project.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordFormDTO {
    @NotNull
    @NotBlank
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
