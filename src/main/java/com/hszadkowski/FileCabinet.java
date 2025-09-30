package com.hszadkowski;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders != null ? folders : new ArrayList<>();
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return folders.stream()
                .map(folder -> findFolderByNameRecursive(folder, name))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return folders.stream()
                .flatMap(folder -> findFoldersBySizeRecursive(folder, size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return folders.stream()
                .mapToInt(this::countRecursive)
                .sum();
    }

    private Optional<Folder> findFolderByNameRecursive(Folder currentFolder, String name) {
        if (currentFolder.getName().equals(name)) {
            return Optional.of(currentFolder);
        }
        if (currentFolder instanceof MultiFolder) {
            return ((MultiFolder) currentFolder).getFolders().stream()
                    .map(subFolder -> findFolderByNameRecursive(subFolder, name))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();
        }
        return Optional.empty();
    }

    private Stream<Folder> findFoldersBySizeRecursive(Folder currentFolder, String size) {
        Stream<Folder> selfStream = currentFolder.getSize().equals(size)
                ? Stream.of(currentFolder)
                : Stream.empty();

        Stream<Folder> childrenStream = Stream.empty();
        if (currentFolder instanceof MultiFolder) {
            childrenStream = ((MultiFolder) currentFolder).getFolders().stream()
                    .flatMap(subFolder -> findFoldersBySizeRecursive(subFolder, size));
        }

        return Stream.concat(selfStream, childrenStream);
    }

    private int countRecursive(Folder currentFolder) {
        int childrenCount = 0;
        if (currentFolder instanceof MultiFolder) {
            childrenCount = ((MultiFolder) currentFolder).getFolders().stream()
                    .mapToInt(this::countRecursive)
                    .sum();
        }
        return 1 + childrenCount;
    }
}
