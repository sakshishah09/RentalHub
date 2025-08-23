package com.rental.sys.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

    private static final String BASE_REPO_DIRECTORY_PATH = "./storage";
    private static final String FILE_PATH_FORMAT = "{0}/{1}/{2}.{3}";

    @Value("${app.max-file-size:5242880}") // default 5MB
    private long fileMaxSize;

    @PostConstruct
    public void init() {
        File file = new File(BASE_REPO_DIRECTORY_PATH);
        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("Base storage directory created successfully");
            } else {
                log.warn("Failed to create base storage directory");
            }
        } else {
            log.info("Base storage directory already exists");
        }
        log.info("Max file size allowed: {} bytes", fileMaxSize);
    }

    /**
     * Save a MultipartFile into given sub-directory with unique filename.
     *
     * @param file          uploaded MultipartFile
     * @param directoryName sub-directory (e.g., "profilePics", "productImages")
     * @return relative file path
     */
    public String saveFile(MultipartFile file, String directoryName) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot save empty file");
        }

        if (file.getSize() > fileMaxSize) {
            throw new IOException("File exceeds max size of " + fileMaxSize + " bytes");
        }

        // Extract extension
        String originalName = file.getOriginalFilename();
        String extension = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf(".") + 1)
                : "dat";

        // Create sub-directory
        File dir = new File(BASE_REPO_DIRECTORY_PATH, directoryName);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Could not create directory: " + dir.getAbsolutePath());
        }

        // Build unique file path
        String filePath = MessageFormat.format(FILE_PATH_FORMAT,
                BASE_REPO_DIRECTORY_PATH,
                directoryName,
                UUID.randomUUID().toString(),
                extension);

        // Save
        file.transferTo(new File(filePath));

        log.info("File saved at: {}", filePath);
        return filePath;
    }
}
