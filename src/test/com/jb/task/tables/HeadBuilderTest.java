package com.jb.task.tables;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HeadBuilderTest {

    @Test
    public void testGetFileWrongFileName() {
        Assertions.assertThrows(
                IOException.class,
                () -> HeaderBuilder.getFile("123")
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> HeaderBuilder.getFile(null)
        );
    }

    @Test
    public void testGetFile() {
        try {
            Assertions.assertFalse(HeaderBuilder.getFile("src/test/resources/emptyTest.md").isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Assertions.assertEquals(HeaderBuilder.getFile("src/test/resources/exampleTest.md"),
                    Arrays.asList("# My Project", "## Idea", "content", "## Implementation",
                            "### Step 1", "content", "### Step 2", "content"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmptyTitlesBuildHeader() {
        try {
            Assertions.assertEquals(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/emptyTitleTest.md"))),
                    Arrays.asList("1. [](#)", "\t1. [](#)", "\t2. [](#)", "2. [](#)", "3. [](#)",
                            "4. [](#)", "\t\t1. [](#)", "5. [](#)"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCodeBlocksBuildHeader() {
        try {
            Assertions.assertEquals(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/codeBlocksTest.md"))),
                    Arrays.asList("1. [Title-1](#title-1)", "2. [Title-2](#title-2)",
                            "3. [Title-3](#title-3)", "4. [1](#1)", "5. [Title-5](#title-5)"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmptyFileBuildHeader() {
        try {
            Assertions.assertTrue(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/emptyTest.md"))
            ).isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNoTitlesBuildHeader() {
        Assertions.assertTrue(HeaderBuilder.buildHeader(Arrays.asList("", "asddsa a; ", "123")).isEmpty());
    }

    @Test
    public void testNameWithHashTestBuildHeader() {
        try {
            Assertions.assertEquals(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/nameWithHash.md"))),
                    Arrays.asList("1. [](#)", "2. [#Title](##title)", "\t1. [#title#](##title#)"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBuildHeader() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> HeaderBuilder.buildHeader(null)
        );

        try {
            Assertions.assertEquals(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/exampleTest.md"))),
                    Arrays.asList("1. [My Project](#my-project)", "\t1. [Idea](#idea)",
                            "\t2. [Implementation](#implementation)", "\t\t1. [Step 1](#step-1)",
                            "\t\t2. [Step 2](#step-2)"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testManyHashSymbolsBuildHeader() {
        try {
            Assertions.assertEquals(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/manyHashSymbolsTest.md"))),
                    Arrays.asList("\t\t\t\t\t1. [#r](##r)", "\t\t\t\t\t2. [r](#r)",
                            "\t\t\t\t\t3. [###################r](####################r)"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLongBuildHeader() {
        try {
            Assertions.assertEquals(HeaderBuilder.buildHeader(
                    Files.readAllLines(Paths.get("src/test/resources/longTest.md"))),
                    Arrays.asList("1. [title-1](#title-1)", "\t1. [title-1](#title-1)", "\t\t1. [title-1](#title-1)",
                            "2. [title-2](#title-2)", "\t\t1. [title-1](#title-1)", "3. [title-3](#title-3)",
                            "\t\t1. [title-1](#title-1)", "\t\t2. [title-2](#title-2)", "\t\t3. [title-3](#title-3)",
                            "\t1. [title-1](#title-1)", "\t\t1. [title-1](#title-1)", "\t2. [title-2](#title-2)",
                            "\t3. [title-3](#title-3)", "\t\t1. [title-1](#title-1)", "4. [title-4](#title-4)",
                            "\t\t\t1. [title-1](#title-1)", "5. [title-5](#title-5)", "\t\t\t1. [title-1](#title-1)",
                            "\t\t\t2. [title-2](#title-2)", "\t\t\t3. [title-3](#title-3)", "\t\t\t4. [title-4](#title-4)",
                            "\t1. [title-1](#title-1)", "\t2. [title-2](#title-2)", "\t\t1. [title-1](#title-1)",
                            "\t3. [title-3](#title-3)", "\t\t\t1. [title-1](#title-1)", "\t4. [title-4](#title-4)",
                            "\t5. [title-5](#title-5)", "\t\t\t\t\t1. [#r](##r)", "\t\t\t\t\t2. [r](#r)",
                            "\t\t\t\t\t3. [###################r](####################r)"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
