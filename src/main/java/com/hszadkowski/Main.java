package com.hszadkowski;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileCabinet cabinet = createSampleCabinet();

        System.out.println("Folder structure has been created.");
        System.out.println("----------------------------------------");
        System.out.println("FileCabinet class features demonstration:");
        System.out.println("----------------------------------------");

        demonstrateCount(cabinet);
        demonstrateFindBySize(cabinet, "LARGE");
        demonstrateFindByName(cabinet, "Source.java");
        demonstrateFindByName(cabinet, "archive.zip");
    }

    private static FileCabinet createSampleCabinet() {
        Directory projectDir = new Directory("Project", "MEDIUM");
        projectDir.addFolder(new SimpleFolder("Source.java", "SMALL"));
        projectDir.addFolder(new SimpleFolder("Readme.md", "SMALL"));

        Directory documentsDir = new Directory("Documents", "LARGE");
        documentsDir.addFolder(new SimpleFolder("Report.doc", "MEDIUM"));
        documentsDir.addFolder(projectDir);

        Directory photosDir = new Directory("Photos", "LARGE");
        photosDir.addFolder(new SimpleFolder("Holidays.jpg", "MEDIUM"));
        photosDir.addFolder(new SimpleFolder("Christmas.png", "LARGE"));

        SimpleFolder configFile = new SimpleFolder("config.sys", "SMALL");

        List<Folder> rootFolders = List.of(documentsDir, photosDir, configFile);
        return new FileCabinet(rootFolders);
    }

    private static void demonstrateCount(Cabinet cabinet) {
        System.out.println("\n>>> Example 1: Counting all elements");
        int totalCount = cabinet.count();
        System.out.println("    Result: There are " + totalCount + " elements in the structure.");
    }

    private static void demonstrateFindBySize(Cabinet cabinet, String size) {
        System.out.println("\n>>> Example 2: Finding elements of size '" + size + "'");
        List<Folder> foundFolders = cabinet.findFoldersBySize(size);
        System.out.println("    Result: Found " + foundFolders.size() + " matching elements:");
        foundFolders.forEach(folder -> System.out.println("      - " + folder));
    }

    private static void demonstrateFindByName(Cabinet cabinet, String name) {
        System.out.println("\n>>> Example 3: Finding element by name '" + name + "'");
        cabinet.findFolderByName(name)
                .ifPresentOrElse(
                        folder -> System.out.println("    Result: Found -> " + folder),
                        () -> System.out.println("    Result: No element found with that name.")
                );
    }
}
