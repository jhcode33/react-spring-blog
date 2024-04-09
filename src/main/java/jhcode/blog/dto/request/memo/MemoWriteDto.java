package jhcode.blog.dto.request.memo;

import jhcode.blog.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request-
 * 게시글 등록 정보 요청, 작성자는 Authentication 받음
 */

@Getter
@Setter
@NoArgsConstructor
public class MemoWriteDto {

    private String title;
    private String content;

    public MemoWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public static Board ofEntity(MemoWriteDto dto) {
        return Board.builder()
                .title(dto.title)
                .content(dto.content)
                .build();
    }
}
