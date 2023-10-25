package pl.piwowarski.facebookly.service.manager.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.service.manager.Manager;

@Service
@RequiredArgsConstructor
public class PasswordManager implements Manager {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String passwordEncryption(String rawPassword){
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
