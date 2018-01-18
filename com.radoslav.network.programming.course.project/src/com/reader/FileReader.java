package com.reader;

import com.exception.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    private static final String FILE_PATH = "/home/raisug/IdeaProjects/com.radoslav.network.programming.course.project/src/com/web/resources";
    private String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public String readContent() {
        try {
            StringBuilder builder = new StringBuilder();

            List<String> fileLines = Files.readAllLines(Paths.get(constructPath()));
            for (String fileLine : fileLines) {
                builder.append(fileLine);
            }

            return builder.toString();
        } catch (IOException e) {
            throw new ApplicationException("Failed to read file content", e);
        }
    }

    private String constructPath() {
        if (fileName.endsWith(".html")) {
            return constructHtmlPath();
        } else if (fileName.endsWith(".css")) {
            return constructCssPath();
        }

        return constructJsPath();
    }

    private String constructCssPath() {
        if (fileName.startsWith("/")) {
            return FILE_PATH + "/css" + fileName;
        }

        return FILE_PATH + "/css/" + fileName;
    }

    private String constructHtmlPath() {
        if (fileName.startsWith("/")) {
            return FILE_PATH + "/html" + fileName;
        }

        return FILE_PATH + "/html/" + fileName;
    }

    private String constructJsPath() {
        if (fileName.startsWith("/")) {
            return FILE_PATH + "/js" + fileName;
        }

        return FILE_PATH + "/js/" + fileName;
    }
}
