package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.entity.Session;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.mapper.Mapper;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;


@Service
@RequiredArgsConstructor
public class CredentialsDtoToSessionMapper implements Mapper<CredentialsDto, Session> {

    private final UserGetService userGetService;
    private final UserToSessionMapper userToSessionMapper;

    @Override
    public Session map(CredentialsDto credentialsDto) {
        User user = userGetService.getUserByEmail(credentialsDto.getEmail());
        return userToSessionMapper.map(user);
    }
}
