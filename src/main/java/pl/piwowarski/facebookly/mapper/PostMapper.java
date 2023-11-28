package pl.piwowarski.facebookly.mapper;

import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.manager.ImageManager;
import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.service.user.UserService;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PostMapper {

    public static Post toEntity(AddPostDto addPostDto,
                                ImageManager imageManager,
                                UserService userService) {
        return Post.builder()
                .content(addPostDto.getContent())
                .image(imageManager.fromPathToBytesArray(addPostDto.getPicturePath()))
                .created(LocalDateTime.now())
                .user(userService.getUser(addPostDto.getUserId()))
                .build();
    }

    public static Post toEntity(PostDto postDto,
                                UserService userService) {
        return Post.builder()
                .id(postDto.getId())
                .content(postDto.getContent())
                .image(postDto.getImage())
                .created(postDto.getCreated())
                .user(userService.getUser(postDto.getUserId()))
                .build();
    }

    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .image(post.getImage())
                .created(post.getCreated())
                .userId(post.getUser().getId())
                .build();
    }
}
