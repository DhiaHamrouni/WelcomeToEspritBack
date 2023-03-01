package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageServiceInterface extends BaseServiceInterface<FileEntity,Long>{
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
