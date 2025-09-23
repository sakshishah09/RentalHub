package com.rental.sys.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final String STORAGE_PATH = "./storage/productImages/";

    public String saveFile(MultipartFile file, String folder) throws IOException {
        // Build path
        String folderPath = STORAGE_PATH + folder;
        File directory = new File(folderPath);

        // ✅ Create folder if it doesn’t exist
        if (!directory.exists()) {
            directory.mkdirs();  // creates parent + subdirectories
        }

        // Generate unique file name
        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        // Final file path
        File destination = new File(directory, uniqueFileName);

        // Save the file
        file.transferTo(destination);

        // Return relative path or absolute path
        return destination.getAbsolutePath();
    }
}