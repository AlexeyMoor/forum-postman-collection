package ait.cohort70.security;

import ait.cohort70.forum.dao.PostRepository;
import ait.cohort70.forum.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomWebSecurity {
    private final PostRepository postRepository;

    public boolean isPostOwner(String username, String postId) {
        try {
            Long id = Long.parseLong(postId);
            Post post = postRepository.findById(id).orElse(null);
            return post != null && post.getAuthor().equalsIgnoreCase(username);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
