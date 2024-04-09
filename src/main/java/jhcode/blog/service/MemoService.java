package jhcode.blog.service;

import jakarta.transaction.Transactional;
import jhcode.blog.common.exception.ResourceNotFoundException;
import jhcode.blog.dto.request.board.BoardUpdateDto;
import jhcode.blog.dto.request.board.BoardWriteDto;
import jhcode.blog.dto.request.board.SearchData;
import jhcode.blog.dto.request.memo.MemoWriteDto;
import jhcode.blog.dto.response.board.ResBoardDetailsDto;
import jhcode.blog.dto.response.board.ResBoardListDto;
import jhcode.blog.dto.response.board.ResBoardWriteDto;
import jhcode.blog.dto.response.memo.ResMemoListDto;
import jhcode.blog.entity.Board;
import jhcode.blog.entity.Member;
import jhcode.blog.entity.Memo;
import jhcode.blog.repository.MemberRepository;
import jhcode.blog.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;

    // 페이징 리스트
    public Page<ResMemoListDto> getAllMemos(Pageable pageable) {
        Page<Memo> memos = memoRepository.findAllWithMemberAndComments(pageable);
        List<ResMemoListDto> list = memos.getContent().stream()
                .map(ResMemoListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, memos.getTotalElements());
    }

    // 게시글 검색, isEmpty() == ""
    public Page<ResMemoListDto> search(SearchData searchData, Pageable pageable) {
        Page<Memo> result = null;
        if (!searchData.getTitle().isEmpty()) {
            result = memoRepository.findAllTitleContaining(searchData.getTitle(), pageable);
        } else if (!searchData.getContent().isEmpty()) {
            result = memoRepository.findAllContentContaining(searchData.getContent(), pageable);
        } else if (!searchData.getWriterName().isEmpty()) {
            result = memoRepository.findAllUsernameContaining(searchData.getWriterName(), pageable);
        }
        List<ResMemoListDto> list = result.getContent().stream()
                .map(ResMemoListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    // 게시글 등록
    public ResBoardWriteDto write(MemoWriteDto memoWriteDto, Member member) {

        Board board = BoardWriteDto.ofEntity(memoWriteDto);
        Member writerMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", member.getEmail())
        );
        board.setMappingMember(writerMember);
        Board saveBoard = memoRepository.save(board);
        return ResBoardWriteDto.fromEntity(saveBoard, writerMember.getUsername());
    }

    // 게시글 상세보기
    public ResBoardDetailsDto detail(Long boardId) {
       Board findBoard = memoRepository.findByIdWithMemberAndCommentsAndFiles(boardId).orElseThrow(
               () -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(boardId))
       );
       // 조회수 증가
       findBoard.upViewCount();
       return ResBoardDetailsDto.fromEntity(findBoard);
    }

    // 게시글 수정
    public ResBoardDetailsDto update(Long boardId, BoardUpdateDto boardDTO) {
        Board updateBoard = memoRepository.findByIdWithMemberAndCommentsAndFiles(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(boardId))
        );
        updateBoard.update(boardDTO.getTitle(), boardDTO.getContent());
        return ResBoardDetailsDto.fromEntity(updateBoard);
    }

    // 게시글 삭제
    public void delete(Long boardId) {
        memoRepository.deleteById(boardId);
    }
}
