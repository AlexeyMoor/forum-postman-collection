package ait.cohort70.forum.dao;

import ait.cohort70.forum.model.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<AttachedFile, Long> {
}
