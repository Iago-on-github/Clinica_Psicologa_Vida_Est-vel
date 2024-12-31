package br.com.psicoclinic.Service;

import br.com.psicoclinic.Config.FileStorageConfig;
import br.com.psicoclinic.Exceptions.FileStorageException;
import br.com.psicoclinic.Exceptions.MyFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    public FileStorageService(FileStorageConfig fileStorageConfig) {

        this.fileStorageLocation = Paths.get(fileStorageConfig.getUpload_dir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the upload files will be stored!", e);
        }
    }

    public String fileStorage(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) throw new FileStorageException("Sorry. Your file contains invalid path sequence. " + filename);
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Could not storage file " + filename + ". Try again.", e);
        }
    }

    public Resource loadFileAsResources(String filename) {
        try {
            Path path = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) return resource;
            else throw new MyFileNotFoundException("File not found");
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found, " + filename, e.getCause());
        }
    }
}
