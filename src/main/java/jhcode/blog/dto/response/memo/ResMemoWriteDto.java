package jhcode.blog.dto.response.memo;

import jhcode.blog.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Response-
 * 게시글 등록 반환 정보
 */

@Getter
@Setter
@NoArgsConstructor
public class ResMemoWriteDto {

    private Long memoId;
    private String title;
    private String content;
    private String writerName;
    private String createdDate;

    @Builder
    public ResMemoWriteDto(Long memoId, String title, String content, String writerName, String createdDate) {
        this.memoId = memoId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.createdDate = createdDate;
    }

    public static ResMemoWriteDto fromEntity(Memo memo, String writerName) {
        return ResMemoWriteDto.builder()
                .memoId(memo.getId())
                .title(memo.getTitle())
                .content(memo.getContent())
                .writerName(writerName)
                .createdDate(memo.getCreatedDate())
                .build();
    }
}
