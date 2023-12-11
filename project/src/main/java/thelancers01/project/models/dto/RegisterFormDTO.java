package thelancers01.project.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterFormDTO extends LoginFormDTO{

    @NotNull
    @NotBlank
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    private String verifyPassword;

    @NotNull
    @NotBlank
    @Email
    private String email;


    public String getVerifyPassword() {return verifyPassword;}

    public void setVerifyPassword(String verifyPassword) { this.verifyPassword = verifyPassword; }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}


}
