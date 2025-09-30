package com.hszadkowski;

import java.util.ArrayList;
import java.util.List;

public class Directory implements MultiFolder {
    private String name;
    private String size;
    private List<Folder> subFolders = new ArrayList<>();

    public Directory(String name, String size) {
        this.name = name;
        this.size = size;
    }

    public void addFolder(Folder folder) {
        subFolders.add(folder);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return this.size;
    }

    @Override
    public List<Folder> getFolders() {
        return this.subFolders;
    }

    @Override
    public String toString() {
        return "Directory{name='" + name + "', size='" + size + "', contains=" + subFolders.size() + " items}";
    }
}
