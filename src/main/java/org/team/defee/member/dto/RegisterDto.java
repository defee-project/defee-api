package org.team.defee.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String email;
    private String password;
    private String username;
    private String blogUrl;
}
