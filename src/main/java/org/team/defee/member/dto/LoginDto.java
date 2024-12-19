package org.team.defee.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginDto {
    @Schema(description = "email", example = "test@test.com")
    private String email;

    @Schema(description = "패스워드", example = "1234")
    private String password;
}
