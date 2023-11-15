package pl.piwowarski.facebookly.service.authenticator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.*;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.SessionRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.authenticator.AuthService;
import pl.piwowarski.facebookly.service.manager.impl.PasswordManager;
import pl.piwowarski.facebookly.service.mapper.impl.CredentialsDtoToSessionMapper;
import pl.piwowarski.facebookly.service.mapper.impl.SessionToSessionDtoMapper;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthService {

    private final SessionRepository sessionRepository;
    private final CredentialsDtoToSessionMapper credentialsDtoToSessionMapper;
    private final SessionToSessionDtoMapper sessionToSessionDtoMapper;
    private final UserGetService userGetService;
    private final UserRepository userRepository;
    private final PasswordManager passwordManager;
    @Value("${facebookly.token.expirationTime}")
    private Integer expirationTime;

    @Transactional
    public SessionDto login(CredentialsDto credentialsDto){
        User user = userGetService.getUserByEmail(credentialsDto.getEmail());
        if(!passwordManager.matches(credentialsDto.getPassword(), user.getPassword())){
            throw new WrongPasswordException();
        }
        Optional<Session> optionalSession = sessionRepository.findByUserId(user.getId());
        optionalSession.ifPresent(sessionRepository::delete);
        user.setLogged(true);
        Session session = credentialsDtoToSessionMapper.map(credentialsDto);
        return sessionToSessionDtoMapper.map(sessionRepository.save(session));
    }

    @Transactional
    public void logout(SessionDto sessionDto) {
        User user = userGetService.getUserById(sessionDto.getUserId());
        user.setLogged(false);
        String token = sessionDto.getToken();
        Session session = findSession(token);
        sessionRepository.delete(session);
    }

    public void authorizeAndAuthenticate(SessionDto sessionDto, Set<Role> authorizedRoles){
        User user = userGetService.getUserById(sessionDto.getUserId());
        if(!user.getLogged()) {
            throw new UserNotLoggedInException();
        }
        if(!sessionDto.getRole().equals(user.getRole())){
            throw new RolesConflictException();
        }
        checkRole(user.getRole(), authorizedRoles);
        checkSession(sessionDto.getToken(), sessionDto.getUserId());
    }

    public void authorizeAndAuthenticate(String token, Long userId, Set<Role> authorizedRoles){
        User user = userGetService.getUserById(userId);
        if(!user.getLogged()) {
            throw new UserNotLoggedInException();
        }
        checkRole(user.getRole(), authorizedRoles);
        checkSession(token, userId);
    }

    public void checkRole(Role role, Set<Role> authorizedRoles){
        if(!authorizedRoles.contains(role)){
            throw new AuthorizationException();
        }
    }

    public void checkSession(String token, Long userId){
        Session session = findSession(token);
        User user = userGetService.getUserById(userId);
        if(!session.getUser().getId().equals(user.getId())){
            throw new UserNotLoggedInException();
        }
        if(session.getExpirationDate().isBefore(LocalDateTime.now())){
            user.setLogged(false);
            userRepository.save(user);
            deleteSession(sessionToSessionDtoMapper.map(session));
            throw new ExpiredSessionException();
        }
        session.setExpirationDate(LocalDateTime.now().plusMinutes(expirationTime));
    }

    private void deleteSession(SessionDto sessionDto){
        String token = sessionDto.getToken();
        Session session = findSession(token);
        sessionRepository.delete(session);
    }

    private Session findSession(String token){
        return sessionRepository
                .findByToken(token)
                .orElseThrow(UserNotLoggedInException::new);
    }
}