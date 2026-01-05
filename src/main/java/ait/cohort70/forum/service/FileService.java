package ait.cohort70.forum.service;

import ait.cohort70.forum.dao.FileRepository;
import ait.cohort70.forum.dto.FileResponseDto;
import ait.cohort70.forum.model.AttachedFile;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public AttachedFile getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<FileResponseDto> getPostFiles(Long postId) {
        List<AttachedFile> files = fileRepository.findAllByPostId(postId);

        return files.stream()
                .map(file -> new FileResponseDto(
                        file.getFileName(),
                        "/forum/files/" + file.getId() + "/download"
                ))
                .toList();
    }
}
