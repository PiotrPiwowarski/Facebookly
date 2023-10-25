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
import pl.piwowarski.facebookly.service.authenticator.AuthService;
import pl.piwowarski.facebookly.service.manager.impl.PasswordManager;
import pl.piwowarski.facebookly.service.mapper.impl.CredentialsDtoToSessionMapper;
import pl.piwowarski.facebookly.service.mapper.impl.SessionToSessionDtoMapper;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthService {

    private final SessionRepository sessionRepository;
    private final CredentialsDtoToSessionMapper credentialsDtoToSessionMapper;
    private final SessionToSessionDtoMapper sessionToSessionDtoMapper;
    private final UserGetService userGetService;
    private final PasswordManager passwordManager;
    @Value("${facebookly.token.expirationTime}")
    private Integer expirationTime;

    @Transactional
    public SessionDto login(CredentialsDto credentialsDto){
        User user = userGetService.getUserByEmail(credentialsDto.getEmail());
        if(!passwordManager.matches(credentialsDto.getPassword(), user.getPassword())){
            throw new WrongPasswordException(WrongPasswordException.MESSAGE);
        }
        user.setLogged(true);
        Session session = credentialsDtoToSessionMapper.map(credentialsDto);
        return sessionToSessionDtoMapper.map(sessionRepository.save(session));
    }

    @Transactional
    public void logout(SessionDto sessionDto) {
        User user = userGetService.getUserById(sessionDto.getUserId());
        user.setLogged(false);
        String token = sessionDto.getToken();
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new ExpiredSessionException(ExpiredSessionException.MESSAGE_1));
        sessionRepository.delete(session);
    }

    public Session saveSession(Session session){
        return sessionRepository.save(session);
    }

    @Transactional
    public void authorizeAndAuthenticate(SessionDto sessionDto, Set<Role> authorizedRoles){
        User user = userGetService.getUserById(sessionDto.getUserId());
        if(!user.getLogged()) {
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        if(!sessionDto.getRole().equals(user.getRole())){
            throw new RolesConflictException(RolesConflictException.MESSAGE);
        }
        checkRole(user.getRole(), authorizedRoles);
        checkSession(sessionDto.getToken(), sessionDto.getUserId());
    }

    @Transactional
    public void authorizeAndAuthenticate(String token, Long userId, Set<Role> authorizedRoles){
        User user = userGetService.getUserById(userId);
        if(!user.getLogged()) {
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        checkRole(user.getRole(), authorizedRoles);
        checkSession(token, userId);
    }

    public void checkRole(Role role, Set<Role> authorizedRoles){
        if(!authorizedRoles.contains(role)){
            throw new AuthorizationException(AuthorizationException.MESSAGE);
        }
    }

    @Transactional
    public void checkSession(String token, Long userId){
        if(userId == null){
            throw new UserIdIsNullException(UserIdIsNullException.MESSAGE);
        }
        if(token == null){
            throw new TokenIsNullException(TokenIsNullException.MESSAGE);
        }
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new UserNotLoggedInException(UserNotLoggedInException.MESSAGE));
        if(session.getUntil().isBefore(LocalDateTime.now())){
            logout(sessionToSessionDtoMapper.map(session));
            throw new ExpiredSessionException(ExpiredSessionException.MESSAGE_2);
        }
        if(!userId.equals(session.getUser().getId())){
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        session.setUntil(LocalDateTime.now().plusMinutes(expirationTime));
    }
}