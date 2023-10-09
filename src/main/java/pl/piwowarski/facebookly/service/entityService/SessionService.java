package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.ExpiredSessionException;
import pl.piwowarski.facebookly.exception.UserNotLoggedInException;
import pl.piwowarski.facebookly.model.dto.LogDataDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.repository.SessionRepository;
import pl.piwowarski.facebookly.service.mapper.CredentialsMapper;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CredentialsMapper credentialsMapper;

    public LogDataDto login(String email, String password){
        return credentialsMapper.map(email, password);
    }

    public void logout(String token){
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new ExpiredSessionException(ExpiredSessionException.MESSAGE_1));
        sessionRepository.delete(session);
    }

    public void verifySession(String token) {
        Session session = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new UserNotLoggedInException(UserNotLoggedInException.MESSAGE));
        if(session.getUntil().isBefore(LocalDateTime.now())){
            logout(token);
            throw new ExpiredSessionException(ExpiredSessionException.MESSAGE_2);
        }
    }
}
