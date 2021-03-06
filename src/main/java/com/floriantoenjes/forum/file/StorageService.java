package com.floriantoenjes.forum.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Path loadThumbnail(String filename);

    Resource loadAsResource(String filename, Function<String, Path> pathFunction);

    void deleteAll();

}
