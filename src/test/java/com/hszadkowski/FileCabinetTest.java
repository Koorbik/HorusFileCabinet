package com.hszadkowski;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileCabinetTest {
    private FileCabinet emptyCabinet;
    private FileCabinet cabinetWithContent;

    @BeforeEach
    void setUp() {
        emptyCabinet = new FileCabinet(Collections.emptyList());

        Directory documentsDir = new Directory("Documents", "LARGE");
        documentsDir.addFolder(new SimpleFolder("Report.doc", "MEDIUM"));

        Directory projectDir = new Directory("Project", "MEDIUM");
        projectDir.addFolder(new SimpleFolder("Source.java", "SMALL"));
        projectDir.addFolder(new SimpleFolder("Readme.md", "SMALL"));
        documentsDir.addFolder(projectDir);

        Directory photosDir = new Directory("Photos", "LARGE");
        photosDir.addFolder(new SimpleFolder("Holidays.jpg", "MEDIUM"));

        cabinetWithContent = new FileCabinet(List.of(documentsDir, photosDir, new SimpleFolder("config.sys", "SMALL")));
    }

    @Test
    @DisplayName("count() should return 0 for an empty cabinet")
    void count_shouldReturnZeroForEmptyCabinet() {
        assertEquals(0, emptyCabinet.count());
    }

    @Test
    @DisplayName("count() should correctly count all folders in a nested structure")
    void count_shouldCountAllFoldersInNestedStructure() {
        assertEquals(8, cabinetWithContent.count());
    }

    @Test
    @DisplayName("findFolderByName() should find a top-level folder")
    void findFolderByName_shouldFindTopLevelFolder() {
        Optional<Folder> found = cabinetWithContent.findFolderByName("config.sys");

        assertTrue(found.isPresent());
        assertEquals("config.sys", found.get().getName());
    }

    @Test
    @DisplayName("findFolderByName() should find a deeply nested folder")
    void findFolderByName_shouldFindNestedFolder() {
        Optional<Folder> found = cabinetWithContent.findFolderByName("Source.java");

        assertTrue(found.isPresent());
        assertEquals("Source.java", found.get().getName());
        assertEquals("SMALL", found.get().getSize());
    }

    @Test
    @DisplayName("findFolderByName() should return empty optional for a non-existent folder")
    void findFolderByName_shouldReturnEmptyWhenFolderDoesNotExist() {
        Optional<Folder> found = cabinetWithContent.findFolderByName("NonExistent.file");

        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("findFoldersBySize() should find all folders of a given size")
    void findFoldersBySize_shouldFindAllFoldersOfSize() {
        List<Folder> smallFolders = cabinetWithContent.findFoldersBySize("SMALL");

        assertEquals(3, smallFolders.size());
        List<String> names = smallFolders.stream().map(Folder::getName).toList();
        assertTrue(names.containsAll(List.of("Source.java", "Readme.md", "config.sys")));
    }

    @Test
    @DisplayName("findFoldersBySize() should return an empty list if no folders match size")
    void findFoldersBySize_shouldReturnEmptyListWhenNoFoldersMatch() {
        List<Folder> tinyFolders = cabinetWithContent.findFoldersBySize("TINY");
        assertTrue(tinyFolders.isEmpty());
    }
}