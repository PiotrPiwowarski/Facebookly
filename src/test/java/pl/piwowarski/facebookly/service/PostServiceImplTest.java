package pl.piwowarski.facebookly.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.piwowarski.facebookly.manager.ImageManager;
import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.dto.post.UpdatePostDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.entity.PostReaction;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.PostReactionRepository;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.post.impl.PostServiceImpl;
import pl.piwowarski.facebookly.service.user.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.piwowarski.facebookly.model.enums.Gender.MALE;
import static pl.piwowarski.facebookly.model.enums.Reaction.DISLIKE;
import static pl.piwowarski.facebookly.model.enums.Reaction.LIKE;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageManager imageManager;
    @Mock
    private PostReactionRepository postReactionRepository;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private PostServiceImpl postService;
    private AddPostDto addPostDto;
    private UserDto userDto;
    private Post post1;
    private Post post2;
    private User user1;
    private User user2;
    private UpdatePostDto updatePostDto;
    private PostReaction postReaction1;
    private PostReaction postReaction2;

    @BeforeEach
    public void init() {
        addPostDto = AddPostDto.builder()
                .content("Test post")
                .userId(1L)
                .build();
        user1 = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .email("user1@gmail.com")
                .password("abcde1")
                .gender(MALE)
                .role(USER)
                .build();
        user2 = User.builder()
                .id(2L)
                .firstName("John")
                .lastName("Johnowski")
                .role(USER)
                .gender(MALE)
                .email("john@gmail.com")
                .password("asdfasdf")
                .build();
        userDto = UserDto.builder()
                .id(2L)
                .firstName("John")
                .lastName("Johnowski")
                .gender(MALE)
                .email("john@gmail.com")
                .build();
        post1 = Post.builder()
                .id(1L)
                .user(user1)
                .content("abc")
                .created(LocalDateTime.now())
                .build();
        post2 = Post.builder()
                .id(2L)
                .user(user2)
                .content("cde")
                .created(LocalDateTime.now())
                .build();
        updatePostDto = UpdatePostDto.builder()
                .userId(1L)
                .content("New content")
                .build();
        postReaction1 = PostReaction.builder()
                .reaction(LIKE)
                .post(post1)
                .user(user2)
                .build();
        postReaction2 = PostReaction.builder()
                .reaction(DISLIKE)
                .post(post1)
                .user(user2)
                .build();
    }

    @Test
    void itShouldAddPost() {
        when(postRepository.save(any(Post.class))).thenReturn(post1);

        PostDto postDto = postService.addPost(addPostDto);

        assertThat(postDto).isNotNull();
        assertThat(postDto.getId()).isEqualTo(1L);
    }

    @Test
    void itShouldGetPostDto() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));

        PostDto postDto = postService.getPostDto(1L);

        assertThat(postDto).isNotNull();
        assertThat(postDto.getId()).isEqualTo(1L);
    }

    @Test
    void itShouldGetPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));

        Post post = postService.getPost(1L);

        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(1L);
    }

    @Test
    void itShouldGetAllPosts() {
        when(postRepository.findAll()).thenReturn(new ArrayList<>(List.of(post1, post2)));

        List<PostDto> allPosts = postService.getAllPosts();

        assertThat(allPosts).isNotNull();
        assertThat(allPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldGetPagedPosts() {
        when(postRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(post1, post2)));

        List<PostDto> pagedPosts = postService.getPagedPosts(0, 2);

        assertThat(pagedPosts).isNotNull();
        assertThat(pagedPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldGetAllUserPosts() {
        when(postRepository.findAllByUserId(1L)).thenReturn(new ArrayList<>(List.of(post1, post2)));

        List<PostDto> allUserPosts = postService.getAllUserPosts(1L);

        assertThat(allUserPosts).isNotNull();
        assertThat(allUserPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldGetPagedUserPosts() {
        when(postRepository.findPagedByUserId(any(Long.class), any(PageRequest.class)))
                .thenReturn(new ArrayList<>(List.of(post1, post2)));

        List<PostDto> allUserPosts = postService.getPagedUserPosts(1L, 0, 2);

        assertThat(allUserPosts).isNotNull();
        assertThat(allUserPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldGetFollowedUsersPosts() {
        when(postRepository.findAllByUserId(2L)).thenReturn(new ArrayList<>(List.of(post1)));
        when(userService.getFollowedUsers(1L)).thenReturn(new ArrayList<>(List.of(userDto)));

        List<PostDto> followedUsersPosts = postService.getFollowedUsersPosts(1L);

        assertThat(followedUsersPosts).isNotNull();
        assertThat(followedUsersPosts.size()).isEqualTo(1);
    }

    @Test
    void itShouldGetPagedFollowedUsersPosts() {
        when(postRepository.findPagedFollowedUsersPosts(1L, PageRequest.of(0, 1)))
                .thenReturn(new ArrayList<>(List.of(post2)));

        List<PostDto> followedUsersPosts = postService.getPagedFollowedUsersPosts(1L, 0, 1);

        assertThat(followedUsersPosts).isNotNull();
        assertThat(followedUsersPosts.size()).isEqualTo(1);
    }

    @Test
    void itShouldDeleteAllUserPosts() {
        postService.deleteAllUserPosts(1L);
        verify(postRepository, times(1)).deleteAllByUserId(1L);
    }

    @Test
    void itShouldDeletePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));
		postService.deletePost(1L, 1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void itShouldUpdatePost() {
		when(postRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(post1));
        when(postRepository.save(any(Post.class))).thenReturn(post1);

        PostDto postDto = postService.updatePost(1L, updatePostDto);

        assertThat(postDto).isNotNull();
    }

    @Test
    void itShouldAddPostReaction() {
		when(postReactionRepository.findByPostIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));
        when(userService.getUser(1L)).thenReturn(user1);

        postService.addPostReaction(1L, 1L, LIKE);

        verify(postReactionRepository, times(1)).save(any(PostReaction.class));
    }

    @Test
    void itShouldGetAllSpecificPostReactions() {
		when(postReactionRepository.findByPostId(1L)).thenReturn(List.of(postReaction1));

        List<UserReactionDto> allPostReactions = postService.getAllPostReactions(1L, LIKE);

        assertThat(allPostReactions).isNotNull();
        assertThat(allPostReactions.size()).isEqualTo(1);
        assertThat(allPostReactions.get(0).getReaction()).isEqualTo(LIKE);
    }

    @Test
    void itShouldGetAllPostReactions() {
        when(postReactionRepository.findByPostId(1L)).thenReturn(List.of(postReaction1, postReaction2));

        List<UserReactionDto> allPostReactions = postService.getAllPostReactions(1L);

        assertThat(allPostReactions).isNotNull();
        assertThat(allPostReactions.size()).isEqualTo(2);
        assertThat(allPostReactions.get(0).getReaction()).isEqualTo(LIKE);
        assertThat(allPostReactions.get(1).getReaction()).isEqualTo(DISLIKE);
    }

    @Test
    void itShouldDeletePostReaction() {
		when(postReactionRepository.findByPostIdAndUserId(1L, 2L)).thenReturn(Optional.of(postReaction1));
        postService.deletePostReaction(1L, 2L);

        verify(postReactionRepository, times(1)).delete(postReaction1);
    }
}