package pl.piwowarski.facebookly.service.mapper.reverseMap.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.mapper.reverseMap.ReverseMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionReverseMapper implements ReverseMapper<User, Session> {

    private static final Long EXPIRATION_TIME = 10L;

    @Override
    public Session map(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(UUID.randomUUID().toString().replace("-", ""));
        session.setUntil(LocalDateTime.now().plusMinutes(EXPIRATION_TIME));
        return session;
    }
}
