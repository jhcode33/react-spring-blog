package jhcode.blog.dto.response.file;

import jhcode.blog.entity.FileEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Response-
 * 파일 업로드 후 응답 dto
 */

@Getter
@Setter
@NoArgsConstructor
public class ResFileUploadDto {

    private Long fileId;
    private String originFileName;
    private String filePath;
    private String fileType;
    private String fileName;

    public String getFileName() {
        return this.originFileName = originFileName;
    }
    @Builder
    public ResFileUploadDto(Long fileId, String originFileName, String filePath, String fileType) {
        this.fileId = fileId;
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public static ResFileUploadDto fromEntity(FileEntity file) {
        return ResFileUploadDto.builder()
                .fileId(file.getId())
                .originFileName(file.getOriginFileName())
                .filePath(file.getFilePath())
                .fileType(file.getFileType())
                .build();
    }
}
