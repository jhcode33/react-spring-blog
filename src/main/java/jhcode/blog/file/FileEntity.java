package jhcode.blog.file;

import jakarta.persistence.*;
import jhcode.blog.board.Board;
import jhcode.blog.common.BaseTimeEntity;
import jhcode.blog.file.dto.FileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILE")
@Getter
@NoArgsConstructor
public class FileEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "ORIGIN_FILE_NAME")
    private String originFileName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_PATH")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    public Board board;

    @Builder
    public FileEntity(Long id, String originFileName, String filePath, String fileType) {
        this.id = id;
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public FileDTO toDTO() {
        return FileDTO.builder()
                .id(this.id)
                .originFileName(this.originFileName)
                .filePath(this.filePath)
                .fileType(this.fileType)
                .build();
    }

    public void setMappingBoard(Board board) {
        this.board = board;
        board.getFiles().add(this);
    }
}