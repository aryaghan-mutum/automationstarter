package com.automationstarter.utils;

import io.qameta.allure.Step;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

public class IoUtil {
    
    private static final Logger LOGGER = getLogger(IoUtil.class);
    private static final String RESPONSE_DIR_PATH = "responses";
    
    /**
     * If the directory: 'logs' doesn't exist, then  create a logs directory
     */
    public static void createDir(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    
    @Step("initialize directory to store the service document before the tests are run")
    private static void initializeFolder() {
        try {
            IoUtil.initFolder(RESPONSE_DIR_PATH);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
    
    /**
     * If the directory: 'logs' exists, then delete all the existing files
     * If the directory: 'logs' doesn't exist, then  create a logs directory
     */
    public static void initFolder(String path) throws IOException {
        
        final Path folderPath = Paths.get(path);
        
        if (Files.exists(folderPath)) {
            //Delete all files
            Files.walk(folderPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } else {
            Files.createDirectory(folderPath);
        }
    }
    
    /**
     * If the path is not empty then create a path
     */
    public static String createPath(String path, String fieldName) {
        if (isNotEmpty(path)) {
            return String.format("%s.%s", path, fieldName);
        } else {
            return fieldName;
        }
    }
    
    /**
     * If the path is not empty then update the path
     */
    public static String updatePath(String path, String filterPath) {
        if (isNotEmpty(path)) {
            return path.substring(0, path.indexOf(filterPath))
                    + path.substring(path.indexOf(filterPath) + filterPath.length());
        } else {
            return path;
        }
    }
    
    /**
     * If the path is not empty and path's index is not equal = -1 then return true
     */
    public static boolean isPrintNode(String currentPath, String filterPath) {
        return (isNotEmpty(currentPath) && currentPath.indexOf(filterPath) != -1);
    }
    
}
