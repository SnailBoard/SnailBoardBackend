package ua.comsys.kpi.snailboard.images.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.comsys.kpi.snailboard.utils.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImagesService {
    private final String DIRECTORY_TO_SAVE = "snailboard_images/";

    public String saveImages(MultipartFile image) {
        if (Boolean.TRUE.equals(FileSystemUtils.createDirectoryIfNotExists(DIRECTORY_TO_SAVE))) {
            var id = UUID.randomUUID();
            String path = DIRECTORY_TO_SAVE + id + ".jpg";
            FileSystemUtils.saveFile(path, image);
            return id.toString();
        }  // TODO THROW EXCEPTION
        return "";
    }

    public String change(MultipartFile image, String oldImageName) {
        String path = DIRECTORY_TO_SAVE + oldImageName + ".jpg";
        FileSystemUtils.rewriteFile(image, path);
        return oldImageName + ".jpg";
    }

    public String getLocation() {
        return new File(DIRECTORY_TO_SAVE).getAbsolutePath() + "/";
    }
}
