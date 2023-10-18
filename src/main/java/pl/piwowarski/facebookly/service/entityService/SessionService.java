package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.ExpiredSessionException;
import pl.piwowarski.facebookly.exception.TokenIsNullException;
import pl.piwowarski.facebookly.exception.UserIdIsNullException;
import pl.piwowarski.facebookly.exception.UserNotLoggedInException;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.repository.SessionRepository;
import pl.piwowarski.facebookly.service.mapper.map.impl.SessionMapper;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionDto authenticate(CredentialsDto credentialsDto){
        return sessionMapper.map(credentialsDto);
    }

    public void logout(String token){
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new ExpiredSessionException(ExpiredSessionException.MESSAGE_1));
        sessionRepository.delete(session);
    }

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
    }

    private void verifySessionTime(Session session) {
        if(session.getUntil().isBefore(LocalDateTime.now())){
            logout(session.getToken());
            throw new ExpiredSessionException(ExpiredSessionException.MESSAGE_2);
        }
    }
}