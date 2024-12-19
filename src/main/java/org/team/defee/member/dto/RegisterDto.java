package org.team.defee.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterDto {
    @Schema(description = "이메일", example = "test@test.com")
    private String email;
    @Schema(description = "패스워드", example = "1234")
    private String password;
    @Schema(description = "유저 이름", example = "테스터")
    private String username;
    @Schema(description = "블로그 주소", example = "https://velog.io")
    private String blogUrl;
}
