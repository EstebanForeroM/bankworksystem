package com.bankworksystem.bankworksystem.frameworks.persistency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImagePersistenceTest {

    private ImagePersistence imagePersistence;
    @BeforeEach
    void setUp() {
        imagePersistence = new ImagePersistence("src/test/resources/images/");
    }

    @Test
    void save() throws IOException {

        Path path = Path.of("C:/Users/esteb/Downloads/rolling.png");

        imagePersistence.save(path, "123");

        Path path2 = imagePersistence.searchImageByClientId("123");

        assertEquals(Path.of("src\\test\\resources\\images\\123.png"), path2);

        imagePersistence.deleteImage("123");

        Path path3 = imagePersistence.searchImageByClientId("123");

        assert path3 == null;
    }

    @Test
    void searchImageByClientId() {
    }

    @Test
    void deleteImage() {
    }
}