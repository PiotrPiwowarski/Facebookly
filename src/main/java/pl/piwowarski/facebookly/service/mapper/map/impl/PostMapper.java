package pl.piwowarski.facebookly.service.mapper.map.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.service.mapper.map.Mapper;

@Service
public class PostMapper implements Mapper<Post, PostDto> {
    @Override
    public PostDto map(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setPicture(post.getPicture());
        postDto.setCreated(post.getCreated());
        postDto.setUserId(post.getUser().getId());
        postDto.setLikes(post.getLikes());
        postDto.setDislikes(post.getDislikes());
        return postDto;
    }
}
