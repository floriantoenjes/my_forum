package com.floriantoenjes.forum.file;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
    private String thumbnailsLocation = "upload-dir/thumbnails";


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThumbnailsLocation() {
        return thumbnailsLocation;
    }

    public void setThumbnailsLocation(String thumbnailsLocation) {
        this.thumbnailsLocation = thumbnailsLocation;
    }
}
