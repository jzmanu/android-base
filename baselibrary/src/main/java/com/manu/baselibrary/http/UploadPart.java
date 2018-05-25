package com.manu.baselibrary.http;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPart {

    private MediaType mediaType = MediaType.parse("application/octet-stream");
    private File file;
    private String fileName;
    private byte[] content;

    public UploadPart(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public UploadPart(String filePath) {
        this.file = new File(filePath);
        this.fileName = file.getName();
    }

    public UploadPart(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    protected void addToBuilder(String name, MultipartBody.Builder builder) {
        if (file != null) {
            builder.addFormDataPart(name, fileName, RequestBody.create(mediaType, file));
        }
        if (content != null) {
            builder.addFormDataPart(name, fileName, RequestBody.create(mediaType, content));
        }
    }
}
