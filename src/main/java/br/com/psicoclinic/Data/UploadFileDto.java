package br.com.psicoclinic.Data;

public record UploadFileDto(String filename,
                            String fileDownloadUri,
                            String fileType,
                            long size) {
}
