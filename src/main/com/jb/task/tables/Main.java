package com.jb.task.tables;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = args[0];
        List<String> fileContent = null;
        try {
            fileContent = HeaderBuilder.getFile(filePath);
        } catch (IOException e) {
            System.out.println("Failed to read file " + filePath);
            System.out.println(e.getMessage());
        }
        if (fileContent != null) {
            List<String> header = HeaderBuilder.buildHeader(fileContent);
            for (String line : header) {
                System.out.println(line);
            }
            System.out.println();
            for (String line : fileContent) {
                System.out.println(line);
            }
        }
    }
}

