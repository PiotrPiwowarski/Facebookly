package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.*;
import pl.piwowarski.facebookly.model.dto.credentials.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.session.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.SessionRepository;
import pl.piwowarski.facebookly.service.mapper.map.impl.SessionMapper;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    @Value("${facebookly.token.expirationTime}")
    private Integer expirationTime;

    public SessionDto authenticate(CredentialsDto credentialsDto){
        return sessionMapper.map(credentialsDto);
    }

    public void logout(String token){
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new ExpiredSessionException(ExpiredSessionException.MESSAGE_1));
        sessionRepository.delete(session);
    }

    public void verifyRole(Role role, Set<Role> authorizedRoles){
        if(!authorizedRoles.contains(role)){
            throw new AuthorizationException(AuthorizationException.MESSAGE);
        }
    }

    @Transactional
    public void verifySession(String token, Long userId){
        if(userId == null){
            throw new UserIdIsNullException(UserIdIsNullException.MESSAGE);
        }
        if(token == null){
            throw new TokenIsNullException(TokenIsNullException.MESSAGE);
        }
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new UserNotLoggedInException(UserNotLoggedInException.MESSAGE));
        verifySessionTime(session);
        if(!userId.equals(session.getUser().getId())){
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        session.setUntil(LocalDateTime.now().plusMinutes(expirationTime));
    }

    private void verifySessionTime(Session session) {
        if(session.getUntil().isBefore(LocalDateTime.now())){
            logout(session.getToken());
            throw new ExpiredSessionException(ExpiredSessionException.MESSAGE_2);
        }
    }
}