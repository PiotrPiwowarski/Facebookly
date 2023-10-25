package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.mapper.Mapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserToSessionMapper implements Mapper<User, Session> {

    @Value("${facebookly.token.expirationTime}")
    private Integer expirationTime;

    @Override
    public Session map(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(UUID.randomUUID().toString().replace("-", ""));
        session.setUntil(LocalDateTime.now().plusMinutes(expirationTime));
        return session;
    }
}
