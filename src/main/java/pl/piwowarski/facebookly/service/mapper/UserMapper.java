package pl.piwowarski.facebookly.service.mapper;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.CreatePostDto;
import pl.piwowarski.facebookly.model.dto.CreateUserDto;
import pl.piwowarski.facebookly.model.entity.User;

@Service
public class UserMapper implements Mapper<User, CreateUserDto> {

    @Override
    public User map(CreateUserDto createUserDto) {
        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setLogin(createUserDto.getLogin());
        user.setPassword(createUserDto.getPassword());
        return user;
    }

    @Override
    public CreateUserDto unmap(User user) {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setId(user.getId());
        createUserDto.setFirstName(user.getFirstName());
        createUserDto.setLastName(user.getLastName());
        createUserDto.setEmail(user.getEmail());
        createUserDto.setLogin(user.getLogin());
        createUserDto.setPassword(user.getPassword());
        return createUserDto;
    }
}
