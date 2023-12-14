package pl.piwowarski.facebookly.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.comment.UpdateCommentDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.*;
import pl.piwowarski.facebookly.repository.CommentReactionRepository;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.comment.impl.CommentServiceImpl;
import pl.piwowarski.facebookly.service.post.impl.PostServiceImpl;
import pl.piwowarski.facebookly.service.user.impl.UserServiceImpl;

import java.time.LocalDateTime;
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
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentReactionRepository commentReactionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private PostServiceImpl postService;
    @InjectMocks
    private CommentServiceImpl commentService;
    private User user1;
    private Post post1;
    private Comment comment1;
    CommentReaction commentReaction1;
    CommentReaction commentReaction2;

    @BeforeEach
    public void init() {
        user1 = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .email("user1@gmail.com")
                .password("abcde1")
                .gender(MALE)
                .role(USER)
                .build();
        post1 = Post.builder()
                .id(1L)
                .user(user1)
                .content("abc")
                .created(LocalDateTime.now())
                .build();
        comment1 = Comment.builder()
                .id(1L)
                .post(post1)
                .created(LocalDateTime.now())
                .content("Test comment")
                .user(user1)
                .build();
        commentReaction1 = CommentReaction.builder()
                .reaction(LIKE)
                .comment(comment1)
                .id(1L)
                .user(user1)
                .build();
        commentReaction2 = CommentReaction.builder()
                .reaction(DISLIKE)
                .comment(comment1)
                .id(1L)
                .user(user1)
                .build();
    }

    @Test
    void itShouldAddComment() {
        AddCommentDto comment = AddCommentDto.builder()
                .userId(1L)
                .postId(1L)
                .content("Test comment")
                .build();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment1);

        CommentDto commentDto2 = commentService.addComment(comment);

        assertThat(commentDto2).isNotNull();
    }

    @Test
    void itShouldGetComment() {
		when(commentRepository.findById(1L)).thenReturn(Optional.of(comment1));
        CommentDto comment = commentService.getComment(1L);

        assertThat(comment).isNotNull();
    }

    @Test
    void itShouldGetAllPostComments() {
		when(commentRepository.findAllByPostId(1L)).thenReturn(List.of(comment1));

        List<CommentDto> allPostComments = commentService.getAllPostComments(1L);

        assertThat(allPostComments).isNotNull();
        assertThat(allPostComments.size()).isEqualTo(1);
    }

    @Test
    void itShouldGetPagePostComments() {
        when(commentRepository.findPagedByPostId(any(Long.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(comment1)));

        List<CommentDto> allPostComments = commentService.getPagePostComments(1L, 0, 1);

        assertThat(allPostComments).isNotNull();
        assertThat(allPostComments.size()).isEqualTo(1);
    }

    @Test
    void itShouldDeleteComment() {
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment1));
		commentService.deleteComment(1L, 1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    void itShouldDeletePostComments() {
		when(commentRepository.findAllByPostId(any(Long.class))).thenReturn(List.of(comment1));

        commentService.deletePostComments(1L, 1L);

        verify(commentRepository, times(1)).deleteAll(any());
    }

    @Test
    void itShouldUpdateComment() {
        UpdateCommentDto comment = UpdateCommentDto.builder()
                .content("Update")
                .userId(1L)
                .build();
        when(commentRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(comment1));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment1);

        CommentDto commentDto = commentService.updateComment(1L, comment);

        assertThat(commentDto).isNotNull();
    }

    @Test
    void itShouldAddCommentReaction() {
        when(commentReactionRepository.findByCommentIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment1));
        when(userService.getUser(1L)).thenReturn(user1);

        commentService.addCommentReaction(1L, 1L, LIKE);

        verify(commentReactionRepository, times(1)).save(any(CommentReaction.class));
    }

    @Test
    void itShouldGetAllSpecificCommentReactions() {
        when(commentReactionRepository.findByCommentId(1L)).thenReturn(List.of(commentReaction1));

        List<UserReactionDto> allCommentReactions = commentService.getAllCommentReactions(1L, LIKE);

        assertThat(allCommentReactions).isNotNull();
        assertThat(allCommentReactions.size()).isEqualTo(1);
        assertThat(allCommentReactions.get(0).getReaction()).isEqualTo(LIKE);
    }

    @Test
    void itShouldGetAllCommentReactions() {
        when(commentReactionRepository.findByCommentId(1L)).thenReturn(List.of(commentReaction1, commentReaction2));

        List<UserReactionDto> allCommentReactions = commentService.getAllCommentReactions(1L);

        assertThat(allCommentReactions).isNotNull();
        assertThat(allCommentReactions.size()).isEqualTo(2);
        assertThat(allCommentReactions.get(0).getReaction()).isEqualTo(LIKE);
        assertThat(allCommentReactions.get(1).getReaction()).isEqualTo(DISLIKE);
    }

    @Test
    void itShouldDeleteCommentReaction() {
        when(commentReactionRepository.findByCommentIdAndUserId(1L, 2L))
                .thenReturn(Optional.of(commentReaction1));
        commentService.deleteCommentReaction(1L, 2L);

        verify(commentReactionRepository, times(1)).delete(commentReaction1);
    }
}