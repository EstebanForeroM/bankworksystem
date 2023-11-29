package com.bankworksystem.bankworksystem.frameworks.persistency;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.function.Consumer;

public class ImagePersistence {

    private final String IMAGE_DIRECTORY;

    public ImagePersistence(String imageDirectory) {
        IMAGE_DIRECTORY = imageDirectory;
    }

    public void save(Path sourcePath, String clientId) throws IOException {
        String fileExtension = getFileExtension(sourcePath.toUri().toURL());
        if (fileExtension == null) {
            throw new IOException("Unsupported file format or invalid path");
        }

        Path targetPath = Paths.get(IMAGE_DIRECTORY + clientId + fileExtension);

        deleteImage(clientId);

        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public Path searchImageByClientId(String clientId) {
        Path imagePath = findImageFile(clientId);
        if (imagePath != null && Files.exists(imagePath)) {
            return imagePath;
        }
        return null;
    }

    public void deleteImage(String clientId) {
        Path imagePath = findImageFile(clientId);
        if (imagePath != null && Files.exists(imagePath)) {
            try {
                Files.delete(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(URL url) {
        String path = url.getPath();
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return ".jpg";
        } else if (path.endsWith(".png")) {
            return ".png";
        } else if (path.endsWith(".gif")) {
            return ".gif";
        }
        return null;
    }

    private Path findImageFile(String clientId) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(IMAGE_DIRECTORY))) {
            for (Path path : directoryStream) {
                if (path.getFileName().toString().startsWith(clientId)) {
                    return path;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}