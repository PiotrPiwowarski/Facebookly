package pl.piwowarski.facebookly.service.mapper.map.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.WrongEmailException;
import pl.piwowarski.facebookly.exception.WrongPasswordException;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.SessionRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.map.Mapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionMapper implements Mapper<CredentialsDto, SessionDto> {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Long EXPIRATION_TIME = 10L;

    @Override
    public SessionDto map(CredentialsDto credentialsDto) {
        User user = userFinder(credentialsDto.getEmail(), credentialsDto.getPassword());
        Session session = createSession(user);
        Session savedToken = sessionRepository.save(session);
        return new SessionDto(savedToken.getUser().getId(), savedToken.getToken());
    }

    private Session createSession(User user){
        Session session = new Session();
        session.setUser(user);
        session.setToken(UUID.randomUUID().toString().replace("-", ""));
        session.setUntil(LocalDateTime.now().plusMinutes(EXPIRATION_TIME));
        return session;
    }

    private User userFinder(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new WrongEmailException(WrongEmailException.MESSAGE));
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new WrongPasswordException(WrongPasswordException.MESSAGE);
        }
        user.setLogged(true);
        return user;
    }
}
