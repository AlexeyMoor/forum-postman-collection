package ait.cohort70.forum.controller;

import ait.cohort70.forum.model.AttachedFile;
import ait.cohort70.forum.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        AttachedFile file = fileService.getFile(fileId);
        String contentType = file.getContentType() != null
                ? file.getContentType()
                : "application/octet-stream"; // default binary type
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(file.getContent());
    }
}
