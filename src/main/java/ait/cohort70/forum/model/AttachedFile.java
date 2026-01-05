package ait.cohort70.forum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "files")
@NoArgsConstructor
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Setter
    @Column(name = "file_name")
    private String fileName;

    @Setter
    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_data")
    @Lob
    @Setter
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private Post post;

    public AttachedFile(String fileName, String contentType, byte[] content) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
    }
}
