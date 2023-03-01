package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.FilesStorageServiceInterface;
import com.example.welcometoesprit.entities.FileEntity;
import com.example.welcometoesprit.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileStorageService extends BaseServiceImp<FileEntity,Long> implements FilesStorageServiceInterface {


    @Autowired
    private FileDBRepository fileDBRepository;

    public FileEntity store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity FileDB = new FileEntity(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public FileEntity getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }


    @Override
    public void init() {

    }

    @Override
    public void save(MultipartFile file) {

    }

    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }
}
