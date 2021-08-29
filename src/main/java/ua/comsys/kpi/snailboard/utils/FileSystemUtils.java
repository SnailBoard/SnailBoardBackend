package ua.comsys.kpi.snailboard.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileSystemUtils {

    private FileSystemUtils() {
    }

    public static String saveFile(String path, MultipartFile file) {
        try {
            var fileToSave = new File(path);
            fileToSave.createNewFile();
            saveImage(file.getBytes(), fileToSave);
            return fileToSave.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void saveImage(byte[] image, File fileToSave) throws IOException {
        FileUtils.writeByteArrayToFile(fileToSave, image);
    }

    public static Boolean createDirectoryIfNotExists(String path) {
        var dir = new File(path);
        if (!dir.isDirectory()) {
            return dir.mkdir();
        }
        return true;
    }

    public static void rewriteFile(MultipartFile image, String path) {
        var rewriteFile = new File(path);
        try (var fileStream = new FileOutputStream(rewriteFile, false)) {
            fileStream.write(image.getBytes());
        } catch (IOException ioException) {
            // TODO
        }
    }
}
