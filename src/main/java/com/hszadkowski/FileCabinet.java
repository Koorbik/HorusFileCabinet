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
        return streamAll()
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return streamAll()
                .filter(folder -> folder.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) streamAll().count();
    }

    private Stream<Folder> streamAll() {
        return this.folders.stream().flatMap(this::streamAllRecursive);
    }

    private Stream<Folder> streamAllRecursive(Folder folder) {
        Stream<Folder> selfStream = Stream.of(folder);

        if (folder instanceof MultiFolder) {
            return Stream.concat(selfStream, ((MultiFolder) folder).getFolders().stream()
                    .flatMap(this::streamAllRecursive));
        }
        return selfStream;
    }
}
