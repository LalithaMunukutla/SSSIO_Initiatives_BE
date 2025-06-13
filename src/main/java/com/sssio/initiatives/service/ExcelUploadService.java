package com.sssio.initiatives.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelUploadService {

    public void processExcel(MultipartFile file) throws IOException;
}
