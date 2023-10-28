package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.service.manager.impl.PictureManager;
import pl.piwowarski.facebookly.service.mapper.Mapper;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

@Service
@RequiredArgsConstructor
public class PostDtoToPostMapper implements Mapper<PostDto, Post> {

    private final UserGetService userGetService;
    private final PictureManager pictureManager;

    @Override
    public Post map(PostDto postDto) {
        return Post
                .builder()
                .content(postDto.getContent())
                .image(pictureManager.fromPathToBytesArray(postDto.getPicturePath()))
                .created(postDto.getCreated())
                .user(userGetService.getUserById(postDto.getUserId()))
                .build();
    }
}
