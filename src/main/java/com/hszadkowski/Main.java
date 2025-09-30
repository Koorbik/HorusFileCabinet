package com.hszadkowski;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

        List<Folder> rootFolders = new ArrayList<>();
        rootFolders.add(documentsDir);
        rootFolders.add(photosDir);
        rootFolders.add(configFile);

        FileCabinet cabinet = new FileCabinet(rootFolders);

        // --- Initial Testing ---

        // Test 1: Count all folders and files
        System.out.println("--- Test count() method ---");
        int totalCount = cabinet.count();
        // Expected result: 9
        System.out.println("Total number of all folders and files: " + totalCount);
        System.out.println();

        // Test 2: Find folders by size
        System.out.println("--- Test findFoldersBySize() method ---");
        System.out.println("Folders with size 'SMALL':");
        cabinet.findFoldersBySize("SMALL").forEach(System.out::println);
        System.out.println("\nFolders with size 'LARGE':");
        cabinet.findFoldersBySize("LARGE").forEach(System.out::println);
        System.out.println();

        // Test 3: Find folder by name
        System.out.println("--- Test findFolderByName() method ---");
        System.out.println("Searching for 'Source.java': " + cabinet.findFolderByName("Source.java"));
        System.out.println("Searching for 'NonExistentFile.txt': " + cabinet.findFolderByName("NonExistentFile.txt"));
    }

}