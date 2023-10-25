package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
public class PostToPostDtoMapper implements Mapper<Post, PostDto> {

    @Override
    public PostDto map(Post post) {
        return PostDto
                .builder()
                .id(post.getId())
                .content(post.getContent())
                .picture(post.getPicture())
                .created(post.getCreated())
                .userId(post.getUser().getId())
                .build();
    }
}
