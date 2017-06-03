package com.floriantoenjes.forum.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("upload")
public class FileController {

    @Autowired
    private StorageService storageService;

    @RequestMapping("/{filename:.+}")
    @ResponseBody
    public Resource getImage(@PathVariable String filename) {
        return storageService.loadAsResource(filename, fn -> storageService.load(fn));
    }

    @RequestMapping("/thumbnails/{filename:.+}")
    @ResponseBody
    public Resource getThumbnail(@PathVariable String filename) {
        return storageService.loadAsResource(filename, fn -> storageService.loadThumbnail(fn));
    }
}
