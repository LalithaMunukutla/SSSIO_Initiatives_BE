package com.sssio.initiatives.controller;

import com.sssio.initiatives.service.ExcelUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelUploadController {

    @Autowired
    private ExcelUploadService excelUploadService;

    @PostMapping("/upload/excel")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file){
        try {
            if(file.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Upload a file");
            }
            excelUploadService.processExcel(file);
            return ResponseEntity.ok("Upload successful");
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
