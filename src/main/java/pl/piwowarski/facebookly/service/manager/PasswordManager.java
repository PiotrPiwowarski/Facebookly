package pl.piwowarski.facebookly.service.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PasswordManager {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String passwordEncryption(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
