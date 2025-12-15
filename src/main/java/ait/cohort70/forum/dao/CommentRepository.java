package ait.cohort70.forum.dao;

import ait.cohort70.forum.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
