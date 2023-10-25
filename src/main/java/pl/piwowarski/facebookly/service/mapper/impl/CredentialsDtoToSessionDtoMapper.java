package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.authenticator.AuthenticationService;
import pl.piwowarski.facebookly.service.mapper.Mapper;
import pl.piwowarski.facebookly.service.user.GetUserService;


@Service
@RequiredArgsConstructor
public class CredentialsDtoToSessionDtoMapper implements Mapper<CredentialsDto, SessionDto> {

    private final AuthenticationService authenticationService;
    private final GetUserService getUserService;
    private final UserToSessionMapper userToSessionMapper;

    @Override
    public SessionDto map(CredentialsDto credentialsDto) {
        User user = getUserService.getUserByEmailAndPassword(credentialsDto.getEmail(), credentialsDto.getPassword());
        Session session = userToSessionMapper.map(user);
        Session savedToken = authenticationService.saveSession(session);
        return new SessionDto(savedToken.getUser().getId(), user.getRole(), savedToken.getToken());
    }
}
