package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
public class SessionToSessionDtoMapper implements Mapper<Session, SessionDto> {
    @Override
    public SessionDto map(Session session) {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setUserId(session.getId());
        sessionDto.setToken(session.getToken());
        sessionDto.setRole(session.getUser().getRole());
        return sessionDto;
    }
}
