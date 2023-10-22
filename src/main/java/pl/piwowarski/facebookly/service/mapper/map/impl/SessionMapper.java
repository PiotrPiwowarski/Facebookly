package pl.piwowarski.facebookly.service.mapper.map.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.WrongEmailException;
import pl.piwowarski.facebookly.exception.WrongPasswordException;
import pl.piwowarski.facebookly.model.dto.credentials.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.session.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.SessionRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.manager.PasswordManager;
import pl.piwowarski.facebookly.service.mapper.map.Mapper;
import pl.piwowarski.facebookly.service.mapper.reverseMap.impl.SessionReverseMapper;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionMapper implements Mapper<CredentialsDto, SessionDto> {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionReverseMapper sessionReverseMapper;
    private final PasswordManager passwordManager;


    @Override
    public SessionDto map(CredentialsDto credentialsDto) {
        User user = userFinder(credentialsDto.getEmail(), credentialsDto.getPassword());
        Session session = sessionReverseMapper.map(user);
        Session savedToken = sessionRepository.save(session);
        return new SessionDto(savedToken.getUser().getId(), user.getRole(), savedToken.getToken());
    }

    private User userFinder(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new WrongEmailException(WrongEmailException.MESSAGE));
        if(!passwordManager.passwordMatches(password, user.getPassword())){
            throw new WrongPasswordException(WrongPasswordException.MESSAGE);
        }
        user.setLogged(true);
        return user;
    }
}
