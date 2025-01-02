package utils;

import com.google.gson.Gson;
import models.LocatorModel;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocatorManager {
    private static final Map<String, By> locatorsMap = new HashMap<>();

    public LocatorManager() {
        loadLocatorsFromJsonFiles();
    }

    private void loadLocatorsFromJsonFiles() {
        String resourcesPath = "src/test/resources/locators";
        File folder = new File(resourcesPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Folder does not exist or is not a directory: " + resourcesPath);
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files == null) {
            throw new RuntimeException("Failed to list files in folder: " + resourcesPath);
        }

        Gson gson = new Gson();
        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                LocatorModel[] locatorData = gson.fromJson(reader, LocatorModel[].class);

                for (LocatorModel entry : locatorData) {
                    String key = entry.getKey();
                    String value = entry.getLocator();
                    String locatorType = entry.getType();

                    By by = getByFromType(locatorType, value);
                    locatorsMap.put(key, by);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private By getByFromType(String type, String value) {
        switch (type.toLowerCase()) {
            case "id":
                return By.id(value);
            case "css":
                return By.cssSelector(value);
            case "xpath":
                return By.xpath(value);
            case "classname":
                return By.className(value);
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + type);
        }
    }

    public static By getLocator(String key) {
        return locatorsMap.get(key);
    }
}