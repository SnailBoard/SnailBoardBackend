package ua.comsys.kpi.snailboard.utils.files;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileManager {

    public static String readPropFile(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }
}
