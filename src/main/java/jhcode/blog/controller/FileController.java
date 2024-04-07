package jhcode.blog.controller;

import jhcode.blog.service.FileService;
import jhcode.blog.dto.response.file.ResFileDownloadDto;
import jhcode.blog.dto.response.file.ResFileUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<List<ResFileUploadDto>> upload(
            @PathVariable Long boardId,
            @RequestParam("file") List<MultipartFile> files) throws IOException {
        List<ResFileUploadDto> saveFile = fileService.upload(boardId, files);

        for (MultipartFile file : files) {
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            if (isAllowedExtension(fileExtension)) {

            } else {

            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saveFile);
    }

    private boolean isAllowedExtension(String extension) {

        return true;

    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download (
            @RequestParam("fileId") Long fileId) throws IOException {
        ResFileDownloadDto downloadDto = fileService.download(fileId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(downloadDto.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + downloadDto.getFilename() + "\"")
                .body(new ByteArrayResource(downloadDto.getContent()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete (
            @RequestParam("fileId") Long fileId) {
        fileService.delete(fileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
