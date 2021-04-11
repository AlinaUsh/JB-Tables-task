package com.jb.task.tables;

import org.jetbrains.annotations.NotNull;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class HeaderBuilder {
    private HeaderBuilder() {
    }

    public static List<String> getFile(@NotNull String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private static boolean checkIfLineIsCode(String line) {
        int numberOfSpaces = 0;
        while (numberOfSpaces < line.length() && line.charAt(numberOfSpaces) == ' ' && numberOfSpaces < 4) {
            numberOfSpaces++;
        }
        return numberOfSpaces >= 4;
    }

    private static String formatLine(String line) {
        return line.replaceAll("\\s", "-").toLowerCase();
    }

    private static String newHeaderString(String line, int tabsNumber, int numberInList) {
        StringBuilder newHeaderLineStr = new StringBuilder();
        for (int i = 0; i < tabsNumber - 1; i++) {
            newHeaderLineStr.append("\t");
        }
        line = line.trim().substring(tabsNumber).trim();
        newHeaderLineStr.append(numberInList + ". [" + line + "](#");
        newHeaderLineStr.append(HeaderBuilder.formatLine(line));
        newHeaderLineStr.append(")");
        return newHeaderLineStr.toString();
    }

    public static List<String> buildHeader(@NotNull List<String> fileContent) {
        List<String> header = new LinkedList();
        Stack<Point> subtitlesStack = new Stack();
        int lastSubtitleType = 0;
        for (String line : fileContent) {
            if (checkIfLineIsCode(line)) {
                continue;
            }
            line = line.trim();
            int numberOfHash = 0;
            while (numberOfHash < line.length() && numberOfHash < 6 && line.charAt(numberOfHash) == '#') {
                numberOfHash++;
            }
            if (numberOfHash == 0) {
                continue;
            }
            //add title to current list
            if (numberOfHash == lastSubtitleType) {
                int lastSubtitleNubmer = subtitlesStack.pop().x + 1;
                subtitlesStack.push(new Point(lastSubtitleNubmer, numberOfHash));
                header.add(newHeaderString(line, numberOfHash, lastSubtitleNubmer));
            }

            //start new list
            if (numberOfHash > lastSubtitleType) {
                subtitlesStack.push(new Point(1, numberOfHash));
                lastSubtitleType = numberOfHash;

                header.add(newHeaderString(line, numberOfHash, 1));
            }

            //finish current list and get back to the previous one
            if (numberOfHash < lastSubtitleType) {
                while (subtitlesStack.peek().y > numberOfHash) {
                    subtitlesStack.pop();
                }
                int lastSubtitleNubmer;
                if (subtitlesStack.empty() || subtitlesStack.peek().y != numberOfHash) {
                    lastSubtitleNubmer = 1;
                    subtitlesStack.push(new Point(lastSubtitleNubmer, numberOfHash));
                } else {
                    lastSubtitleNubmer = subtitlesStack.pop().x + 1;
                    subtitlesStack.push(new Point(lastSubtitleNubmer, numberOfHash));
                }
                lastSubtitleType = numberOfHash;

                header.add(newHeaderString(line, numberOfHash, lastSubtitleNubmer));
            }
        }
        return header;
    }
}