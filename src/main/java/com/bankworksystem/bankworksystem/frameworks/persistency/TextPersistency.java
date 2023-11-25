package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Identifiable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TextPersistency<T extends Identifiable> {

    private final String fullFilePath;
    private final Serializer<T> objectSerializer;
    private final List<Runnable> changeListeners = new ArrayList<>();
    private final Map<String, String> objects = new HashMap<>();

    public TextPersistency(String directoryPath, String fileName, Serializer<T> objectSerializer) {
        this.objectSerializer = Objects.requireNonNull(objectSerializer, "Serializer cannot be null");
        this.fullFilePath = Paths.get(directoryPath, fileName).toString();

        createFileIfNotExists();
        loadDataFromFile();
    }

    private void createFileIfNotExists() {
        File file = new File(fullFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("Error creating file");
            }
        }
    }

    private void loadDataFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fullFilePath));
            lines.stream()
                    .filter(line -> !line.trim().isEmpty())
                    .forEach(this::processLine);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data from file " + fullFilePath, e);
        }
    }

    private void processLine(String line) {
        String[] objectData = line.split(",");
        if (objectData.length >= 2) {
            objects.put(objectData[1], line);
        }
    }

    public void saveObject(T object) {
        Objects.requireNonNull(object, "Object cannot be null");
        objects.put(object.getId(), objectSerializer.serialize(object));
        saveObjectsToFile();
        notifyChangeListeners();
    }

    public T getObject(String id) {
        String serializedObject = objects.get(id);
        return serializedObject != null ? objectSerializer.deserialize(serializedObject) : null;
    }

    public void deleteObject(String id) {
        objects.remove(id);
        saveObjectsToFile();
        notifyChangeListeners();
    }

    public void updateObject(String id, T updatedObject) {
        Objects.requireNonNull(updatedObject, "Updated object cannot be null");
        saveObject(updatedObject);
    }

    private void saveObjectsToFile() {
        try (FileWriter fileWriter = new FileWriter(fullFilePath, false)) {
            for (String serializedObject : objects.values()) {
                fileWriter.write(serializedObject + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save objects to file " + fullFilePath, e);
        }
    }

    public Set<T> getObjects() {
        Set<T> objectSet = new HashSet<>();
        objects.values().forEach(serializedString -> objectSet.add(objectSerializer.deserialize(serializedString)));
        return objectSet;
    }

    public void addChangeListener(Runnable callback) {
        changeListeners.add(Objects.requireNonNull(callback, "Callback cannot be null"));
    }

    private void notifyChangeListeners() {
        changeListeners.forEach(Runnable::run);
    }
}
