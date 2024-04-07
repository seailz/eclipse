package com.seailz.eclipse.storage;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemStorage implements StorageSystem {

    public static String STORAGE_PATH = "eclipse/tasks/";

    @Override
    public long getLastRun(String taskId) {
        JSONObject json = null;
        try {
            json = new JSONObject(readFile(STORAGE_PATH + taskId + ".json"));
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }

        return json.getLong("lastRun");
    }

    @Override
    public void setLastRun(String taskId, long time) {
        JSONObject json = new JSONObject();
        json.put("lastRun", time);
        try {
            saveFile(json.toString(), STORAGE_PATH + taskId + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeLastRun(String taskId) {
        try {
            Files.delete(Path.of(STORAGE_PATH + taskId + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean taskExists(String taskId) {
        return Files.exists(Path.of(STORAGE_PATH + taskId + ".json"));
    }


    private void saveFile(String content, String path) throws IOException {
        Path pathObject = Path.of(path);
        Files.createDirectories(pathObject.getParent());
        Files.write(pathObject, content.getBytes());
    }

    private String readFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
