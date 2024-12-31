package br.com.psicoclinic.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private String upload_dir;

    public FileStorageConfig(String upload_dir) {
        this.upload_dir = upload_dir;
    }

    public String getUpload_dir() {
        return upload_dir;
    }

    public void setUpload_dir(String upload_dir) {
        this.upload_dir = upload_dir;
    }
}
