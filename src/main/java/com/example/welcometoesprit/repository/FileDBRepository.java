package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.FileEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends BaseRepository<FileEntity,Long> {
}
