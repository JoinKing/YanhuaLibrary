package com.yanhua.mvvmlibrary.utils.searchfile;

import android.databinding.Bindable;
import android.databinding.Observable;

/**
 *
 *  Created by king on 2019.1.17
 */
public class FileBean implements Observable {
    private String name;
    private String fileType;
    private String path;
    private long size;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
    @Bindable
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}
