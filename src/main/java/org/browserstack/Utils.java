package org.browserstack;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Utils {

    public static String saveImage(String imageUrl, String title) throws IOException {
        String fileName = title.replaceAll("\\s+", "_") + ".jpg";
        Path filePath = Paths.get(fileName);

        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }
}
