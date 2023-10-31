package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.service.manager.impl.PictureManager;
import pl.piwowarski.facebookly.service.mapper.Mapper;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

@Service
@RequiredArgsConstructor
public class PostDtoToPostMapper implements Mapper<AddPostDto, Post> {

    private final UserGetService userGetService;
    private final PictureManager pictureManager;

    @Override
    public Post map(AddPostDto addPostDto) {
        return Post
                .builder()
                .content(addPostDto.getContent())
                .image(pictureManager.fromPathToBytesArray(addPostDto.getPicturePath()))
                .created(addPostDto.getCreated())
                .user(userGetService.getUserById(addPostDto.getUserId()))
                .build();
    }
}
