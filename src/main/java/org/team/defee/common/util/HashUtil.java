package org.team.defee.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashUtil {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String hashPassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }
}
