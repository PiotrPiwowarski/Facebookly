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
        return Session
                .builder()
                .user(user)
                .token(UUID.randomUUID().toString().replace("-", ""))
                .expirationDate(LocalDateTime.now().plusMinutes(expirationTime))
                .build();
    }
}
