package ait.cohort70.forum.dao;

import ait.cohort70.forum.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
}
