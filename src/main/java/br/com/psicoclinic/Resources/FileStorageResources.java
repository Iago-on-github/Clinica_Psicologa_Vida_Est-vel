package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Data.UploadFileDto;
import br.com.psicoclinic.Service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileStorageResources {
    private final FileStorageService fileStorageService;

    public FileStorageResources(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileDto uploadFile(@RequestParam("file")MultipartFile file) {
        var filename = fileStorageService.fileStorage(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile")
                .path(filename)
                .toUriString();
        return new UploadFileDto(filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultiplesFiles")
    public List<UploadFileDto> uploadMultiplesFiles(@RequestParam("files")MultipartFile[] files) {
        return Arrays.stream(files).map(this::uploadFile).toList();
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResources(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Could not determine file type");
        }

        if (contentType.isBlank()) contentType = "application/octet-stream";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "/")
                .body(resource);
    }
}
